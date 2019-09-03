package seng202.team1.data;

import seng202.team1.model.FoodItem;
import seng202.team1.model.Supplier;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * UploadHandler contains methods to upload
 * an XML file using specific handlers.
 * This relieves the need to create each handler
 * objects when wanting to upload files.
 */
public class UploadHandler {

    /**
     * Path directory to file.
     */
    private static String pathName = null;

    /**
     * Validity check.
     */
    private static boolean validating = true;

    /**
     * A dictionary that contains uploaded suppliers.
     */
    private static Map<String, Supplier> suppliersUploaded;

    /**
     * A dictionary that contains uploaded food items.
     */
    private static Map<String, FoodItem> foodItemsUploaded;

    /**
     * Static function that calls methods from all the necessary
     * methods from SupplierHandler to upload a suppliers XML file.
     * @param supplierFile String
     */
    public static void uploadSuppliers(String supplierFile) {
        if (checkFileOK(supplierFile)) {
            SupplierHandler supplierHandler = new SupplierHandler(pathName, validating);
            supplierHandler.parseInput();
            suppliersUploaded = supplierHandler.getSuppliers();
        }
    }

    /**
     * Static function that calls methods from all the necessary
     * methods from FoodItemHandler to upload a food items XML file.
     * @param foodItemFile String
     */
    public static void uploadFoodItems(String foodItemFile) {
        if (checkFileOK(foodItemFile)) {
            FoodItemHandler foodItemHandler = new FoodItemHandler(pathName, validating);
            foodItemHandler.parseInput();
            foodItemsUploaded = foodItemHandler.getFoodItems();

            FoodItemDAO itemStorage = MemoryStorage.getInstance();
            for (FoodItem foodItem: foodItemsUploaded.values()) {
                itemStorage.addFoodItem(foodItem, 1);
            }
        }
    }

    /**
     * Helper function to check if pathName is valid.
     * @param fName String
     * @return boolean
     */
    private static boolean checkFileOK(String fName) {
        try {
            pathName = (new File(fName)).toURI().toURL().toString();
        } catch (IOException ioe) {
            System.err.println("Problem reading file: <" + fName + ">  Check for typos");
            System.err.println(ioe);
            System.exit(666); // ??
        }
        return true;
    }


    public static void main(String args[]) {
        UploadHandler u = new UploadHandler();
        u.uploadSuppliers("resources/data/Supplier.xml");
//        System.out.println(suppliersUploaded.keySet());
//        System.out.println(suppliersUploaded.values());
//        System.out.println("");

        u.uploadFoodItems("resources/data/FoodItem.xml");
//        System.out.println(foodItemsUploaded.keySet());
//        System.out.println(foodItemsUploaded.values());
//        System.out.println("");
//        for (Supplier supplier: suppliersUploaded.values()) {
//            System.out.println(supplier.getName());
//        }
//        System.out.println("");
//        for (FoodItem foo: foodItemsUploaded.values()) {
//            System.out.println(foo.getName());
//        }
    }


}
