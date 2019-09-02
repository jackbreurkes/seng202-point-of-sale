package seng202.team1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.data.MemoryStorage;
import seng202.team1.util.NotEnoughStockException;
import seng202.team1.util.RecipeNotFoundException;

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
        // TODO uncomment above when memory storage implements RecipeDAO
    }

    @Test
    void testConstructor() {

        assertThrows(NullPointerException.class, () -> {
            new Kitchen(MemoryStorage.getInstance(), null);
        });

        assertThrows(NullPointerException.class, () -> {
            // new Kitchen(null, MemoryStorage.getInstance());
        });
        // TODO uncomment above when memory storage implements RecipeDAO

        // assertNotNull(new Kitchen(MemoryStorage.getInstance(), MemoryStorage.getInstance()));
        // TODO uncomment above when memory storage implements RecipeDAO
    }

    @Test
    void testCreateFoodItems() {

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
        // TODO add recipe to memory storage with a code TESTITEM
        assertThrows(NotEnoughStockException.class, () -> {
            kitchen.createFoodItems("TESTITEM", 10);
        });

        // TODO reconsider how missing stock is handled
        // recipe exists enough stock in storage
        Recipe recipe = new Recipe();

        // TODO add recipe to memory storage with a code TESTITEM2
        // TODO add required ingredients to storage
        FoodItem item = recipe.getProduct();
        List<FoodItem> expectedItems = Arrays.asList(item);
        assertEquals(expectedItems, kitchen.createFoodItems("TESTITEM2", 1));

        // more than one
        FoodItem item2 = recipe.getProduct();
        List<FoodItem> expectedItems2 = Arrays.asList(item2, item2);
        assertEquals(expectedItems, kitchen.createFoodItems("TESTITEM2", 2));
    }
}