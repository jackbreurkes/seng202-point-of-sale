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

/**
 * class containing the tests for any implementation of the OrderDAO interface.
 * this is abstract because orderStorage should be set to an instance of the concrete class under test.
 * concrete class tests can extend this test method to get access to all of its tests.
 */
abstract class OrderDAOTest {

    protected OrderDAO orderStorage;
    private Order testOrder;
    private FoodItem testItem;
    private FoodItem testItem2;


    private void assertEqualsExceptTimestamp(Order o1, Order o2) {
        o1.setLastUpdated(null);
        o2.setLastUpdated(null);
        assertEquals(o1, o2);
    }

    @BeforeEach
    void resetTestValues() {
        testItem = new FoodItem("TEST", "Test Item", UnitType.COUNT);
        testItem2 = new FoodItem("TES2", "Another Test Item", UnitType.COUNT);
        testOrder = new Order();
        testOrder.addItem(testItem);
        testOrder.addItem(testItem2);
        testOrder.submitOrder();
    }

    @Test
    void testReturnsSeparateInstance() {
        orderStorage.addOrder(testOrder);
        assertEquals(testOrder, orderStorage.getOrderByID(testOrder.getOrderID()));
        assertNotSame(testOrder, orderStorage.getOrderByID(testOrder.getOrderID()));
    }

    @Test
    void testGetAllOrders() {
        //test an empty set of orders has size 0
        Set<Order> expectedOrders = new HashSet<>();
        Set<Order> items = orderStorage.getAllOrders();
        assertEquals(expectedOrders, items);

        // test getting when order is in the database
        orderStorage.addOrder(testOrder);
        expectedOrders.add(testOrder);
        items = orderStorage.getAllOrders();
        assertEquals(expectedOrders, items);
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
        testOrder1.addItem(testItem);
        testOrder1.submitOrder();
        orderStorage.addOrder(testOrder1);
        expectedResult.add(testOrder1);
        assertEquals(expectedResult, orderStorage.getAllSubmittedOrders());

        // two submitted orders
        Order testOrder2 = new Order();
        testOrder2.setId(2);
        testOrder2.addItem(testItem);
        testOrder2.submitOrder();
        orderStorage.addOrder(testOrder2);
        expectedResult.add(testOrder2);
        assertEquals(expectedResult, orderStorage.getAllSubmittedOrders());

        // un-submitted orders in storage
        Order testOrder3 = new Order();
        testOrder3.setId(3);
        testOrder3.addItem(testItem);
        testOrder3.submitOrder();
        testOrder3.completeOrder();
        orderStorage.addOrder(testOrder3);
        Order testOrder4 = new Order();
        testOrder4.setId(4);
        testOrder4.addItem(testItem);
        testOrder4.submitOrder();
        testOrder4.completeOrder();
        testOrder4.refundOrder();
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
        orderStorage.addOrder(testOrder);

        assertEquals(testOrder, orderStorage.getOrderByID(testOrder.getOrderID()));

        //cant get an order that doesn't exist in the system
        assertNull(orderStorage.getOrderByID(testOrder.getOrderID() + 1));
    }

    @Test
    void testAddOrder() {
        //cant add a CREATING order.
        assertThrows(IllegalArgumentException.class, () -> {
            orderStorage.addOrder(new Order());
        });

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

        // test adding a creating order
        Order creatingOrder = new Order();
        creatingOrder.addItem(testItem);
        assertThrows(IllegalArgumentException.class, () -> {
            orderStorage.addOrder(creatingOrder);
        });
    }

    @Test
    void testUpdateOrder() {

        //adds an order to storage, then updates the order and calls updateOrder, then checks the order was correctly updated
        orderStorage.addOrder(testOrder);
        int orderId = testOrder.getOrderID();

        testOrder.cancelOrder();
        orderStorage.updateOrder(testOrder);
        assertEquals(testOrder, orderStorage.getOrderByID(orderId));

        //makes sure you cant update a null order
        assertThrows(NullPointerException.class, () -> {
            orderStorage.updateOrder(null);
        });

        // can't update an order to the CREATING status
        Order creatingTestOrder = new Order();
        creatingTestOrder.addItem(testItem);
        creatingTestOrder.setId(testOrder.getOrderID());
        assertThrows(IllegalArgumentException.class, () -> {
            orderStorage.updateOrder(creatingTestOrder);
        });

        // makes sure you can't update an order with a non-existing id
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
