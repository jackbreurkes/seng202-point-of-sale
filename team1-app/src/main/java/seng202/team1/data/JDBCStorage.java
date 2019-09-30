package seng202.team1.data;

import org.apache.ibatis.io.Resources;
import org.joda.money.BigMoney;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.model.Recipe;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.OrderStatus;
import seng202.team1.util.UnitType;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.*;
import java.util.*;

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
        String sql = "DELETE FROM FoodItem; DELETE FROM Recipe; DELETE FROM RecipeContains";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createFoodItemTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS FoodItem\n" +
                "(Id /* primary key ID. added because varchar prim key isn't good? */ INTEGER PRIMARY KEY,\n" +
                " Code /* alphanumberic code of the FoodItem */ VARCHAR(10) UNIQUE NOT NULL\n" +
                "    CONSTRAINT code_min_size CHECK (LENGTH(Code) >= 3),\n" +
                " Name /* the FoodItem's name */ VARCHAR(20) NOT NULL\n" +
                "    CONSTRAINT name_min_size CHECK (LENGTH(Name) >= 3),\n" +
                " UnitType /* unit type (count, ml or gram) */ CHAR(1) NOT NULL\n" +
                "    CONSTRAINT check_unit CHECK (UnitType in ('c', 'm', 'g')),\n" +
                "Cost /* cost of the FoodItem for customers */ VARCHAR(8000) NOT NULL DEFAULT '0'\n" +
                "    /* TODO check that Cost is a numeric string */,\n" +
                " StockLevel /* the current amount of this item stored */ INTEGER NOT NULL DEFAULT 0,\n" +
                " IsVegetarian /* whether the item is vegetarian */ BIT NOT NULL DEFAULT 0,\n" +
                " IsVegan /* whether the item is vegan */ BIT NOT NULL DEFAULT 0,\n" +
                " IsGlutenFree /* whether the item is gluten free */ BIT NOT NULL DEFAULT 0,\n" +
                " CalPerUnit /* number of calories per single unit */ VARCHAR(8000)\n" +
                "    /* TODO check that CalPerUnit is a numeric string */);";

        try (Connection conn = DriverManager.getConnection(url); // will create DB if doesn't exist
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createRecipeTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Recipe\n" +
                "(Id /* unique identifier for a Recipe */ INTEGER,\n" +
                " Product /* the Id of the FoodItem created by the Recipe */ INTEGER UNIQUE NOT NULL REFERENCES FoodItem,\n" +
                " AmountCreated /* the amount of the Product created by the Recipe */ INT NOT NULL,\n" +
                " PRIMARY KEY (Id, Product));";

        try (Connection conn = DriverManager.getConnection(url); // will create DB if doesn't exist
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createRecipeContainsTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS RecipeContains\n" +
                "(Recipe /* Id of the Recipe */ INT NOT NULL REFERENCES Recipe,\n" +
                " FoodItem /* FoodItem contained in the recipe */ INT NOT NULL REFERENCES FoodItem,\n" +
                " Amount /* the amount of the FoodItem in the Recipe */ INT NOT NULL,\n" +
                " PRIMARY KEY (Recipe, FoodItem));";

        try (Connection conn = DriverManager.getConnection(url); // will create DB if doesn't exist
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createOrderTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS CustomerOrder\n" +
                "(Id /* unique identifier for an Order */ INTEGER PRIMARY KEY,\n" +
                " Status /* the status of the order (Creating='c', Submitted='s', Completed='d', Cancelled='x', Refunded='r') */ CHAR(1) NOT NULL\n" +
                "    CONSTRAINT check_status CHECK (Status in ('c', 's', 'd', 'x', 'r')),\n" +
                " Note /* any notes added to the order */ VARCHAR(8000),\n" +
                " LastUpdated /* the time the order's status was last updated in unix time */ DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, /* TODO update with trigger */\n" +
                " Location /* the location the order was processed if known */ VARCHAR(8000),\n" +
                " Weather /* the location the order was processed if known */ VARCHAR(8000));";

        try (Connection conn = DriverManager.getConnection(url); // will create DB if doesn't exist
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createOrderContainsTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS OrderContains\n" +
                "(CustomerOrder /* Id of the Order*/ INTEGER NOT NULL REFERENCES CustomerOrder,\n" +
                " FoodItem /* Id of the FoodItem contained in the Order */ INTEGER NOT NULL REFERENCES FoodItem,\n" +
                " PRIMARY KEY (CustomerOrder, FoodItem));";

        try (Connection conn = DriverManager.getConnection(url); // will create DB if doesn't exist
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createOrderedFoodItemTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS OrderedFoodItem\n" +
                "(Id /* primary key ID */ INTEGER PRIMARY KEY,\n" +
                " Code /* alphanumberic code of the FoodItem */ VARCHAR(10) NOT NULL\n" +
                "    CONSTRAINT code_min_size CHECK (LENGTH(Code) >= 3),\n" +
                " Name /* the FoodItem's name */ VARCHAR(20) NOT NULL\n" +
                "    CONSTRAINT name_min_size CHECK (LENGTH(Name) >= 3),\n" +
                " UnitType /* unit type (count, ml or gram) */ CHAR(1) NOT NULL\n" +
                "    CONSTRAINT check_unit CHECK (UnitType in ('c', 'm', 'g')),\n" +
                " Cost /* cost of the FoodItem for customers */ VARCHAR(8000) NOT NULL DEFAULT '0'\n" +
                "    /* TODO check that Cost is correct format */,\n" +
                " IsVegetarian /* whether the item is vegetarian */ BIT NOT NULL DEFAULT 0,\n" +
                " IsVegan /* whether the item is vegan */ BIT NOT NULL DEFAULT 0,\n" +
                " IsGlutenFree /* whether the item is gluten free */ BIT NOT NULL DEFAULT 0,\n" +
                " CalPerUnit /* number of calories per single unit */ VARCHAR(8000)\n" +
                "    /* TODO check that CalPerUnit is a numeric string */);";

        try (Connection conn = DriverManager.getConnection(url); // will create DB if doesn't exist
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createAllTables() {
        /*try (Connection conn = DriverManager.getConnection(url); // will create DB if doesn't exist
             Statement stmt = conn.createStatement()) {
            ScriptRunner sr = new ScriptRunner(conn);
            Reader reader = new InputStreamReader(JDBCStorage.class.getResourceAsStream("/sql/create_tables.sql"));
            //InputStream file = Class.getResourceAsStream("/dtd/fooditem.dtd");
            //System.out.println(file.available() +  " pls");
            //Reader reader = Resources.getResourceAsReader("com/mkyong/MyBatis/sql/create_tables.sql");
            sr.runScript(reader);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

        createFoodItemTable();
        createRecipeTable();
        createRecipeContainsTable();
        createOrderTable();
        createOrderContainsTable();
        createOrderedFoodItemTable();
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



        return result;
    }

    private Recipe getFoodItemRecipe(String foodItemCode) {
        String sql = "SELECT * FROM Recipe WHERE Product = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(JDBCStorage.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, foodItemCode);
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
            id = rs.getInt("id");
            amountCreated = rs.getInt("amountcreated");
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
