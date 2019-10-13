package seng202.team1.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Recipe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeBuilderTest {

    Set<FoodItem> testIngredients, testAddableIngredients;
    Map<String, Integer> testIngredientAmounts;
    Recipe testRecipeNotBuilt;
    RecipeBuilder testBuilder;
    FoodItem lettuce;
    FoodItem bun;
    FoodItem glutenFreeBun;

    @BeforeEach
    void beforeEach() {
        bun = new FoodItem("1111", "bun", UnitType.COUNT);
        lettuce = new FoodItem("4444", "lettuce", UnitType.COUNT);
        glutenFreeBun = new FoodItem("5555", "glutenFreeBun", UnitType.COUNT);

        lettuce.setIsVegan(true);
        glutenFreeBun.setIsVegetarian(true);
        glutenFreeBun.setIsGlutenFree(true);

        testIngredients = new HashSet<>();
        testIngredients.add(lettuce);
        testIngredients.add(bun);

        testAddableIngredients = new HashSet<>();
        testAddableIngredients.add(glutenFreeBun);

        testIngredientAmounts = new HashMap<>();
        testIngredientAmounts.put(bun.getCode(), 2);
        testIngredientAmounts.put(lettuce.getCode(), 1);
        testIngredientAmounts.put(glutenFreeBun.getCode(), 2);

        testRecipeNotBuilt = new Recipe(testIngredients, testAddableIngredients, testIngredientAmounts, 1);

        testBuilder = new RecipeBuilder();
    }

    @Test
    void testLoadFromExisting() {
        testBuilder.loadExistingRecipeData(testRecipeNotBuilt);
        assertEquals(testRecipeNotBuilt, testBuilder.generateRecipe(testRecipeNotBuilt.getAmountCreated()));
    }

    @Test
    void testLoadFromNull() {
        testBuilder.loadExistingRecipeData(null);
        assertEquals(new HashSet<FoodItem>(), testBuilder.getIngredients());
        assertEquals(new HashSet<FoodItem>(), testBuilder.getAddableIngredients());
        assertEquals(new HashMap<String, Integer>(), testBuilder.getIngredientAmounts());
    }

    @Test
    void testBuildTestRecipe() {
        testBuilder.addIngredient(lettuce, 1);
        testBuilder.addIngredient(bun, 2);
        testBuilder.addAddableIngredient(glutenFreeBun, 2);
        assertEquals(testRecipeNotBuilt, testBuilder.generateRecipe(testRecipeNotBuilt.getAmountCreated()));
    }

    @Test
    void testBuildEmptyRecipe() {
        assertNull(testBuilder.generateRecipe(1));
    }

    @Test
    void testAddAlreadyExisting() {
        testBuilder.addIngredient(lettuce, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            testBuilder.addIngredient(lettuce, 2);
        });
    }

    @Test
    void testAddAddableAlreadyExisting() {
        testBuilder.addAddableIngredient(lettuce, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            testBuilder.addAddableIngredient(lettuce, 2);
        });
    }

    @Test
    void testAddZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            testBuilder.addIngredient(lettuce, 0);
        });
    }

    @Test
    void testAddAddableZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            testBuilder.addAddableIngredient(glutenFreeBun, 0);
        });
    }

    @Test
    void testUpdateAmountZero() {
        testBuilder.addIngredient(bun, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            testBuilder.updateIngredientAmount(bun.getCode(), 0);
        });
    }

    @Test
    void testBuildWithAmountZero() {
        testBuilder.addIngredient(lettuce, 1);
        assertNull(testBuilder.generateRecipe(0));
    }

    @Test
    void testRemoveIngredient() {
        testBuilder.addIngredient(bun, 1);
        testBuilder.addIngredient(lettuce, 1);
        testBuilder.removeIngredient(lettuce.getCode());
        Set<FoodItem> expected = new HashSet<>();
        expected.add(bun);
        assertEquals(expected, testBuilder.getIngredients());
    }

    @Test
    void testRemoveAddableIngredient() {
        testBuilder.addAddableIngredient(glutenFreeBun, 1);
        testBuilder.addAddableIngredient(lettuce, 1);
        testBuilder.removeAddableIngredient(lettuce.getCode());
        Set<FoodItem> expected = new HashSet<>();
        expected.add(glutenFreeBun);
        assertEquals(expected, testBuilder.getAddableIngredients());
    }

    @Test
    void testRemoveNonexistent() {
        assertThrows(InvalidDataCodeException.class, () -> {
            testBuilder.removeIngredient("NULL");
        });
    }

    @Test
    void testUpdateIngredientAmount() {
        testBuilder.addIngredient(lettuce, 1);
        testBuilder.updateIngredientAmount(lettuce.getCode(), 2);
        assertEquals(2, testBuilder.getIngredientAmounts().get(lettuce.getCode()));
    }

    @Test
    void testUpdateNonexistentAmount() {
        assertThrows(InvalidDataCodeException.class, () -> {
            testBuilder.updateIngredientAmount("NULL", 1);
        });
    }

    @Test
    void testGetIngredientsIsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> {
            testBuilder.getIngredients().add(bun);
        });
    }

    @Test
    void testGetAddableIngredientsIsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> {
            testBuilder.getAddableIngredients().add(bun);
        });
    }

    @Test
    void testGetIngredientAmountsIsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> {
            testBuilder.getIngredientAmounts().put(bun.getCode(), 5);
        });
    }
}