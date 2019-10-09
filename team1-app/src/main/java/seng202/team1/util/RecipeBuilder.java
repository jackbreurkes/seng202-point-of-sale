package seng202.team1.util;

import seng202.team1.model.FoodItem;
import seng202.team1.model.Recipe;

import java.util.*;

/**
 * class used to create Recipe objects with the desired data.
 */
public class RecipeBuilder {

    private Map<String, Integer> ingredientAmounts = new HashMap<>();
    private Set<FoodItem> ingredients = new HashSet<>();
    private Set<FoodItem> addableIngredients = new HashSet<>();

    /**
     * adds data from an existing recipe to the builder.
     * if items already exist in one of the ingredient sets, the loaded ingredient is ignored.
     * if the passed recipe is null, no changes are made.
     * @param recipe the Recipe who's data should be extracted into this builder
     */
    public void loadExistingRecipeData(Recipe recipe) {

        if (recipe == null) {
            return;
        }

        for (FoodItem ingredient : recipe.getIngredients()) {
            int amount = recipe.getIngredientAmounts().get(ingredient.getCode());
            try {
                addIngredient(ingredient, amount);
            } catch (IllegalArgumentException ignored) {
                // do nothing
            }
        }
    }

    /**
     * adds an ingredient to the Set of ingredients with the given amount.
     * @param ingredient the FoodItem to add to the ingredients Set
     * @param amount the amount of the given ingredient to use when making the recipe
     */
    public void addIngredient(FoodItem ingredient, int amount) {
        addIngredient(ingredient, amount, false);
    }

    /**
     * adds an ingredient to the Set of optionally addable ingredients with the given amount.
     * @param ingredient the FoodItem to add to the addableIngredients Set
     * @param amount the amount of the given ingredient to use when making the recipe if it is added
     */
    public void addAddableIngredient(FoodItem ingredient, int amount) {
        addIngredient(ingredient, amount, true);
    }

    /**
     * adds an ingredient to the planned recipe ingredients.
     * whether the ingredient should be addable or not depends on the value of the addable parameter.
     * @param ingredient the FoodItem to add to the ingredient set
     * @param amount the amount of the FoodItem to use when making the recipe
     * @param addable whether the ingredient should be optional
     */
    private void addIngredient(FoodItem ingredient, int amount, boolean addable) {
        if (amount == 0) {
            throw new IllegalArgumentException("the amount of the given ingredient cannot be 0");
        }

        Set<FoodItem> union = new HashSet<>(ingredients);
        union.addAll(addableIngredients);
        if (union.contains(ingredient)) {
            throw new IllegalArgumentException("cannot add an ingredient that is already in one of the ingredient sets");
        }

        ingredientAmounts.put(ingredient.getCode(), amount);
        if (addable) {
            addableIngredients.add(ingredient);
        } else {
            ingredients.add(ingredient);
        }
    }

    /**
     * removes an ingredient from the Set of ingredients.
     * @param ingredient the FoodItem to remove from the ingredients Set
     */
    public void removeIngredient(FoodItem ingredient) {
        ingredients.remove(ingredient);
        ingredientAmounts.remove(ingredient.getCode());
    }

    /**
     * removes an ingredient from the Set of optionally addable ingredients.
     * @param ingredient the FoodItem to remove from the addableIngredients Set
     */
    public void removeAddableIngredient(FoodItem ingredient) {
        addableIngredients.remove(ingredient);
        ingredientAmounts.remove(ingredient.getCode());
    }

    /**
     * updates the amount of a FoodItem to use in the recipe if it is in the recipe.
     * @param ingredient the FoodItem to update the amount of
     * @param amount the new amount to use
     */
    public void updateIngredientAmount(FoodItem ingredient, int amount) {
        if (!ingredientAmounts.containsKey(ingredient.getCode())) {
            throw new InvalidDataCodeException("ingredient is not in the recipe");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("cannot have a non-positive amount of an ingredient");
        }
        ingredientAmounts.put(ingredient.getCode(), amount);
    }

    /**
     * generates a recipe with the created information that will result in a certain amount of its product being created.
     * @param amount the amount of its product the recipe will create
     * @return a new Recipe instance with the desired ingredient information
     */
    public Recipe generateRecipe(int amount) {
        try {
            return new Recipe(ingredients, addableIngredients, ingredientAmounts, amount);
        } catch (Exception ignored) {
            return null; // TODO best way to handle this?
        }
    }

    /**
     * returns a read only version of the builder's ingredient set.
     * @return an unmodifiable set generated from the ingredients set
     * TODO how to clearly mark these as returning read only??
     */
    public Set<FoodItem> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }

    /**
     * returns a read only version of the builder's addable ingredient set.
     * @return an unmodifiable set generated from the addable ingredients set
     */
    public Set<FoodItem> getAddableIngredients() {
        return Collections.unmodifiableSet(addableIngredients);
    }

    /**
     * returns a read only version of the builder's ingredient amounts map.
     * @return an unmodifiable Map generated from the ingredients amounts map
     */
    public Map<String, Integer> getIngredientAmounts() {
        return Collections.unmodifiableMap(ingredientAmounts);
    }

}
