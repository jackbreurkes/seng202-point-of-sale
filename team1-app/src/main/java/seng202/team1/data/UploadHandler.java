package seng202.team1.data;

import seng202.team1.model.FoodItem;
import seng202.team1.model.Supplier;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class UploadHandler {

    private static String pathName = null;
    private static boolean validating = true;

    private static Map<String, Supplier> suppliersLoaded;
    private static Map<String, FoodItem> foodItemsLoaded;

    public static void uploadSuppliers(String supplierFile) {
        if (checkFileOK(supplierFile)) {
            SupplierHandler supplierHandler = new SupplierHandler(pathName, validating);
            supplierHandler.parseInput();
            suppliersLoaded = supplierHandler.getSuppliers();
        }
    }

    public static void uploadFoodItems(String foodItemFile) {
        if (checkFileOK(foodItemFile)) {
            FoodItemHandler foodItemHandler = new FoodItemHandler(pathName, validating);
            foodItemHandler.parseInput();
            foodItemsLoaded = foodItemHandler.getFoodItems();
        }
    }

    private static boolean checkFileOK(String fName) {
        try {
            pathName = (new File(fName)).toURI().toURL().toString();
        } catch (IOException ioe) {
            System.err.println("Problem reading file: <" + fName + ">  Check for typos");
            System.err.println(ioe);
            System.exit(666);
        }
        return true;
    }

    public static void main(String args[]) {
        UploadHandler u = new UploadHandler();
        u.uploadSuppliers("team1-app/resources/data/Supplier.xml");
        System.out.println(suppliersLoaded.keySet());
        System.out.println(suppliersLoaded.values());
        for (Supplier supplier: suppliersLoaded.values()) {
            System.out.println(supplier.getName());
        }

        System.out.println("");

        u.uploadFoodItems("team1-app/resources/data/FoodItem.xml");
        System.out.println(foodItemsLoaded.keySet());
        System.out.println(foodItemsLoaded.values());
        for (FoodItem foo: foodItemsLoaded.values()) {
            System.out.println(foo.getName());
        }

    }


}
