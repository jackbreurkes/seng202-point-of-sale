package seng202.team1.model;

import org.joda.money.Money;
import seng202.team1.util.DietaryLogic;
import seng202.team1.util.UnitType;

/**
 * 
 */
public abstract class FoodItem {

    private String code; // uppercase alphanumeric
    private double caloriesPerUnit;
    private String name;
    private UnitType unit;
    private Money stockCostPerUnit;
    private DietaryLogic isVegetarian;
    private DietaryLogic isVegan;
    private DietaryLogic isGlutenFree;


    /**
     * Default constructor
     * @param code simple code to use when referencing the item. between 3 and 10 characters (inclusive). uppercase alphanumeric
     * @param name the name to give the food item. between 1 and 20 characters (inclusive)
     */
    public FoodItem(String code, String name) {
        // have this in setCode method?? it would only be used in the constructor anyway, but
        // maybe cleaner/better if someone messes up?
        if (code.length() < 3 || code.length() > 10) {
            throw new IllegalArgumentException("food item codes must be between 3 and 10 characters (inclusive)");
        }
        if (!code.matches("[A-Z0-9]+")) {
            throw new IllegalArgumentException("food item codes must be uppercase alphanumeric");
        }
        code = code.toUpperCase();
        setName(name);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * returns the number of calories in
     * @return
     */
    public double getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public UnitType getUnit() {
        return unit;
    }

    public DietaryLogic getIsVegetarian() {
        return isVegetarian;
    }

    public DietaryLogic getIsVegan() {
        return isVegan;
    }

    public DietaryLogic getIsGlutenFree() {
        return isGlutenFree;
    }

    public void setCaloriesPerUnit(double caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
    }

    public void setName(String name) {
        if (name.length() < 1 || name.length() > 20) {
            throw new IllegalArgumentException("food item names must be between 1 and 20 characters (inclusive)");
        }
        this.name = name;
    }

    public void setUnit(UnitType unit) {
        this.unit = unit;
    }

    public void setIsVegetarian(DietaryLogic isVegetarian) {
        this.isVegetarian = isVegetarian;
    }

    public void setIsVegan(DietaryLogic isVegan) {
        this.isVegan = isVegan;
    }

    public void setIsGlutenFree(DietaryLogic isGlutenFree) {
        this.isGlutenFree = isGlutenFree;
    }
}