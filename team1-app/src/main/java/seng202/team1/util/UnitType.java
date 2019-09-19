package seng202.team1.util;

/**
 * Simple enum to illustrate XML attribute parsing in SAX example
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