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
    void testGetFoodItemInstance() {
        // FoodItem is null
        assertThrows(NullPointerException.class, () -> {
            kitchen.getFoodItemInstance(null);
        });

        // FoodItem exists but has no recipe
        FoodItem testItem = new FoodItem("TEST", "Test Item", UnitType.COUNT);
        assertTrue(kitchen.getFoodItemInstance(testItem) == testItem);


        //FoodItem is out of stock
        FoodItem testIngredient = new FoodItem("ING1", "ingred", UnitType.COUNT);
        Set<FoodItem> ingredients = new HashSet<>(Arrays.asList(testIngredient));
        Map<String, Integer> ingredAmounts = new HashMap<>();
        ingredAmounts.put(testIngredient.getCode(), 2);
        testItem.setRecipe(new Recipe(ingredients, new HashSet<>(), ingredAmounts, 3));
        storage.addFoodItem(testIngredient, 6);
        storage.addFoodItem(testItem, 0);



        //make sure the correct value is returned
        assertTrue(kitchen.getFoodItemInstance(testItem) == testItem);
        //check the correct amount of ingredients are removed from the database
        assertTrue(storage.getFoodItemStock(testIngredient.getCode()) == 4);
        //check the correct amount of fooditems are created
        assertTrue(storage.getFoodItemStock(testItem.getCode()) == 2);

        //check that after getting a fooditem that is in stock, the stock correctly decrements by one
        kitchen.getFoodItemInstance(testItem);
        assertTrue(storage.getFoodItemStock(testItem.getCode()) == 1);
        //check that the stock of an ingredient for a fooditem is not changed when the fooditem is in stock (doesn't need to be made)
        assertTrue(storage.getFoodItemStock(testIngredient.getCode()) == 4);



    }



    @Disabled
    @Test
    void testAddAmountToFoodStorage() {
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