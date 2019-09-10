package seng202.team1.model;

import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.RecipeDAO;

import java.util.List;

public class Kitchen implements FoodSource {

    private FoodItemDAO foodStorage;

    /**
     * default constructor
     *
     * @param foodStorage
     */
    public Kitchen(FoodItemDAO foodStorage) {
        this.foodStorage = foodStorage;
    }

    @Override
    /**
     * tries to create the given amount of a FoodItem with the given code using its recipe.
     * throws a TODO create exception for if no recipe exists
     * throws a TODO create exception for not enough ingredients
     * @param code the code of the FoodItem to create using its recipe
     * @param amount the amount of the given FoodItem to create
     * @return list of created food items, should all be the same item
     * TODO figure out how to deal with recipes that return amounts > 1 since it may not divide 'amount' perfectly
     */
    public List<FoodItem> createFoodItems(String code, int amount) {
        return null;
    }

}
