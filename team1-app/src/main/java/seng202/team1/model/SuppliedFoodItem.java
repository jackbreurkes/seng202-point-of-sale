package seng202.team1.model;

import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import java.util.*;
import java.util.function.Supplier;

/**
 * 
 */
public class SuppliedFoodItem extends FoodItem {

    private List<Supplier> suppliers;

    /**
     * Default constructor
     */
    public SuppliedFoodItem(String code, String name, UnitType unitType) {
        super(code, name);
        super.setUnit(unitType);
    }

    public void addSupplier(Supplier supplier) {
        // TODO implement
    }

    public void removeSupplier(Supplier supplier) {
        // TODO implement
    }

}