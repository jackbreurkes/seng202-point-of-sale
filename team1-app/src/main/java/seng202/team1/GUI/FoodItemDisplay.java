package seng202.team1.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import org.joda.money.BigMoney;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.MemoryStorage;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Recipe;
import seng202.team1.util.CodeValidator;
import seng202.team1.util.UnitType;

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

    public FoodItem getModelFoodItem() {
        return this.item;
    }

    public void setStock(int currentStock) {
        this.stock = currentStock;
    }

    public String getCode() {
        return item.getCode();
    }

    public String getName() {
        return item.getName();
    }

    public BigMoney getCost() {
        return item.getCost();
    }

    public UnitType getUnit() {
        return item.getUnit();
    }

    public double getCaloriesPerUnit() {
        return item.getCaloriesPerUnit();
    }

    public boolean getIsVegetarian() {
        return item.getIsVegetarian();
    }

    public boolean getIsVegan() {
        return item.getIsVegan();
    }

    public boolean getIsGlutenFree() { return item.getIsGlutenFree(); }

    public Recipe getRecipe() { return item.getRecipe(); }

    public int getStock() {
        return this.stock;
    }


}
