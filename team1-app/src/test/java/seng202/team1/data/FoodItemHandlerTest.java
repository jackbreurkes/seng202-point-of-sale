package seng202.team1.data;

import org.junit.jupiter.api.Test;
import seng202.team1.model.FoodItem;
import seng202.team1.util.DietaryLogic;
import seng202.team1.util.UnitType;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FoodItemHandlerTest {

    @Test
    void parseInput() {
    }

    @Test
    void parsedDoc() {
    }

    @Test
    void testParseTESTXML1() {
        FoodItemHandler handler = new FoodItemHandler("src/test/resources/xml/TESTXML1.xml", true);
        handler.parseInput();

        FoodItem beefburg = new FoodItem("BEEFBURG", "Hamburger", UnitType.GRAM);
        beefburg.setCaloriesPerUnit(295);
        FoodItem cheeseburg = new FoodItem("CHEESEBURG", "Cheeseburger", UnitType.GRAM);
        cheeseburg.setCaloriesPerUnit(303);
        FoodItem tofuburg = new FoodItem("TOFUBURG", "Tofu Burger", UnitType.GRAM);
        tofuburg.setCaloriesPerUnit(168.4);
        tofuburg.setIsVegetarian(DietaryLogic.YES);
        FoodItem applesoda = new FoodItem("APPLESODA", "Sparkling Apple", UnitType.ML);
        applesoda.setCaloriesPerUnit(87);
        applesoda.setIsVegan(DietaryLogic.YES);

        List<FoodItem> expectedItems = Arrays.asList(
                beefburg, cheeseburg, tofuburg, applesoda
        );
        List<FoodItem> items = new ArrayList<FoodItem>(handler.getFoodItems().values());
        // sorting lists is used for ease of reading if assertEquals(expectedItems, items) fails
        expectedItems.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));
        items.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));

        assertEquals(expectedItems.size(), items.size());
        assertEquals(expectedItems, items);
    }
}