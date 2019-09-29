package seng202.team1.data;

import org.joda.money.BigMoney;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Processes FoodItem using DOM
 */
public class FoodItemHandler {

    private DocumentBuilder builder;
    private Document parsedDoc;
    private String source;
    private Map<String, FoodItem> foodItems;

//    private List<FoodItem> components;
//    private Map<FoodItem, Integer> ingredientCounts;
//    private String recipeNotes;
//    private double salePrice;

    private String code;
    private String name;
    private BigMoney cost;
    private UnitType unit;
    private double caloriesPerUnit;

    private boolean isVegetarian;
    private boolean isVegan;
    private boolean isGlutenFree;


    /**
     * Constructor for FoodItemHandler class.
     *
     * @param filePath the file path to the XML file to parse
     * @param validating a boolean to validate an XML
     */
    public FoodItemHandler(String filePath, boolean validating) {
        source = filePath;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(validating);

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace(); // the parser configuration is set in this method only, so this shouldn't be a problem
        }

        builder.setEntityResolver((publicId, systemId) -> new InputSource(SupplierHandler.class.getResourceAsStream("/dtd/fooditems.dtd")));
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void error(SAXParseException exception) throws SAXException {
                throw exception;
            }
            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                throw exception;
            }

            @Override
            public void warning(SAXParseException exception) throws SAXException {
                throw exception;
            }
        });
    }


    /**
     * Uses DocumentBuilder builder to parse the input XML file
     * and generates a tree for processing.
     * @throws SAXException if an XML parsing error occurs
     */
    public void parseInput() throws IOException, SAXException {
        parsedDoc = builder.parse(source);
    }

    /**
     * Obtains parsed document Document.
     * @return parsedDoc
     */
    public Document parsedDoc() {
        return parsedDoc;
    }


    /**
     * Selects each "fooditem" XML element and constructs a FoodItem object
     * by assigning its values from the "fooditem" XML element.
     * Returns a dictionary, taking the FoodItem's code as its key
     * and the FoodItem itself as its value.
     * @return a FoodItem dictionary
     */
    public Map<String, FoodItem> getFoodItems() {
        foodItems = new HashMap<String, FoodItem>();
        NodeList nodeList = parsedDoc.getElementsByTagName("fooditem");
        int numNodes = nodeList.getLength();

        Element node;

        for (int i = 0; i < numNodes; i++) {
            reset();
            node = (Element) nodeList.item(i);

            code = node.getElementsByTagName("code").item(0).getTextContent();
            name = node.getElementsByTagName("name").item(0).getTextContent();
            cost = BigMoney.parse(node.getElementsByTagName("cost").item(0).getTextContent());
            unit = units(node.getAttribute("unit"));
            caloriesPerUnit = Double.parseDouble(node.getElementsByTagName("caloriesPerUnit").item(0).getTextContent());


            FoodItem food = new FoodItem(code, name, unit);
            food.setCost(cost);
            food.setCaloriesPerUnit(caloriesPerUnit);
            if (node.hasAttribute("isVegetarian")) {
                isVegetarian = diet(node.getAttribute("isVegetarian"));
                food.setIsVegetarian(isVegetarian);
            }
            if (node.hasAttribute("isVegan")) {
                isVegan = diet(node.getAttribute("isVegan"));
                food.setIsVegan(isVegan);
            }
            if (node.hasAttribute("isGlutenFree")) {
                isGlutenFree = diet(node.getAttribute("isGlutenFree"));
                food.setIsGlutenFree(isGlutenFree);
            }
            foodItems.put(code, food);
        }
        return foodItems;
    }


    // Maybe thinking of having an UNKNOWN
    // UnitType for passing invalid unit types
    /**
     * Takes a String that corresponds to a unit, validates it,
     * and returns a UnitType object of that unit.
     * @param s a unit type in the form of a string
     * @return a unit type of type UnitType
     */
    private UnitType units(String s) {
        UnitType unit;
        switch (s) {
            case "g":
                unit = UnitType.GRAM;
                break;
            case "ml":
                unit = UnitType.ML;
                break;
            default:
                unit = UnitType.COUNT;
        }
        return unit;
    }

    /**
     * Takes a string yes or no which indicates a food item's
     * dietary logic. If a food item is of a particular valid
     * dietary logic: isVegan, isVegetarian, or isGlutenFree, returns True.
     * @param s a String "yes" or "no"
     * @return a boolean that indicates a valid dietary logic
     */
    private boolean diet(String s) {
        boolean logic;
        switch (s) {
            case "yes":
                logic = true;
                break;
            default:
                logic = false;
                break;
        }
        return logic;
    }

    /**
     * Resets the variables of a FoodItem for use of
     * next FoodItem parsing.
     */
    private void reset() {
//        recipeNotes = "";
//        salePrice = 0;
        code = "";
        name = "";
        cost = BigMoney.parse("NZD 0.00");
        unit = UnitType.COUNT;
        caloriesPerUnit = 0;
        isVegetarian = false;
        isVegan = false;
        isGlutenFree = false;
    }


    public static void main(String args[]) {
            FoodItemHandler fh = new FoodItemHandler("resources/data/FoodItem.xml", true);
        try {
            fh.parseInput();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        fh.getFoodItems();
            System.out.println(fh.getFoodItems().keySet());
            System.out.println(fh.getFoodItems().values());
            System.out.println("");

            System.out.println("");
            for (FoodItem foo : fh.getFoodItems().values()) {
                System.out.println(foo.getName());
            }
    }
}