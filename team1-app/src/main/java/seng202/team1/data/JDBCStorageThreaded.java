package seng202.team1.data;

import org.joda.money.BigMoney;
import org.sqlite.SQLiteConfig;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.model.Recipe;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.OrderStatus;
import seng202.team1.util.RecipeBuilder;
import seng202.team1.util.UnitType;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * concrete implementation of data access object interfaces using JDBC and SQLite.
 */
public class JDBCStorageThreaded implements FoodItemDAO, OrderDAO {


    private static String url = "jdbc:sqlite:rosemary.db";
    private static JDBCStorage instance;
    private SQLiteConfig config = makeConfig();
    private static Connection conn;

    /**
     * Creates a database configuration
     *  - enables foreign key cascades
     * @return SQLConfig configuration
     */
    private SQLiteConfig makeConfig() {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        return config;
    }

    private JDBCStorage() {
        initializeDatabaseIfNotExists();
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

    /**
     * returns a Connection to the database at JDBCStorage.url.
     * @return a Connection to the database
     */
    private Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url, config.toProperties());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public void resetInstance() {
        String[] dropStatements = {
                "DROP TABLE IF EXISTS OrderContains;",
                "DROP TABLE IF EXISTS RecipeContains;",
                "DROP TABLE IF EXISTS CustomerOrder;",
                "DROP TABLE IF EXISTS Recipe;",
                "DROP TABLE IF EXISTS OrderedFoodItem;",
                "DROP TABLE IF EXISTS FoodItem;"
        };

        for (String sql : dropStatements) {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                // create a new table
                stmt.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        initializeDatabaseIfNotExists();
    }

    /**
     * runs the scripts in resources/sql to set up the database if it does not already exist.
     */
    private void initializeDatabaseIfNotExists() {
        Connection conn = getConnection();
        try (Statement stmt = conn.createStatement()) {

            InputStream createTablesStream = JDBCStorage.class.getResourceAsStream("/sql/create_tables.sql");
            String createTables = new BufferedReader(new InputStreamReader(createTablesStream))
                    .lines().collect(Collectors.joining("\n"));

            InputStream createTriggersStream = JDBCStorage.class.getResourceAsStream("/sql/create_triggers.sql");
            String createTriggers = new BufferedReader(new InputStreamReader(createTriggersStream))
                    .lines().collect(Collectors.joining("\n"));

            stmt.executeUpdate(createTables);
            stmt.executeUpdate(createTriggers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads a single food item from a non-empty result set from the FoodItem or OrderedFoodItem tables.
     * @param rs the ResultSet containing at least one valid row
     * @return the FoodItem created using the data from the result set
     */
    private FoodItem readFoodItem(ResultSet rs) {
        return readFoodItem(rs, false);
    }

    /**
     * reads a single food item from a non-empty result set from the FoodItem or OrderedFoodItem tables.
     * can optionally set the recipe of the FoodItem to null.
     * @param rs the ResultSet containing at least one valid row
     * @param setRecipeToNull whether the food item's recipe should be set to null
     * @return the FoodItem created using the data from the result set
     */
    private FoodItem readFoodItem(ResultSet rs, boolean setRecipeToNull) {
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
            e.printStackTrace();
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

        if (!setRecipeToNull) {
            result.setRecipe(getRecipeOfFoodItem(code));
        }

        return result;
    }

    /**
     * reads the stock level attribute from the result set of the FoodItem table.
     * @param rs the non-empty ResultSet
     * @return integer stock level for the food item
     */
    private int readFoodItemStock(ResultSet rs) {
        try {
            return rs.getInt("StockLevel");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // TODO error handling
        }
    }

    /**
     * reads a single recipe from a non-empty result set from the Recipe table.
     * @param rs the ResultSet containing at least one valid row
     * @return the Recipe created using the data from the result set
     */
    private Recipe readRecipe(ResultSet rs) {
        int id, amountCreated;

        try {
            id = rs.getInt("Id");
            amountCreated = rs.getInt("AmountCreated");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

//        RecipeBuilder builder = new RecipeBuilder();

        Set<FoodItem> ingredients = new HashSet<>();
        Set<FoodItem> addableIngredients = new HashSet<>();
        Map<String, Integer> ingredientAmounts = new HashMap<>();

        RecipeBuilder builder = getBuilderForRecipe(id);
        try {
            Recipe result = builder.generateRecipe(amountCreated);
            return result;
        } catch (Exception ignored) { // the recipe has no food items
            return null;
        }
    }

    /**
     * reads the next row in a ResultSet and returns an Order corresponding to the information derived from that row.
     * @param rs the ResultSet to read from, rs should contain at least one row after the cursor
     * @return an Order corresponding to the next row in the ResultSet
     */
    private Order readOrder(ResultSet rs) {
        int id;
        List<FoodItem> foodItems = new ArrayList<FoodItem>();
        String orderNote, statusString, lastUpdatedString;
        Date lastUpdated = null;

        try {
            id = rs.getInt("Id");
            orderNote = rs.getString("Note");
            statusString = rs.getString("Status");
            lastUpdatedString = rs.getString("LastUpdated");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            lastUpdated = dateFormat.parse(lastUpdatedString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Order result = new Order();
        result.setId(id);
        result.setOrderNote(orderNote);
        result.setLastUpdated(lastUpdated);


        String sql = "SELECT *\n" +
                "FROM OrderContains JOIN OrderedFoodItem ON FoodItem = Id\n" +
                "WHERE CustomerOrder = ?";

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, result.getId());
            ResultSet rs2 = pstmt.executeQuery();

            while (rs2.next()) {
                result.addItem(readFoodItem(rs2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OrderStatus status = OrderStatus.getValueFromString(statusString);
        setOrderStatus(result, status);
        return result;
    }

    /**
     * returns the Recipe of the stored FoodItem with the given code.
     * @param foodItemCode the code of the FoodItem to get the Recipe of
     * @return the FoodItem's Recipe or null if it has none
     */
    public Recipe getRecipeOfFoodItem(String foodItemCode) {
        String sql = "SELECT * FROM Recipe WHERE Product = ? LIMIT 1";

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, foodItemCode);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                return readRecipe(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // TODO error handling?
        }
    }

    /**
     * returns the RecipeBuilder loaded with the data needed to generate the recipe with the given id.
     * @param recipeId the
     * @return
     */
    private RecipeBuilder getBuilderForRecipe(int recipeId) {
        String sql = "SELECT * FROM RecipeContains WHERE Recipe = ?";

        RecipeBuilder builder = new RecipeBuilder();

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, recipeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int foodItemId = rs.getInt("FoodItem");
                FoodItem item = getFoodItemByCode(getFoodItemCodeFromId(foodItemId), true);
                int amount = rs.getInt("Amount");
                builder.addIngredient(item, amount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return builder;
    }

    private String getFoodItemCodeFromId(int id) {
        String sql = "SELECT Code FROM FoodItem WHERE Id = ? LIMIT 1";

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                return rs.getString("Code");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * sets the status of an Order using the correct sequence of status modifications.
     * this has not been placed in Order since it only works for orders with the CREATING status.
     * @param order the Order to set the status of
     * @param status the desired OrderStatus
     */
    private void setOrderStatus(Order order, OrderStatus status) {
        switch (status) {
            case SUBMITTED:
                order.submitOrder();
                break;
            case CANCELLED:
                order.submitOrder();
                order.cancelOrder();
                break;
            case COMPLETED:
                order.submitOrder();
                order.completeOrder();
                break;
            case REFUNDED:
                order.submitOrder();
                order.completeOrder();
                order.refundOrder();
            default:
                break;
        }
    }

    @Override
    public Set<FoodItem> getAllFoodItems() {
        String sql = "SELECT * FROM FoodItem";

        Connection conn = getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Set<FoodItem> items = new HashSet<FoodItem>();
            while (rs.next()) {
                items.add(readFoodItem(rs));
            }
            return items;

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // TODO error handling?
        }
    }

    @Override
    public FoodItem getFoodItemByCode(String code) {
        return getFoodItemByCode(code, false);
    }

    private FoodItem getFoodItemByCode(String code, boolean setRecipeToNull) {
        String sql = "SELECT * FROM FoodItem WHERE Code = ? LIMIT 1";

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                return readFoodItem(rs, setRecipeToNull);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // TODO error handling?
        }
    }

    @Override
    public void addFoodItem(FoodItem item, int count) {
        if (getFoodItemByCode(item.getCode()) != null) {
            throw new InvalidDataCodeException("FoodItem with code " + item.getCode() + " already exists in the database.");
        }

        String sql = "INSERT INTO FoodItem(Code, Name, Cost, UnitType, StockLevel, IsVegetarian, IsVegan, IsGlutenFree, CalPerUnit) VALUES(?,?,?,?,?,?,?,?,?)";

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
            e.printStackTrace();
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

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
            e.printStackTrace();
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

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {
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

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, count);
            pstmt.setString(2, code);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getFoodItemStock(String code) {
        if (getFoodItemByCode(code) == null) {
            throw new InvalidDataCodeException("no FoodItem with given code exists in the database.");
        }

        String sql = "SELECT StockLevel FROM FoodItem WHERE Code = ? LIMIT 1";

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return 0; // TODO throw an error
            } else {
                return readFoodItemStock(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // TODO error handling
        }
    }

    @Override
    public Order getOrderByID(int id) {
        String sql = "SELECT * FROM CustomerOrder WHERE Id = ? LIMIT 1";

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                return readOrder(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // TODO error handling?
        }
    }

    private void addOrderedFoodItem(Order order, FoodItem item) {
        String sql = "INSERT INTO OrderedFoodItem(Code, Name, Cost, UnitType, IsVegetarian, IsVegan, IsGlutenFree, CalPerUnit) VALUES(?,?,?,?,?,?,?,?)";

        int insertedId;

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
                pstmt2.setInt(1, order.getId());
                pstmt2.setInt(2, insertedId);
                pstmt2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

        if (order.getStatus() == OrderStatus.CREATING) {
            throw new IllegalArgumentException("creating orders should not be stored in the database, please submit it first.");
        }

        if (getOrderByID(order.getId()) != null) {
            throw new InvalidDataCodeException("order with the given order's id already exists. please use update.");
        }

        String insertOrder = "INSERT INTO CustomerOrder(Status, Note) VALUES (?,?)";
        //String insertOrderItem = "INSERT INTO OrderContains(CustomerOrder, FoodItem)"

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, order.getStatus().toString());
            pstmt.setString(2, order.getOrderNote());

            pstmt.executeUpdate();
            order.setId(pstmt.getGeneratedKeys().getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (FoodItem item : order.getOrderContents()) {
            addOrderedFoodItem(order, item);
        }
    }

    @Override
    public Set<Order> getAllSubmittedOrders() {
        String sql = "SELECT * FROM CustomerOrder WHERE status = 's'";

        Connection conn = getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Set<Order> submittedOrders = new HashSet<Order>();
            while (rs.next()) {
                submittedOrders.add(readOrder(rs));
            }
            return submittedOrders;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateOrder(Order alteredOrder) {
        if (getOrderByID(alteredOrder.getId()) == null) {
            throw new InvalidDataCodeException("order with given order's code does not exist in the database.");
        }

        if (alteredOrder.getStatus() == OrderStatus.CREATING) {
            throw new IllegalArgumentException("creating orders should not be stored in the database.");
        }

        String sql = "UPDATE CustomerOrder\n" +
                "SET Status=?\n" +
                "WHERE Id=?";

        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, alteredOrder.getStatus().toString());
            pstmt.setInt(2, alteredOrder.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Order> getAllOrders() {
        String sql = "SELECT * FROM CustomerOrder;";

        Set<Order> allOrders = new HashSet<>();

        Connection conn = getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                allOrders.add(readOrder(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allOrders;
    }

    @Override
    public void removeOrder(int ID) {

    }

}
