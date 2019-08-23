package seng202.team1.data;

import seng202.team1.model.*;

import java.util.*;

/**
 * 
 */
public class StorageMemory implements SuppliedFoodItemStorage {

    private static StorageMemory instance;

    private List<SuppliedFoodItem> suppliedFoodItems;
    private Map<String, Integer> suppliedFoodItemCounts; // maps code to a count

    /**
     * private constructor to enforce singleton pattern
     */
    private StorageMemory() {
        suppliedFoodItems = new ArrayList<SuppliedFoodItem>();
        suppliedFoodItemCounts = new HashMap<String, Integer>();
    }

    /**
     * function used to get an instance of a StorageMemory object.
     *Implements the singleton pattern
     * @return an instance of StorageMemory
     */
    public static StorageMemory getInstance() {
        if (instance == null) {
            instance = new StorageMemory();
        }
        return instance;
        // TODO move this into a parent class??
    }

    @Override
    public List<SuppliedFoodItem> getAllSuppliedFoodItems() {
        return null;
    }

    @Override
    public SuppliedFoodItem getSuppliedFoodItemByCode(String code) {
        return null;
    }

    @Override
    public void addSuppliedFoodItem(SuppliedFoodItem item, int count) {

    }

    @Override
    public void editSuppliedFoodItem(String code, SuppliedFoodItem alteredItem) {

    }

    @Override
    public void removeSuppliedFoodItem(String code) {

    }

    @Override
    public void setSuppliedFoodItemStock(String code, int count) {

    }

    @Override
    public int getSuppliedFoodItemStock(String code) {
        return 0;
    }
}