package seng202.team1.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.util.UnitType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuppliedFoodItemTest {

    @Test
    public void testConstructor() {
        SuppliedFoodItem coke = new SuppliedFoodItem("COKE", "Coca Cola", UnitType.ML);

        assertEquals("COKE", coke.getCode());
        assertEquals("Coca Cola", coke.getName());
        assertEquals(UnitType.ML, coke.getUnit());
    }

}