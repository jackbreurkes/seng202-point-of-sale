package seng202.team1.model;

import java.util.*;

/**
 * stores information about the component FoodItems needed to create a FoodItem, their required amounts and
 * the amount of the desired FoodItem created by the process.
 * ingredients can marked as default or addable - addable ingredients can optionally be added when ordering.
 */
public class Recipe {

    private int amountCreated;
    private final Map<String, Integer> ingredientAmounts;
    private final Set<FoodItem> ingredients;
    private final Set<FoodItem> addableIngredients;

    /**
     * Default constructor
     * @param ingredients the non-empty set of food items used in the recipe
     * @param addableIngredients the set of food items that can be added to a recipe during an order
     * @param ingredientAmounts the amount of each ingredient to use in the recipe, and the amount of each addableIngredient to use if added
     * @param amountCreated the amount of the product FoodItem that is created by the recipe. int greater than 0
     */
    public Recipe(Set<FoodItem> ingredients, Set<FoodItem> addableIngredients, Map<String, Integer> ingredientAmounts, int amountCreated) {
        if (ingredients == null || addableIngredients == null || ingredientAmounts == null) {
            throw new NullPointerException();
        }

        if (ingredients.size() == 0) {
            throw new IllegalArgumentException("the set of ingredients should contain at least one FoodItem");
        }

        if (amountCreated == 0) {
            throw new IllegalArgumentException("a recipe cannot create 0 of its product");
        }

        Set<FoodItem> union = new HashSet<FoodItem>(ingredients);
        union.addAll(addableIngredients);
        for (FoodItem ingredient : union) {
            if (!ingredientAmounts.containsKey(ingredient.getCode())) {
                throw new IllegalArgumentException("ingredientAmounts does not contain an entry for " + ingredient.getCode());
            }
        }

        for (Map.Entry<String,Integer> amount : ingredientAmounts.entrySet()) {
            if (amount.getValue() == 0) {
                throw new IllegalArgumentException("the amount of the ingredient " + amount.getKey() + " cannot be 0.");
            }
        }

        Set<FoodItem> intersection = new HashSet<FoodItem>(ingredients);
        intersection.retainAll(addableIngredients);
        if (intersection.size() != 0) {
            throw new IllegalArgumentException("a FoodItem cannot be contained in both ingredients and addableIngredients.");
        }

//        for (FoodItem ingredient : ingredients) { // used to eliminate risk of StackOverflowErrors with circular recipe dependencies
//            ingredient.setRecipe(null);
//        }
//        for (FoodItem addableIngredient : addableIngredients) {
//            addableIngredient.setRecipe(null);
//        }

        this.amountCreated = amountCreated;
        this.ingredients = ingredients;
        this.addableIngredients = addableIngredients;
        this.ingredientAmounts = ingredientAmounts;
    }

    /**
     * Gets the ingredient amounts used in the recipe and the ingredient amounts of the addable food items
     * @return the ingredient amounts used in the recipe
     */
    public Map<String, Integer> getIngredientAmounts() { return Collections.unmodifiableMap(ingredientAmounts); }

    /**
     * Gets the amount of food items the recipe creates
     * @return type int of amount created
     */
    public int getAmountCreated() { return amountCreated; }

    /**
     * Gets the set of ingredients in the recipe
     * @return set of food items in the recipe
     */
    public Set<FoodItem> getIngredients(){ return Collections.unmodifiableSet(ingredients); }

    /**
     * Gets the set of addable ingredients in the recipe
     * @return set of addable ingredients in the recipe
     */
    public Set<FoodItem> getAddableIngredients(){ return Collections.unmodifiableSet(addableIngredients); }

