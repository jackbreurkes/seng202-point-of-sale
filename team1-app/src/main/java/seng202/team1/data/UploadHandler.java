package seng202.team1.data;

import org.xml.sax.SAXException;
import seng202.team1.model.FoodItem;

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
     * A dictionary that contains uploaded food items.
     */
    private static Map<String, FoodItem> foodItemsUploaded;





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

                    itemStorage.updateFoodItem(foodItem);
                    itemStorage.setFoodItemStock(code, itemStorage.getFoodItemStock(code) + 1);
                }
            }
        }
    }



    public static void parseFoodItems(String foodItemFile) throws IOException, SAXException {
        if (checkFileOK(foodItemFile)) {
            FoodItemHandler foodItemHandler = new FoodItemHandler(pathName, validating);
            foodItemHandler.parseInput();
            foodItemsUploaded = foodItemHandler.getFoodItems();
        }
    }

    // checks if xml file has food item that has different values
    public static boolean checkModifiedFoodItem() {
        FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
        for (FoodItem foodItem: foodItemsUploaded.values()) {
            String code = foodItem.getCode();
            FoodItem storageFoodItem = itemStorage.getFoodItemByCode(code);
            // food item in storage
            if (storageFoodItem != null) {
                System.out.println("------- FOOD ITEM " + foodItem.toString());
                System.out.println("STORAGE FOOD ITEM " + storageFoodItem.toString());
                if (foodItem.toString().compareTo(storageFoodItem.toString()) != 0) {
                    System.out.println("not identical");
                    return true;

                // food item in storage has different values
                } else {
                    System.out.println("identical");
                }
            }
        }
        // food item not in storage
        return false;
    }

    public static void uploadFoodItems(boolean overwrite) {
        FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
        for (FoodItem foodItem: foodItemsUploaded.values()) {
            String code = foodItem.getCode();
            FoodItem storageFoodItem = itemStorage.getFoodItemByCode(code);

            if (overwrite == false) {
                if (storageFoodItem == null) {
                    itemStorage.addFoodItem(foodItem, 1);
                } else {
                    itemStorage.setFoodItemStock(code, itemStorage.getFoodItemStock(code) + 1);
                }
            } else {
                if (storageFoodItem == null) {
                    itemStorage.addFoodItem(foodItem, 1);
                } else {
                    itemStorage.updateFoodItem(foodItem);
                    itemStorage.setFoodItemStock(code, itemStorage.getFoodItemStock(code) + 1);
                }

            }
        }
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
        FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
        DAOFactory.resetInstances();
        //UploadHandler.uploadFoodItems("resources/data/FoodItem2.xml");
        UploadHandler.parseFoodItems("resources/data/FoodItem2.xml");
        Boolean duplicateFoodItem = checkModifiedFoodItem();
        UploadHandler.uploadFoodItems(false);

        for (FoodItem foo: itemStorage.getAllFoodItems()) {
            System.out.println(foo);
        }

        UploadHandler.parseFoodItems("resources/data/FoodItem3.xml");
        duplicateFoodItem = checkModifiedFoodItem();
        UploadHandler.uploadFoodItems(true);

        for (FoodItem foo: itemStorage.getAllFoodItems()) {
            System.out.println(foo);
        }
    }
}
