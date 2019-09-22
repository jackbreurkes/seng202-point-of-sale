package seng202.team1.data;

import org.joda.money.BigMoney;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UploadHandlerTest {

    final String source1 = "src/test/resources/xml/TESTXML1.xml";
    final String source2 = "src/test/resources/xml/TESTXML2.xml";
    List<FoodItem> expectedItems;

    @BeforeEach
    void beforeEach() {
        FoodItem beefburg = new FoodItem("BEEFBURG", "Hamburger", UnitType.GRAM);
        beefburg.setCost(BigMoney.parse("NZD 21.00"));
        beefburg.setCaloriesPerUnit(295);
        FoodItem cheeseburg = new FoodItem("CHEESEBURG", "Cheeseburger", UnitType.GRAM);
        cheeseburg.setCost(BigMoney.parse("NZD 22.00"));
        cheeseburg.setCaloriesPerUnit(303);
        FoodItem tofuburg = new FoodItem("TOFUBURG", "Tofu Burger", UnitType.GRAM);
        tofuburg.setCost(BigMoney.parse("NZD 50.00"));
        tofuburg.setCaloriesPerUnit(168.4);
        tofuburg.setIsVegetarian(true);
        FoodItem applesoda = new FoodItem("APPLESODA", "Sparkling Apple", UnitType.ML);
        applesoda.setCost(BigMoney.parse("NZD 96.00"));
        applesoda.setCaloriesPerUnit(87);
        applesoda.setIsVegan(true);
        applesoda.setIsGlutenFree(true);

        expectedItems = Arrays.asList(
                beefburg, cheeseburg, tofuburg, applesoda
        );
        expectedItems.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));
    }


    @Test
    void uploadFoodItemsToStorageTest() {
        // Checks if food items are successfully uploaded to storage

        FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
        DAOFactory.resetInstances();
        try {
            UploadHandler.uploadFoodItems(source1);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        List<FoodItem> items = new ArrayList<FoodItem>(itemStorage.getAllFoodItems());
        items.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));

        assertEquals(items.size(), expectedItems.size());
        assertEquals(items, expectedItems);
    }

    @Test
    void uploadDuplicateFoodItemsInStorage() {
        // Checks if duplicates of food items are ignored
        // Tofu calories altered and stock updated
        // Two of the same food items in same xml
        // And attributes of food items are updated to match uploaded food item
        // And stock count is incremented

        FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
        DAOFactory.resetInstances();
        try {
            UploadHandler.uploadFoodItems(source1);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }

        // Tofu FoodItem before attributes are altered
        FoodItem initialTofu = itemStorage.getFoodItemByCode("TOFUBURG");
        // Tofu stock before second XML uploaded
        int initialTofuStock = itemStorage.getFoodItemStock("TOFUBURG");
        // Uploads second XML file with altered tofu calories
        try {
            UploadHandler.uploadFoodItems(source2);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
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
}