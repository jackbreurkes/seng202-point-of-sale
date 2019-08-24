package seng202.team1.data;

import seng202.team1.model.*;
import seng202.team1.util.InvalidDataCodeException;

import java.util.*;

/**
 * 
 */
public class InMemoryDAO implements SuppliedFoodItemStorage {

    private static InMemoryDAO instance;

    private Set<SuppliedFoodItem> suppliedFoodItems;
    private Map<String, Integer> suppliedFoodItemCounts; // maps code to a count

    /**
     * private constructor to enforce singleton pattern
     */
    private InMemoryDAO() {
        suppliedFoodItems = new HashSet<SuppliedFoodItem>();
        suppliedFoodItemCounts = new HashMap<String, Integer>();
    }

    /**
     * function used to get an instance of a InMemoryDAO object.
     *Implements the singleton pattern
     * @return an instance of InMemoryDAO
     */
    public static InMemoryDAO getInstance() {
        if (instance == null) {
            instance = new InMemoryDAO();
        }
        return instance;
        // TODO move this into a parent class??
    }

    /**
     * I don't think we should ever ues this outside of testing? not sure?
     */
    public void resetInstance() {
        // TODO get rid of this? we need it for testing but it seems bad?
        suppliedFoodItems = new HashSet<SuppliedFoodItem>();
        suppliedFoodItemCounts = new HashMap<String, Integer>();
    }

    @Override
    public Set<SuppliedFoodItem> getAllSuppliedFoodItems() {
        return suppliedFoodItems;
    }

    @Override
    public SuppliedFoodItem getSuppliedFoodItemByCode(String code) {
        for (SuppliedFoodItem item : suppliedFoodItems) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void addSuppliedFoodItem(SuppliedFoodItem item, int count) {
        // code is assumed to be valid, is that good practice? seems fine imo but should ask
        suppliedFoodItems.add(item);
        suppliedFoodItemCounts.put(item.getCode(), count);
    }

    @Override
    public void editSuppliedFoodItem(String code, SuppliedFoodItem alteredItem) {

    }

    @Override
    public void removeSuppliedFoodItem(String code) {
        boolean itemRemoved = suppliedFoodItems.removeIf(item -> item.getCode().equals(code));
        if (!itemRemoved) {
            throw new InvalidDataCodeException("no SuppliedFoodItem found with corresponding code");
        }
        suppliedFoodItemCounts.remove(code);
    }

    @Override
    public void setSuppliedFoodItemStock(String code, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count must be non-negative.");
        }
        if (getSuppliedFoodItemByCode(code) == null) {
            throw new InvalidDataCodeException("no SuppliedFoodItem found with corresponding code");
        }
        suppliedFoodItemCounts.put(code, count);
    }

    @Override
    public int getSuppliedFoodItemStock(String code) {
        return suppliedFoodItemCounts.get(code);
    }
}