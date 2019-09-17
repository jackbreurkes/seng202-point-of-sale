package seng202.team1.data;

import seng202.team1.model.FoodItem;
import seng202.team1.util.InvalidDataCodeException;

import java.util.*;

/**
 * 
 */
public class MemoryStorage implements FoodItemDAO {

    private static MemoryStorage instance;

    private Map<String, FoodItem> foodItems;
    private Map<String, Integer> foodItemCounts; // maps code to a count

    /**
     * private constructor to enforce singleton pattern
     */
    private MemoryStorage() {
        foodItems = new HashMap<String, FoodItem>();
        foodItemCounts = new HashMap<String, Integer>();
    }

    /**
     * function used to get an instance of a InMemoryDAO object.
     *Implements the singleton pattern
     * @return an instance of InMemoryDAO
     */
    public static MemoryStorage getInstance() {
        if (instance == null) {
            instance = new MemoryStorage();
        }
        return instance;
        // TODO move this into a parent class??
    }

    /**
     * I don't think we should ever ues this outside of testing? not sure?
     */
    public void resetInstance() {
        // TODO get rid of this? we need it for testing but it seems bad?
        foodItems = new HashMap<String, FoodItem>();
        foodItemCounts = new HashMap<String, Integer>();
    }

    @Override
    public Set<FoodItem> getAllFoodItems() {
        return new HashSet<FoodItem>(foodItems.values());
    }

    @Override
    public FoodItem getFoodItemByCode(String code) {
        return foodItems.get(code);
    }

    @Override
    public void addFoodItem(FoodItem item, int count) {
        // code is assumed to be valid, is that good practice? seems fine imo but should ask
        if (item == null) {
            throw new NullPointerException();
        }
        if (foodItems.containsKey(item.getCode())) {
            throw new InvalidDataCodeException("FoodItem with given code is already in storage");
        }

        foodItems.put(item.getCode(), item);
        foodItemCounts.put(item.getCode(), count);
    }

    @Override
    public void updateFoodItem(FoodItem alteredItem) {
        if (alteredItem == null) {
            throw new NullPointerException();
        }
        String code = alteredItem.getCode();
        if (!foodItems.containsKey(code)) {
            throw new InvalidDataCodeException("no item exists with the given code, please use addFoodItem instead");
        }
        foodItems.put(code, alteredItem);
    }

    @Override
    public void removeFoodItem(String code) {
        if (foodItems.remove(code) == null) {
            throw new InvalidDataCodeException("no FoodItem found with corresponding code");
        }
        foodItemCounts.remove(code);
    }

    @Override
    public void setFoodItemStock(String code, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count must be non-negative.");
        }
        if (getFoodItemByCode(code) == null) {
            throw new InvalidDataCodeException("no FoodItem found with corresponding code");
        }
        foodItemCounts.put(code, count);
    }

    @Override
    public int getFoodItemStock(String code) {
        return foodItemCounts.get(code);
    }
}