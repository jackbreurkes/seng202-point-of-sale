package seng202.team1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.util.DietaryLogic;
import seng202.team1.util.UnitType;

import static org.junit.jupiter.api.Assertions.*;

class FoodItemTest {

    FoodItem testItem;

    @BeforeEach
    void beforeEach() {
        testItem = new FoodItem("TEST", "Test Item", UnitType.COUNT);
    }

    @Test
    void testConstructor() {
        // how should we test abstract classes??
        FoodItem item1 = new FoodItem("COD", "1", UnitType.COUNT);
        assertEquals("COD", item1.getCode());
        assertEquals("1", item1.getName());


        FoodItem item2 = new FoodItem("THISIS10", "thisstring is twenty", UnitType.COUNT);
        assertEquals(item2.getName(), "thisstring is twenty");

        // code not enough chars
        assertThrows(IllegalArgumentException.class, () -> {
            new FoodItem("CC", "E", UnitType.COUNT);
        });

        // code too many chars
        assertThrows(IllegalArgumentException.class, () -> {
            new FoodItem("elevenchars", "E", UnitType.COUNT);
        });

        // code not uppercase alphanumeric
        assertThrows(IllegalArgumentException.class, () -> {
            new FoodItem("code", "Test Name", UnitType.COUNT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new FoodItem("COD\u2202", "Test Name", UnitType.COUNT);
        });

        // ask about testing name for characters? some foreign places might use funky chars
        // damn should we even assume codes can be uppercase alphanumeric? should ask, or check our doc for multilingual support

        // should we be testing if setName is called vs name = "whatever" or is that overkill

        assertThrows(NullPointerException.class, () -> {
            new FoodItem(null, "Test Name", UnitType.COUNT);
        });
    }

    @Test
    void testName() {
        testItem.setName("test rename");
        assertEquals("test rename", testItem.getName());

        testItem.setName("1");
        assertEquals("1", testItem.getName());

        testItem.setName("thisstring is twenty");
        assertEquals("thisstring is twenty", testItem.getName());

        assertThrows(IllegalArgumentException.class, () -> {
            testItem.setName("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            testItem.setName("thisstringistwentyone");
        });

        assertThrows(NullPointerException.class, () -> {
            testItem.setName(null);
        });
    }

    @Test
    void testCaloriesPerUnit() {
        double cal = 0.45;
        testItem.setCaloriesPerUnit(cal);
        assertEquals(cal, testItem.getCaloriesPerUnit());

        double negCal = -0.1;
        assertThrows(IllegalArgumentException.class, () -> {
            testItem.setCaloriesPerUnit(negCal);
        });
    }

    @Test
    void testUnit() {
        testItem.setUnit(UnitType.GRAM);
        assertEquals(UnitType.GRAM, testItem.getUnit());

        testItem.setUnit(UnitType.ML);
        assertEquals(UnitType.ML, testItem.getUnit());

        testItem.setUnit(UnitType.COUNT);
        assertEquals(UnitType.COUNT, testItem.getUnit());

        assertThrows(NullPointerException.class, () -> {
            testItem.setUnit(null);
        });
    }

    @Test
    @Disabled
    void testDietary() {
        // default values
        assertEquals(DietaryLogic.NO, testItem.getIsVegetarian());
        assertEquals(DietaryLogic.NO, testItem.getIsVegan());
        assertEquals(DietaryLogic.NO, testItem.getIsGlutenFree());

        testItem.setIsVegan(DietaryLogic.YES);
        assertEquals(DietaryLogic.YES, testItem.getIsVegan());
        assertEquals(DietaryLogic.YES, testItem.getIsVegetarian());

        testItem.setIsVegan(DietaryLogic.OPTIONAL);
        assertEquals(DietaryLogic.OPTIONAL, testItem.getIsVegan());
        assertNotEquals(DietaryLogic.NO, testItem.getIsVegetarian()); // must be YES or OPTIONAL

        testItem.setIsVegan(DietaryLogic.NO);
        assertEquals(DietaryLogic.NO, testItem.getIsVegan());

        testItem.setIsVegetarian(DietaryLogic.YES);
        assertEquals(DietaryLogic.YES, testItem.getIsVegetarian());

        testItem.setIsGlutenFree(DietaryLogic.YES);
        assertEquals(DietaryLogic.YES, testItem.getIsGlutenFree());

        // do we need tests for every possibility??

        assertThrows(NullPointerException.class, () -> {
            testItem.setIsVegetarian(null);
        });

        assertThrows(NullPointerException.class, () -> {
            testItem.setIsVegan(null);
        });

        assertThrows(NullPointerException.class, () -> {
            testItem.setIsGlutenFree(null);
        });
    }

}