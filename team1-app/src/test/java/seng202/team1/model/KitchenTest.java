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
    FoodItem testItem, testIngredient;
    Recipe testRecipe;


    @BeforeEach
    void beforeEach() {
        storage = DAOFactory.getFoodItemDAO();
        DAOFactory.resetInstances();
        kitchen = new Kitchen(storage);

        testItem = new FoodItem("TEST", "Test Item", UnitType.COUNT);
        testIngredient = new FoodItem("ING1", "ingred", UnitType.COUNT);
        RecipeBuilder recipeBuilder = new RecipeBuilder();
        recipeBuilder.addIngredient(testIngredient, 2);
        testRecipe = recipeBuilder.generateRecipe(3);
        testItem.setRecipe(testRecipe);
        storage.addFoodItem(testItem, 0);
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
    @Disabled
    void testGetFoodItemInstance() {
        //FoodItem is out of stock
        FoodItem testIngredient = new FoodItem("ING1", "ingred", UnitType.COUNT);
        Set<FoodItem> ingredients = new HashSet<>(Arrays.asList(testIngredient));
        Map<String, Integer> ingredAmounts = new HashMap<>();
        ingredAmounts.put(testIngredient.getCode(), 2);
        testItem.setRecipe(new Recipe(ingredients, new HashSet<>(), ingredAmounts, 3));
        storage.setFoodItemStock(testIngredient.getCode(), 6);


        //check that after getting a fooditem that is in stock, the stock correctly decrements by one
        kitchen.getFoodItemInstance(testItem);


        FoodItem testItem2 = new FoodItem("TEST2", "Test Item 2", UnitType.COUNT);
        Set<FoodItem> ingredients2 = new HashSet<>(Arrays.asList(testItem));
        Map<String, Integer> ingredAmounts2 = new HashMap<>();
        ingredAmounts2.put(testItem.getCode(), 2);


        //test fooditems whose recipe contains other fooditems that contain ingredients.
        testItem2.setRecipe(new Recipe(ingredients2, new HashSet<>(), ingredAmounts2, 2));

        //test return value
        assertEquals(kitchen.getFoodItemInstance(testItem2), testItem2);
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
    void testGetNull() {
        // FoodItem is null
        assertThrows(NullPointerException.class, () -> {
            kitchen.getFoodItemInstance(null);
        });
    }

    @Test
    void testFoodItemWithNoRecipeInStorage() {
        // FoodItem exists but has no recipe
        testItem.setRecipe(null);
        storage.updateFoodItem(testItem);
        storage.setFoodItemStock(testItem.getCode(), 1);
        FoodItem instance = kitchen.getFoodItemInstance(testItem);

        assertEquals(testItem, instance);
        assertEquals(0, storage.getFoodItemStock(testItem.getCode()));
    }

    @Test
    void testItemInStorageSameRecipeOutOfStock() {
        //FoodItem is out of stock
        testItem.setRecipe(testRecipe);
        storage.setFoodItemStock(testIngredient.getCode(), 6);

        //make sure the correct value is returned
        FoodItem instance = kitchen.getFoodItemInstance(testItem);
        assertEquals(testItem, instance);
        //check the correct amount of ingredients are removed from the database
        assertEquals(4, storage.getFoodItemStock(testIngredient.getCode()));
        //check the correct amount of food items are created
        assertEquals(2, storage.getFoodItemStock(testItem.getCode()));
    }

    @Test
    void testItemInStorageSameRecipeHasStock() {
        testItem.setRecipe(testRecipe);
        storage.setFoodItemStock(testIngredient.getCode(), 6);
        storage.setFoodItemStock(testItem.getCode(), 1);
        kitchen.getFoodItemInstance(testItem); // shouldn't use the recipe as we have the item in the database

        assertEquals(0, storage.getFoodItemStock(testItem.getCode()));
        assertEquals(6, storage.getFoodItemStock(testIngredient.getCode()));
    }

    @Test
    void testRecipeWithIngredientNotInStockThatHasRecipe() {
        FoodItem testIngredientIngredient = new FoodItem("ING2", "test ingr's ingr", UnitType.COUNT);
        RecipeBuilder builder = new RecipeBuilder();
        builder.addIngredient(testIngredientIngredient, 1);
        Recipe testIngredientRecipe = builder.generateRecipe(10);
        testIngredient.setRecipe(testIngredientRecipe);
        storage.updateFoodItem(testIngredient);
        storage.setFoodItemStock(testIngredient.getCode(), 0);
        storage.setFoodItemStock(testIngredientIngredient.getCode(), 0);
        storage.setFoodItemStock(testIngredientIngredient.getCode(), 1);
        kitchen.getFoodItemInstance(testItem);

        assertEquals(testRecipe.getAmountCreated() - 1, storage.getFoodItemStock(testItem.getCode()));
        int amountOfTestIngredientNeeded = testRecipe.getIngredientAmounts().get(testIngredient.getCode());
        assertEquals(testIngredientRecipe.getAmountCreated() - amountOfTestIngredientNeeded, storage.getFoodItemStock(testIngredient.getCode()));
        assertEquals(0, storage.getFoodItemStock(testIngredientIngredient.getCode()));
    }

    @Test
    void testSomeIngredientsInStockNotEnough() {
        storage.setFoodItemStock(testIngredient.getCode(), 2);
        storage.setFoodItemStock(testItem.getCode(), 0);
        //needs 3 ingredients to build, only 2 in database
        kitchen.getFoodItemInstance(testItem);
        //test return value
        assertEquals(kitchen.getFoodItemInstance(testItem), testItem);
        //test item stock updates correctly
        assertEquals(1, storage.getFoodItemStock(testItem.getCode()));
        //test correct number of ingredient now in database
        assertEquals(0, storage.getFoodItemStock(testIngredient.getCode()));
        // TODO test when not enough of testIngredient but there is still some
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