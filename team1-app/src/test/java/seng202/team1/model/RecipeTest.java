package seng202.team1.model;

import org.junit.jupiter.api.BeforeEach;
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
        FoodItem burger = new FoodItem("1234", "Burger", UnitType.COUNT);
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

        recipe = new Recipe(ingredients, addableIngredients, 1, ingredientAmount, burger);
    }

    @Test
    void testConstructor(){
        // Pass null pointer
        // Test if set of ingredients is empty
        // Test if amount created is 0
        // Test ingredient amount does not have an entry for every ingredient
        // Test everything works

    }

    @Test
    void addIngredient() {
        // Pass null pointer
        // Pass food item that is not addable
        // Pass a food item that is addable
        // Adding a food item that has been previously removed
        // Adding a food item that is already in the recipe
    }

    @Test
    void removeIngredient() {
        // Pass a null pointer
        // Remove a food item that is not within the recipe
        // Remove a food item that is within the recipe
        // Removing an item that has already been removed
        // Removing an item that has been added that wasn't in the original recipe
    }

    @Test
    void getIsVegetarian() {
        // When food item is vegetarian
        // When food item is not vegetarian
        // When food item can be optionally made vegetarian
        // Adding a non vegetarian food item to a vegetarian recipe
        //TODO write this after refactoring dietary logic
    }

    @Test
    void getIsVegan() {
        //TODO write this after refactoring dietary logic
    }

    @Test
    void getIsGlutenFree() {
        //TODO write this after refactoring dietary logic
    }

    @Test
    void testCalories() {
        // Test correct initial number of calories
        // Correct after adding an addable ingredient
        // Correct after removing a removable ingredient
    }
}