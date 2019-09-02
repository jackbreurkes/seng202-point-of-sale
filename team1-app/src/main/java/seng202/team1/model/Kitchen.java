package seng202.team1.model;

import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.RecipeDAO;

import java.util.List;

public class Kitchen implements FoodSource {

    private FoodItemDAO foodStorage;
    private RecipeDAO recipeStorage;

    /**
     * default constructor
     *
     * @param foodStorage
     * @param recipeStorage
     */
    public Kitchen(FoodItemDAO foodStorage, RecipeDAO recipeStorage) {
        this.foodStorage = foodStorage;
        this.recipeStorage = recipeStorage;
    }

    @Override
    /**
     * tries to create the given amount of a FoodItem using its recipe.
     * throws a TODO create exception for if no recipe exists
     * throws a TODO create exception for not enough ingredients
     * @return list of created food items, should all be the same item
     */
    public List<FoodItem> createFoodItems(String code, int amount) {
        return null;
    }

}
