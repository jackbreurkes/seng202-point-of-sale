package seng202.team1.model;

import seng202.team1.util.DietaryLogic;
import seng202.team1.util.UnitType;



/**
 * 
 */
public class FoodItem {

    private String code; // uppercase alphanumeric
    private String name;
    private UnitType unit;
    private DietaryLogic isVegetarian = DietaryLogic.NO;
    private DietaryLogic isVegan = DietaryLogic.NO;
    private DietaryLogic isGlutenFree = DietaryLogic.NO;
    private double caloriesPerUnit;


    /**
     * Default constructor
     * @param code simple code to use when referencing the item. between 3 and 10 characters (inclusive). uppercase alphanumeric
     * @param name the name to give the food item. between 1 and 20 characters (inclusive)
     */
    public FoodItem(String code, String name, UnitType unit) {
        setCode(code);
        setName(name);
        setUnit(unit);
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
        if (caloriesPerUnit < 0) {
            throw new IllegalArgumentException("caloriesPerUnit must be non-negative");
        }
        this.caloriesPerUnit = caloriesPerUnit;
    }

    public void setCode(String code) {
        if (code.length() < 3 || code.length() > 10) {
            throw new IllegalArgumentException("food item codes must be between 3 and 10 characters (inclusive)");
        }
        if (!code.matches("[A-Z0-9]+")) {
            throw new IllegalArgumentException("food item codes must be uppercase alphanumeric");
        }
        this.code = code;
    }

    public void setName(String name) {
        if (name.length() < 1 || name.length() > 20) {
            throw new IllegalArgumentException("food item names must be between 1 and 20 characters (inclusive)");
        }
        this.name = name;
    }

    public void setUnit(UnitType unit) {
        if (unit == null) {
            throw new NullPointerException();
        }
        this.unit = unit;
    }

    public void setIsVegetarian(DietaryLogic isVegetarian) {
        if (isVegetarian == null) {
            throw new NullPointerException();
        }
        this.isVegetarian = isVegetarian;
    }

    public void setIsVegan(DietaryLogic isVegan) {
        if (isVegan == null) {
            throw new NullPointerException();
        }
        this.isVegan = isVegan;
    }

    public void setIsGlutenFree(DietaryLogic isGlutenFree) {
        if (isGlutenFree == null) {
            throw new NullPointerException();
        }
        this.isGlutenFree = isGlutenFree;
    }

}