package seng202.team1.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import org.joda.money.BigMoney;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.MemoryStorage;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

public class FoodItemDisplay extends FoodItem {

    private int stock;
    private Button button;

    /**
     * Creates display from fooditem
     * @param itemCode
     */
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
        setButton(new Button("edit item"));
        button.setAlignment(Pos.CENTER);
    }

    public void setStock(int currentStock) {
        this.stock = currentStock;
    }

    public void setButton(Button button) { this.button = button; }

    public int getStock() {
        return this.stock;
    }

    public Button getButton() { return this.button; }

}
