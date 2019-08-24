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
    FoodItem testItem;
    FoodItem testItem2;
    List<FoodItem> testList;


    @BeforeEach
    void beforeEach() {
        testOrder = new Order();
        testItem = new SuppliedFoodItem("TEST", "Test Item", UnitType.COUNT);
    }


    @Test
    void testGetOrderContents() {
        assertNull(testOrder.getOrderContents());
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
    }

    @Test
    void testCancelOrder(){
        //TODO
        testOrder.addItem(testItem);
        //make a second item and add it to the order, check the order is as expected
        testItem2 = new SuppliedFoodItem("TESB", "Test Item 2", UnitType.COUNT);
        testOrder.addItem(testItem2);
        testOrder.cancelOrder();

        //makes sure that a cancelled order is null (is this what is should do?)
        assertEquals(testOrder, null);

        //makes sure an order can't be cancelled twice
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.cancelOrder();
        });

        //Makes sure a cancelled order cannot be completed
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.completeOrder();
        });
    }

    @Test
    void testCompleteOrder(){
        //TODO
        testOrder.addItem(testItem);
        testItem2 = new SuppliedFoodItem("TESB", "Test Item 2", UnitType.COUNT);
        testOrder.addItem(testItem2);
        testOrder.completeOrder();

        //makes sure that a completed order is null (is this what is should do?)
        assertEquals(testOrder, null);

        //Makes sure an order cannot be completed twice
        assertThrows(IllegalArgumentException.class, () -> {
            testOrder.completeOrder();
        });
    }

    @Test
    void testRefundOrder(){
        //TODO
        //sets a starting amount of money
        double cash = 20;
        testOrder.completeOrder();
    }



}
