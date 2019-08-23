package seng202.team1.data;

import seng202.team1.model.Order;

import java.util.Set;

public interface OrderStorage {

    /**
     * returns all the Orders stored in the system.
     * @return a List of all Orders stored in the system
     */
    Set<Order> getAllOrders();

    /**
     * gets a single Order from the system.
     * @param code the Order's unique code
     * @return the desired Order or null if not found
     */
    Order getOrderById(String code);

    /**
     * adds an Order to storage. the order will be stored using its code attribute.
     * @param order the Order to store
     */
    void addOrder(Order order);

    /**
     * sets the properties of a Order to those of a new Order.
     * the new order's code should be the same as the one that is being changed.
     * @param code the code of the order to edit
     * @param alteredOrder the new Order whose information should replace the old order's
     */
    void editOrder(String code, Order alteredOrder);

    /**
     * remove an Order from storage.
     * @param code the code of the Order to remove
     */
    void removeOrder(String code);

}
