package seng202.team1.util;

import java.util.*;

/**
 * enum to represent the status of an order.
 */
public enum OrderStatus {
    CREATING("c"),
    SUBMITTED("s"),
    COMPLETED("d"),
    CANCELLED("x"),
    REFUNDED("r");

    private String string;

    OrderStatus(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}