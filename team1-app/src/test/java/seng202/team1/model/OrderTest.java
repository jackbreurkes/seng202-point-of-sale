package seng202.team1.model;

import org.joda.money.BigMoney;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.util.InvalidOrderStatusException;
import seng202.team1.util.OrderStatus;
import seng202.team1.util.UnitType;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static seng202.team1.util.OrderStatus.*;

class OrderTest {

    private Order testOrder;
    private FoodItem testItem;
    private FoodItem testItem2;
    private List<FoodItem> testList = new ArrayList<FoodItem>();


    @BeforeEach
    void beforeEach() {
        testOrder = new Order();
        testItem = new FoodItem("TEST", "Test Item", UnitType.COUNT);
        testItem2 = new FoodItem("TESB", "Test Item 2", UnitType.COUNT);
    }

    @Test
    void testGetOrderContents() {
        assertEquals(testOrder.getOrderContents(), testList);
        testOrder.addItem(testItem);
        testList.add(testItem);
        assertEquals(testOrder.getOrderContents(), testList);
    }

    @Disabled
    @Test
    void testGetOrderCode() {
        assertNotNull(testOrder.getOrderCode());
    }

    @Test
    void testGetCost() {
        // empty order
        assertEquals(Money.parse("NZD 0.0"), testOrder.getCost());

        // single item
        BigMoney testItemCost = BigMoney.parse("NZD 8.32");
        testItem.setCost(testItemCost);
        testOrder.addItem(testItem);
        assertEquals(testItemCost.toMoney(), testOrder.getCost());

        // multiple items
        BigMoney testItem2Cost = BigMoney.parse("NZD 3.21");
        testItem2.setCost(testItem2Cost);
        testOrder.addItem(testItem2);
        assertEquals(testItemCost.plus(testItem2Cost).toMoney(), testOrder.getCost());
        // TODO toMoney rounding mode? undecided as of yet. maybe custom?

        // after removing an item
        testOrder.removeItem(testItem);
        assertEquals(testItem2Cost.toMoney(), testOrder.getCost());

        // items with sub-cent cost
        testOrder.removeItem(testItem2);
        BigMoney subCentCost = BigMoney.parse("NZD 0.05324214");
        FoodItem subCentCostItem = new FoodItem("SUBCENT", "Oil", UnitType.ML);
        subCentCostItem.setCost(subCentCost);
        int amount = 27;
        for (int i = 0; i < amount; i++) { // add 27
            testOrder.addItem(subCentCostItem);
        }
        assertEquals(subCentCost.multipliedBy(amount).toMoney(RoundingMode.HALF_UP), testOrder.getCost());
    }

    @Test
    void testAddItem() {
        // TODO you should only be able to add items to CREATING orders

        //initializes a list to check against the order
        testList = new ArrayList<FoodItem>();
        testList.add(testItem);
        //adds an item and the checks the order for the item
        testOrder.addItem(testItem);
        assertEquals(testOrder.getOrderContents(), testList);

        //make a second item and add it to the order, check the order is as expected
        testItem2 = new FoodItem("TESB", "Test Item 2", UnitType.COUNT);
        testList.add(testItem2);
        testOrder.addItem(testItem2);
        assertEquals(testOrder.getOrderContents(), testList);

        //try to add another instance of testItem and ensure it correctly enters the list
        testList.add(testItem);
        testOrder.addItem(testItem);
        assertEquals(testOrder.getOrderContents(), testList);

        assertThrows(IllegalArgumentException.class, () -> testOrder.addItem(null));
    }

    @Test
    void testRemoveItem(){
        // TODO you should only be able to remove items from CREATING orders

        //initializes a list to check against the order
        testList.add(testItem);
        //adds an item
        testOrder.addItem(testItem);
        assertEquals(testOrder.getOrderContents(), testList);

        //removes the item and checks it is no longer in the order
        testOrder.removeItem(testItem);
        testList.remove(testItem);
        assertEquals(testOrder.getOrderContents(), testList);
        //checks items cannot be removed from an empty order
        assertThrows(IllegalArgumentException.class, () -> testOrder.removeItem(testItem));

        //tests that removing an item only removes one instance of said item
        testOrder.addItem(testItem);
        testOrder.addItem(testItem);
        testOrder.removeItem(testItem);
        testList.add(testItem);
        assertEquals(testOrder.getOrderContents(), testList);
        //checks that removing the item again will make the order empty
        testList.remove(testItem);
        testOrder.removeItem(testItem);
        assertEquals(testOrder.getOrderContents(), testList);

        //tests that an item that is not in the list cannot be removed
        assertThrows(IllegalArgumentException.class, () -> testOrder.removeItem(testItem2));

        assertThrows(IllegalArgumentException.class, () -> testOrder.addItem(null));
    }

    @Test
    void testCancelOrder(){
        testOrder.addItem(testItem);
        testOrder.addItem(testItem2);
        testOrder.cancelOrder();
        // checks the status of the order has correctly been set to cancelled
        assertEquals(testOrder.getOrderStatus(), CANCELLED);

        //makes sure an order can't be cancelled twice
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.cancelOrder());

        //checks a cancelled order cannot have items added to it
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.addItem(testItem));

        //checks a cancelled order cannot have items removed from it
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.removeItem(testItem));

        //Makes sure a cancelled order cannot be completed
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.completeOrder());
    }

    @Test
    void testCompleteOrder(){
        testOrder.addItem(testItem);
        testOrder.addItem(testItem2);
        testOrder.completeOrder();
        // checks the status of the order has correctly been set to completed
        assertEquals(testOrder.getOrderStatus(), COMPLETED);

        //Makes sure an order cannot be completed twice
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.completeOrder());

        //checks a completed order cannot have items removed from it
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.removeItem(testItem));

        //Makes sure a completed order cannot be cancelled
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.completeOrder());
    }

    @Disabled
    @Test
    void testSubmitOrder(){
        // TODO write tests here
    }

    @Test
    void testRefundOrder(){
        //makes sure an incomplete order cannot be refunded
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.refundOrder());

        testOrder.completeOrder();
        testOrder.refundOrder();

        // checks the status of the order has correctly been set to refunded
        assertEquals(testOrder.getOrderStatus(), REFUNDED);

        //makes sure a refunded order cannot be completed
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.completeOrder());

        //checks a refunded order cannot have items removed from it
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.removeItem(testItem));

        //Makes sure a refunded order cannot be cancelled
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.completeOrder());

        //Makes sure an order cannot be refunded twice
        assertThrows(InvalidOrderStatusException.class, () -> testOrder.refundOrder());

    }



}
