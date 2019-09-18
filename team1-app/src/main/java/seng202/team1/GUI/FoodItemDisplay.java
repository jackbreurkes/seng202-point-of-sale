package seng202.team1.GUI;

import org.joda.money.BigMoney;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.MemoryStorage;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

public class FoodItemDisplay extends FoodItem {

    private int stock;

    public FoodItemDisplay(String itemCode) {
        FoodItemDAO itemStorage = MemoryStorage.getInstance();
        setCode(itemCode);
        setName(itemStorage.getFoodItemByCode(itemCode).getName());
        setUnit(itemStorage.getFoodItemByCode(itemCode).getUnit());
        setCost(itemStorage.getFoodItemByCode(itemCode).getCost());
        setIsVegetarian(itemStorage.getFoodItemByCode(itemCode).getIsVegetarian());
        setIsVegan(itemStorage.getFoodItemByCode(itemCode).getIsVegan());
        setIsGlutenFree(itemStorage.getFoodItemByCode(itemCode).getIsGlutenFree());
        setCaloriesPerUnit(itemStorage.getFoodItemByCode(itemCode).getCaloriesPerUnit());
        setRecipe(itemStorage.getFoodItemByCode(itemCode).getRecipe());
        setStock(itemStorage.getFoodItemStock(itemCode));
    }

    public void setStock(int currentStock) {
        this.stock = currentStock;
    }

    public int getStock() {
        return this.stock;
    }
}
