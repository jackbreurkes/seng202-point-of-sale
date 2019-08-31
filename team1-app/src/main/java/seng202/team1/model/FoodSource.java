package seng202.team1.model;

import seng202.team1.util.UnitType;

import java.util.List;

public interface FoodSource {

    /**
     * create the given amount of a certain FoodItem
     * @param code the code of the FoodItem to create
     * @param amount the amount of that FoodItem to create
     * @return a List containing the FoodItems created. should all be the same item.
     */
    public List<FoodItem> createFoodItems(String code, int amount);

}
