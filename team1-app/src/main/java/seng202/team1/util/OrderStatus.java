package seng202.team1.util;

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

    /**
     * get an OrderStatus based on its corresponding string.
     * works similarly to OrderStatus.valueOf(String s) but for its toString() value.
     * @param s the String corresponding to the desired OrderStatus
     * @return the OrderStatus corresponding to s if it exists
     */
    public static OrderStatus getValueFromString(String s) {
        for (OrderStatus status : values()) {
            if (status.toString().equalsIgnoreCase(s)) {
                return status;
            }
        }
        throw new IllegalArgumentException(s + " does not correspond to a valid status");
    }
}