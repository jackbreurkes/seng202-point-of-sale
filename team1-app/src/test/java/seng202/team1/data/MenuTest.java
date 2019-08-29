package seng202.team1.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Menu;
import seng202.team1.model.Order;
import seng202.team1.util.UnitType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;



public class MenuTest {

    Menu menu;
    FoodItem testFood1;
    FoodItem testFood2;
    FoodItem testFood3;
    List<FoodItem> testList;

    @BeforeEach
    void beforeEach() {
        menu = new Menu();
        testFood1 = new FoodItem("TS01", "Test Food 1", UnitType.ML);
        testFood2 = new FoodItem("TS02", "Test food 2", UnitType.GRAM);
    }

    @Test
    void testAddItem() {
        assertNull(menu.getMenuItems());
        menu.addItem(testFood1);
        testList = new ArrayList<FoodItem>();
        testList.add(testFood1);
        assertEquals(menu.getMenuItems(), testList);
    }

    @Test
    void testRemoveItem() {
        // initializes a list to check against the order
        testList = new ArrayList<FoodItem>();
        // adds an item on the testList on two items on the menu
        testList.add(testFood1);
        menu.addItem(testFood1);
        menu.addItem(testFood2);
        // checks if testList and menu are not equal
        assertNotEquals(menu.getMenuItems(), testList);
        // removes item from menu and checks if equal with testList
        menu.removeItem(testFood2);
        assertEquals(menu.getMenuItems(), testList);
        // removes item from menu and checks if it is empty
        menu.removeItem(testFood1);
        assertNull(menu.getMenuItems());
    }
    @Test
    void testRemoveMenu() {
        testList = new ArrayList<FoodItem>();
        testList.add(testFood1);
        testList.add(testFood2);
        testList.add(testFood3);
        menu.addItem(testFood1);
        menu.addItem(testFood2);
        menu.addItem(testFood3);
        assertEquals(menu.getMenuItems(), testList);
        menu.removeMenu();
        assertNull(menu.getMenuItems());
    }
}




