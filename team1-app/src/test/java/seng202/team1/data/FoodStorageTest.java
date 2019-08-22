package seng202.team1.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.model.SuppliedFoodItem;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.UnitType;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class FoodStorageTest {

    private FoodStorage foodStorage;
    private SuppliedFoodItem suppliedTestItem;

    @BeforeEach
    public void setupStorage() {
        foodStorage = new StorageMemory();
        suppliedTestItem = new SuppliedFoodItem("ITEM1", "Oil", UnitType.GRAM);
    }

    @Test
    public void testGetInstance() {
        fail();
        // TODO implement
    }

    @Test
    public void testAddSupplied() {
        foodStorage.addSuppliedFoodItem(suppliedTestItem, 1000);
        // should we have equality checking in fooditems??
        // TODO check if item is in storage

        // test adding an already added item
        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.addSuppliedFoodItem(suppliedTestItem, 1000);
        });
    }

    @Test
    public void testEditSupplied() {
        foodStorage.addSuppliedFoodItem(suppliedTestItem, 1000);

        SuppliedFoodItem alteredItem = new SuppliedFoodItem("ITEM1", "Canola Oil", UnitType.GRAM);
        foodStorage.editSuppliedFoodItem(suppliedTestItem.getCode(), alteredItem);
        // TODO check correct operation

        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.editSuppliedFoodItem(suppliedTestItem.getCode(),
                    new SuppliedFoodItem("ITEM2", "Linseed Oil", UnitType.GRAM));
        });
    }

    @Test
    public void testRemoveSupplied() {
        foodStorage.addSuppliedFoodItem(suppliedTestItem, 1000);

        foodStorage.removeSuppliedFoodItem(suppliedTestItem.getCode());
        assertNull(foodStorage.getSuppliedFoodItemByCode(suppliedTestItem.getCode()));

        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.removeSuppliedFoodItem(suppliedTestItem.getCode());
        });
    }

    @Test
    public void testGetSetSuppliedStock() {
        foodStorage.addSuppliedFoodItem(suppliedTestItem, 1000);
        foodStorage.setSuppliedFoodItemStock(suppliedTestItem.getCode(), 500);
        assertEquals(500, foodStorage.getSuppliedFoodItemStock(suppliedTestItem.getCode()));

        // TODO set the right exception to throw here
        assertThrows(Exception.class, () -> {
            foodStorage.setSuppliedFoodItemStock(suppliedTestItem.getCode(), -1);
        });
    }


}