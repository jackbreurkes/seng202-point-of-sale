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
    @Disabled
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


        FoodItem testItem2 = new FoodItem("TEST", "Test Item 2", UnitType.COUNT);
        Set<FoodItem> ingredients2 = new HashSet<>(Arrays.asList(testItem));
        Map<String, Integer> ingredAmounts2 = new HashMap<>();
        ingredAmounts2.put(testItem.getCode(), 2);


        //test fooditems whose recipe contains other fooditems that contain ingredients.
        testItem2.setRecipe(new Recipe(ingredients2, new HashSet<>(), ingredAmounts2, 2));

        //test return value
        assertSame(kitchen.getFoodItemInstance(testItem2), testItem2);
        //test item stock updates correctly
        //assertTrue(storage.getFoodItemStock(testItem2.getCode()) == 1);
        //test fooditem in recipe updates correctly
        assertTrue(storage.getFoodItemStock(testItem.getCode()) == 4);
        //test recursive removal of ingredient
        assertTrue(storage.getFoodItemStock(testIngredient.getCode()) == 2);


        //test fooditems in the database that do not match the fooditem being updated
    }

}