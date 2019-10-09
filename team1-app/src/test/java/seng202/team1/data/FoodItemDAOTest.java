package seng202.team1.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Recipe;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.RecipeBuilder;
import seng202.team1.util.UnitType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

abstract class FoodItemDAOTest {

    protected FoodItemDAO foodStorage;
    protected FoodItem testItem;
    protected Recipe testRecipe;

    @BeforeEach
    void testtest() {
        testItem  = new FoodItem("ITEM1", "Oil", UnitType.GRAM);

        FoodItem testIngredient = new FoodItem("TESTINGR", "test ingredient", UnitType.COUNT);
        Set<FoodItem> ingredients = new HashSet<>();
        Set<FoodItem> addableIngredients = new HashSet<>();
        Map<String, Integer> ingredientAmounts = new HashMap<>();
        ingredients.add(testIngredient);
        ingredientAmounts.put(testIngredient.getCode(), 1);
        testRecipe = new Recipe(ingredients, addableIngredients, ingredientAmounts, 1);
    }

    @Test
    void testReturnsSeparateInstance() {
        foodStorage.addFoodItem(testItem, 1);
        assertEquals(testItem, foodStorage.getFoodItemByCode(testItem.getCode()));
        assertNotSame(testItem, foodStorage.getFoodItemByCode(testItem.getCode()));
    }

    @Test
    void testGetDoesNotAllowModification() {
        foodStorage.addFoodItem(testItem, 1);
        FoodItem testFoodItemFromStorage = foodStorage.getFoodItemByCode(testItem.getCode());
        testFoodItemFromStorage.setUnit(UnitType.ML);
        assertNotEquals(testFoodItemFromStorage, foodStorage.getFoodItemByCode(testItem.getCode()));
    }

    @Test
    void testGetByCode() {
        assertNull(foodStorage.getFoodItemByCode(testItem.getCode()));

        foodStorage.addFoodItem(testItem, 1);
        assertEquals(testItem, foodStorage.getFoodItemByCode(testItem.getCode()));

        assertNull(foodStorage.getFoodItemByCode("UNUSED CODE"));

        assertNull(foodStorage.getFoodItemByCode(null));

        // TODO invalid codes
    }

    @Test
    void testGetAll() {
        // empty
        Set<FoodItem> items = foodStorage.getAllFoodItems();
        assertEquals(0, items.size());

        // single item
        foodStorage.addFoodItem(testItem, 0);
        items = foodStorage.getAllFoodItems();
        assertEquals(1, items.size());
        assertTrue(items.contains(testItem));

        // empty after removing
        foodStorage.removeFoodItem(testItem.getCode());
        items = foodStorage.getAllFoodItems();
        assertEquals(0, items.size());
        assertFalse(items.contains(testItem));
    }

    @Test
    void testAdd() {
        foodStorage.addFoodItem(testItem, 1000);
        assertEquals(testItem,
                foodStorage.getFoodItemByCode(testItem.getCode()));

        // test adding an already added item
        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.addFoodItem(testItem, 1000);
        });

        // test adding null
        assertThrows(NullPointerException.class, () -> {
            foodStorage.addFoodItem(null, 1000);
        });
    }

    @Test
    void testUpdate() {
        foodStorage.addFoodItem(testItem, 1000);

        FoodItem alteredItem = new FoodItem("ITEM1", "Canola Oil", UnitType.GRAM);
        foodStorage.updateFoodItem(alteredItem);
        assertEquals(alteredItem, foodStorage.getFoodItemByCode(testItem.getCode()));

        // the item's code does not exist within storage
        FoodItem notStoredItem = new FoodItem("ITEM2", "Eggs", UnitType.COUNT);
        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.updateFoodItem(notStoredItem);
        });

        assertThrows(NullPointerException.class, () -> {
            foodStorage.updateFoodItem(null);
        });
    }

    @Test
    void testRemove() {
        foodStorage.addFoodItem(testItem, 1000);

        // removing an existing FoodItem
        foodStorage.removeFoodItem(testItem.getCode());
        assertNull(foodStorage.getFoodItemByCode(testItem.getCode()));

        // removing a non-existent FoodItem
        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.removeFoodItem(testItem.getCode());
        });
    }

    @Test
    void testGetSetStock() {
        foodStorage.addFoodItem(testItem, 1000);
        assertEquals(1000, foodStorage.getFoodItemStock(testItem.getCode()));

        foodStorage.setFoodItemStock(testItem.getCode(), 500);
        assertEquals(500, foodStorage.getFoodItemStock(testItem.getCode()));

        foodStorage.setFoodItemStock(testItem.getCode(), 0);
        assertEquals(0, foodStorage.getFoodItemStock(testItem.getCode()));

        assertThrows(IllegalArgumentException.class, () -> {
            foodStorage.setFoodItemStock(testItem.getCode(), -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            foodStorage.setFoodItemStock(testItem.getCode(), Integer.MAX_VALUE + 1);
        });

        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.setFoodItemStock("UNUSED CODE", 100);
        });

        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.setFoodItemStock(null, 100);
        });
    }

    @Test
    void testGetWithRecipe() {
        testItem.setRecipe(testRecipe);
        foodStorage.addFoodItem(testItem, 0);

        assertEquals(testRecipe, foodStorage.getFoodItemByCode(testItem.getCode()).getRecipe());
    }

    @Test
    void testUpdateWithRecipe() {
        foodStorage.addFoodItem(testItem, 1000);
        assertEquals(testItem, foodStorage.getFoodItemByCode(testItem.getCode()));

        // adding a recipe
        testItem.setRecipe(testRecipe);
        foodStorage.updateFoodItem(testItem);
        assertEquals(testItem, foodStorage.getFoodItemByCode(testItem.getCode()));

        // changing the recipe
        RecipeBuilder recipeBuilder = new RecipeBuilder();
        recipeBuilder.loadExistingRecipeData(testRecipe);
        recipeBuilder.addIngredient(new FoodItem("INGR2", "test ingredient 2", UnitType.COUNT), 3);
        testItem.setRecipe(recipeBuilder.generateRecipe(5));
        foodStorage.updateFoodItem(testItem);
        assertEquals(testItem, foodStorage.getFoodItemByCode(testItem.getCode()));

        // removing the recipe
        testItem.setRecipe(null);
        foodStorage.updateFoodItem(testItem);
        assertEquals(testItem, foodStorage.getFoodItemByCode(testItem.getCode()));
    }


}