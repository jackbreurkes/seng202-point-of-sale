package seng202.team1.data;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import seng202.team1.model.SuppliedFoodItem;
import seng202.team1.model.FoodItem;
import seng202.team1.model.SuppliedFoodItem;
import seng202.team1.util.DietaryLogic;
import seng202.team1.util.UnitType;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SuppliedFoodItemHander {

    private DocumentBuilder builder;
    private Document parsedDoc;
    private String path;
    private File xmlFile;
    private Map<String, SuppliedFoodItem> SuppliedFoodItem;
    private String code;
    private double caloriesPerUnit;
    private String name;
    private UnitType unit;
    private DietaryLogic isVegetarian;
    private DietaryLogic isVegan;
    private DietaryLogic isGlutenFree;
    private List<Supplier> suppliers;

    public SuppliedFoodItemHander(String filePath, boolean validate) {

    }
}
