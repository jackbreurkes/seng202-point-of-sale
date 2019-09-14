package seng202.team1.model;

import org.joda.money.BigMoney;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.util.UnitType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    void testRecipe() {
        Set<FoodItem> ingredients = new HashSet<>();
        Set<FoodItem> addableIngredients = new HashSet<>();
        Map<String, Integer> ingredientAmounts = new HashMap<>();

        FoodItem ingredient1 = new FoodItem("INGRED1", "test ingredient 1", UnitType.COUNT);
        ingredients.add(ingredient1);
        ingredientAmounts.put(ingredient1.getCode(), 1);
        Recipe testRecipe = new Recipe(ingredients, addableIngredients, ingredientAmounts, 1);

        // default recipe
        assertNull(testItem.getRecipe());

        // trying to remove the current recipe when no recipe is set
        testItem.setRecipe(null);
        assertNull(testItem.getRecipe());

        // setting a FoodItem's recipe
        testItem.setRecipe(testRecipe);
        assertEquals(testRecipe, testItem.getRecipe());

        FoodItem ingredient2 = new FoodItem("INGRED2", "test ingredient 2", UnitType.COUNT);
        ingredients.add(ingredient2);
        ingredientAmounts.put(ingredient2.getCode(), 1);
        testRecipe = new Recipe(ingredients, addableIngredients, ingredientAmounts, 2);

        // overriding the existing recipe
        testItem.setRecipe(testRecipe);
        assertEquals(testRecipe, testItem.getRecipe());

        // removing the existing recipe
        testItem.setRecipe(null);
        assertNull(testItem.getRecipe());
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
    void testIsVegetarian() {
        assertEquals(false, testItem.getIsVegetarian()); // default value

        testItem.setIsVegetarian(true);
        assertEquals(true, testItem.getIsVegetarian());
        assertEquals(false, testItem.getIsVegan()); // should not change

        testItem.setIsVegan(true);
        testItem.setIsVegetarian(false);
        assertEquals(false, testItem.getIsVegetarian());
        assertEquals(false, testItem.getIsVegan()); // should change from true to false

        testItem.setIsVegetarian(true);
        assertEquals(true, testItem.getIsVegetarian());
        assertEquals(false, testItem.getIsVegan()); // should not change
    }

    @Test
    void testIsVegan() {
        assertEquals(false, testItem.getIsVegan()); // default value
        assertEquals(false, testItem.getIsVegetarian()); // default value

        testItem.setIsVegan(true);
        assertEquals(true, testItem.getIsVegan());
        assertEquals(true, testItem.getIsVegetarian()); // should change from false to true

        testItem.setIsVegan(false);
        assertEquals(false, testItem.getIsVegan());
        assertEquals(true, testItem.getIsVegetarian()); // should not change
    }

    @Test
    void testIsGlutenFree() {
        assertEquals(false, testItem.getIsGlutenFree()); // default value

        testItem.setIsGlutenFree(true);
        assertEquals(true, testItem.getIsGlutenFree());

        testItem.setIsGlutenFree(false);
        assertEquals(false, testItem.getIsGlutenFree());
    }

    @Disabled
    @Test
    void testCost() {
        // default value
        assertEquals(BigMoney.parse("NZD 0.00"), testItem.getCost());

        // null value
        assertThrows(NullPointerException.class, () -> {
            testItem.setCost(null);
        });

        // set cost
        testItem.setCost(BigMoney.parse("NZD 5.86"));
        assertEquals(BigMoney.parse("NZD 5.86"), testItem.getCost());

        // more than 2 d.p.
        testItem.setCost(BigMoney.parse("NZD 0.05439273"));
        assertEquals(BigMoney.parse("NZD 0.05439273"), testItem.getCost());

        // negative cost
        assertThrows(IllegalArgumentException.class, () -> {
            testItem.setCost(BigMoney.parse("NZD -0.5"));
        });
    }

    // TODO test equals even though it's autogenerated? yeah just to check things still work
    // TODO test toString even though its autogenerated?

}