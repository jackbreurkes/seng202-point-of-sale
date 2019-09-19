package seng202.team1.data;

import org.joda.money.BigMoney;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.util.UnitType;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JDBCStorage implements FoodItemDAO, OrderDAO {

    private static String url = "jdbc:sqlite:rosemary.db";
    private static JDBCStorage instance;

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    private JDBCStorage() {
        createFoodItemTable();
        createRecipeTable();
        createRecipeContainsTable();
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

    private static void createFoodItemTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS FoodItem\n" +
                "(Id /* primary key ID. added because varchar prim key isn't good? */ INTEGER PRIMARY KEY,\n" +
                " Code /* alphanumberic code of the FoodItem */ VARCHAR(10) UNIQUE NOT NULL\n" +
                "    CONSTRAINT code_min_size CHECK (LENGTH(Code) >= 3),\n" +
                " Name /* the FoodItem's name */ VARCHAR(20) NOT NULL\n" +
                "    CONSTRAINT name_min_size CHECK (LENGTH(Name) >= 3),\n" +
                " UnitType /* unit type (count, ml or gram) */ CHAR(1)\n" +
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
                " Product /* the Id of the FoodItem created by the Recipe */ INT NOT NULL REFERENCES FoodItem,\n" +
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

    public static void main(String[] args) {
        JDBCStorage storage = JDBCStorage.getInstance();
        storage.updateFoodItem(new FoodItem("CRACKERS", "A Mouldy Cracker", UnitType.COUNT));
        storage.setFoodItemStock("PIZZA", 20);
        try {
            Set<FoodItem> items = storage.getAllFoodItems();
            for (FoodItem item : items) {
                System.out.println(item);
                System.out.println(storage.getFoodItemStock(item.getCode()));
            }
            storage.getFoodItemByCode("CRACKERS");
        } catch (Exception e) {
            System.out.println("error thrown:" + e);
        }
    }

    @Override
    public Set<Order> getAllOrders() {
        return null;
    }

    @Override
    public Order getOrderByID(int ID) {
        return null;
    }

    @Override
    public void addOrder(Order order) {

    }

    @Override
    public void updateOrder(Order alteredOrder) {

    }

    @Override
    public void removeOrder(int ID) {

    }


}
