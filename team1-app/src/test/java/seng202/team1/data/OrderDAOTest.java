package seng202.team1.data;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import seng202.team1.model.Order;
import seng202.team1.util.InvalidDataCodeException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@Disabled
class OrderDAOTest {

    private OrderDAO orderStorage;
    private Order testOrder;

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
    }

    @Test
    void testRemoveOrder() {
        orderStorage.addOrder(testOrder);
        /*
        //needs getCode to work
        orderStorage.removeOrder(testOrder.getCode());
        assertNull(orderStorage.getOrderByCode(testOrder.getCode()));
        //needs getCode to work
        assertThrows(InvalidDataCodeException.class, () -> {
            orderStorage.removeOrder(testOrder.getCode());
        });
        */
    }
}
