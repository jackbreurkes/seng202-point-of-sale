package seng202.team1.model;

import java.util.*;
import seng202.team1.util.DietaryLogic;
import seng202.team1.util.UnitType;

/**
 * 
 */
public abstract class FoodItem {

    private String code;
    private double calorieCount;
    private String name;
    private UnitType unit;
    private double stockCostPerUnit;
    private DietaryLogic isVegetarian;
    private DietaryLogic isVegan;
    private DietaryLogic isGlutenFree;


    /**
     * Default constructor
     * @param code simple code to use when referencing the item
     * @param name the name to give the food item
     */
    public FoodItem(String code, String name) {
        // TODO implement me
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}