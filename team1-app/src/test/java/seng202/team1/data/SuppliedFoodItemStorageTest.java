package seng202.team1.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.model.SuppliedFoodItem;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.UnitType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class SuppliedFoodItemStorageTest {

    private SuppliedFoodItemStorage foodStorage;
    private SuppliedFoodItem suppliedTestItem;

    @BeforeEach
    void setupStorage() {
        foodStorage = StorageMemory.getInstance(); // TODO make this more modular??
        suppliedTestItem = new SuppliedFoodItem("ITEM1", "Oil", UnitType.GRAM);
    }

    @Test
    void testGetByCode() {
        assertNull(foodStorage.getSuppliedFoodItemByCode(suppliedTestItem.getCode()));

        foodStorage.addSuppliedFoodItem(suppliedTestItem, 1);
        assertEquals(suppliedTestItem, foodStorage.getSuppliedFoodItemByCode(suppliedTestItem.getCode()));

        assertNull(foodStorage.getSuppliedFoodItemByCode("UNUSED CODE"));
    }

    @Test
    void testGetAllSupplied() {
        List<SuppliedFoodItem> items = foodStorage.getAllSuppliedFoodItems();
        assertEquals(0, items.size());

        foodStorage.addSuppliedFoodItem(suppliedTestItem, 0);
        items = foodStorage.getAllSuppliedFoodItems();
        assertEquals(1, items.size());
        assertTrue(items.contains(suppliedTestItem));
    }

    @Test
    void testAddSupplied() {
        foodStorage.addSuppliedFoodItem(suppliedTestItem, 1000);
        assertEquals(suppliedTestItem,
                foodStorage.getSuppliedFoodItemByCode(suppliedTestItem.getCode()));

        // test adding an already added item
        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.addSuppliedFoodItem(suppliedTestItem, 1000);
        });
    }

    @Test
    void testEditSupplied() {
        foodStorage.addSuppliedFoodItem(suppliedTestItem, 1000);

        SuppliedFoodItem alteredItem = new SuppliedFoodItem("ITEM1", "Canola Oil", UnitType.GRAM);
        foodStorage.editSuppliedFoodItem(suppliedTestItem.getCode(), alteredItem);
        assertEquals(alteredItem, foodStorage.getSuppliedFoodItemByCode(suppliedTestItem.getCode()));

        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.editSuppliedFoodItem(suppliedTestItem.getCode(),
                    new SuppliedFoodItem("ITEM2", "Linseed Oil", UnitType.GRAM));
        });
    }

    @Test
    void testRemoveSupplied() {
        foodStorage.addSuppliedFoodItem(suppliedTestItem, 1000);

        foodStorage.removeSuppliedFoodItem(suppliedTestItem.getCode());
        assertNull(foodStorage.getSuppliedFoodItemByCode(suppliedTestItem.getCode()));

        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.getSuppliedFoodItemStock(suppliedTestItem.getCode());
        });

        assertThrows(InvalidDataCodeException.class, () -> {
            foodStorage.removeSuppliedFoodItem(suppliedTestItem.getCode());
        });
    }

    @Test
    void testGetSetSuppliedStock() {
        foodStorage.addSuppliedFoodItem(suppliedTestItem, 1000);
        foodStorage.setSuppliedFoodItemStock(suppliedTestItem.getCode(), 500);
        assertEquals(500, foodStorage.getSuppliedFoodItemStock(suppliedTestItem.getCode()));

        foodStorage.setSuppliedFoodItemStock(suppliedTestItem.getCode(), 0);
        assertEquals(0, foodStorage.getSuppliedFoodItemStock(suppliedTestItem.getCode()));


        assertThrows(IllegalArgumentException.class, () -> {
            foodStorage.setSuppliedFoodItemStock(suppliedTestItem.getCode(), -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            foodStorage.setSuppliedFoodItemStock(suppliedTestItem.getCode(), Integer.MAX_VALUE + 1);
        });
    }


}