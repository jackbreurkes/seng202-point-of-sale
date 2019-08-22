package seng202.team1.data;

import seng202.team1.model.CompositeFoodItem;
import seng202.team1.model.SuppliedFoodItem;

import java.util.List;

public interface FoodStorage {

    /**
     * returns all the CompositeFoodItems stored in the system.
     * @return a List of all CompositeFoodItems stored in the system
     */
    List<CompositeFoodItem> getAllCompositeFoodItems();

    /**
     * gets a single CompositeFoodItem stored in the system.
     * @param code the CompositeFoodItem's unique code
     * @return the desired CompositeFoodItem or null if not found
     */
    CompositeFoodItem getCompositeFoodItemById(String code);

    /**
     * adds a CompositeFoodItem to storage. the item will be stored using its code attribute.
     * @param item the CompositeFoodItem to store
     */
    void addCompositeFoodItem(CompositeFoodItem item);

    /**
     * sets the properties of a CompositeFoodItem to those of a new CompositeFoodItem.
     * the new item's code should be the same as the one that is being changed.
     * @param code the code of the item to edit
     * @param alteredItem the CompositeFoodItem
     */
    void editCompositeFoodItem(String code, CompositeFoodItem alteredItem);

    /**
     * remove a CompositeFoodItem from storage.
     * @param code the code of the CompositeFoodItem to remove
     */
    void removeCompositeFoodItem(String code);


    // SuppliedFoodItems

    /**
     * returns all the SuppliedFoodItems stored in the system.
     * @return a List of all SuppliedFoodItems stored in the system
     */
    List<SuppliedFoodItem> getAllSuppliedFoodItems();

    /**
     * gets a single SuppliedFoodItem stored in the system.
     * @param code the SuppliedFoodItem's unique code
     * @return the desired SuppliedFoodItem or null if not found
     */
    SuppliedFoodItem getSuppliedFoodItemByCode(String code);

    /**
     * adds a SuppliedFoodItem to storage. the item will be stored using its code attribute.
     * @param item the CompositeFoodItem to store
     * @param count the amount to store initially
     */
    void addSuppliedFoodItem(SuppliedFoodItem item, int count);

    /**
     * sets the properties of a SuppliedFoodItem to those of a new SuppliedFoodItem.
     * the new item's code should be the same as the one that is being changed.
     * @param code the code of the item to edit
     * @param alteredItem the SuppliedFoodItem
     */
    void editSuppliedFoodItem(String code, SuppliedFoodItem alteredItem);

    /**
     * remove a SuppliedFoodItem from storage entirely.
     * @param code the code of the SuppliedFoodItem to remove
     */
    void removeSuppliedFoodItem(String code);

    /**
     * set the current stock level of a SuppliedFoodItem within the system.
     * @param code the SuppliedFoodItem's unique code
     * @param count the stock count of the item
     */
    void setSuppliedFoodItemStock(String code, int count);

    /**
     * get the current stock level of a SuppliedFoodItem within the system.
     * @param code the SuppliedFoodItem's unique code
     * @return the current stock level
     */
    int getSuppliedFoodItemStock(String code);

}
