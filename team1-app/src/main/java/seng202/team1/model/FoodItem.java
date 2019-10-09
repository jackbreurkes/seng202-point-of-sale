package seng202.team1.model;

import com.sun.javafx.application.PlatformImpl;
import javafx.scene.control.Button;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import seng202.team1.util.CodeValidator;
import seng202.team1.util.UnitType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


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
     * @param unit the UnitType to use to measure this FoodItem
     */
    public FoodItem(String code, String name, UnitType unit) {
        setCode(code);
        setName(name);
        setUnit(unit);
    }

    /**
     * returns the code of the FoodItem. codes are validated by CodeValidator
     * @return the food item's code
     */
    public String getCode() {
        return code;
    }

    /**
     * returns the name of the FoodItem
     * @return the food item's name
     */
    public String getName() {
        return name;
    }

    /**
     * returns the cost of the FoodItem as a BigMoney object.
     * @return BigMoney cost of the item
     */
    public BigMoney getCost() {
        return cost;
    }

    /**
     * returns the unit type of the item.
     * @return the item's UnitType parameter value
     */
    public UnitType getUnit() {
        return unit;
    }

    /**
     * returns the number of calories in the food item
     * @return a double amount of calories
     */
    public double getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    /**
     * returns whether the item is vegetarian
     * @return boolean true if the item is vegetarian, false otherwise
     */
    public boolean getIsVegetarian() {
        return isVegetarian;
    }

    /**
     * returns whether the item is vegan
     * @return boolean true if the item is vegan, false otherwise
     */
    public boolean getIsVegan() {
        return isVegan;
    }

    /**
     * returns whether the item is gluten free
     * @return boolean true if the item is gluten free, false otherwise
     */
    public boolean getIsGlutenFree() {
        return isGlutenFree;
    }

    /**
     * returns the item's Recipe, or null if it has none.
     * @return the item's Recipe or null.
     */
    public Recipe getRecipe() {
        return this.recipe;
    }

    /**
     * sets the calories per unit for the item. throws an error if below 0
     * @param caloriesPerUnit double amount of calories per unit greater than or equal to 0
     */
    public void setCaloriesPerUnit(double caloriesPerUnit) {
        if (caloriesPerUnit < 0) {
            throw new IllegalArgumentException("caloriesPerUnit must be non-negative");
        }
        this.caloriesPerUnit = caloriesPerUnit;
    }

    /**
     * sets the food item's code. this is validated by the CodeValidator class.
     * @param code the code to attempt to set as the FoodItem's code
     */
    public void setCode(String code) {
        this.code = CodeValidator.checkCode(code);
    }

    /**
     * sets the food item's name to a String between 1 and 20 characters.
     * @param name String name between 1 and 20 characters
     */
    public void setName(String name) {
        if (name.length() < 1 || name.length() > 20) {
            throw new IllegalArgumentException("food item names must be between 1 and 20 characters (inclusive)");
        }
        this.name = name;
    }

    /**
     * sets the item's UnitType.
     * @param unit the UnitType to set as the item's unit type
     */
    public void setUnit(UnitType unit) {
        if (unit == null) {
            throw new NullPointerException();
        }
        this.unit = unit;
    }

    /**
     * sets the item's cost.
     * @param cost a non-negative BigMoney cost
     */
    public void setCost(BigMoney cost) {
        if (cost == null) {
            throw new NullPointerException();
        }
        if (cost.isNegative()) {
            throw new IllegalArgumentException("cost cannot be negative");
        }
        this.cost = cost;
    }

    /**
     * sets whether the item is vegetarian. if false, isVegan will also be set to false.
     * @param isVegetarian true if the item is vegetarian, false otherwise
     */
    public void setIsVegetarian(boolean isVegetarian) {
        if (!isVegetarian) {
            this.isVegan = false;
        }
        this.isVegetarian = isVegetarian;
    }

    /**
     * sets whether the item is vegan. if true, isVegetarian will also be set to true.
     * @param isVegan true if the imte is vegan, false otherwise
     */
    public void setIsVegan(boolean isVegan) {
        if (isVegan) {
            isVegetarian = true;
        }
        this.isVegan = isVegan;
    }

    /**
     * sets whether the item is gluten free.
     * @param isGlutenFree true if the item is gluten free, false otherwise
     */
    public void setIsGlutenFree(boolean isGlutenFree) {
        this.isGlutenFree = isGlutenFree;
    }

    /**
     * sets the food item's recipe. overwrites the existing recipe if one is already set.
     * the recipe cannot contain the FoodItem itself.
     * @param recipe the Recipe to associate with the FoodItem
     */
    public void setRecipe(Recipe recipe) {
        if (recipe != null) {
            Set<FoodItem> union = new HashSet<>(recipe.getIngredients());
            union.addAll(recipe.getAddableIngredients());
            if (union.contains(this)) {
                throw new IllegalArgumentException("a FoodItem's Recipe cannot contain itself.");
            }
        }
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