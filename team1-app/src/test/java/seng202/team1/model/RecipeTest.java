package seng202.team1.model;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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

        tofuPattie.setIsVegan(true);
        lettuce.setIsVegan(true);
        glutenFreeBun.setIsVegetarian(true);
        glutenFreeBun.setIsGlutenFree(true);

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

    @Test
    void testConstructorWithNullPointer() {
        // The '->' separates the parameters(left-side) from the actual expression (right-side)

        // Pass null pointer.
        assertThrows(NullPointerException.class, () -> {
            new Recipe(null, null, null, 0);
        });
    }

    @Test
    void testConstructorWithEmptySet() {
        // Test if set of ingredients is empty
        assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(new HashSet<>(), new HashSet<>(), new HashMap<>(), 1);
        });
    }

    @Test
    void testConstructorWithZeroCreated() {
        // Test if the amount of its product created is 0
        assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(testIngredients, testAddableIngredients, testIngredientAmounts, 0);
        });
    }

    @Test
    void testConstructorWithNoAmountEntry() {
        // Test ingredient amount does not have an entry for every ingredient
        Set<FoodItem> ingredients1 = new HashSet<>();
        ingredients1.add(new FoodItem("1111", "bun", UnitType.COUNT));
        assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(ingredients1, new HashSet<>(), new HashMap<>(), 1);
        });
    }

    @Test
    void testContructorNotAllAddable() {
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
    }

    @Test
    void testContructorWithZeroAmount() {
        // ingredient amounts contains a 0 for an item
        Set<FoodItem> ingredients3 = new HashSet<>();
        ingredients3.add(new FoodItem("2468", "borga", UnitType.COUNT));

        Set<FoodItem> addableIngredients3 = new HashSet<>();

        Map<String, Integer> ingredientAmounts3 = new HashMap<String, Integer>();
        ingredientAmounts3.put("2468", 0);

        assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(ingredients3, addableIngredients3, ingredientAmounts3, 1);
        });
    }

    @Test
    void testConstructorWithZeroAmountAddable() {
        // ingredient amounts contains a 0 for an addable item
        Set<FoodItem> ingredients = new HashSet<>();
        Set<FoodItem> addableIngredients = new HashSet<>();
        Map<String, Integer> ingredientAmounts = new HashMap<String, Integer>();

        addableIngredients.add(new FoodItem("9865", "iPhone", UnitType.COUNT));
        ingredientAmounts.put("9865", 0);

        assertThrows(IllegalArgumentException.class, () -> {
        new Recipe(ingredients, addableIngredients, ingredientAmounts, 1);
        });
    }

    @Test
    void testIngredientInAddable() {
        // ingredients contains an item that is also in addableIngredients
        testIngredients.add(testAddableIngredients.iterator().next());
        assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(testIngredients, testAddableIngredients, testIngredientAmounts, 1);
        });
    }

    @Test
    void addIngredientWithNullPointer() {
        // Pass null pointer
        assertThrows(NullPointerException.class, () -> {
            recipe.addIngredient(null);
        });
    }

    @Test
    void addIngredientNotAddable() {
        // Pass food item that is not addable
        FoodItem niceBun1 = new FoodItem("6969", "niceBun", UnitType.COUNT);
        Set<FoodItem> ingredients1 = new HashSet<>();
        Set<FoodItem> addableIngredients1 = new HashSet<>();

        Map<String, Integer> ingredientAmounts1 = new HashMap<String, Integer>();

        assertThrows(IllegalArgumentException.class, () -> {
            Recipe testRecipe1 = new Recipe(ingredients1, addableIngredients1, ingredientAmounts1, 1);
            testRecipe1.addIngredient(niceBun1);
        });
    }

    @Test
    void addIngredientAddableFoodItem() {
        // Pass a food item that is addable
        FoodItem niceBun2 = new FoodItem("6969", "niceBun", UnitType.COUNT);
        Set<FoodItem> ingredients2 = new HashSet<>();
        ingredients2.add(niceBun2);
        Set<FoodItem> addableIngredients2 = new HashSet<>();

        Map<String, Integer> ingredientAmounts2 = new HashMap<String, Integer>();
        FoodItem tofuPatty2 = new FoodItem("2222", "tofu patty", UnitType.COUNT);
        ingredientAmounts2.put("6969", 1);
        ingredientAmounts2.put("2222", 1);
        addableIngredients2.add(tofuPatty2);

        Recipe testRecipe2 = new Recipe(ingredients2, addableIngredients2, ingredientAmounts2, 1);
        testRecipe2.addIngredient(tofuPatty2);

        Set<FoodItem> testSet = new HashSet<>();
        testSet.add(niceBun2);
        testSet.add(tofuPatty2);
        assertEquals(testSet, testRecipe2.getIngredients());
    }

    @Test
    void addIngredientPrevRemoved() {
        // Adding a food item that has been previously removed
        FoodItem durian = new FoodItem("3939", "durian", UnitType.COUNT);
        Set<FoodItem> ingredients3 = new HashSet<>();
        ingredients3.add(durian);

        Map<String, Integer> ingredientAmounts3 = new HashMap<String, Integer>();
        ingredientAmounts3.put("3939", 1);

        Recipe testRecipe3 = new Recipe(ingredients3, new HashSet<>(), ingredientAmounts3, 1);
        testRecipe3.removeIngredient("3939");

        testRecipe3.addIngredient(durian);
        Set<FoodItem> testSet2 = new HashSet<>();
        testSet2.add(durian);
        assertEquals(testSet2, testRecipe3.getIngredients());
    }

    @Test
    void addIngredientDuplicate() {
        // Adding a food item that is already in the recipe
        FoodItem bread = new FoodItem("9999", "bread", UnitType.COUNT);
        Set<FoodItem> ingredients4 = new HashSet<>();
        ingredients4.add(bread);

        Set<FoodItem> addableIngredients4 = new HashSet<>();

        Map<String, Integer> ingredientAmounts4 = new HashMap<String, Integer>();
        ingredientAmounts4.put("9999", 1);

        Recipe testRecipe4 = new Recipe(ingredients4, addableIngredients4, ingredientAmounts4, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            testRecipe4.addIngredient(bread);
        });

        Set<FoodItem> testSet4 = new HashSet<>();
        testSet4.add(bread);
        assertEquals(testSet4, testRecipe4.getIngredients());
    }

    @Test
    void removeIngredientWithNullPointer() {
        // Pass a null pointer
        assertThrows(NullPointerException.class, () -> {
            recipe.removeIngredient(null);
        });
    }

    @Test
    void removeIngredientNotInRecipe() {
        // Remove a food item that is not within the recipe
        assertThrows(IllegalArgumentException.class, () -> {
            recipe.removeIngredient("not in the recipe");
        });
    }

    @Test
    void removeIngredientInRecipe() {
        // Remove a food item that is within the recipe
        FoodItem lemon = new FoodItem("4444", "lemon", UnitType.COUNT);
        Set<FoodItem> ingredients1 = new HashSet<>();
        ingredients1.add(lemon);

        Map<String, Integer> ingredientAmounts1 = new HashMap<String, Integer>();
        ingredientAmounts1.put("4444", 1);

        Recipe testRecipe1 = new Recipe(ingredients1, new HashSet<>(), ingredientAmounts1, 1);
        testRecipe1.removeIngredient("4444");

        Set<FoodItem> testSet1 = new HashSet<>();
        assertEquals(testSet1, testRecipe1.getIngredients());

        // Removing an item that has already been removed
        assertThrows(IllegalArgumentException.class, () -> {
            recipe.removeIngredient("1111");
            recipe.removeIngredient("1111");
        });
    }

    @Test
    void removeIngredientNotInOriginal() {
        // Removing an item that has been added that wasn't in the original recipe
        Set<FoodItem> ingredients2 = new HashSet<>();
        FoodItem bread = new FoodItem("1234", "bread", UnitType.COUNT);
        ingredients2.add(bread);

        Set<FoodItem> addableIngredients2 = new HashSet<>();
        FoodItem cheese = new FoodItem("1123", "cheese", UnitType.COUNT);
        addableIngredients2.add(cheese);

        Map<String, Integer> ingredientAmounts2 = new HashMap<String, Integer>();
        ingredientAmounts2.put("1234", 1);
        ingredientAmounts2.put("1123", 1);

        Recipe testRecipe2 = new Recipe(ingredients2, addableIngredients2, ingredientAmounts2, 1);
        testRecipe2.addIngredient(cheese);
        testRecipe2.removeIngredient("1123");

        assertEquals(ingredients2, testRecipe2.getIngredients());
    }

    @Test
    void getIsVegetarianDefault() {
        // unaltered recipe that is vegetarian by default
        FoodItem bread = new FoodItem("4567", "bread", UnitType.COUNT);
        bread.setIsVegetarian(true);

        Set<FoodItem> ingredients1 = new HashSet<>();
        ingredients1.add(bread);

        Map<String, Integer> ingredientAmounts1 = new HashMap<String, Integer>();
        ingredientAmounts1.put("4567", 10);

        Recipe breadSandwich = new Recipe(ingredients1, new HashSet<>(), ingredientAmounts1, 1);

        assertEquals(true, breadSandwich.getIsVegetarian());
    }

    @Test
    void getIsVegeterianNotDefault() {
        // unaltered recipe that is not vegetarian by default
        FoodItem ham = new FoodItem("7777", "ham", UnitType.COUNT);
        Set<FoodItem> ingredients = new HashSet<>();
        ingredients.add(ham);

        Map<String, Integer> ingredientAmounts = new HashMap<String, Integer>();
        ingredientAmounts.put("7777", 1);

        Recipe hamSandwich = new Recipe(ingredients, new HashSet<>(), ingredientAmounts, 1);

        assertEquals(false, hamSandwich.getIsVegetarian());
    }

    @Test
    void getIsVegeterianAddNonVegeterian() {
        // Adding a non vegetarian food item to a currently vegetarian recipe
        FoodItem ham = new FoodItem("7777", "ham", UnitType.COUNT);
        FoodItem bread = new FoodItem("4567", "bread", UnitType.COUNT);
        bread.setIsVegetarian(true);

        Set<FoodItem> ingredients2 = new HashSet<>();
        ingredients2.add(bread);

        Set<FoodItem> addableIngredients2 = new HashSet<>();
        addableIngredients2.add(ham);

        Map<String, Integer> ingredientAmounts = new HashMap<String, Integer>();
        ingredientAmounts.put("7777", 1);
        ingredientAmounts.put("4567", 1);

        Recipe sandwich = new Recipe(ingredients2, addableIngredients2, ingredientAmounts, 1);

        sandwich.addIngredient(ham);

        assertEquals(false, sandwich.getIsVegetarian());
    }

    @Test
    void getIsVegeterianRemoveNonVegeterian() {
        // removing the only non-vegetarian item from a recipe
        FoodItem ham = new FoodItem("7777", "ham", UnitType.COUNT);
        FoodItem bread = new FoodItem("4567", "bread", UnitType.COUNT);
        bread.setIsVegetarian(true);

        Set<FoodItem> ingredients2 = new HashSet<>();
        ingredients2.add(bread);

        Set<FoodItem> addableIngredients2 = new HashSet<>();
        addableIngredients2.add(ham);

        Map<String, Integer> ingredientAmounts = new HashMap<String, Integer>();
        ingredientAmounts.put("7777", 1);
        ingredientAmounts.put("4567", 1);

        Recipe sandwich = new Recipe(ingredients2, addableIngredients2, ingredientAmounts, 1);
        sandwich.addIngredient(ham);
        sandwich.removeIngredient("7777");

        assertEquals(true, sandwich.getIsVegetarian());
    }

    @Test
    void getIsVeganDefault() {
        // default values
        FoodItem veganBread = new FoodItem("4568", "vegan bread", UnitType.COUNT);
        veganBread.setIsVegan(true);

        Set<FoodItem> ingredients1 = new HashSet<>();
        ingredients1.add(veganBread);

        Map<String, Integer> ingredientAmounts1 = new HashMap<String, Integer>();
        ingredientAmounts1.put("4568", 1);

        Recipe veganBreadSandwich = new Recipe(ingredients1, new HashSet<>(), ingredientAmounts1, 1);

        assertEquals(true, veganBreadSandwich.getIsVegan());

        assertEquals(true, veganBreadSandwich.getIsVegetarian());
    }

    @Test
    void getIsVeganAddNonVegan() {
        // Adding a non-vegan food item to a currently vegan recipe
        FoodItem normalBread = new FoodItem("2222", "normal bread", UnitType.COUNT);

        Set<FoodItem> addableIngredients1 = new HashSet<>();
        addableIngredients1.add(normalBread);

        Set<FoodItem> ingredients = new HashSet<>();
        FoodItem veganBread = new FoodItem("4568", "vegan bread", UnitType.COUNT);
        veganBread.setIsVegan(true);
        ingredients.add(veganBread);

        Map<String, Integer> ingredientAmounts = new HashMap<String, Integer>();
        ingredientAmounts.put("2222", 1);
        ingredientAmounts.put("4568", 1);

        Recipe normalSandwich = new Recipe(ingredients, addableIngredients1, ingredientAmounts, 1);

        normalSandwich.addIngredient(normalBread);

        assertEquals(false, normalSandwich.getIsVegan());
    }

    @Test
    void getIsVeganRemoveNonVegan() {
        // removing the only non-vegan item from a recipe
        FoodItem normalBread = new FoodItem("2222", "normal bread", UnitType.COUNT);
        Set<FoodItem> ingredients = new HashSet<>();
        FoodItem veganBread = new FoodItem("4568", "vegan bread", UnitType.COUNT);
        veganBread.setIsVegan(true);
        ingredients.add(veganBread);
        ingredients.add(normalBread);

        Set<FoodItem> addableIngredients = new HashSet<>();

        Map<String, Integer> ingredientAmounts = new HashMap<String, Integer>();
        ingredientAmounts.put("2222", 1);
        ingredientAmounts.put("4568", 1);
        ingredientAmounts.put("2222", 1);

        Recipe normalSandwich = new Recipe(ingredients, addableIngredients, ingredientAmounts, 1);
        normalSandwich.removeIngredient("2222");

        assertEquals(true, normalSandwich.getIsVegan());
    }

    @Test
    void getIsGlutenFreeDefault() {
        // default values
        FoodItem GFBread = new FoodItem("6666", "gluten free bread", UnitType.COUNT);
        GFBread.setIsGlutenFree(true);

        Set<FoodItem> ingredients1 = new HashSet<>();
        ingredients1.add(GFBread);

        Map<String, Integer> ingredientAmounts1 = new HashMap<String, Integer>();
        ingredientAmounts1.put("6666", 1);

        Recipe GFBreadSandwich = new Recipe(ingredients1, new HashSet<>(), ingredientAmounts1, 1);

        assertEquals(true, GFBreadSandwich.getIsGlutenFree());
    }

    @Test
    void getIsGlutenFreeAddNonGF() {
        // Adding a non-GF food item to a currently GF recipe
        FoodItem normalBread = new FoodItem("2222", "normal bread", UnitType.COUNT);

        Set<FoodItem> addableIngredients1 = new HashSet<>();
        addableIngredients1.add(normalBread);

        FoodItem GFBread = new FoodItem("6666", "gluten free bread", UnitType.COUNT);
        GFBread.setIsGlutenFree(true);

        Set<FoodItem> ingredients = new HashSet<>();
        ingredients.add(GFBread);

        Map<String, Integer> ingredientAmounts = new HashMap<String, Integer>();
        ingredientAmounts.put("6666", 1);
        ingredientAmounts.put("2222", 1);

        Recipe normalSandwich = new Recipe(ingredients, addableIngredients1, ingredientAmounts, 1);

        normalSandwich.addIngredient(normalBread);

        assertEquals(false, normalSandwich.getIsGlutenFree());
    }

    @Test
    void getIsGlutenFreeRemoveNonGF() {
        // removing the only non-GF item from a recipe
        FoodItem normalBread = new FoodItem("2222", "normal bread", UnitType.COUNT);

        Set<FoodItem> addableIngredients1 = new HashSet<>();
        addableIngredients1.add(normalBread);

        FoodItem GFBread = new FoodItem("6666", "gluten free bread", UnitType.COUNT);
        GFBread.setIsGlutenFree(true);

        Set<FoodItem> ingredients = new HashSet<>();
        ingredients.add(GFBread);

        Map<String, Integer> ingredientAmounts = new HashMap<String, Integer>();
        ingredientAmounts.put("6666", 1);
        ingredientAmounts.put("2222", 1);

        Recipe normalSandwich = new Recipe(ingredients, addableIngredients1, ingredientAmounts, 1);

        normalSandwich.addIngredient(normalBread);
        normalSandwich.removeIngredient("2222");

        assertEquals(true, normalSandwich.getIsGlutenFree());
    }

    @Test
    void testCaloriesInit() {
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
        ingredientAmounts.put(banana.getCode(), 1);
        ingredientAmounts.put(apple.getCode(), 1);

        Recipe testRecipe = new Recipe(ingredients, addableIngredients, ingredientAmounts, 1);
        assertEquals(101, testRecipe.getCalories());
    }

    @Test
    void testCaloriesAdd() {
        // Correct after adding an addable ingredient
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
        ingredientAmounts.put(banana.getCode(), 1);
        ingredientAmounts.put(apple.getCode(), 1);

        Recipe testRecipe = new Recipe(ingredients, addableIngredients, ingredientAmounts, 1);
        testRecipe.addIngredient(apple);
        assertEquals(158, testRecipe.getCalories());
    }

    @Test
    void testCaloriesRemove() {
        // Correct after removing an ingredient
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
        ingredientAmounts.put(banana.getCode(), 1);
        ingredientAmounts.put(apple.getCode(), 1);

        Recipe testRecipe = new Recipe(ingredients, addableIngredients, ingredientAmounts, 1);
        testRecipe.addIngredient(apple);
        testRecipe.removeIngredient("9876");
        assertEquals(157, testRecipe.getCalories());
    }
}