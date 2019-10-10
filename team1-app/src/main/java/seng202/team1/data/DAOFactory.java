package seng202.team1.data;

/**
 * factory class to be used to retrieve or reset the currently in use implementation of the
 * data access object interfaces available within the system.
 */
public class DAOFactory {

    /**
     * returns the FoodItemDAO implementation currently in use by the system.
     * @return an instance of a class implementing FoodItemDAO
     */
    public static FoodItemDAO getFoodItemDAO() {
        return JDBCStorage.getInstance();
    }

    /**
     * resets any storage implementations.
     */
    public static void resetInstances() {
        JDBCStorage.getInstance().resetInstance();
    }

    /**
     * returns the OrderDAO implementation currently in use by the system.
     * @return an instance of a class implementing OrderDAO
     */
    public static OrderDAO getOrderDAO() {
        return JDBCStorage.getInstance();
    }

}
