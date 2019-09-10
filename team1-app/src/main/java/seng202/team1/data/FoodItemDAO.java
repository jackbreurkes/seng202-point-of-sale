package seng202.team1.data;

import seng202.team1.model.FoodItem;
import seng202.team1.model.FoodSource;

import java.util.Set;

public interface FoodItemDAO {

    /**
     * returns all the FoodItems stored in the system.
     * @return a List of all FoodItems stored in the system
     */
    Set<FoodItem> getAllFoodItems();

    /**
     * gets a single FoodItem stored in the system.
     * @param code the FoodItem's unique code
     * @return the desired FoodItem or null if not found
     */
    FoodItem getFoodItemByCode(String code);

    /**
     * adds a FoodItem to storage. the item will be stored using its code attribute.
     * the item's code should be
     * @param item the FoodItem to store
     * @param count the amount to store initially
     */
    void addFoodItem(FoodItem item, int count);

    /**
     * sets the properties of a FoodItem to those of a new FoodItem.
     * the new item's code should be the same as the one that is being updated.
     * @param alteredItem the FoodItem to update in storage
     */
    void updateFoodItem(FoodItem alteredItem);

    /**
     * remove a FoodItem from storage entirely.
     * @param code the code of the FoodItem to remove
     */
    void removeFoodItem(String code);

    /**
     * set the current stock level of a FoodItem within the system.
     * @param code the FoodItem's unique code
     * @param count the stock count of the item
     */
    void setFoodItemStock(String code, int count);

    /**
     * get the current stock level of a FoodItem within the system.
     * @param code the FoodItem's unique code
     * @return the current stock level
     */
    int getFoodItemStock(String code);

    // TODO add methods for things related to Recipes of FoodItems

}
