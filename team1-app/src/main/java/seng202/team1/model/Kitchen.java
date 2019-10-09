package seng202.team1.model;

import seng202.team1.data.FoodItemDAO;

import java.util.*;

public class Kitchen {

    private FoodItemDAO foodStorage;
    private Map<String, Integer> totalAmountRequired = new HashMap<>();

    /**
     * default constructor
     * @param foodStorage the FoodItemDAO to use when retrieving information about FoodItems
     */
    public Kitchen(FoodItemDAO foodStorage) {
        this.foodStorage = foodStorage;
    }

    public FoodItem getFoodItemInstance(FoodItem modelFoodItem) {

        String code = modelFoodItem.getCode();
        FoodItem storedFoodItem = foodStorage.getFoodItemByCode(code);
        if (storedFoodItem != null) {
            boolean recipesAreEqual = Objects.equals(modelFoodItem.getRecipe(), storedFoodItem.getRecipe());
            int stockCount = foodStorage.getFoodItemStock(code);
            if (recipesAreEqual && stockCount > 0) {
                foodStorage.setFoodItemStock(code, stockCount - 1);
                return storedFoodItem;
            }
        }

        if (modelFoodItem.getRecipe() == null) {
            return modelFoodItem; // for now we don't want to give any issues here
        }
        return makeFoodItemFromRecipe(modelFoodItem);
    }

    /**
     *
     * @param foodItem
     * @return
     */
    private FoodItem makeFoodItemFromRecipe(FoodItem foodItem) {
        Recipe recipe = foodItem.getRecipe();
        Set<FoodItem> ingredients = recipe.getIngredients();

        Integer totalAlreadyRequired = totalAmountRequired.get(foodItem.getCode());
        if (totalAlreadyRequired != null && totalAlreadyRequired > 0) {
            System.out.println("cycle detected " + foodItem.getCode());
        }

        recipe.getIngredientAmounts().forEach((code, amount) -> {
            totalAmountRequired.merge(code, amount, (amountRecipe, amountKitchen) -> amountRecipe + amountKitchen);
        });

        Map<String, Integer> ingredientAmounts = recipe.getIngredientAmounts();

        int amountToCreate = recipe.getAmountCreated();
        for (FoodItem ingredient : ingredients) {
            String code = ingredient.getCode();
            int amount = ingredientAmounts.get(code);
            while (amount > 0) {
                getFoodItemInstance(ingredient);
                amount -= 1;
                totalAmountRequired.put(code, totalAmountRequired.get(code) - 1);
            }
        }
        addAmountToFoodStorage(foodItem, amountToCreate - 1);
        return foodItem;
    }

    /**
     * this does not treat different recipes for the same order different
     * @param item
     */
    private void addAmountToFoodStorage(FoodItem item, int amount) {
        String code = item.getCode();
        if (foodStorage.getFoodItemByCode(code) == null) {
            foodStorage.addFoodItem(item, 0);
        }
        int currentStock = foodStorage.getFoodItemStock(code);
        foodStorage.setFoodItemStock(code, currentStock + amount);
    }

}
