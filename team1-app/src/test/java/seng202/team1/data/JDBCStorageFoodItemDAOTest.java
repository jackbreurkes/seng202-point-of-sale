package seng202.team1.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.team1.model.FoodItem;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.UnitType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JDBCStorageFoodItemDAOTest extends FoodItemDAOTest {

    @BeforeEach
    void setupStorage() {
        foodStorage = JDBCStorage.getInstance();
        ((JDBCStorage) foodStorage).resetInstance();
    }

}