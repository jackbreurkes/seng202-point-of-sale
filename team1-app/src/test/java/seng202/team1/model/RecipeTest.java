package seng202.team1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.data.MemoryStorage;
import seng202.team1.util.UnitType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    Recipe recipe;

    @BeforeEach
    void beforeEach() {
        FoodItem bun = new FoodItem("1111", "bun", UnitType.COUNT);
        FoodItem pattie = new FoodItem("2222", "pattie", UnitType.COUNT);
        FoodItem tofuPattie = new FoodItem("3333", "tofuPattie", UnitType.COUNT);
        FoodItem lettuce = new FoodItem("4444", "lettuce", UnitType.COUNT);
        FoodItem glutenFreeBun = new FoodItem("5555", "glutenFreeBun", UnitType.COUNT);

        Set<FoodItem> ingredients = new HashSet<>();
        ingredients.add(bun);
        ingredients.add(pattie);
        ingredients.add(tofuPattie);

        Set<FoodItem> addableIngredients = new HashSet<>();
        addableIngredients.add(lettuce);
        addableIngredients.add(glutenFreeBun);

        Map<String, Integer> ingredientAmount = new HashMap<>();
        ingredientAmount.put(bun.getCode(), 1);
        ingredientAmount.put(pattie.getCode(), 1);
        ingredientAmount.put(tofuPattie.getCode(), 1);
        ingredientAmount.put(lettuce.getCode(), 1);
        ingredientAmount.put(glutenFreeBun.getCode(), 1);

        recipe = new Recipe(ingredients, addableIngredients, ingredientAmount, 1);
    }

    @Disabled
    @Test
    void testConstructor() {
        // The '->' separates the parameters(left-side) from the actual expression (right-side)

        // Pass null pointer.
        assertThrows(NullPointerException.class, () -> {
            new Recipe(null, null, null, 0);
        });

        // Test if set of ingredients is empty
        assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(new HashSet<>(), new HashSet<>(), new HashMap<>(), 1);
        });


        // Test if the amount of its product created is 0
        Recipe testRecipe = new Recipe(new HashSet<>(), new HashSet<>(), new HashMap<>(), 0);
        assertEquals(0, testRecipe.getAmountCreated());

        // Test ingredient amount does not have an entry for every ingredient
        Set<FoodItem> ingredients1 = new HashSet<>();
        ingredients1.add(new FoodItem("1111", "bun", UnitType.COUNT));
        assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(ingredients1, new HashSet<>(), new HashMap<>(), 1);
        });

        // Test ingredient amount does not have an entry for every addable ingredient
        Set<FoodItem> ingredients2 = new HashSet<>();
        ingredients2.add(new FoodItem("1111", "bun", UnitType.COUNT));
        Map<String, Integer> ingredientAmounts = new HashMap<String, Integer>();
        ingredientAmounts.put("1111", 1);
        Set<FoodItem> addableIngredients = new HashSet<>();

        addableIngredients.add(new FoodItem("2222", "tofu patty", UnitType.COUNT));

        assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(ingredients2, addableIngredients, ingredientAmounts, 1);
        });

        // Test everything works

    }

    @Disabled
    @Test
    void addIngredient() {
        // Pass null pointer
        assertThrows(NullPointerException.class, () -> {
            recipe.addIngredient(null);
        });

        // Pass food item that is not addable
        FoodItem niceBun1 = new FoodItem("6969", "niceBun", UnitType.COUNT);
        Set<FoodItem> ingredients1 = new HashSet<>();
        Set<FoodItem> addableIngredients1 = new HashSet<>();

        Map<String, Integer> ingredientAmounts1 = new HashMap<String, Integer>();

        assertThrows(IllegalArgumentException.class, () -> {
            Recipe testRecipe1 = new Recipe(ingredients1, addableIngredients1, ingredientAmounts1, 0);
            testRecipe1.addIngredient(niceBun1);
        });

        // Pass a food item that is addable
        FoodItem niceBun2 = new FoodItem("6969", "niceBun", UnitType.COUNT);
        Set<FoodItem> ingredients2 = new HashSet<>();
        Set<FoodItem> addableIngredients2 = new HashSet<>();

        Map<String, Integer> ingredientAmounts2 = new HashMap<String, Integer>();
        FoodItem tofuPatty2 = new FoodItem("2222", "tofu patty", UnitType.COUNT);
        addableIngredients2.add(tofuPatty2);

        Recipe testRecipe2 = new Recipe(ingredients2, addableIngredients2, ingredientAmounts2, 0);
        testRecipe2.addIngredient(niceBun2);

        Set<FoodItem> testSet = new HashSet<>();
        testSet.add(niceBun2);
        assertEquals(testSet, testRecipe2.getIngredients());

        // Adding a food item that has been previously removed
        FoodItem durian = new FoodItem("3939", "durian", UnitType.COUNT);
        Set<FoodItem> ingredients3 = new HashSet<>();
        ingredients3.add(durian);
        //Recipe testRecipe3 = new Recipe(ingredients3, new HashSet<>(), );

        // Adding a food item that is already in the recipe
    }

    @Disabled
    @Test
    void removeIngredient() {
        // Pass a null pointer
        assertThrows(NullPointerException.class, () -> {
            recipe.removeIngredient(null);
        });
        // Remove a food item that is not within the recipe

        // Remove a food item that is within the recipe
        // Removing an item that has already been removed
        // Removing an item that has been added that wasn't in the original recipe
    }

    @Test
    void getIsVegetarian() {
        // Adding a non vegetarian food item to a currently vegetarian recipe
        // TODO write this after refactoring dietary logic
    }

    @Test
    void getIsVegan() {
        // TODO write this after refactoring dietary logic
    }

    @Test
    void getIsGlutenFree() {
        // TODO write this after refactoring dietary logic
    }

    @Test
    void testCalories() {
        // Test correct initial number of calories
        // Correct after adding an addable ingredient
        // Correct after removing an ingredient
    }
}