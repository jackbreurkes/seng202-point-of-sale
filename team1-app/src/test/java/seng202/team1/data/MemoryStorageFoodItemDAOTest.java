package seng202.team1.data;

import org.junit.jupiter.api.BeforeEach;

class MemoryStorageFoodItemDAOTest extends FoodItemDAOTest {

    @BeforeEach
    void setupStorage() {
        foodStorage = MemoryStorage.getInstance();
        ((MemoryStorage) foodStorage).resetInstance();
    }

}