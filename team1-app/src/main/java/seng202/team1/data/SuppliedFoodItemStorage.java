package seng202.team1.data;

import seng202.team1.model.SuppliedFoodItem;

import java.util.Set;

public interface SuppliedFoodItemStorage {

    /**
     * returns all the SuppliedFoodItems stored in the system.
     * @return a List of all SuppliedFoodItems stored in the system
     */
    Set<SuppliedFoodItem> getAllSuppliedFoodItems();

    /**
     * gets a single SuppliedFoodItem stored in the system.
     * @param code the SuppliedFoodItem's unique code
     * @return the desired SuppliedFoodItem or null if not found
     */
    SuppliedFoodItem getSuppliedFoodItemByCode(String code);

    /**
     * adds a SuppliedFoodItem to storage. the item will be stored using its code attribute.
     * the item's code should be
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
