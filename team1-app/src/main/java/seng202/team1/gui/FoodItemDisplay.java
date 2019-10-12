package seng202.team1.gui;

import org.joda.money.BigMoney;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Recipe;
import seng202.team1.util.UnitType;

/**
 * display class to be used in TableViews where FoodItems should be displayed with their stock.
 * allows populating of the table with information both from a FoodItem instance and its stock count.
 */
public class FoodItemDisplay {

    private FoodItem item;
    private int stock;

    /**
     * Creates display from fooditem
     * @param itemCode
     */
    public FoodItemDisplay(String itemCode) {
        FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
        item = itemStorage.getFoodItemByCode(itemCode);
        setStock(itemStorage.getFoodItemStock(itemCode));
    }

    /**
     * returns the FoodItem this FoodItemDisplay is displaying the information of
     * @return the FoodItem whose information is being displayed
     */
    public FoodItem getModelFoodItem() {
        return this.item;
    }

    /**
     * sets the stock count displayed by the FoodItemDisplay
     * @param currentStock the stock count to display
     */
    public void setStock(int currentStock) {
        this.stock = currentStock;
    }

    /**
     * returns the model FoodItem's code
     * @return the model FoodItem's code
     */
    public String getCode() {
        return item.getCode();
    }

    /**
     * returns the model FoodItem's name
     * @return the model FoodItem's name
     */
    public String getName() {
        return item.getName();
    }

    /**
     * returns the model FoodItem's cost.
     * @return the model FoodItem's cost
     */
    public BigMoney getCost() {
        return item.getCost();
    }

    /**
     * returns the model FoodItem's unit type.
     * @return the model FoodItem's unit type
     */
    public UnitType getUnit() {
        return item.getUnit();
    }

    /**
     * returns the model FoodItem's calories per unit value.
     * @return the model FoodItem's calories per unit value
     */
    public double getCaloriesPerUnit() {
        return item.getCaloriesPerUnit();
    }

    /**
     * returns whether the model FoodItem is vegetarian.
     * @return whether the model FoodItem is vegetarian
     */
    public boolean getIsVegetarian() {
        return item.getIsVegetarian();
    }

    /**
     * returns whether the model FoodItem is vegan.
     * @return whether the model FoodItem is vegan
     */
    public boolean getIsVegan() {
        return item.getIsVegan();
    }

    /**
     * returns whether the model FoodItem is gluten free.
     * @return whether the model FoodItem is gluten free
     */
    public boolean getIsGlutenFree() { return item.getIsGlutenFree(); }

    /**
     * returns the model FoodItem's recipe.
     * @return the model FoodItem's recipe
     */
    public Recipe getRecipe() { return item.getRecipe(); }

    /**
     * returns the model FoodItem's stock count.
     * @return the amount of the  model FoodItem in storage
     */
    public int getStock() {
        return this.stock;
    }


}
