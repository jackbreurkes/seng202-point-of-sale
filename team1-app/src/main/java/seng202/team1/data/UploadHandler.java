package seng202.team1.data;

import org.xml.sax.SAXException;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Supplier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
     * @param supplierFile supplier XML file path
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
     * @param foodItemFile food item xml file path
     */
    public static void uploadFoodItems(String foodItemFile) throws IOException, SAXException {
        if (checkFileOK(foodItemFile)) {
            FoodItemHandler foodItemHandler = new FoodItemHandler(pathName, validating);
            foodItemHandler.parseInput();
            foodItemsUploaded = foodItemHandler.getFoodItems();

            FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
            for (FoodItem foodItem: foodItemsUploaded.values()) {
                String code = foodItem.getCode();
                FoodItem storageFoodItem = itemStorage.getFoodItemByCode(code);
                // This checks if foodItem already in database or nah
                if (storageFoodItem == null) {
                    itemStorage.addFoodItem(foodItem, 1);
                } else {
//                    System.out.println("IN STORAGE: " + storageFoodItem);
//                    System.out.println("COMING: " + foodItem);

                    //findDifferences(storageFoodItem, foodItem);

                    // Find differences in storage food item


                    // LETS USER choose between keeping old value or overwriting data
                    // Consult the GUI pros
                    // like if user wants updated: update
                    // else don't update?
                    // but have to alter JDBC storage?
                    // Update don't have to but prioritising
                    // led to the decision of maybe not going forward with this.
                    // Please review in future

                    itemStorage.updateFoodItem(foodItem);
                    itemStorage.setFoodItemStock(code, itemStorage.getFoodItemStock(code) + 1);
                }
            }
        }
    }

    public static void findDifferences(FoodItem storageFoodItem, FoodItem uploadedFoodItem) {
        List<FoodItem> foodItems = new ArrayList<>();
    }


    /**
     * Helper function to check if pathName is valid.
     * @param fName pathname to file
     * @return boolean that indicates if file is valid
     */
    private static boolean checkFileOK(String fName) {
        try {
            pathName = (new File(fName)).toURI().toURL().toString();
        } catch (IOException ioe) {
            System.err.println("Problem reading file: <" + fName + ">  Check for typos please!");
            System.err.println(ioe);
            //System.exit(666); // ??
        }
        return true;
    }


    public static void main(String args[]) throws IOException, SAXException {
//        FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
//        DAOFactory.resetInstances();
//        UploadHandler.uploadFoodItems("resources/data/FoodItem2.xml");
//        UploadHandler.uploadFoodItems("resources/data/FoodItem3.xml");
//        for (FoodItem foo: itemStorage.getAllFoodItems()) {
//            System.out.println(foo);
//        }
    }
}
