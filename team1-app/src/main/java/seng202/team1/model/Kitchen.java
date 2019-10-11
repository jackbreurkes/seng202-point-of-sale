package seng202.team1.model;

import seng202.team1.data.FoodItemDAO;

import java.util.*;

/**
 * used to get instances of a FoodItem, either from storage if some are available or by creating it using its
 * Recipe if it has one.
 */
public class Kitchen {

    private FoodItemDAO foodStorage;
    //private Set<FoodItem> beingCreated = new HashSet<>();
    private Map<String, Integer> totalAmountRequired = new HashMap<>();

    /**
     * default constructor
     * @param foodStorage the FoodItemDAO to use when retrieving information about FoodItems
     */
    public Kitchen(FoodItemDAO foodStorage) {
        if (foodStorage == null) {
            throw new NullPointerException("foodStorage cannot be null");
        }
        this.foodStorage = foodStorage;
    }

    /**
     * Creates a single instance of the desired FoodItem either by taking one from storage or creating it
     * using its recipe.
     * @implNote currently this function will not throw any errors or warnings if there is not enough stock to
     * retrieve or create the FoodItem.
     * @param modelFoodItem the FoodItem to create an instance of
     * @return a pointer to modelFoodItem
     */
    public FoodItem createFoodItem(FoodItem modelFoodItem) {

        String code = modelFoodItem.getCode();
        FoodItem storedFoodItem = foodStorage.getFoodItemByCode(code);
        if (storedFoodItem != null) {
            boolean recipesAreEqual = Objects.equals(modelFoodItem.getRecipe(), storedFoodItem.getRecipe());
            int stockCount = foodStorage.getFoodItemStock(code);
            if (recipesAreEqual && stockCount > 0) {
                foodStorage.setFoodItemStock(code, stockCount - 1);
                return modelFoodItem;
            }
        }

        if (modelFoodItem.getRecipe() == null) {
            return modelFoodItem; // for now we don't want to give any issues here
        }
        FoodItem result = makeFoodItemFromRecipe(modelFoodItem);
        return result;
    }

    /**
     * makes the given FoodItem using its recipe.
     * @param foodItem the FoodItem to create. must have a non-null recipe.
     * @return the FoodItem being created.
     */
    private FoodItem makeFoodItemFromRecipe(FoodItem foodItem) {
        Recipe recipe = foodItem.getRecipe();
        Set<FoodItem> ingredients = recipe.getIngredients();

        Map<String, Integer> ingredientAmounts = recipe.getIngredientAmounts();

        int amountToCreate = recipe.getAmountCreated();
        for (FoodItem ingredient : ingredients) {
            String code = ingredient.getCode();
            int amount = ingredientAmounts.get(code);
            ingredient = foodStorage.getFoodItemByCode(code);

            totalAmountRequired.putIfAbsent(code, 0);
            if (totalAmountRequired.get(code) > 0) { // cycle detected
                totalAmountRequired.put(code, totalAmountRequired.get(code) - amount);
                break;
            }
            totalAmountRequired.put(code, totalAmountRequired.get(code) + amount);

            while (amount > 0) {
                createFoodItem(ingredient);
                amount -= 1;
            }

            totalAmountRequired.put(code, totalAmountRequired.get(code) - amount);
        }
        addAmountToFoodStorage(foodItem, amountToCreate - 1);
        return foodItem;
    }

    /**
     * adds the given amount of an item to food storage. used when a recipe creates more than one of its product.
     * @implNote if there is already an item with the given item's code in storage then the count of that food
     * item will be increased even if it has a different recipe.
     * @param item the FoodItem to update the stock count of in food storage
     */
    private void addAmountToFoodStorage(FoodItem item, int amount) {
        String code = item.getCode();
        FoodItem storedItem = foodStorage.getFoodItemByCode(code);
        if (storedItem == null) {
            foodStorage.addFoodItem(item, 0);
            storedItem = foodStorage.getFoodItemByCode(code);
        }
        int currentStock = foodStorage.getFoodItemStock(code);
        foodStorage.setFoodItemStock(code, currentStock + amount);
    }

}
