package seng202.team1.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UploadHandlerTest {

    String source1;
    String source2;
    List<FoodItem> expectedItems;

    @BeforeEach
    void beforeEach() {
        source1 = "src/test/resources/xml/TESTXML1.xml";
        source2 = "src/test/resources/xml/TESTXML2.xml";

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

        expectedItems = Arrays.asList(
                beefburg, cheeseburg, tofuburg, applesoda
        );
        expectedItems.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));

    }


    @Test
    void uploadFoodItemsToMemoryStorageTest() {
        FoodItemDAO itemStorage = MemoryStorage.getInstance();
        UploadHandler.uploadFoodItems(source1);
        List<FoodItem> items = new ArrayList<FoodItem>(itemStorage.getAllFoodItems());
        items.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));

        assertEquals(items.size(), expectedItems.size());
        assertEquals(items, expectedItems);
        // Checks if food items are successfully uploaded to storage

        // Checks if duplicates of food items are ignored
        // Tofu calories altered and stock updated
        // Two of the same food items in same xml
        // And attributes of food items are updated to match uploaded food item
        // And stock count is incremented

        // Tofu FoodItem before attributes are altered
        FoodItem initialTofu = itemStorage.getFoodItemByCode("TOFUBURG");
        // Tofu stock before second XML uploaded
        int initialTofuStock = itemStorage.getFoodItemStock("TOFUBURG");
        // Uploads second XML file with altered tofu calories
        UploadHandler.uploadFoodItems(source2);
        // Updated Tofu FoodItem
        FoodItem changedTofu = itemStorage.getFoodItemByCode("TOFUBURG");
        // Updated Tofu stock
        int changedTofuStock = itemStorage.getFoodItemStock("TOFUBURG");

        // initialTofu !=  changedTofu
        assertNotEquals(initialTofu, changedTofu);
        // In particular, their calories are different
        assertNotEquals(initialTofu.getCaloriesPerUnit(), changedTofu.getCaloriesPerUnit());
        // initialTofu stock != changedTofuStock
        assertNotEquals(initialTofuStock, changedTofuStock);
        
    }

//
//    @Test
//    void uploadDuplicateFoodItemsInMemoryStorage() {
//        // Checks if duplicates of food items are ignored
//        // And attributes of food items are updated to match uploaded food item
//        // And stock count is incremented
//
//
//        FoodItemDAO itemStorage = MemoryStorage.getInstance();
//        System.out.println(itemStorage.getAllFoodItems());
//        UploadHandler.uploadFoodItems(source1);
//
//        for (FoodItem foo: itemStorage.getAllFoodItems()) {
//            System.out.println(foo);
//        }
//
////        UploadHandler.uploadFoodItems(source2);
////        System.out.println(itemStorage.getAllFoodItems());
//
//
//    }

}
