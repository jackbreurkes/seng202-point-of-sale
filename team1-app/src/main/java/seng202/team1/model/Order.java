package seng202.team1.model;

import seng202.team1.util.OrderStatus;

import java.util.*;

/**
 * 
 */
public class Order {

    private List<FoodItem> foodItems;
    private String orderNote;
    private OrderStatus status;
    // private Location location;
    // private Weather weather;



    /**
     * Default constructor
     */
    public Order() {
    }

    /**
     * @param item
     * adds a single instance of the specified item to the foodItems list
     */
    public void addItem(FoodItem item) {
        // TODO implement here
    }

    /**
     * @param item
     * removes a single instance of the specified item from the foodItems list
     */
    public void removeItem(FoodItem item) {
        // TODO implement here
    }

    /**
     * changes the status of the order to cancelled, (registers it in the database?)
     */
    public void cancelOrder() {
        // TODO implement here
    }

    /**
     * updates the status of the order to refunded (returns the amount of refunded money calculated by components?)
     */
    public void refundOrder() {
        // TODO implement here
    }

    /**
     * changes the status of the order to complete, (registers it in the database?)
     */
    public void completeOrder() {
        // TODO implement here
    }

    /**
     * returns the contents of the order in list form
     */
    public List getOrderContents() {
        // TODO implement here
        return foodItems;
    }

    /**
     * returns the current status of the order
     */
    public OrderStatus getOrderStatus() {
        // TODO implement here
        return status;
    }
}