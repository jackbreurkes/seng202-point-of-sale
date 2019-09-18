package seng202.team1.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UploadHandlerTest {

    String source;

    @BeforeEach
    void beforeEach() {
        source = "src/test/resources/xml/TESTXML1.xml";
    }


    @Test
    void uploadFoodItemsToMemoryStorageTest() {
        UploadHandler.uploadFoodItems(source);
        FoodItemDAO itemStorage = MemoryStorage.getInstance();
        List<FoodItem> items = new ArrayList<FoodItem>(itemStorage.getAllFoodItems());

        FoodItem beefburg = new FoodItem("BEEFBURG", "Hamburger", UnitType.GRAM);
        beefburg.setCaloriesPerUnit(295);
        FoodItem cheeseburg = new FoodItem("CHEESEBURG", "Cheeseburger", UnitType.GRAM);
        cheeseburg.setCaloriesPerUnit(303);
        FoodItem tofuburg = new FoodItem("TOFUBURG", "Tofu Burger", UnitType.GRAM);
        tofuburg.setCaloriesPerUnit(168.4);
        tofuburg.setIsVegetarian(true);
        FoodItem applesoda = new FoodItem("APPLESODA", "Sparkling Apple", UnitType.ML);
        applesoda.setCaloriesPerUnit(87);
        applesoda.setIsVegan(true);
        applesoda.setIsGlutenFree(true);

        List<FoodItem> expectedItems = Arrays.asList(
                beefburg, cheeseburg, tofuburg, applesoda
        );

        items.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));
        expectedItems.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));

        assertEquals(expectedItems.size(), items.size());
        assertEquals(expectedItems, items);


        // Checks if food items are successfully uploaded to storage
    }

    @Test
    void uploadDuplicateFoodItemsInMemoryStorage() {
        // Checks if duplicates of food items are ignored
        // And attributes of food items are updated to match uploaded food item
        // And stock count is incremented
    }

}
