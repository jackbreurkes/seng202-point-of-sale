package seng202.team1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.JDBCStorage;
import seng202.team1.util.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class KitchenTest {

    FoodItemDAO storage;
    Kitchen kitchen;


    @BeforeEach
    void beforeEach() {
        storage = DAOFactory.getFoodItemDAO();
        DAOFactory.resetInstances();
        kitchen = new Kitchen(storage);
    }

    @Disabled
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


        FoodItem testItem2 = new FoodItem("TEST2", "Test Item 2", UnitType.COUNT);
        Set<FoodItem> ingredients2 = new HashSet<>(Arrays.asList(testItem));
        Map<String, Integer> ingredAmounts2 = new HashMap<>();
        ingredAmounts2.put(testItem.getCode(), 2);


        //test fooditems whose recipe contains other fooditems that contain ingredients.
        testItem2.setRecipe(new Recipe(ingredients2, new HashSet<>(), ingredAmounts2, 2));

        //test return value
        assertSame(kitchen.getFoodItemInstance(testItem2), testItem2);
        //test item stock updates correctly
        assertEquals(storage.getFoodItemStock(testItem2.getCode()), 1);
        //test fooditem in recipe updates correctly
        assertEquals(storage.getFoodItemStock(testItem.getCode()), 2);
        //test recursive removal of ingredient
        assertEquals(storage.getFoodItemStock(testIngredient.getCode()), 2);


        //test fooditems in the database that do not match the recipe of the fooditem being updated

        FoodItem testIngredient2 = new FoodItem("ING9", "ingredi", UnitType.COUNT);
        storage.addFoodItem(testIngredient2, 5);
        Set<FoodItem> ingredients3 = new HashSet<>(Arrays.asList(testIngredient2));
        Map<String, Integer> ingredAmounts3 = new HashMap<>();
        ingredAmounts3.put(testIngredient2.getCode(), 2);


        testItem2.setRecipe(new Recipe(ingredients3, new HashSet<>(), ingredAmounts3, 2));
        //test return value
//        kitchen.getFoodItemInstance(testItem2);
        assertEquals(kitchen.getFoodItemInstance(testItem2), testItem2);
        //System.out.println(storage.getFoodItemStock("ING9"));


        //test stock update for item in stored version of foodItem
        assertEquals(storage.getFoodItemStock(testItem.getCode()), 2);
        //test stock update for item in local version of foodItem

        assertEquals(storage.getFoodItemStock(testIngredient2.getCode()), 3);


    }

    @Test
    @Disabled
    void testCyclicRecipeDependencies() {
        FoodItem a = new FoodItem("ITEMA", "item A", UnitType.COUNT);
        FoodItem b = new FoodItem("ITEMB", "item B", UnitType.COUNT);

        RecipeBuilder builderA = new RecipeBuilder();
        builderA.addIngredient(b, 1);
        a.setRecipe(builderA.generateRecipe(1));

        RecipeBuilder builderB = new RecipeBuilder();
        builderB.addIngredient(a, 1);
        b.setRecipe(builderB.generateRecipe(1));

        FoodItem aInstance = kitchen.getFoodItemInstance(a); // should not throw a StackOverflowError
        assertEquals(a, aInstance);
    }

}