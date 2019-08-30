package seng202.team1.model;

import seng202.team1.util.OrderStatus;

import java.util.*;

import static seng202.team1.util.OrderStatus.*;

/**
 * 
 */
public class Order {

    private List<FoodItem> foodItems;
    private String orderNote;
    private OrderStatus status = CREATING;
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
        foodItems.add(item);
    }

    /**
     * @param item
     * removes a single instance of the specified item from the foodItems list
     */
    public void removeItem(FoodItem item) {
        // TODO need to change it so we only remove ONE instance of the item specified
        foodItems.remove(item);
    }

    /**
     * changes the status of the order to cancelled, (registers it in the database?)
     */
    public void cancelOrder() {
        // TODO change exception to custom exception
        //if status is still being processed it can be cancelled
        if (status == CREATING) {
            status = CANCELLED;
        }
        //orders cannot be cancelled twice
        else if(status == CANCELLED) {
            throw new IllegalArgumentException();
        }
        //orders cannot be cancelled if they've been refunded
        else if(status == REFUNDED) {
            throw new IllegalArgumentException();
        }
        //completed orders cannot be cancelled
        else if(status == COMPLETED) {
            throw new IllegalArgumentException();
        }

    }

    /**
     * updates the status of the order to refunded (returns the amount of refunded money calculated by components?)
     * only completed orders can be refunded
     */
    public void refundOrder() {
        //TODO discuss what actually gets refunded and how it works
        if (status == COMPLETED) {
            status = REFUNDED;
            //refund the order or something
        }
        //cancelled orders cannot be refunded
        else if(status == CANCELLED) {
            throw new IllegalArgumentException();
        }
        //orders cannot be refunded twice
        else if(status == REFUNDED) {
            throw new IllegalArgumentException();
        }
        //incomplete orders cannot be cancelled
        else if(status == CREATING) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * changes the status of the order to complete, (registers it in the database?)
     */
    public void completeOrder() {
        // TODO implement here
        if (status == CREATING) {
            status = COMPLETED;
            //refund the order or something
        }
        //cancelled orders cannot be completed
        else if(status == CANCELLED) {
            throw new IllegalArgumentException();
        }
        //refunded orders cannot be completed
        else if(status == REFUNDED) {
            throw new IllegalArgumentException();
        }
        //incomplete orders cannot be cancelled
        else if(status == CREATING) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * returns the contents of the order in list form
     */
    public List getOrderContents() {
        return foodItems;
    }

    /**
     * returns the current status of the order
     */
    public OrderStatus getOrderStatus() {
        return status;
    }
}