    /**
     * Add an addable food item to the recipe during the order
     * @param ingredient food item from the set of addable ingredients
     */
    public void addIngredient(FoodItem ingredient){
        if (ingredient == null) {
            throw new NullPointerException("the ingredients cannot be null.");
        }
        Iterator<FoodItem> itr = addableIngredients.iterator();
        while (itr.hasNext()) {
            if (itr.next() == ingredient) {
                ingredients.add(ingredient);
                return;
            }
        }

        throw new IllegalArgumentException("the ingredient does not exist in the addableIngredients");
    }

    /**
     * Remove an ingredient from a recipe during an order
     * @param code the code of the ingredient
     */
    public void removeIngredient(String code){
        if (code == null) {
            throw new NullPointerException("the code cannot be null.");
        }

        Iterator<FoodItem> itr = ingredients.iterator();
        while (itr.hasNext()) {
            FoodItem item = itr.next();
            if (item.getCode() == code) {
                ingredients.remove(item);
                addableIngredients.add(item);
                return;
            }
        }

        throw new IllegalArgumentException("cannot remove ingredient that is not in the ingredient set.");

    }

    /**
     * Returns if the recipe is vegetarian or not. Returns optionally if the recipe is currently not vegetarian and can be vegetarian
     * @return YES if vegetarian, OPTIONAL if currently not vegetarian and can be made vegetarian, NO otherwise
     */
    public boolean getIsVegetarian() {
        Iterator<FoodItem> itr = ingredients.iterator();
        while (itr.hasNext()) {
            if (itr.next().getIsVegetarian() == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns if the recipe is vegan or not. Returns optionally if the recipe is currently not vegan and can be vegan
     * @return YES if vegan, OPTIONAL if currently not vegan and can be made vegan, NO otherwise
     */
    public boolean getIsVegan() {
        Iterator<FoodItem> itr = ingredients.iterator();
        while (itr.hasNext()) {
            if (itr.next().getIsVegan() == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns if the recipe is glutenFree or not. Returns optionally if the recipe is currently not glutenFree and can be glutenFree
     * @return YES if glutenFree, OPTIONAL if currently not glutenFree and can be made glutenFree, NO otherwise
     */
    public boolean getIsGlutenFree() {
        Iterator<FoodItem> itr = ingredients.iterator();
        while (itr.hasNext()) {
            if (itr.next().getIsGlutenFree() == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns total calories in the recipe
     * @return type double of the number of calories
     */
    public double getCalories() {
        double totalCal = 0;

        Iterator<FoodItem> ingreItr = ingredients.iterator();
        while (ingreItr.hasNext()) {
            totalCal += ingreItr.next().getCaloriesPerUnit();
        }

        return totalCal;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return amountCreated == recipe.amountCreated &&
                Objects.equals(ingredientAmounts, recipe.ingredientAmounts) &&
                Objects.equals(ingredients, recipe.ingredients) &&
                Objects.equals(addableIngredients, recipe.addableIngredients);
    }

    @Override
    public int hashCode() {
        Set<String> ingredientCodes = new HashSet<>();
        for (FoodItem ingredient : ingredients) {
            ingredientCodes.add(ingredient.getCode());
        }
        Set<String> addableIngredientCodes = new HashSet<>();
        for (FoodItem ingredient : addableIngredients) {
            addableIngredientCodes.add(ingredient.getCode());
        }
        return Objects.hash(amountCreated, ingredientAmounts, ingredientCodes, addableIngredientCodes);
    }

    @Override
    public String toString() {
        Set<String> ingredientCodes = new HashSet<>();
        for (FoodItem ingredient : ingredients) {
            ingredientCodes.add(ingredient.getCode());
        }
        Set<String> addableIngredientCodes = new HashSet<>();
        for (FoodItem ingredient : addableIngredients) {
            addableIngredientCodes.add(ingredient.getCode());
        }
        return "Recipe{" +
                "amountCreated=" + amountCreated +
                ", ingredientAmounts=" + ingredientAmounts +
                ", ingredients=" + ingredientCodes +
                ", addableIngredients=" + addableIngredientCodes +
                '}';
    }
}
