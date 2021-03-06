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
     * Static function that parses an XML file that contains food items to be uploaded.
     * Calls the parseInput() method from FoodItemHandler to do so.
     * @param foodItemFile Food Item XML file to be parsed
     * @throws IOException Exception generated by parseInput()
     * @throws SAXException Exception generated by parseInput()
     */
    public static void parseFoodItems(String foodItemFile) throws IOException, SAXException {
        if (checkFileOK(foodItemFile)) {
            FoodItemHandler foodItemHandler = new FoodItemHandler(pathName, validating);
            foodItemHandler.parseInput();
            foodItemsUploaded = foodItemHandler.getFoodItems();
        }
    }


    // checks if xml file has food item that has different values

    /**
     * Checks if the food items parsed from parseFoodItems()
     * are present in the database storage.
     * If they are, check if their values are different.
     * Returns true if a food item exists in the database
     * with different values. Otherwise returns false.
     * @return a Boolean that indicates modified food items
     */
    public static boolean checkModifiedFoodItem() {
        FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
        for (FoodItem foodItem: foodItemsUploaded.values()) {
            String code = foodItem.getCode();
            FoodItem storageFoodItem = itemStorage.getFoodItemByCode(code);
            // food item in storage
            if (storageFoodItem != null) {
                if (foodItem.toString().compareTo(storageFoodItem.toString()) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Uploads a food item XML file parsed from parseFoodItems().
     * Takes a parameter overwrite which dictates whether or not
     * the food item values of the XML file parsed that are different
     * from the storage is overwritten or ignored.
     * @param overwrite a Boolean responsible of overwriting or ignoring changes
     */
    public static void uploadFoodItems(boolean overwrite) {
        FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
        for (FoodItem foodItem: foodItemsUploaded.values()) {
            String code = foodItem.getCode();
            FoodItem storageFoodItem = itemStorage.getFoodItemByCode(code);

            if (overwrite == false) {
                if (storageFoodItem == null) {
                    itemStorage.addFoodItem(foodItem, 0);
                }
            } else {
                if (storageFoodItem == null) {
                    itemStorage.addFoodItem(foodItem, 0);
                } else {
                    itemStorage.updateFoodItem(foodItem);
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
}
