package seng202.team1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.data.MemoryStorage;
import seng202.team1.util.NotEnoughStockException;
import seng202.team1.util.RecipeNotFoundException;
import seng202.team1.util.UnitType;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class KitchenTest {

    Kitchen kitchen;

    @BeforeEach
    void beforeEach() {
        MemoryStorage storage = MemoryStorage.getInstance();
        storage.resetInstance();
//        kitchen = new Kitchen(storage, storage);
        // TODO uncomment above when storage class implements RecipeDAO
    }

    @Test
    void testConstructor() {

        assertThrows(NullPointerException.class, () -> {
            new Kitchen(null);
        });

        assertNotNull(new Kitchen(MemoryStorage.getInstance())); // TODO how to test this works??
    }

    @Test
    void testCreateFoodItemsArgErrors() {

        // code is null
        assertThrows(NullPointerException.class, () -> {
            kitchen.createFoodItems(null, 1);
        });

        // amount is < 0
        assertThrows(IllegalArgumentException.class, () -> {
            kitchen.createFoodItems("12345", -1);
        });

        // amount = 0
        assertThrows(IllegalArgumentException.class, () -> {
            kitchen.createFoodItems("12345", 0);
        });

        // recipe doesn't exist enough stock in storage
        assertThrows(RecipeNotFoundException.class, () -> {
            kitchen.createFoodItems("12345", 1);
        });

        // recipe exists but not enough stock in storage
        // TODO add FoodItem with a recipe to memory storage with a code TESTITEM
        assertThrows(NotEnoughStockException.class, () -> {
            kitchen.createFoodItems("TESTITEM", 10);
        });
        // TODO reconsider how missing stock is handled, do we actually want to throw an error?
    }

    @Test
    void testCreateFoodItemsAmounts() {
        // recipe exists enough stock in storage
        FoodItem foodItem = new FoodItem("TESTITEM2", "test item 2", UnitType.COUNT);
        Recipe recipe = new Recipe(null, null, null, 1);
        foodItem.setRecipe(recipe);
        // TODO add foodItem to memory storage
        // TODO add required ingredients to storage
        List<FoodItem> expectedItems = Arrays.asList(foodItem);
        assertEquals(expectedItems, kitchen.createFoodItems("TESTITEM2", 1));

        // more than one
        List<FoodItem> expectedItems2 = Arrays.asList(foodItem, foodItem);
        assertEquals(expectedItems, kitchen.createFoodItems("TESTITEM2", 2));

        // TODO test when amount parameter does not perfectly divide the FoodItem's recipe creation amount
        FoodItem gramFoodItem = new FoodItem("GRAMITEM", "item measured grams", UnitType.GRAM);
        Recipe recipeFor100 = new Recipe(null, null, null, 100);
        // TODO add gramFoodItem to storage and ingredients

        kitchen.createFoodItems("GRAMITEM", 100); // TODO test this

        kitchen.createFoodItems("GRAMITEM", 60); // TODO test this

        kitchen.createFoodItems("GRAMITEM", 120); // TODO test this

    }
}