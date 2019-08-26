package seng202.team1.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.model.SuppliedFoodItem;
import seng202.team1.util.UnitType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class OrderTest {

    Order testOrder;
    SuppliedFoodItem testItem;
    SuppliedFoodItem testItem2;
    SuppliedFoodItem testItem3;
    List<FoodItem> testList;


    @BeforeEach
    void beforeEach() {
        testOrder = new Order();
        testItem = new SuppliedFoodItem("TEST", "Test Item", UnitType.COUNT);
    }


    @Test
    void testGetOrderContents() {
        assertNull(testOrder.getOrderContents());
        testOrder.addItem(testItem);
        testList = new ArrayList<FoodItem>();
        testList.add(testItem);
        assertEquals(testOrder.getOrderContents(), testList);
    }

    @Test
    void testAddItem() {
        //initializes a list to check against the order
        testList = new ArrayList<FoodItem>();
        testList.add(testItem);
        //adds an item and the checks the order for the item
        testOrder.addItem(testItem);
        assertEquals(testOrder.getOrderContents(), testList);

        //make a second item and add it to the order, check the order is as expected
        testItem2 = new SuppliedFoodItem("TESB", "Test Item 2", UnitType.COUNT);
        testList.add(testItem2);
        testOrder.addItem(testItem2);
        assertEquals(testOrder.getOrderContents(), testList);

        //try to add another instance of testItem and ensure it correctly enters the list
        testList.add(testItem);
        testOrder.addItem(testItem);
        assertEquals(testOrder.getOrderContents(), testList);

        //adds an item that doesn't fit proper criteria of an item
        testItem3 = new SuppliedFoodItem("COD\u2202", "Test Name", UnitType.COUNT);
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.addItem(testItem);
        });
    }

    @Test
    void testRemoveItem(){
        //initializes a list to check against the order
        testList = new ArrayList<FoodItem>();
        testList.add(testItem);
        //adds an item
        testOrder.addItem(testItem);
        assertEquals(testOrder.getOrderContents(), testList);

        //removes the item and checks it is no longer in the order
        testOrder.removeItem(testItem);
        assertEquals(testOrder.getOrderContents(), null);

        //checks items cannot be removed from an empty order
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.removeItem(testItem);
        });

        //tests that removing an item only removes one instance of said item
        testOrder.addItem(testItem);
        testOrder.addItem(testItem);
        testOrder.removeItem(testItem);
        assertEquals(testOrder.getOrderContents(), testList);

        //checks that removing the item again will make the order empty
        testOrder.removeItem(testItem);
        assertNull(testOrder.getOrderContents());

        //tests that an item that is not in the list cannot be removed
        testItem2 = new SuppliedFoodItem("TESB", "Test Item 2", UnitType.COUNT);
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.removeItem(testItem2);
        });
    }

    @Test
    void testCancelOrder(){
        //TODO come up with some more tests, write a custom exception for whe an order is cancelled twice
        testOrder.addItem(testItem);
        testItem2 = new SuppliedFoodItem("TESB", "Test Item 2", UnitType.COUNT);
        testOrder.addItem(testItem2);
        testOrder.cancelOrder();
        // TODO specify how the order status works (is it a string, etc)
        // checks the status of the order has correctly been set to cancelled
        assertEquals(testOrder.getOrderStatus(), "Cancelled");

        //makes sure an order can't be cancelled twice
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.cancelOrder();
            // TODO maybe write a custom exception for this?
        });

        //checks a cancelled order cannot have items added to it
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.addItem(testItem);
        });

        //checks a cancelled order cannot have items removed from it
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.removeItem(testItem);
        });

        //Makes sure a cancelled order cannot be completed
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.completeOrder();
        });

        // TODO does cancelling orders happen at a certain time? different behaviour for different order statuses
    }

    @Test
    void testCompleteOrder(){
        //TODO come up with some more tests
        testOrder.addItem(testItem);
        testItem2 = new SuppliedFoodItem("TESB", "Test Item 2", UnitType.COUNT);
        testOrder.addItem(testItem2);
        testOrder.completeOrder();
        // checks the status of the order has correctly been set to completed
        assertEquals(testOrder.getOrderStatus(), "Complete");

        //Makes sure an order cannot be completed twice
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.completeOrder();
        });

        //checks a completed order cannot have items removed from it
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.removeItem(testItem);
        });

        //Makes sure a completed order cannot be cancelled
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.completeOrder();
        });
    }

    @Test
    void testRefundOrder(){
        //TODO sort out the actual refund part (money stuff)
        //sets a starting amount of money
        double cash = 20;

        //makes sure an incomplete order cannot be refunded
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.refundOrder();
        });

        testOrder.completeOrder();
        testOrder.refundOrder();

        // checks the status of the order has correctly been set to refunded
        assertEquals(testOrder.getOrderStatus(), "Refunded");

        //makes sure a refunded order cannot be completed
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.completeOrder();
        });

        //checks a refunded order cannot have items removed from it
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.removeItem(testItem);
        });

        //Makes sure a refunded order cannot be cancelled
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.completeOrder();
        });

        //Makes sure an order cannot be refunded twice
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.refundOrder();
        });

    }



}
