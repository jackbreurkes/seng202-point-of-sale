package seng202.team1.model;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.util.DietaryLogic;
import seng202.team1.util.UnitType;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class FoodItemTest {

    FoodItem testItem;

    @BeforeEach
    void beforeEach() {
        testItem = new SuppliedFoodItem("TEST", "Test Item", UnitType.COUNT);
    }

    @Test
    void testConstructor() {
        // how should we test abstract classes??
        SuppliedFoodItem item1 = new SuppliedFoodItem("COD", "1", UnitType.COUNT);
        assertEquals("COD", item1.getCode());
        assertEquals("1", item1.getName());


        SuppliedFoodItem item2 = new SuppliedFoodItem("THISIS10", "thisstring is twenty", UnitType.COUNT);
        assertEquals(item2.getName(), "thisstring is twenty");

        // code not enough chars
        assertThrows(IllegalArgumentException.class, () -> {
            SuppliedFoodItem item = new SuppliedFoodItem("CC", "E", UnitType.COUNT);
        });

        // code too many chars
        assertThrows(IllegalArgumentException.class, () -> {
            SuppliedFoodItem item = new SuppliedFoodItem("1234567890", "E", UnitType.COUNT);
        });

        // code not uppercase alphanumeric
        assertThrows(IllegalArgumentException.class, () -> {
            SuppliedFoodItem item = new SuppliedFoodItem("code", "Test Name", UnitType.COUNT);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            SuppliedFoodItem item = new SuppliedFoodItem("COD\u2202", "Test Name", UnitType.COUNT);
        });

        // ask about testing name for characters? some foreign places might use funky chars
        // damn should we even assume codes can be uppercase alphanumeric? should ask, or check our doc for multilingual support

        // should we be testing if setName is called vs name = "whatever" or is that overkill

        assertThrows(IllegalArgumentException.class, () -> {
            SuppliedFoodItem item = new SuppliedFoodItem(null, "Test Name", UnitType.COUNT);
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

        assertThrows(IllegalArgumentException.class, () -> {
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

        assertThrows(IllegalArgumentException.class, () -> {
            testItem.setUnit(null);
        });
    }

    @Test
    void testDietary() {
        // default values
        assertEquals(DietaryLogic.NO, testItem.getIsVegetarian());
        assertEquals(DietaryLogic.NO, testItem.getIsVegan());
        assertEquals(DietaryLogic.NO, testItem.getIsGlutenFree());

        testItem.setIsVegan(DietaryLogic.YES);
        assertEquals(DietaryLogic.YES, testItem.getIsVegan());

        testItem.setIsVegan(DietaryLogic.OPTIONAL);
        assertEquals(DietaryLogic.OPTIONAL, testItem.getIsVegan());

        testItem.setIsVegan(DietaryLogic.NO);
        assertEquals(DietaryLogic.NO, testItem.getIsVegan());

        testItem.setIsVegetarian(DietaryLogic.YES);
        assertEquals(DietaryLogic.YES, testItem.getIsVegetarian());

        testItem.setIsGlutenFree(DietaryLogic.YES);
        assertEquals(DietaryLogic.YES, testItem.getIsGlutenFree());

        // do we need tests for every possibility??

        assertThrows(IllegalArgumentException.class, () -> {
            testItem.setIsVegetarian(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            testItem.setIsVegan(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            testItem.setIsGlutenFree(null);
        });
    }

}