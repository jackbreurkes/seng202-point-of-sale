package seng202.team1.model;

import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import java.util.*;
import java.util.function.Supplier;

/**
 * 
 */
public class SuppliedFoodItem extends FoodItem {

    private UnitType unit;
    private Supplier supplier;


    /**
     * Default constructor
     */
    public SuppliedFoodItem() {
    }

    /**
     * @param count
     */
    @Override
    public void removeUnits(int count) {
        // TODO implement me
    }

}