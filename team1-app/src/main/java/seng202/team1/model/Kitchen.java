package seng202.team1.model;

import seng202.team1.data.FoodItemDAO;

public class Kitchen {

    public FoodItem createItem(FoodItemDAO storage, Recipe recipe) {
        // TODO if all ingredients in recipe have the required amount in storage:
        return recipe.getProduct();
    }

}
