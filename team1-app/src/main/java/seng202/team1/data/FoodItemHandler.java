package seng202.team1.data;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import seng202.team1.model.FoodItem;
import seng202.team1.util.DietaryLogic;
import seng202.team1.util.UnitType;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
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
    private UnitType unit;
    private DietaryLogic isVegetarian;
    private DietaryLogic isVegan;
    private DietaryLogic isGlutenFree;
    private double caloriesPerUnit;


    /**
     * Constructor for FoodItemHandler class.
     *
     * @param filePath
     * @param validating
     */
    public FoodItemHandler(String filePath, boolean validating) {
        source = filePath;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(validating);

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            System.exit(1); // TODO this seems a bit extreme???
        }
    }


    /**
     * Uses DocumentBuilder builder to parse the input XML file
     * and generates a tree for processing.
     */
    public void parseInput() {
        try {
            parsedDoc = builder.parse(source);
        } catch (SAXException se) {
            System.err.println(se.getMessage());
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            System.exit(1);
        }
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
     *
     * @return Map<String, FoodItem>
     */
    public Map<String, FoodItem> getFoodItems() {
        foodItems = new HashMap<String, FoodItem>();
        NodeList nodeList = parsedDoc.getElementsByTagName("fooditem");
        int numNodes = nodeList.getLength();

        Element node;

        for (int i = 0; i < numNodes; i++) {
            reset();
            node = (Element) nodeList.item(i);

//            recipeNotes = node.getElementsByTagName("recipeNotes").item(0).getTextContent();
//            salePrice = Double.parseDouble(node.getElementsByTagName("salePrice").item(0).getTextContent());
            code = node.getElementsByTagName("code").item(0).getTextContent();
            name = node.getElementsByTagName("name").item(0).getTextContent();
            unit = units(node.getAttribute("unit"));
            isVegetarian = diet(node.getAttribute("isVegetarian"));
            isVegan = diet(node.getAttribute("isVegan"));
            isGlutenFree = diet(node.getAttribute("isGlutenFree"));
            caloriesPerUnit = Double.parseDouble(node.getElementsByTagName("caloriesPerUnit").item(0).getTextContent());
            // I'm not sure how components and ingredientCounts should be done.

            // set up FoodItem
            FoodItem food = new FoodItem(code, name, unit);
            food.setIsGlutenFree(isGlutenFree);
            food.setIsVegan(isVegan);
            food.setIsVegetarian(isVegetarian);
            food.setCaloriesPerUnit(caloriesPerUnit);

            // FOR TESTING
//            System.out.println("Code is: " + code);
//            System.out.println("Unit is: " + unit);
//            System.out.println("isVegetarian: " + isVegetarian);
//            System.out.println("isVegan: " + isVegan);
//            System.out.println("isGlutenFree: " + isGlutenFree);
//            System.out.println("name: " + name);
//            System.out.println("caloriesPerUnit: " + caloriesPerUnit);
//            System.out.println();

            foodItems.put(code, food);
        }
        return foodItems;
    }

    private UnitType units(String s) {
        UnitType uni;
        switch (s) {
            case "g":
                uni = UnitType.GRAM;
                break;
            case "ml":
                uni = UnitType.ML;
                break;
            case "count":
                uni = UnitType.COUNT;
                break;
            default:
                uni = UnitType.UNKNOWN;
        }
        return uni;
    }

    private DietaryLogic diet(String s) {
        DietaryLogic logic;
        switch (s) {
            case "yes":
                logic = DietaryLogic.YES;
                break;
//            case "no":        // HELLO Euan here. I commented this out as the case NO is already covered with the default case :)
//                logic = DietaryLogic.NO;
//                break;
            case "optional":
                logic = DietaryLogic.OPTIONAL;
                break;
            default:
                logic = DietaryLogic.NO;
        }
        return logic;
    }


    private void reset() {
//        recipeNotes = "";
//        salePrice = 0;
        code = "";
        name = "";
        unit = UnitType.UNKNOWN;
        isVegetarian = DietaryLogic.NO;
        isVegan = DietaryLogic.NO;
        isGlutenFree = DietaryLogic.NO;
        caloriesPerUnit = 0;
    }


    /**
     * Main function for testing.
     * @param args
     */
    public static void main(String args[]) {
        FoodItemHandler fh = new FoodItemHandler("resources/data/FoodItem.xml", true);
        fh.parseInput();
        fh.getFoodItems();
    }
}