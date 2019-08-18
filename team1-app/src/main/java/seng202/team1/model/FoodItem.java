package seng202.team1.model;

import java.util.*;
import seng202.team1.util.DietaryLogic;

/**
 * 
 */
public abstract class FoodItem {

    /**
     * Default constructor
     */
    public FoodItem() {
    }

    /**
     * 
     */
    private String code;

    /**
     * 
     */
    private double calorieCount;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private int amountRemaining;

    /**
     * 
     */
    public double costPerUnit;

    /**
     * 
     */
    private double salePrice;

    /**
     * 
     */
    private DietaryLogic isVegetarian;

    /**
     * 
     */
    private DietaryLogic isVegan;

    /**
     * 
     */
    private DietaryLogic isGlutenFree;




    /**
     * @param count
     */
    public abstract void removeUnits(int count);

}