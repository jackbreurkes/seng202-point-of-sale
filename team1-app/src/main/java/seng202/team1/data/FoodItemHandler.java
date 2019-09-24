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
     * @param validating boolean
     */
    public FoodItemHandler(String filePath, boolean validating) {
        source = filePath;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(validating);

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace(); // the parser configuration is set in this method only, so this shouldn't be a problem
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
     * @throws IOException if file handling error occurs
     * @throws SAXException if XML parsing error occurs
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
     * Selects each "fooditem" element and constructs a FoodItem object
     * by assigning its values from the "fooditem" element.
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

    /**
     * Takes a String that corresponds to a unit, validates it,
     * and returns a UnitType object of that unit.
     * @param s String
     * @return UnitType
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
     * dietary logic: isVegan, isVegetarian, or isGluten,
     * returns True.
     * @param s String
     * @return boolean
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

        code = "";
        name = "";
        cost = BigMoney.parse("NZD 0.00");
        unit = UnitType.COUNT;
        caloriesPerUnit = 0;
        isVegetarian = false;
        isVegan = false;
        isGlutenFree = false;
    }
}