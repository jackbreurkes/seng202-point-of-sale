package seng202.team1.model;

import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import java.util.*;
import java.util.function.Supplier;

/**
 * 
 */
public class SuppliedFoodItem extends FoodItem {

    private Supplier supplier;

    /**
     * Default constructor
     */
    public SuppliedFoodItem(String code, String name) {
        super(code, name);
    }

}