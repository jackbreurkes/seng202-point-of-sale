package seng202.team1.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuppliedFoodItemTest {

    @Test
    public void testConstructors() {
        SuppliedFoodItem coke = new SuppliedFoodItem("COKE", "Coca Cola");

        assertEquals("COKE", coke.getCode());
        assertEquals("Coca Cola", coke.getName());
    }

}