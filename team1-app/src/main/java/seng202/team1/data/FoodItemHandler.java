package seng202.team1.data;

import org.w3c.dom.*;
import org.xml.sax.*;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import javax.xml.parsers.*;
import java.io.*;
import java.net.MalformedURLException;
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
    private UnitType unit;
    private boolean isVegetarian;
    private boolean isVegan;
    private boolean isGlutenFree;
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
            //builder.setEntityResolver((publicId, systemId) -> new InputSource(SupplierHandler.class.getResourceAsStream("/dtd/fooditem.dtd")));
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            System.exit(1); // TODO this seems a bit extreme???
        }

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
     */
    public void parseInput() {
        try {
            parsedDoc = builder.parse(source);
        } catch (SAXException se) {
            System.err.println(se.getMessage());
            System.exit(1);
        } catch (MalformedURLException mue) {
            System.err.println(mue);
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
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

            code = node.getElementsByTagName("code").item(0).getTextContent();
            name = node.getElementsByTagName("name").item(0).getTextContent();
            unit = units(node.getAttribute("unit"));

            FoodItem food = new FoodItem(code, name, unit);
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
            caloriesPerUnit = Double.parseDouble(node.getElementsByTagName("caloriesPerUnit").item(0).getTextContent());
            food.setCaloriesPerUnit(caloriesPerUnit);
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
            default:
                uni = UnitType.COUNT;
        }
        return uni;
    }

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


    private void reset() {
//        recipeNotes = "";
//        salePrice = 0;
        code = "";
        name = "";
        unit = UnitType.COUNT;
        isVegetarian = false;
        isVegan = false;
        isGlutenFree = false;
        caloriesPerUnit = 0;
    }


    /**
     * Main function for testing.
     * @param args
     */
    public static void main(String args[]) {
        FoodItemHandler fh = null;
        fh = new FoodItemHandler("resources/data/FoodItem.xml", true);
        fh.parseInput();
        fh.getFoodItems();
        System.out.println(fh.getFoodItems().keySet());
        System.out.println(fh.getFoodItems().values());
        System.out.println("");

        System.out.println("");
        for (FoodItem foo: fh.getFoodItems().values()) {
            System.out.println(foo.getName());
        }
    }
}