package seng202.team1.model;

import seng202.team1.util.InvalidOrderStatusException;
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
        if (item == null) {
            throw new IllegalArgumentException("A null item cannot be added to an order.");
        } else {
            foodItems.add(item);
        }
    }

    /**
     * @param item
     * removes a single instance of the specified item from the foodItems list
     */
    public void removeItem(FoodItem item) {
        if (item == null) {
            throw new IllegalArgumentException("A null item cannot be removed from an order.");
        }
        if (foodItems.size() > 0) {
            foodItems.remove(item);
        } else {
            throw new IllegalArgumentException("Items cannot be removed from an empty order.");
        }
    }

    /**
     * changes the status of the order to cancelled, (registers it in the database?)
     */
    public void cancelOrder() {
        //if status is still being processed the order can be cancelled
        if (status == CREATING) {
            status = CANCELLED;
        }
        else if(status == CANCELLED) {
            throw new InvalidOrderStatusException("An order cannot be cancelled twice.");
        }
        else if(status == REFUNDED) {
            throw new InvalidOrderStatusException("Orders cannot be cancelled if they've been refunded.");
        }
        else if(status == COMPLETED) {
            throw new InvalidOrderStatusException("Completed orders cannot be cancelled.");
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
            throw new InvalidOrderStatusException("Cancelled orders cannot be refunded.");
        }
        else if(status == REFUNDED) {
            throw new InvalidOrderStatusException("Orders cannot be refunded twice.");
        }
        else if(status == CREATING) {
            throw new InvalidOrderStatusException("Incomplete orders cannot be cancelled.");
        }
    }

    /**
     * changes the status of the order to complete, (registers it in the database?)
     */
    public void completeOrder() {
        if (status == CREATING) {
            status = COMPLETED;
            //refund the order or something
        }
        else if(status == CANCELLED) {
            throw new InvalidOrderStatusException("Cancelled orders cannot be completed.");
        }
        else if(status == REFUNDED) {
            throw new InvalidOrderStatusException("Refunded orders cannot be completed.");
        }
        //Completed orders cannot be completed twice
        else if(status == COMPLETED) {
            throw new InvalidOrderStatusException("An order cannot be completed twice.");
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