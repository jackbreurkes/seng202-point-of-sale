package seng202.team1.model;

import java.util.*;

/**
 * 
 */
public class CompositeFoodItem extends FoodItem {

    private List<FoodItem> components;
    private Map<FoodItem, Integer> ingredientCounts;
    private String recipeNotes;
    private double salePrice;


    /**
     * Default constructor
     */
    public CompositeFoodItem(String code, String name) {
        super(code, name);
    }


    /**
     * @param item 
     * @param count
     */
    public void addComponent(FoodItem item, int count) {
        // TODO implement here
    }

    /**
     * @param item
     */
    public void removeComponent(FoodItem item) {
        // TODO implement here
    }

}