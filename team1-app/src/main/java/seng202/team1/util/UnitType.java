package seng202.team1.util;

/**
 * enum UnitType for FoodItems
 */
public enum UnitType {
    GRAM("g"),
    ML("m"),
    COUNT("c");

    private String string;

    UnitType(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}