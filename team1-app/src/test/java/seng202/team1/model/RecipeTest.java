package seng202.team1.model;

import org.junit.Assert;
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

    Set<FoodItem> testIngredients, testAddableIngredients;
    Map<String, Integer> testIngredientAmounts;
    Recipe recipe;

    @BeforeEach
    void beforeEach() {
        FoodItem bun = new FoodItem("1111", "bun", UnitType.COUNT);
        FoodItem pattie = new FoodItem("2222", "pattie", UnitType.COUNT);
        FoodItem tofuPattie = new FoodItem("3333", "tofuPattie", UnitType.COUNT);
        FoodItem lettuce = new FoodItem("4444", "lettuce", UnitType.COUNT);
        FoodItem glutenFreeBun = new FoodItem("5555", "glutenFreeBun", UnitType.COUNT);

        testIngredients = new HashSet<>();
        testIngredients.add(bun);
        testIngredients.add(pattie);
        testIngredients.add(tofuPattie);

        testAddableIngredients = new HashSet<>();
        testAddableIngredients.add(lettuce);
        testAddableIngredients.add(glutenFreeBun);

        testIngredientAmounts = new HashMap<>();
        testIngredientAmounts.put(bun.getCode(), 1);
        testIngredientAmounts.put(pattie.getCode(), 1);
        testIngredientAmounts.put(tofuPattie.getCode(), 1);
        testIngredientAmounts.put(lettuce.getCode(), 1);
        testIngredientAmounts.put(glutenFreeBun.getCode(), 1);

        recipe = new Recipe(testIngredients, testAddableIngredients, testIngredientAmounts, 1);
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
        assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(testIngredients, testAddableIngredients, testIngredientAmounts, 0);
        });

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

        // ingredient amounts contains a 0 for an item
        fail("not yet implemented");

        // ingredient amounts contains a 0 for an addable item
        fail("not yet implemented");
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
        ingredientAmounts2.put("6969", 1);
        ingredientAmounts2.put("2222", 1);
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

        Map<String, Integer> ingredientAmounts3 = new HashMap<String, Integer>();
        ingredientAmounts3.put("3939", 1);

        Recipe testRecipe3 = new Recipe(ingredients3, new HashSet<>(), ingredientAmounts3, 0);
        testRecipe3.removeIngredient("3939");

        testRecipe3.addIngredient(durian);
        Set<FoodItem> testSet2 = new HashSet<>();
        testSet.add(durian);
        assertEquals(testSet2, testRecipe3.getIngredients());

        // Adding a food item that is already in the recipe
        FoodItem bread = new FoodItem("9999", "bread", UnitType.COUNT);
        Set<FoodItem> ingredients4 = new HashSet<>();
        ingredients4.add(bread);

        Map<String, Integer> ingredientAmounts4 = new HashMap<String, Integer>();
        ingredientAmounts4.put("9999", 1);

        Recipe testRecipe4 = new Recipe(ingredients4, new HashSet<>(), ingredientAmounts4, 0);
        testRecipe4.addIngredient(bread);

        Set<FoodItem> testSet4 = new HashSet<>();
        testSet4.add(bread);
        assertEquals(testSet4, testRecipe4.getIngredients());
    }

    @Disabled
    @Test
    void removeIngredient() {
        // Pass a null pointer
        assertThrows(NullPointerException.class, () -> {
            recipe.removeIngredient(null);
        });

        // Remove a food item that is not within the recipe
        assertThrows(IllegalArgumentException.class, () -> {
            recipe.removeIngredient("not in the recipe");
        });

        // Remove a food item that is within the recipe
        FoodItem lemon = new FoodItem("4444", "lemon", UnitType.COUNT);
        Set<FoodItem> ingredients1 = new HashSet<>();
        ingredients1.add(lemon);

        Map<String, Integer> ingredientAmounts1 = new HashMap<String, Integer>();
        ingredientAmounts1.put("4444", 1);

        Recipe testRecipe1 = new Recipe(ingredients1, new HashSet<>(), ingredientAmounts1, 0);
        testRecipe1.removeIngredient("4444");

        Set<FoodItem> testSet1 = new HashSet<>();
        assertEquals(testSet1, testRecipe1.getIngredients());

        // Removing an item that has already been removed
        assertThrows(IllegalArgumentException.class, () -> {
            recipe.removeIngredient("1111");
            recipe.removeIngredient("1111");
        });

        // Removing an item that has been added that wasn't in the original recipe
        Set<FoodItem> ingredients2 = new HashSet<>();
        FoodItem bread = new FoodItem("1234", "bread", UnitType.COUNT);
        ingredients2.add(bread);

        Set<FoodItem> addableIngredients2 = new HashSet<>();
        FoodItem cheese = new FoodItem("1123", "cheese", UnitType.COUNT);
        addableIngredients2.add(cheese);

        Map<String, Integer> ingredientAmounts2 = new HashMap<String, Integer>();
        ingredientAmounts2.put("1234", 1);
        ingredientAmounts2.put("1123", 0);

        Recipe testRecipe2 = new Recipe(ingredients2, addableIngredients2, ingredientAmounts2, 0);
        testRecipe2.addIngredient(cheese);
        testRecipe2.removeIngredient("1123");

        assertEquals(ingredients2, testRecipe2.getIngredients());
    }

    @Test
    @Disabled
    void getIsVegetarian() {
        // unaltered recipe that is vegetarian by default
        fail("not yet implemented");

        // unaltered recipe that is not vegetarian by default
        fail("not yet implemented");

        // Adding a non vegetarian food item to a currently vegetarian recipe
        fail("not yet implemented");

        // removing the only non-vegetarian item from a recipe
        fail("not yet implemented");
    }

    @Test
    @Disabled
    void getIsVegan() {
        // default values
        fail("not yet implemented");

        // Adding a non-vegan food item to a currently vegan recipe
        fail("not yet implemented");

        // removing the only non-vegan item from a recipe
        fail("not yet implemented");
    }

    @Test
    @Disabled
    void getIsGlutenFree() {
        // default values
        fail("not yet implemented");

        // Adding a non-vegan food item to a currently vegan recipe
        fail("not yet implemented");

        // removing the only non-vegan item from a recipe
        fail("not yet implemented");
    }

    @Test
    @Disabled
    void testCalories() {
        // Test correct initial number of calories
        Set<FoodItem> ingredients = new HashSet<>();
        FoodItem bread = new FoodItem("9876", "bread", UnitType.COUNT);
        bread.setCaloriesPerUnit(1);
        FoodItem banana = new FoodItem("2345", "banana", UnitType.COUNT);
        banana.setCaloriesPerUnit(100);
        ingredients.add(bread);
        ingredients.add(banana);

        Set<FoodItem> addableIngredients = new HashSet<>();
        FoodItem apple = new FoodItem("2323", "apple", UnitType.COUNT);
        apple.setCaloriesPerUnit(57);
        addableIngredients.add(apple);

        Map<String, Integer> ingredientAmounts = new HashMap<String, Integer>();
        ingredientAmounts.put(bread.getCode(), 1);
        ingredientAmounts.put(banana.getCode(), 2);
        ingredientAmounts.put(apple.getCode(), 3);

        Recipe testRecipe = new Recipe(ingredients, addableIngredients, ingredientAmounts, 0);
        assertEquals(101, testRecipe.getCalories());

        // Correct after adding an addable ingredient
        testRecipe.addIngredient(apple);
        assertEquals(158, testRecipe.getCalories());

        // Correct after removing an ingredient
        testRecipe.removeIngredient("9876");
        assertEquals(157, testRecipe.getCalories());
    }
}