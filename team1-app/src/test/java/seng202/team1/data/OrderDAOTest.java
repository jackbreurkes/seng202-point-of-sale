package seng202.team1.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.UnitType;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

abstract class OrderDAOTest {

    protected OrderDAO orderStorage;
    private Order testOrder = new Order();
    private FoodItem testItem = new FoodItem("TEST", "Test Item", UnitType.COUNT);
    private FoodItem testItem2 = new FoodItem("TES2", "Another Test Item", UnitType.COUNT);

    @BeforeEach
    void resetTestOrder() {
        testOrder = new Order();
    }

    @Test
    void testGetAllOrders() {
        //test an empty set of orders has size 0
        Set<Order> items = orderStorage.getAllOrders();
        assertEquals(0, items.size());

        orderStorage.addOrder(testOrder);
        items = orderStorage.getAllOrders();
        assertEquals(1, items.size());
        assertTrue(items.contains(testOrder));

    }

    @Test
    void testGetOrderById() {
        testOrder.addItem(testItem);
        orderStorage.addOrder(testOrder);


        assertEquals(testOrder, orderStorage.getOrderByID(testOrder.getOrderID()));

        //cant get an order with the default ID
        Order testOrder2 = new Order();
        assertThrows(InvalidDataCodeException.class, () -> {
            orderStorage.getOrderByID(testOrder2.getOrderID());
        });

        //cant get an order that doesn't exist in the system
        assertNull(orderStorage.getOrderByID(testOrder.getOrderID() + 1));
    }

    @Test
    void testAddOrder() {

        //cant add an empty order.
        assertThrows(IllegalArgumentException.class, () -> {
            orderStorage.addOrder(testOrder);
        });

        testOrder.addItem(testItem);
        orderStorage.addOrder(testOrder);
        assertEquals(testOrder, orderStorage.getOrderByID(testOrder.getOrderID()));

        // test adding an order with an id already existing
        assertThrows(InvalidDataCodeException.class, () -> {
            orderStorage.addOrder(testOrder);
        });

        // test adding null
        assertThrows(NullPointerException.class, () -> {
            orderStorage.addOrder(null);
        });
    }

    @Test
    void testUpdateOrder() {

        //adds an order to storage, then updates the order and calls updateOrder, then checks the order was correctly updated
        testOrder.addItem(testItem);
        orderStorage.addOrder(testOrder);
        int orderId = testOrder.getOrderID();

        testOrder.submitOrder();
        orderStorage.updateOrder(testOrder);
        System.out.println(testOrder);
        System.out.println(orderStorage.getOrderByID(orderId));
        assertEquals(testOrder, orderStorage.getOrderByID(orderId));

        //makes sure you cant update a null order
        assertThrows(NullPointerException.class, () -> {
            orderStorage.updateOrder(null);
        });

        // makes sure you can't update an order with a nonexisting Id
        testOrder.setId(orderId + 1);
        assertThrows(InvalidDataCodeException.class, () -> {
            orderStorage.updateOrder(testOrder);
        });
    }

    @Test
    void testRemoveOrder() {

        //cant remove from an order that isnt in the system
        assertThrows(NullPointerException.class, () -> {
            orderStorage.removeOrder(testOrder.getOrderID());
        });
        orderStorage.addOrder(testOrder);

        //checks orders are correctly removed
        orderStorage.removeOrder(testOrder.getOrderID());
        assertNull(orderStorage.getOrderByID(testOrder.getOrderID()));

        //cant remove an order that has already been removed
        assertThrows(InvalidDataCodeException.class, () -> {
            orderStorage.removeOrder(testOrder.getOrderID());
        });


        //cant remove an order with a negative ID number
        assertThrows(NullPointerException.class, () -> {
            orderStorage.removeOrder(-1);
        });


    }
}
