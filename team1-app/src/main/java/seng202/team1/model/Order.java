package seng202.team1.model;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import seng202.team1.util.InvalidOrderStatusException;
import seng202.team1.util.OrderStatus;
import seng202.team1.model.FoodItem;


import java.math.RoundingMode;
import java.util.*;

import static seng202.team1.util.OrderStatus.*;

/**
 * 
 */
public class Order {

    private int ID;
    private List<FoodItem> foodItems = new ArrayList<FoodItem>();
    private String orderNote;
    private OrderStatus status = CREATING;
    // private Location location;
    // private Weather weather;



    /**
     * Default constructor
     */
    public Order(int Identifier) {
        this.ID = Identifier;
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
    public void submitOrder() {
        if (status == CREATING) {
            if (foodItems.size() > 0) {
                status = SUBMITTED;
            } else {
                throw new InvalidOrderStatusException("cannot submit an empty order");
            }
        }
        else {
            throw new InvalidOrderStatusException("Only orders with the CREATING status can be submitted.");
        }
    }


    /**
     * changes the status of the order to cancelled, (registers it in the database?)
     * Only CREATING orders can be cancelled.
     */
    public void cancelOrder() {
        //if status is still being processed the order can be cancelled
        if (status == CREATING) {
            if (foodItems.size() > 0) {
                status = CANCELLED;
            } else {
                throw new InvalidOrderStatusException("cannot cancel an empty order");
            }
        } else {
            throw new InvalidOrderStatusException("Only orders with the CREATING status can be cancelled.");
        }
    }

    /**
     * updates the status of the order to refunded (returns the amount of refunded money calculated by components?)
     * only completed orders can be refunded
     */
    public void refundOrder() {
        if (status == COMPLETED) {
            if (foodItems.size() > 0) {
                status = REFUNDED;
            } else {
                throw new InvalidOrderStatusException("cannot refund an empty order");
            }
        } else {
            throw new InvalidOrderStatusException("Only orders with the COMPLETED status can be refunded.");
        }
    }

    /**
     * changes the status of the order to complete
     * only CREATING orders can be set to complete.
     */
    public void completeOrder() {
        if (status == CREATING) {
            if (foodItems.size() > 0) {
                status = COMPLETED;
            } else {
                throw new InvalidOrderStatusException("cannot complete an empty order");
            }
        } else {
            throw new InvalidOrderStatusException("Only orders with the CREATING status can be completed.");
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
        BigMoney totalCost = BigMoney.parse("NZD 0.00");
        for (FoodItem item : foodItems) {
            totalCost = totalCost.plus(item.getCost());
        }
        return totalCost.toMoney(RoundingMode.HALF_UP);
        // TODO do we want this type of rounding here?
    }

    public int getOrderID() { return ID; }


}

