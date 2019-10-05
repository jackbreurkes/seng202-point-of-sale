package seng202.team1.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.OrderStatus;
import seng202.team1.util.UnitType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
    void testGetAllSubmittedOrders() {
        Set<Order> expectedResult = new HashSet<Order>();

        // no orders in database
        assertEquals(expectedResult, orderStorage.getAllSubmittedOrders());

        // single submitted order
        Order testOrder1 = new Order();
        testOrder1.setId(1);
        testOrder1.setStatus(OrderStatus.SUBMITTED);
        orderStorage.addOrder(testOrder1);
        expectedResult.add(testOrder1);
        assertEquals(expectedResult, orderStorage.getAllSubmittedOrders());

        // two submitted orders
        Order testOrder2 = new Order();
        testOrder2.setId(2);
        testOrder2.setStatus(OrderStatus.SUBMITTED);
        orderStorage.addOrder(testOrder2);
        expectedResult.add(testOrder2);
        assertEquals(expectedResult, orderStorage.getAllSubmittedOrders());

        // un-submitted orders in storage
        Order testOrder3 = new Order();
        testOrder3.setId(3);
        testOrder3.setStatus(OrderStatus.COMPLETED);
        orderStorage.addOrder(testOrder3);
        Order testOrder4 = new Order();
        testOrder4.setId(4);
        testOrder4.setStatus(OrderStatus.REFUNDED);
        orderStorage.addOrder(testOrder4);
        assertEquals(expectedResult, orderStorage.getAllSubmittedOrders());

        // existing submitted order marked as completed
        expectedResult.remove(testOrder1);
        testOrder1.cancelOrder();
        orderStorage.updateOrder(testOrder1);
        assertEquals(expectedResult, orderStorage.getAllSubmittedOrders());
    }

    @Test
    void testGetOrderById() {
        testOrder.addItem(testItem);
        orderStorage.addOrder(testOrder);

        assertEquals(testOrder, orderStorage.getOrderByID(testOrder.getOrderID()));

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

        Order creatingOrder = new Order();
        creatingOrder.addItem(testItem);
        creatingOrder.setStatus(OrderStatus.CREATING);
        // test adding a creating order
        assertThrows(IllegalArgumentException.class, () -> {
            orderStorage.addOrder(creatingOrder);
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
