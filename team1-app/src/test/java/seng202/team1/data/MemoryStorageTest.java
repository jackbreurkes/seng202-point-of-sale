package seng202.team1.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import static org.junit.jupiter.api.Assertions.*;

class MemoryStorageTest {

    @Test
    void testSingleton() {
        MemoryStorage instance1 = MemoryStorage.getInstance();
        MemoryStorage instance2 = MemoryStorage.getInstance();
        assertSame(instance1, instance2);
    }

}

class MemoryStorageFoodItemDAOTest extends FoodItemDAOTest {

    @BeforeEach
    void setupStorage() {
        foodStorage = MemoryStorage.getInstance();
        ((MemoryStorage) foodStorage).resetInstance();
    }

}