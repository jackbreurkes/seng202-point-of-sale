package seng202.team1.model;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import seng202.team1.util.InvalidOrderStatusException;
import seng202.team1.util.OrderStatus;
import seng202.team1.model.FoodItem;


import java.util.*;

import static seng202.team1.util.OrderStatus.*;

/**
 * 
 */
public class Order {

    private String code;
    private List<FoodItem> foodItems = new ArrayList<FoodItem>();
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

        if (status == CREATING) {
            if (item == null) {
                throw new IllegalArgumentException("A null item cannot be added to an order.");
            } else {
                foodItems.add(item);
            }
        } else {
            throw new InvalidOrderStatusException("Only orders that are still in the creation process can have items added to them");
        }
    }

    /**
     * @param item
     * removes a single instance of the specified item from the foodItems list
     */
    public void removeItem(FoodItem item) {
        if (status == CREATING) {
            if (item == null) {
                throw new IllegalArgumentException("A null item cannot be removed from an order.");
            }
            if (foodItems.size() > 0) {
                foodItems.remove(item);
            } else {
                throw new IllegalArgumentException("Items cannot be removed from an empty order.");
            }
        } else {
            throw new InvalidOrderStatusException("Only orders that are still in the creation process can have items removed from them");
        }
    }

    /**
     * changes the status of the order to cancelled, (registers it in the database?)
     * Only CREATING orders can be cancelled.
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
        if (status == COMPLETED) {
            status = REFUNDED;
        }
        else if(status == CANCELLED) {
            throw new InvalidOrderStatusException("Cancelled orders cannot be refunded.");
        }
        else if(status == REFUNDED) {
            throw new InvalidOrderStatusException("Orders cannot be refunded twice.");
        }
        else if(status == CREATING) {
            throw new InvalidOrderStatusException("Incomplete orders cannot be refunded.");
        }
    }

    /**
     * changes the status of the order to complete, (registers it in the database?)
     * only CREATING orders can be set to complete.
     */
    public void completeOrder() {
        if (status == CREATING) {
            status = COMPLETED;
        }
        else if(status == CANCELLED) {
            throw new InvalidOrderStatusException("Cancelled orders cannot be completed.");
        }
        else if(status == REFUNDED) {
            throw new InvalidOrderStatusException("Refunded orders cannot be completed.");
        }
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

    /**
     * returns the cost of the order
     * @return the total cost of the order
     */
    public Money getCost() {
        // TODO add money stuff to tests
        return Money.parse("NZD 0.00");
    }

    public String getOrderCode() { return code; }
}

