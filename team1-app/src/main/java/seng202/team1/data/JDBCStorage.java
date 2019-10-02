package seng202.team1.data;

import org.joda.money.BigMoney;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.model.Recipe;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.OrderStatus;
import seng202.team1.util.UnitType;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class JDBCStorage implements FoodItemDAO, OrderDAO {


    private static String url = "jdbc:sqlite:rosemary.db";
    private static JDBCStorage instance;

    private JDBCStorage() {
        createAllTables();
    }

    /**
     * function used to get an instance of a InMemoryDAO object.
     *Implements the singleton pattern
     * @return an instance of InMemoryDAO
     */
    public static JDBCStorage getInstance() {
        if (instance == null) {
            instance = new JDBCStorage();
        }
        return instance;
    }

    public void resetInstance() {
        String sql = "DELETE FROM FoodItem;\n" +
                "DELETE FROM Recipe;\n" +
                "DELETE FROM RecipeContains;\n" +
                "DELETE FROM CustomerOrder;\n" +
                "DELETE FROM OrderContains;\n" +
                "DELETE FROM OrderedFoodItem;";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createAllTables() {
        try (Connection conn = DriverManager.getConnection(url); // will create DB if doesn't exist
             Statement stmt = conn.createStatement()) {
            InputStream stream = JDBCStorage.class.getResourceAsStream("/sql/create_tables.sql");
            String sql = new BufferedReader(new InputStreamReader(stream))
                    .lines().collect(Collectors.joining("\n"));
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private FoodItem readFoodItem(ResultSet rs) {
        String code, name, cost, unitType, calPerUnit;
        boolean isVegetarian, isVegan, isGlutenFree;

        try {
            code = rs.getString("Code");
            name = rs.getString("Name");
            unitType = rs.getString("UnitType");
            cost = rs.getString("Cost");
            isVegetarian = rs.getBoolean("IsVegetarian");
            isVegan = rs.getBoolean("IsVegan");
            isGlutenFree = rs.getBoolean("IsGlutenFree");
            calPerUnit = rs.getString("CalPerUnit");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

        UnitType unit;
        switch (unitType) {
            case "g":
                unit = UnitType.GRAM;
                break;
            case "m":
                unit = UnitType.ML;
                break;
            default:
                unit = UnitType.COUNT;
                break;
        }
        FoodItem result = new FoodItem(code, name, unit);
        result.setCost(BigMoney.parse(cost)); // TODO error handling, or add a DB column constraint
        result.setIsVegetarian(isVegetarian);
        result.setIsVegan(isVegan);
        result.setIsGlutenFree(isGlutenFree);
        result.setCaloriesPerUnit(Double.parseDouble(calPerUnit)); // TODO error handling, or add a DB column constraint

        //result.setRecipe(getFoodItemRecipe(code));

        return result;
    }

    private Recipe getFoodItemRecipe(String foodItemCode) {
        String sql = "SELECT * FROM Recipe WHERE Product = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, getFoodItemIdFromCode(foodItemCode));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                return readRecipe(rs);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null; // TODO error handling?
        }
    }

    private Recipe readRecipe(ResultSet rs) {
        int id, amountCreated;

        try {
            id = rs.getInt("Id");
            amountCreated = rs.getInt("AmountCreated");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

        Set<FoodItem> ingredients = new HashSet<>();
        Set<FoodItem> addableIngredients = new HashSet<>();
        Map<String, Integer> ingredientAmounts = new HashMap<>();

        populateRecipeData(id, ingredients, addableIngredients, ingredientAmounts);
        Recipe result = new Recipe(ingredients, addableIngredients, ingredientAmounts, amountCreated);
        return result;
    }

    private void populateRecipeData(int recipeId, Set<FoodItem> ingredients, Set<FoodItem> addableIngredients, Map<String, Integer> ingredientAmounts) {
        String sql = "SELECT * FROM RecipeContains WHERE Recipe = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, recipeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int foodItemId = rs.getInt("FoodItem");
                FoodItem item = getFoodItemByCode(getFoodItemCodeFromId(foodItemId));
                ingredients.add(item);
                int amount = rs.getInt("Amount");
                ingredientAmounts.put(item.getCode(), amount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getFoodItemCodeFromId(int id) {
        String sql = "SELECT Code FROM FoodItem WHERE Id = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                return rs.getString("Code");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private Order readOrder(ResultSet rs) {
        int id;
        List<FoodItem> foodItems = new ArrayList<FoodItem>();
        String orderNote, statusString;

        try {
            id = rs.getInt("Id");
            orderNote = rs.getString("Note");
            statusString = rs.getString("Status");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

        OrderStatus status;
        switch (statusString) {
            case "c":
                status = OrderStatus.CREATING;
                break;
            case "s":
                status = OrderStatus.SUBMITTED;
                break;
            case "x":
                status = OrderStatus.CANCELLED;
                break;
            case "r":
                status = OrderStatus.REFUNDED;
                break;
            default:
                status = OrderStatus.COMPLETED;
                break;
        }
        Order result = new Order();
        result.setId(id);
        result.setOrderNote(orderNote);

        String sql = "SELECT *\n" +
                "FROM OrderContains JOIN OrderedFoodItem ON FoodItem = Id\n" +
                "WHERE CustomerOrder = ?";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, result.getOrderID());
                ResultSet rs2 = pstmt.executeQuery();

                while (rs2.next()) {
                    result.addItem(readFoodItem(rs2));
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        result.setStatus(status);

        return result;
    }

    private int readFoodItemStock(ResultSet rs) {
        try {
            return rs.getInt("StockLevel");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 0; // TODO error handling
        }
    }

    @Override
    public Set<FoodItem> getAllFoodItems() {
        String sql = "SELECT * FROM FoodItem";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Set<FoodItem> items = new HashSet<FoodItem>();
            while (rs.next()) {
                items.add(readFoodItem(rs));
            }
            return items;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null; // TODO error handling?
        }
    }

    @Override
    public FoodItem getFoodItemByCode(String code) {
        String sql = "SELECT * FROM FoodItem WHERE Code = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                return readFoodItem(rs);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null; // TODO error handling?
        }
    }

    @Override
    public void addFoodItem(FoodItem item, int count) {
        if (getFoodItemByCode(item.getCode()) != null) {
            throw new InvalidDataCodeException("FoodItem with code " + item.getCode() + " already exists in the database.");
        }

        String sql = "INSERT INTO FoodItem(Code, Name, Cost, UnitType, StockLevel, IsVegetarian, IsVegan, IsGlutenFree, CalPerUnit) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getCode());
            pstmt.setString(2, item.getName());
            pstmt.setString(3, item.getCost().toString());
            pstmt.setString(4, item.getUnit().toString()); // TODO error handling
            pstmt.setInt(5, count);
            pstmt.setBoolean(6, item.getIsVegetarian());
            pstmt.setBoolean(7, item.getIsVegan());
            pstmt.setBoolean(8, item.getIsGlutenFree());
            pstmt.setString(9, Double.toString(item.getCaloriesPerUnit()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        if (item.getRecipe() != null) {
            addRecipe(item);
        }
    }

    @Override
    public void updateFoodItem(FoodItem alteredItem) {
        if (getFoodItemByCode(alteredItem.getCode()) == null) {
            throw new InvalidDataCodeException("item with given item's code does not exist in storage.");
        }

        String sql = "UPDATE FoodItem\n" +
                "SET Name=?, Cost=?, UnitType=?, IsVegetarian=?, IsVegan=?, IsGlutenFree=?, CalPerUnit=?\n" +
                "WHERE Code=?";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, alteredItem.getName());
            pstmt.setString(2, alteredItem.getCost().toString());
            pstmt.setString(3, alteredItem.getUnit().toString()); // TODO error handling
            pstmt.setBoolean(4, alteredItem.getIsVegetarian());
            pstmt.setBoolean(5, alteredItem.getIsVegan());
            pstmt.setBoolean(6, alteredItem.getIsGlutenFree());
            pstmt.setString(7, Double.toString(alteredItem.getCaloriesPerUnit()));
            pstmt.setString(8, alteredItem.getCode());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        deleteRecipeWithProduct(alteredItem);
        if (alteredItem.getRecipe() != null) {
            addRecipe(alteredItem);
        }
    }

    /**
     * adds the recipe of a given FoodItem to the database.
     * @param item
     */
    private void addRecipe(FoodItem item) {
        Recipe recipe = item.getRecipe();
        if (recipe == null) {
            throw new NullPointerException("the given FoodItem has no Recipe");
        }

        String sql = "INSERT INTO Recipe (Product, AmountCreated) VALUES (?, ?)";

        int recipeId;
        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, item.getCode());
            pstmt.setInt(2, recipe.getAmountCreated());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            recipeId = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        for (FoodItem ingredient : recipe.getIngredients()) {
            if (getFoodItemByCode(ingredient.getCode()) == null) {
                addFoodItem(ingredient, 0);
            }
            int amount = recipe.getIngredientAmounts().get(ingredient.getCode());
            addRecipeContains(recipeId, ingredient, amount);
        }
    }

    private int getFoodItemIdFromCode(String code) {
        String sql = "SELECT Id FROM FoodItem WHERE Code = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                throw new InvalidDataCodeException("no entry with code " + code + " found");
            } else {
                return rs.getInt("Id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addRecipeContains(int recipeId, FoodItem ingredient, int amount) {
        String sql = "INSERT INTO RecipeContains (Recipe, FoodItem, Amount) VALUES (?, ?, ?)";

        // TODO why does this fail if the DB exists? shouldn't reset instance fix this?

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recipeId);
            pstmt.setInt(2, getFoodItemIdFromCode(ingredient.getCode()));
            pstmt.setInt(3, amount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * removes the Recipe with the given product from the database if it exists.
     * @param product the FoodItem the Recipe produces
     */
    private void deleteRecipeWithProduct(FoodItem product) {
        String sql2 = "DELETE FROM Recipe WHERE Product = ?";
        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql2)) {
            pstmt.setString(1, product.getCode());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFoodItem(String code) {
        if (getFoodItemByCode(code) == null) {
            throw new InvalidDataCodeException("no FoodItem with the given code exists in storage.");
        }

        String sql = "DELETE FROM FoodItem\n" +
                "WHERE Code=?";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void setFoodItemStock(String code, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count must be non-negative.");
        }
        if (getFoodItemByCode(code) == null) {
            throw new InvalidDataCodeException("no FoodItem with given code exists in the database.");
        }

        String sql = "UPDATE FoodItem\n" +
                "SET StockLevel=?\n" +
                "WHERE Code=?";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, count);
            pstmt.setString(2, code);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public int getFoodItemStock(String code) {
        if (getFoodItemByCode(code) == null) {
            throw new InvalidDataCodeException("no FoodItem with given code exists in the database.");
        }

        String sql = "SELECT StockLevel FROM FoodItem WHERE Code = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return 0; // TODO throw an error
            } else {
                return readFoodItemStock(rs);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 0; // TODO error handling
        }
    }

    @Override
    public Set<Order> getAllOrders() {
        return null;
    }

    @Override
    public Order getOrderByID(int id) {
        if (id == Order.DEFAULT_ID) {
            throw new InvalidDataCodeException("given order has the default id " + Order.DEFAULT_ID + ". set a valid ID");
        }

        String sql = "SELECT * FROM CustomerOrder WHERE Id = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                return readOrder(rs);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null; // TODO error handling?
        }
    }

    private void addOrderedFoodItem(Order order, FoodItem item) {
        String sql = "INSERT INTO OrderedFoodItem(Code, Name, Cost, UnitType, IsVegetarian, IsVegan, IsGlutenFree, CalPerUnit) VALUES(?,?,?,?,?,?,?,?)";

        int insertedId;

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, item.getCode());
            pstmt.setString(2, item.getName());
            pstmt.setString(3, item.getCost().toString());
            pstmt.setString(4, item.getUnit().toString());
            pstmt.setBoolean(5, item.getIsVegetarian());
            pstmt.setBoolean(6, item.getIsVegan());
            pstmt.setBoolean(7, item.getIsGlutenFree());
            pstmt.setString(8, Double.toString(item.getCaloriesPerUnit()));
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            insertedId = rs.getInt(1);

            String sql2 = "INSERT INTO OrderContains(CustomerOrder, FoodItem) VALUES (?,?)";

            try (PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
                pstmt2.setInt(1, order.getOrderID());
                pstmt2.setInt(2, insertedId);
                pstmt2.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void addOrder(Order order) {
        if (order == null) {
            throw new NullPointerException();
        }

        if (order.getOrderContents().size() == 0) {
            throw new IllegalArgumentException("cannot add an empty order");
        }

        if (order.getOrderID() != Order.DEFAULT_ID) {
            throw new InvalidDataCodeException("cannot add order with a non-default id. please use update");
        }

        String insertOrder = "INSERT INTO CustomerOrder(Status, Note) VALUES (?,?)";
        //String insertOrderItem = "INSERT INTO OrderContains(CustomerOrder, FoodItem)"

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, order.getOrderStatus().toString());
            pstmt.setString(2, order.getOrderNote());

            pstmt.executeUpdate();
            order.setId(pstmt.getGeneratedKeys().getInt(1));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        for (FoodItem item : order.getOrderContents()) {
            addOrderedFoodItem(order, item);
        }
    }

    @Override
    public void updateOrder(Order alteredOrder) {
        if (getOrderByID(alteredOrder.getOrderID()) == null) {
            throw new InvalidDataCodeException("order with given order's code does not exist in the database.");
        }

        String sql = "UPDATE CustomerOrder\n" +
                "SET Status=?\n" +
                "WHERE Id=?";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, alteredOrder.getOrderStatus().toString());
            pstmt.setInt(2, alteredOrder.getOrderID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void removeOrder(int ID) {

    }

    public static void main(String[] args) {
        JDBCStorage storage = JDBCStorage.getInstance();
//        storage.updateFoodItem(new FoodItem("CRACKERS", "A Mouldy Cracker", UnitType.COUNT));
//        storage.setFoodItemStock("PIZZA", 20);
        Order testOrder = new Order();
        testOrder.addItem(new FoodItem("OOHYEA", "test item", UnitType.COUNT));
        testOrder.addItem(new FoodItem("OOHYEA", "test item", UnitType.COUNT));
        storage.addOrder(testOrder);
        int orderId = testOrder.getOrderID();
        testOrder = null;
        testOrder = storage.getOrderByID(orderId);
        System.out.println(testOrder.getOrderContents().size());
        for (FoodItem item : testOrder.getOrderContents()) {
            System.out.println(item);
        }
        try {
            //storage.addOrder(new Order(1));
//            Set<FoodItem> items = storage.getAllFoodItems();
//            for (FoodItem item : items) {
//                System.out.println(item);
//                System.out.println(storage.getFoodItemStock(item.getCode()));
//            }
//            storage.getFoodItemByCode("CRACKERS");
//            storage.resetInstance();
//            System.out.println(storage.getFoodItemByCode("CRACKERS"));
        } catch (Exception e) {
            System.out.println("error thrown:" + e);
        }
    }


}
