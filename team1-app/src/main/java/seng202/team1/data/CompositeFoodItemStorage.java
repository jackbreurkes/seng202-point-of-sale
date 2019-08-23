package seng202.team1.data;

import seng202.team1.model.CompositeFoodItem;

import java.util.List;
import java.util.Set;

public interface CompositeFoodItemStorage {

    /**
     * returns all the CompositeFoodItems stored in the system.
     * @return a List of all CompositeFoodItems stored in the system
     */
    Set<CompositeFoodItem> getAllCompositeFoodItems();

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

}
