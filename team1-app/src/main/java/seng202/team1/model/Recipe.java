package seng202.team1.model;

import seng202.team1.util.DietaryLogic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Recipe {

    private FoodItem product; // the FoodItem the recipe produces
    private int amountCreated;
    private Map<String, Integer> ingredientAmount = new HashMap<>();
    private Set<FoodItem> ingredients = new HashSet<>();
    private Set<FoodItem> addableIngredients = new HashSet<>();
    private boolean isOptionallyVegetarian, isOptionallyVegan, isOptionallyGlutenFree;

    /**
     * Default constructor
     * @param ingredients the set of food items
     * @param addableIngredients the set of food items that can be added to a recipe during an order
     */
    public Recipe(Set<FoodItem> ingredients, Set<FoodItem> addableIngredients, int amountCreated, Map<String, Integer> ingredientAmount) {

    }

    /**
     * Gets the ingredient amounts used in the recipe and the ingredient amounts of the addable food items
     * @return the ingredient amounts used in the recipe
     */
    public Map<String, Integer> getIngredientAmount() {
        return ingredientAmount;
    }

    /**
     * Gets the amount of food items the recipe creates
     * @return type int of amount created
     */
    public int getAmountCreated() {
        return amountCreated;
    }

    /**
     * Returns the food item that the recipe creates
     * @return product
     */
    public FoodItem getProduct() {
        return product;
    }

    /**
     * Gets the set of ingredients in the recipe
     * @return set of food items in the recipe
     */
    public Set<FoodItem> getIngredients(){
        return null;
    }

    /**
     * Gets the set of addable ingredients in the recipe
     * @return set of addable ingredients in the recipe
     */
    public Set<FoodItem> getAddableIngredients(){
        return null;
    }

    /**
     * Add an addable food item to the recipe during the order
     * @param ingredient food item from the set of addable ingredients
     */
    public void addIngredient(FoodItem ingredient){

    }

    /**
     * Remove an ingredient from a recipe during an order
     * @param code the code of the ingredient
     */
    public void removeIngredient(String code){

    }

    /**
     * Returns if the recipe is vegetarian or not. Returns optionally if the recipe is currently not vegetarian and can be vegetarian
     * @return YES if vegetarian, OPTIONAL if currently not vegetarian and can be made vegetarian, NO otherwise
     */
    public DietaryLogic getIsVegetarian() {
        return null;
    }

    /**
     * Returns if the recipe is vegan or not. Returns optionally if the recipe is currently not vegan and can be vegan
     * @return YES if vegan, OPTIONAL if currently not vegan and can be made vegan, NO otherwise
     */
    public DietaryLogic getIsVegan() {
        return null;
    }

    /**
     * Returns if the recipe is glutenFree or not. Returns optionally if the recipe is currently not glutenFree and can be glutenFree
     * @return YES if glutenFree, OPTIONAL if currently not glutenFree and can be made glutenFree, NO otherwise
     */
    public DietaryLogic getIsGlutenFree() {
        return null;
    }

    /**
     * Returns total calories in the recipe
     * @return type double of the number of calories
     */
    public double getCalories() {
        return 0;
    }





}
