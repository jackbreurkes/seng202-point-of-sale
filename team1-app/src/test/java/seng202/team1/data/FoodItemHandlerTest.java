package seng202.team1.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import seng202.team1.model.FoodItem;
import seng202.team1.util.DietaryLogic;
import seng202.team1.util.UnitType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FoodItemHandlerTest {

    FoodItemHandler handler;
    String source;


    @BeforeEach
    void beforeEach() {
        source = "src/test/resources/xml/TESTXML1.xml";
        handler = new FoodItemHandler(source, true);
    }

    @Test
    void parseInput() throws ParserConfigurationException, IOException, SAXException {
        assertNull(handler.parsedDoc());
        handler.parseInput();
        // Create another document to parse and assert equals to.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document parsedDoc = builder.parse(source);
        assertEquals(handler.parsedDoc().toString(), parsedDoc.toString());

    }


    @Test
    void testParseTESTXML1() {
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
        applesoda.setIsGlutenFree(DietaryLogic.YES);

        List<FoodItem> expectedItems = Arrays.asList(
                beefburg, cheeseburg, tofuburg, applesoda
        );
        List<FoodItem> items = new ArrayList<FoodItem>(handler.getFoodItems().values());
        // sorting lists is used for ease of reading if assertEquals(expectedItems, items) fails
        expectedItems.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));
        items.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));

        assertEquals(expectedItems.size(), items.size());
        assertEquals(expectedItems, items);
//        for (int i = 0; i < items.size(); i++) {
//            assertEquals(expectedItems.get(i), items.get(i));
//        }
//
//        I wrote this test then realised that I am not sure if this test
//        is necessary since assertEquals(expectedItems, items) pretty much does the same thing.. - Euan
    }
}