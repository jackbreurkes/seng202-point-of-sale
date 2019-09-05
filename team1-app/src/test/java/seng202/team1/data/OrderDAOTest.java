package seng202.team1.data;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import seng202.team1.model.Order;

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
    void getAllOrders() {
        //test an empty set of orders has size 0
        Set<Order> items = orderStorage.getAllOrders();
        assertEquals(0, items.size());

        //TODO Jack used MemoryStorage.getInstance here, maybe refactor this to use that.
        orderStorage.addOrder(testOrder);

        assertEquals(1, items.size());
        assertTrue(items.contains(testOrder));

    }

    @Test
    void getOrderByCode() {
        //TODO Need to setup a system for Order Codes.
    }

    @Test
    void addOrder() {

    }

    @Test
    void updateOrder() {

    }

    @Test
    void removeOrder() {

    }
}
