package seng202.team1.data;

import seng202.team1.model.*;

import java.util.*;

/**
 * 
 */
public class StorageMemory implements FoodStorage {


    public List<CompositeFoodItem> getAllCompositeFoodItems() {
        return null;
    }

    public CompositeFoodItem getCompositeFoodItemById(String code) {
        return null;
    }

    public void addCompositeFoodItem(CompositeFoodItem item) {

    }

    public void editCompositeFoodItem(String code, CompositeFoodItem alteredItem) {

    }

    public void removeCompositeFoodItem(String code) {

    }

    public List<SuppliedFoodItem> getAllSuppliedFoodItems() {
        return null;
    }

    public SuppliedFoodItem getSuppliedFoodItemByCode(String code) {
        return null;
    }

    @Override
    public void addSuppliedFoodItem(SuppliedFoodItem item, int count) {

    }

    public void addSuppliedFoodItem(SuppliedFoodItem item) {

    }

    public void editSuppliedFoodItem(String code, SuppliedFoodItem alteredItem) {

    }

    public void removeSuppliedFoodItem(String code) {

    }

    public void setSuppliedFoodItemStock(String code, int count) {

    }

    public int getSuppliedFoodItemStock(String code) {
        return 0;
    }
}