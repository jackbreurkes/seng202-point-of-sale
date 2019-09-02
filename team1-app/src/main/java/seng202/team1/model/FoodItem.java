package seng202.team1.model;

import seng202.team1.util.CodeValidator;
import seng202.team1.util.DietaryLogic;
import seng202.team1.util.UnitType;

import java.util.Objects;


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
        this.code = CodeValidator.checkCode(code);
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

        if (isVegetarian == DietaryLogic.NO) {
            this.isVegan = DietaryLogic.NO;
        } else if (isVegetarian == DietaryLogic.OPTIONAL && getIsVegan() == DietaryLogic.YES) {
            this.isVegan = DietaryLogic.OPTIONAL;
        }

        this.isVegetarian = isVegetarian;
    }

    public void setIsVegan(DietaryLogic isVegan) {
        if (isVegan == null) {
            throw new NullPointerException();
        }

        if (isVegan == DietaryLogic.YES) {
            isVegetarian = DietaryLogic.YES;
        } else if (isVegan == DietaryLogic.OPTIONAL && getIsVegetarian() == DietaryLogic.NO) {
            isVegetarian = DietaryLogic.OPTIONAL;
        }

        this.isVegan = isVegan;
    }

    public void setIsGlutenFree(DietaryLogic isGlutenFree) {
        if (isGlutenFree == null) {
            throw new NullPointerException();
        }
        this.isGlutenFree = isGlutenFree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodItem foodItem = (FoodItem) o;
        return Double.compare(foodItem.caloriesPerUnit, caloriesPerUnit) == 0 &&
                code.equals(foodItem.code) &&
                name.equals(foodItem.name) &&
                unit == foodItem.unit &&
                isVegetarian == foodItem.isVegetarian &&
                isVegan == foodItem.isVegan &&
                isGlutenFree == foodItem.isGlutenFree;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", unit=" + unit +
                ", isVegetarian=" + isVegetarian +
                ", isVegan=" + isVegan +
                ", isGlutenFree=" + isGlutenFree +
                ", caloriesPerUnit=" + caloriesPerUnit +
                '}';
    }
}