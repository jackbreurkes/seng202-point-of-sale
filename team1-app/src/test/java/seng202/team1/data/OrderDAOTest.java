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
        items = orderStorage.getAllOrders();
        assertEquals(1, items.size());
        assertTrue(items.contains(testOrder));

    }

    @Test
    void testGetOrderByCode() {
        testOrder.addItem(testItem);
        orderStorage.addOrder(testOrder);

        assertEquals(testOrder, orderStorage.getOrderByID(testOrder.getOrderID()));

        //cant get an order with a negative ID
        assertThrows(InvalidDataCodeException.class, () -> {
            orderStorage.getOrderByID(-1);
        });

        //cant get an order that doesnt exist in the system
        assertThrows(InvalidDataCodeException.class, () -> {
            orderStorage.getOrderByID(2);
        });
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
        assertEquals(testOrder, orderStorage.getOrderByID(testOrder.getOrderID()));

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

        //adds an order to storage, then updates the order and calls updateOrder, then checks the order was correctly updated
        testOrder.addItem(testItem);
        orderStorage.addOrder(testOrder);

        testOrder.addItem(testItem2);
        orderStorage.updateOrder(testOrder);
        assertEquals(testOrder, orderStorage.getOrderByID(testOrder.getOrderID()));

        //makes sure you cant update a null order
        assertThrows(NullPointerException.class, () -> {
            orderStorage.updateOrder(null);
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
