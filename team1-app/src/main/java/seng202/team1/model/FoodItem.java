package seng202.team1.model;

import com.sun.javafx.application.PlatformImpl;
import javafx.scene.control.Button;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import seng202.team1.util.CodeValidator;
import seng202.team1.util.UnitType;

import java.util.Objects;


/**
 * 
 */
public class FoodItem {

    private String code; // uppercase alphanumeric
    private String name;
    private UnitType unit;
    private BigMoney cost = BigMoney.parse("NZD 0.00");
    private boolean isVegetarian = false;
    private boolean isVegan = false;
    private boolean isGlutenFree = false;
    private double caloriesPerUnit;
    private Recipe recipe;


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

    /**
     * returns the code of the FoodItem
     * @return uppercase alphanumberic code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    public BigMoney getCost() {
        return cost;
    }

    public UnitType getUnit() {
        return unit;
    }

    /**
     * returns the number of calories in
     * @return
     */
    public double getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public boolean getIsVegetarian() {
        return isVegetarian;
    }

    public boolean getIsVegan() {
        return isVegan;
    }

    public boolean getIsGlutenFree() {
        return isGlutenFree;
    }

    public Recipe getRecipe() {
        return this.recipe;
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

    public void setCost(BigMoney cost) {
        if (cost == null) {
            throw new NullPointerException();
        }
        if (cost.isNegative()) {
            throw new IllegalArgumentException("cost cannot be negative");
        }
        this.cost = cost;
    }

    public void setIsVegetarian(boolean isVegetarian) {
        if (!isVegetarian) {
            this.isVegan = false;
        }
        this.isVegetarian = isVegetarian;
    }

    public void setIsVegan(boolean isVegan) {
        if (isVegan) {
            isVegetarian = true;
        }
        this.isVegan = isVegan;
    }

    public void setIsGlutenFree(boolean isGlutenFree) {
        this.isGlutenFree = isGlutenFree;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodItem foodItem = (FoodItem) o;
        return isVegetarian == foodItem.isVegetarian &&
                isVegan == foodItem.isVegan &&
                isGlutenFree == foodItem.isGlutenFree &&
                Double.compare(foodItem.caloriesPerUnit, caloriesPerUnit) == 0 &&
                Objects.equals(code, foodItem.code) &&
                Objects.equals(name, foodItem.name) &&
                unit == foodItem.unit &&
                Objects.equals(cost, foodItem.cost) &&
                Objects.equals(recipe, foodItem.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, unit, cost, isVegetarian, isVegan, isGlutenFree, caloriesPerUnit, recipe);
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", unit=" + unit +
                ", cost=" + cost +
                ", isVegetarian=" + isVegetarian +
                ", isVegan=" + isVegan +
                ", isGlutenFree=" + isGlutenFree +
                ", caloriesPerUnit=" + caloriesPerUnit +
                ", recipe=" + recipe +
                '}';
    }
}