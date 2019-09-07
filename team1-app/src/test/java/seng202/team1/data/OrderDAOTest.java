package seng202.team1.data;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.UnitType;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@Disabled
class OrderDAOTest {

    private OrderDAO orderStorage;
    private Order testOrder;
    private FoodItem testItem = new FoodItem("TEST", "Test Item", UnitType.COUNT);
    private FoodItem testItem2 = new FoodItem("TES2", "Another Test Item", UnitType.COUNT);

    @BeforeEach
    void setupStorage() {
        //TODO set this up
        testOrder = new Order();
    }

    @Test
    void testGetAllOrders() {
        //test an empty set of orders has size 0
        Set<Order> items = orderStorage.getAllOrders();
        assertEquals(0, items.size());

        //TODO Jack used MemoryStorage.getInstance here, maybe refactor this to use that.
        orderStorage.addOrder(testOrder);

        assertEquals(1, items.size());
        assertTrue(items.contains(testOrder));

    }

    @Test
    void testGetOrderByCode() {
        //TODO Need to setup a system for Order Codes.
    }

    @Test
    void TestAddOrder() {

        //cant add an empty order.
        assertThrows(NullPointerException.class, () -> {
            orderStorage.addOrder(testOrder);
        });

        testOrder.addItem(testItem);
        orderStorage.addOrder(testOrder);
        //test for when order-code is implemented in the order class
        //assertEquals(testOrder, orderStorage.getOrderByCode(testOrder.getCode()));

        // test adding an already added order
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
        //TODO update the order using the provided order code? still gotta get order codes done

        /*
        //adds an order to storage, then updates the order and calls updateOrder, then checks the order was correctly updated
        testOrder.addItem(testItem);
        orderStorage.addOrder(testOrder);

        testOrder.addItem(testItem2);
        orderStorage.updateOrder(testOrder);
        assertEquals(testOrder, orderStorage.getOrderByCode(testOrder.getCode()));

        //makes sure you cant update a null order
        assertThrows(NullPointerException.class, () -> {
            orderStorage.updateOrder(null);
        });

         */
    }

    @Test
    void testRemoveOrder() {
        /*
        //cant remove from an order that isnt in the system
        assertThrows(NullPointerException.class, () -> {
            orderStorage.removeOrder(testOrder.getCode());
        });
        orderStorage.addOrder(testOrder);

        //needs getCode to work
        orderStorage.removeOrder(testOrder.getCode());
        assertNull(orderStorage.getOrderByCode(testOrder.getCode()));z
        //needs getCode to work
        assertThrows(InvalidDataCodeException.class, () -> {
            orderStorage.removeOrder(testOrder.getCode());
        });
        */

        //cant remove from a null order
        assertThrows(NullPointerException.class, () -> {
            orderStorage.removeOrder(null);
        });


    }
}
