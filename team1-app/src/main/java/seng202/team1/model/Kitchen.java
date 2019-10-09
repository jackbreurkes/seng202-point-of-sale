package seng202.team1.model;

import seng202.team1.data.FoodItemDAO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Kitchen {

    private FoodItemDAO foodStorage;

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
        Map<String, Integer> ingredientAmounts = recipe.getIngredientAmounts();

        int amountToCreate = recipe.getAmountCreated();
        for (FoodItem ingredient : ingredients) {
            int amount = ingredientAmounts.get(ingredient.getCode());
            while (amount > 0) {
                getFoodItemInstance(ingredient);
                amount -= 1;
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
