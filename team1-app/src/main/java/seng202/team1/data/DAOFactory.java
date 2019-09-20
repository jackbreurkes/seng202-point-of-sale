package seng202.team1.data;

public class DAOFactory {

    /**
     * returns the FoodItemDAO implementation currently in use by the system.
     * @return an instance of a class implementing FoodItemDAO
     */
    public static FoodItemDAO getFoodItemDAO() {
        return JDBCStorage.getInstance();
    }

    /**
     * resets any storage implementations
     */
    public static void resetInstances() {
        MemoryStorage.getInstance().resetInstance();
        JDBCStorage.getInstance().resetInstance();
    }

    /**
     * returns the OrderDAO implementation currently in use by the system.
     * @return an instance of a class implementing OrderDAO
     */
    public static OrderDAO getOrderDAO() {
        return null;
    }

}
