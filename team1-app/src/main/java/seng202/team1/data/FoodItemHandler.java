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
    private String path;
    private File xmlFile;
    private Map<String, FoodItem> foodItem;
    private List<FoodItem> components;
    private Map<FoodItem, Integer> ingredientCounts;
    private String recipeNotes;
    private double salePrice;
    private String code;
    private UnitType unit;
    private DietaryLogic isVegetarian;
    private DietaryLogic isVegan;
    private DietaryLogic isGlutenFree;
    private String name;
    private double caloriesPerUnit;

    public FoodItemHandler(String filePath, boolean validate) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(validate);
        path = filePath;
        xmlFile = new File(path);

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException parseE) {
            parseE.printStackTrace();
            System.exit(1);
        }
    }

    public void parseInput() {
        try {
            parsedDoc = builder.parse(xmlFile);
        } catch (SAXException se) {
            System.err.println(se.getMessage());
            System.exit(1);
        } catch (IOException ie) {
            System.err.println(ie.getMessage());
            System.exit(1);
        }
    }

    public Document parsedDoc() {
        return parsedDoc;
    }

    public Map<String, FoodItem> getFoodItem() {
        foodItem = new HashMap<String, FoodItem>();
        NodeList nodeList = parsedDoc.getElementsByTagName("cfooditem");
        Element node;

        for (int i = 0; i < nodeList.getLength(); i++) {
            reset();
            node = (Element) nodeList.item(i);

            recipeNotes = node.getElementsByTagName("recipeNotes").item(0).getTextContent();
            salePrice = Double.parseDouble(node.getElementsByTagName("salePrice").item(0).getTextContent());
            code = node.getElementsByTagName("code").item(0).getTextContent();
            unit = units(node.getAttribute("unit"));
            isVegetarian = diet(node.getAttribute("isVeg"));
            isVegan = diet(node.getAttribute("isVegan"));
            isGlutenFree = diet(node.getAttribute("isgf"));
            name = node.getElementsByTagName("name").item(0).getTextContent();
            caloriesPerUnit = Double.parseDouble(node.getElementsByTagName("calorieCount").item(0).getTextContent());
            // I'm not sure how components and ingredientCounts should be done.

            FoodItem food = new FoodItem(code, name, UnitType.COUNT);
            food.setCaloriesPerUnit(caloriesPerUnit);
            food.setIsGlutenFree(isGlutenFree);
            food.setIsVegan(isVegan);
            food.setIsVegetarian(isVegetarian);
            food.setName(name);
            food.setUnit(unit);

            foodItem.put(code, food);
        }
        return foodItem;
    }

    private UnitType units(String s) {
        UnitType uni;
        switch (s) {
            case "gram":
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
        recipeNotes = "";
        salePrice = 0;
        code = "";
        name = "";
        unit = UnitType.UNKNOWN;
        isVegetarian = DietaryLogic.NO;
        isVegan = DietaryLogic.NO;
        isGlutenFree = DietaryLogic.NO;
        caloriesPerUnit = 0;
    }
}