package seng202.team1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.MemoryStorage;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.NotEnoughStockException;
import seng202.team1.util.RecipeNotFoundException;
import seng202.team1.util.UnitType;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class KitchenTest {

    FoodItemDAO storage;
    Kitchen kitchen;

    @BeforeEach
    void beforeEach() {
        storage = MemoryStorage.getInstance();
        DAOFactory.resetInstances();
        kitchen = new Kitchen(storage);
    }

    @Test
    void testConstructor() {
        // null storage instance
        assertThrows(NullPointerException.class, () -> {
            new Kitchen(null);
        });
    }

    @Test
    void testCreateFoodItemsArgErrors() {
        // FoodItem is null
        assertThrows(NullPointerException.class, () -> {
            kitchen.getFoodItemInstance(null);
        });

        // FoodItem doesn't exist
        assertThrows(InvalidDataCodeException.class, () -> {
            //kitchen.getFoodItemInstance("12345");
        });

        // FoodItem exists but has no recipe
        fail("not yet implemented");

        // recipe exists but not enough stock in storage
        FoodItem testItem = new FoodItem("TESTITEM", "test food item", UnitType.COUNT);
        FoodItem testIngredient = new FoodItem("ING1", "ingred", UnitType.COUNT);
        Set<FoodItem> ingredients = new HashSet<>(Arrays.asList(testIngredient));
        Map<String, Integer> ingredAmounts = new HashMap<>();
        ingredAmounts.put(testIngredient.getCode(), 2);
        testItem.setRecipe(new Recipe(ingredients, new HashSet<>(), ingredAmounts, 1));
        storage.addFoodItem(testIngredient, 5);
        storage.addFoodItem(testItem, 0);
        assertThrows(NotEnoughStockException.class, () -> {
            //kitchen.createFoodItems(testItem.getCode(), 10);
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
        //assertEquals(expectedItems, kitchen.createFoodItems("TESTITEM2", 1));

        // more than one
        List<FoodItem> expectedItems2 = Arrays.asList(foodItem, foodItem);
        //assertEquals(expectedItems, kitchen.createFoodItems("TESTITEM2", 2));

        // TODO test when amount parameter does not perfectly divide the FoodItem's recipe creation amount
        FoodItem gramFoodItem = new FoodItem("GRAMITEM", "item measured grams", UnitType.GRAM);
        Recipe recipeFor100 = new Recipe(null, null, null, 100);
        // TODO add gramFoodItem to storage and ingredients

        //kitchen.createFoodItems("GRAMITEM", 100); // TODO test this

        //kitchen.createFoodItems("GRAMITEM", 60); // TODO test this

        //kitchen.createFoodItems("GRAMITEM", 120); // TODO test this

    }
}