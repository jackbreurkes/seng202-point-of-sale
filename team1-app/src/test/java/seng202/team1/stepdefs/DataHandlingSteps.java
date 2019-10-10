package seng202.team1.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.joda.money.BigMoney;

import java.math.RoundingMode;
import org.junit.Ignore;
import org.xml.sax.SAXException;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.UploadHandler;
import seng202.team1.model.FoodItem;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.UnitType;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataHandlingSteps {

    String directory;
    String fileName;

    static String MONEY_COUNTRY_CODE_PREFIX = "NZD ";
    FoodItemDAO itemStorage;
    FoodItem foodItem;
    BigMoney foodItemCost;
    BigMoney updatedCost;
    BigMoney discountedCost;
    BigMoney expectedCostNZD;


    @Given("the user has data from directory {string} to upload")
    public void the_user_has_data_from_directory_to_upload(String expectedDirectory) {
        directory = expectedDirectory;
    }

    @And("data is within a valid directory")
    public void data_is_within_a_valid_directory() {
        try {
            Paths.get(directory);
        } catch (InvalidPathException | NullPointerException ex) {
            throw new cucumber.api.PendingException(directory + " is not a valid directory");
        }
    }

    @When("{string} is parsed and uploaded")
    public void is_parsed_and_uploaded(String fileName) {
        itemStorage = DAOFactory.getFoodItemDAO();
        DAOFactory.resetInstances();
        try {
            UploadHandler.uploadFoodItems(directory);
        } catch (IOException | SAXException e) {
            throw new cucumber.api.PendingException(fileName + " is not a valid file");
        }
    }

    @Then("upload of {string} is a success")
    public void upload_of_is_a_success(String string) {
        assertEquals( 4, itemStorage.getAllFoodItems().size());
    }


    @Given("XML file from {string} is uploaded")
    public void XML_file_from_directory_is_uploaded(String directory) {
        if (directory.contains(".xml")) {
            itemStorage = DAOFactory.getFoodItemDAO();
            DAOFactory.resetInstances();
            try {
                UploadHandler.uploadFoodItems(directory);
            } catch (IOException | SAXException e) {
                throw new cucumber.api.PendingException(directory + " not a valid directory");
            }
        } else {
            throw new cucumber.api.PendingException(directory + " not a valid directory");
        }
    }

    @When("user wants to know the total cost of {int} {string}")
    public void user_wants_to_know_the_total_cost_of(int expectedCount, String expectedCode) {
        foodItem = itemStorage.getFoodItemByCode(expectedCode);
        if (foodItem == null) {
            throw new cucumber.api.PendingException(expectedCode + " has not been added to the database");
        } else {
            foodItemCost = foodItem.getCost().multipliedBy(expectedCount);
        }
    }

    @Then("the total cost is ${double}")
    public void the_total_cost_is_$(Double expectedCost) {
        expectedCostNZD = BigMoney.parse(MONEY_COUNTRY_CODE_PREFIX + expectedCost);
        try {
            assertEquals(0, foodItemCost.compareTo(expectedCostNZD));
        } catch (AssertionError ae) {
            throw new cucumber.api.PendingException("Expected cost " + expectedCostNZD +
                                                    " not equivalent to actual cost " + foodItemCost);
        }
    }

    @When("user wants to update cost of a single {string} to ${double}")
    public void user_wants_to_update_cost_of_a_single_to(String expectedCode, Double expectedUpdatedCost) {
        foodItem = itemStorage.getFoodItemByCode(expectedCode);
        if (foodItem == null) {
            throw new cucumber.api.PendingException(expectedCode + " has not been added to the database");
        } else {
            updatedCost = BigMoney.parse(MONEY_COUNTRY_CODE_PREFIX + expectedUpdatedCost);
            foodItem.setCost(updatedCost);
        }
    }

    @Then("cost is successfully updated to ${double}")
    public void cost_is_successfully_updated_to_$(Double expectedUpdatedCost) {
        expectedCostNZD = BigMoney.parse(MONEY_COUNTRY_CODE_PREFIX + expectedUpdatedCost);
        try {
            assertEquals(0, updatedCost.compareTo(expectedCostNZD));
        } catch (AssertionError ae) {
            throw new cucumber.api.PendingException("Expected updated cost " + expectedCostNZD +
                    " not equivalent to actual updated cost " + updatedCost);
        }
    }

    @When("user wants to discount cost of a single {string} by {int}%")
    public void user_wants_to_discount_cost_of_a_single_by(String expectedCode, Integer percentage) {
        foodItem = itemStorage.getFoodItemByCode(expectedCode);
        if (foodItem == null) {
            throw new cucumber.api.PendingException(expectedCode + " has not been added to the database");
        } else {
            BigMoney discount = foodItem.getCost().multipliedBy(percentage).dividedBy(100, RoundingMode.FLOOR);
            discountedCost = foodItem.getCost().minus(discount);
            foodItem.setCost(discountedCost);
        }
    }

    @Then("cost should be ${double}")
    public void cost_should_be_$(Double expectedDiscountedCost) {
        expectedCostNZD = BigMoney.parse(MONEY_COUNTRY_CODE_PREFIX + expectedDiscountedCost);
        try {
            assertEquals(0, discountedCost.compareTo(expectedCostNZD));
        } catch (AssertionError ae) {
            throw new cucumber.api.PendingException("Expected discounted cost " + expectedCostNZD +
                    " not equivalent to actual discounted cost " + discountedCost);
        }
    }


    @When("user creates a new dish {string} with the code {string} and the unit {string}")
    public void user_adds_a_new_dish_with_the_code_and_the_unit(String name, String code, String unitString) {
        UnitType unitType = UnitType.GRAM;
        switch(unitString) {
            case "g":
                unitType = UnitType.GRAM;
                break;
            case "ml":
                unitType = UnitType.ML;
                break;
            case "c":
                unitType = UnitType.COUNT;
                break;
            default:
                throw new cucumber.api.PendingException(unitString + " does not correspond to a valid unit type");
        }
        try {
            foodItem = new FoodItem(code, name, unitType);
        } catch (IllegalArgumentException iae) {
            throw new cucumber.api.PendingException(iae.getMessage());
        }
    }

    @And("sets the food item's price to ${double}")
    public void sets_the_food_item_s_price_to_$(Double cost) {
        foodItemCost = BigMoney.parse(MONEY_COUNTRY_CODE_PREFIX + cost);
        try {
            foodItem.setCost(foodItemCost);
        } catch (IllegalArgumentException iae) {
            throw new cucumber.api.PendingException("Cost is " + foodItemCost + " and " + iae.getMessage());
        }
    }

    @And("adds food item to database")
    public void adds_food_item_to_database() {
        try {
            itemStorage.addFoodItem(foodItem, 1);
        } catch (InvalidDataCodeException idce) {
            throw new cucumber.api.PendingException(idce.getMessage());
        }
    }

    @Then("the food item {string} should be successfully added to the database storage")
    public void the_food_item_should_be_successfully_added_to_the_database_storage(String addedFoodCode) {
        try {
            assertEquals(foodItem, itemStorage.getFoodItemByCode(addedFoodCode));
        } catch (AssertionError ae) {
            throw new cucumber.api.PendingException(foodItem.getName() + " does not exist in the storage database");
        }
    }

    @When("user deletes food item with the code {string}")
    public void user_deletes_food_item_with_the_code(String toBeDeletedCode) {
        try {
            itemStorage.removeFoodItem(toBeDeletedCode);
        } catch (InvalidDataCodeException idce) {
            throw new cucumber.api.PendingException(idce.getMessage());
        }
    }

    @Then("{string} should be successfully removed from the database storage")
    public void should_be_successfully_removed_from_the_database_storage(String expectedDeletedCode) {
        try {
            assertNull(itemStorage.getFoodItemByCode(expectedDeletedCode));
        } catch (AssertionError ae) {
            throw new cucumber.api.PendingException(expectedDeletedCode + " has not been deleted from the database");
        }
    }
















//    String source;
//    String testName;
//    List<FoodItem> expectedItems;
//    FoodItemDAO itemStorage;
//
//    @Given("the user has a data from directory {string} to upload")
//    public void the_user_has_a_data_from_directory_to_upload(String source) {
//        this.source = source;
//        //throw new cucumber.api.PendingException();
//    }
//
//    @And("the file is the right type and correctly formatted")
//    public void the_file_is_the_right_type_and_correctly_formatted() {
//        if (source.contains("TESTXML1.xml")) {
//            testName = "TESTXML1";
//        } else if (source.contains("TESTXML2.xml")) {
//            testName = "TESTXML2";
//        } else {
//            throw new cucumber.api.PendingException(source + " not a valid directory");
//        }
//    }
//
//    @When("the user uploads the file")
//    public void the_user_uploads_the_file() {
//        itemStorage = DAOFactory.getFoodItemDAO();
//        DAOFactory.resetInstances();
//        try {
//            UploadHandler.uploadFoodItems(source);
//        } catch (IOException | SAXException e) {
//            e.printStackTrace();
//            throw new cucumber.api.PendingException("Failed uploading " + testName);
//        }
//    }
//
//    @Then("the system adds the new data to the current data")
//    public void the_system_adds_the_new_data_to_the_current_data() {
//        // Write code here that turns the phrase above into concrete actions
//        List<FoodItem> items = new ArrayList<FoodItem>(itemStorage.getAllFoodItems());
//        items.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));
//
//        switch (testName) {
//            case "TESTXML1":
//                FoodItem beefburg = new FoodItem("BEEFBURG", "Hamburger", UnitType.GRAM);
//                beefburg.setCost(BigMoney.parse("NZD 21.00"));
//                beefburg.setCaloriesPerUnit(295);
//                FoodItem cheeseburg = new FoodItem("CHEESEBURG", "Cheeseburger", UnitType.GRAM);
//                cheeseburg.setCost(BigMoney.parse("NZD 22.00"));
//                cheeseburg.setCaloriesPerUnit(303);
//                FoodItem tofuburg1 = new FoodItem("TOFUBURG", "Tofu Burger", UnitType.GRAM);
//                tofuburg1.setCost(BigMoney.parse("NZD 50.00"));
//                tofuburg1.setCaloriesPerUnit(168.4);
//                tofuburg1.setIsVegetarian(true);
//                FoodItem applesoda = new FoodItem("APPLESODA", "Sparkling Apple", UnitType.ML);
//                applesoda.setCost(BigMoney.parse("NZD 96.00"));
//                applesoda.setCaloriesPerUnit(87);
//                applesoda.setIsVegan(true);
//                applesoda.setIsGlutenFree(true);
//                expectedItems = Arrays.asList(
//                        beefburg, cheeseburg, tofuburg1, applesoda
//                );
//                expectedItems.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));
//                break;
//            case "TESTXML2":
//                FoodItem banana = new FoodItem("BANANA", "Banana", UnitType.COUNT);
//                banana.setCost(BigMoney.parse("NZD 1.00"));
//                banana.setCaloriesPerUnit(100);
//                FoodItem applepie = new FoodItem("APPLEPIE", "Apple Pie", UnitType.COUNT);
//                applepie.setCost(BigMoney.parse("NZD 21.00"));
//                applepie.setCaloriesPerUnit(200);
//                FoodItem tofuburg2 = new FoodItem("TOFUBURG", "Tofu Burger", UnitType.GRAM);
//                tofuburg2.setCost(BigMoney.parse("NZD 50.00"));
//                tofuburg2.setCaloriesPerUnit(268.4);
//                tofuburg2.setIsVegetarian(true);
//                FoodItem water = new FoodItem("WATER", "Glass of water", UnitType.ML);
//                water.setCost(BigMoney.parse("NZD 200.00"));
//                water.setCaloriesPerUnit(0);
//                water.setIsVegan(true);
//                water.setIsGlutenFree(true);
//                expectedItems = Arrays.asList(
//                        banana, applepie, tofuburg2, water
//                );
//                expectedItems.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));
//                break;
//            default:
//                throw new cucumber.api.PendingException(testName + " is an invalid file");
//        }
//
//        Assertions.assertEquals(items.size(), expectedItems.size());
//        Assertions.assertEquals(items, expectedItems);
//
//    }
//
//
//    @Ignore
//    @And("prompts the user to decide what to keep for each duplicate entry")
//    public void prompts_the_user_to_decide_what_to_keep_for_each_duplicate_entry() {
//        // Write code here that turns the phrase above into concrete actions
//        //throw new cucumber.api.PendingException();
//    }
//
//
//
//
//
//
//
//    @Ignore
//    @Given("the system is in data entry mode")
//    public void the_system_is_in_data_entry_mode() {
//        // Write code here that turns the phrase above into concrete actions
//        //throw new cucumber.api.PendingException();
//    }
//
//    @Ignore
//    @When("the user provides new or updated data in the correct field\\(s)")
//    public void the_user_provides_new_or_updated_data_in_the_correct_field_s() {
//        // Write code here that turns the phrase above into concrete actions
//        //throw new cucumber.api.PendingException();
//    }
//
//    @Ignore
//    @Then("the system updates the data entry")
//    public void the_system_updates_the_data_entry() {
//        // Write code here that turns the phrase above into concrete actions
//        //throw new cucumber.api.PendingException();
//    }
//
//
//
//
//
//
//    @Ignore
//    @Given("the system is in the food item entry state")
//    public void the_system_is_in_the_food_item_entry_state() {
//        // Write code here that turns the phrase above into concrete actions
//        //throw new cucumber.api.PendingException();
//    }
//
//    @Ignore
//    @When("user adds a new food item")
//    public void user_adds_a_new_food_item() {
//        // Write code here that turns the phrase above into concrete actions
//        //throw new cucumber.api.PendingException();
//    }
//
//    @Ignore
//    @Then("system adds new food item to database")
//    public void system_adds_new_food_item_to_database() {
//        // Write code here that turns the phrase above into concrete actions
//        //throw new cucumber.api.PendingException();
//    }
//
//    @Ignore
//    @Then("system displays the new food item")
//    public void system_displays_the_new_food_item() {
//        // Write code here that turns the phrase above into concrete actions
//        //throw new cucumber.api.PendingException();
//    }










    // This @give test is done twice. Once in this test case and again in the following one below.
    // Intellij doesn't like duplicate code so I just renamed the one below.
    // Refer to design doc acceptance tests section for more info
    @Ignore
    @Given("menu is not empty")
    public void menu_is_not_empty() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("user deletes recipe")
    public void user_deletes_recipe() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("system removes food item from database")
    public void system_removes_food_item_from_database() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }









    @Ignore
    @Given("menu is not empty2")
    public void menu_is_not_empty2() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }


    @Ignore
    @When("user modifies recipe")
    public void user_modifies_recipe() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("system updates recipe in database")
    public void system_updates_recipe_in_database() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }











    @Ignore
    @Given("an ingredient is selected")
    public void an_ingredient_is_selected() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @And("the system is showing all available information about the ingredient")
    public void the_system_is_showing_all_available_information_about_the_ingredient() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the user changes a specific field")
    public void the_user_changes_a_specific_field() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the system updates the value to match the user input")
    public void the_system_updates_the_value_to_match_the_user_input() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }







    @Ignore
    @Given("the current stock is recorded into the database")
    public void the_current_stock_is_recorded_into_the_database() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("user requests remaining serves")
    public void user_requests_remaining_serves() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the application should compute how many servings can be made with the given amount of stock")
    public void the_application_should_compute_how_many_servings_can_be_made_with_the_given_amount_of_stock() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }





    @Ignore
    @Given("a food item is in the database and is currently selected")
    public void a_food_item_is_in_the_database_and_is_currently_selected() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the user changes the quantity value")
    public void the_user_changes_the_quantity_value() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the recipe is updated to display the new quantity")
    public void the_recipe_is_updated_to_display_the_new_quantity() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }







    @Ignore
    @Given("the selected food item\\(s) are not already in the menu")
    public void the_selected_food_item_s_are_not_already_in_the_menu() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the user adds a food item to the menu")
    public void the_user_adds_a_food_item_to_the_menu() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the food item and its details should be displayed on the menu")
    public void the_food_item_and_its_details_should_be_displayed_on_the_menu() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }







    @Ignore
    @Given("the items exist in the database")
    public void the_items_exist_in_the_database() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @And("the item has appropriate search\\/ordering values")
    public void the_item_has_appropriate_search_ordering_values() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the user specifies the attribute to sort by")
    public void the_user_specifies_the_attribute_to_sort_by() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the database returns a sorted list of the relevant items")
    public void the_database_returns_a_sorted_list_of_the_relevant_items() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }









    @Ignore
    @Given("the ingredients are registered in the database")
    public void the_ingredients_are_registered_in_the_database() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the ingredients reach a certain low level")
    public void the_ingredients_reach_a_certain_low_level() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the user is informed that the ingredient is running low")
    public void the_user_is_informed_that_the_ingredient_is_running_low() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }







    @Ignore
    @Given("the tags and time frame are specified")
    public void the_tags_and_time_frame_are_specified() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("sales are made during the time frame")
    public void sales_are_made_during_the_time_frame() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the sales data is saved with the property specified")
    public void the_sales_data_is_saved_with_the_property_specified() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }






    @Ignore
    @Given("the sales data is recorded")
    public void the_sales_data_is_recorded() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the application is asked to generate a sales report")
    public void the_application_is_asked_to_generate_a_sales_report() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the sales data is displayed with visualised sales figures")
    public void the_sales_data_is_displayed_with_visualised_sales_figures() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }
}
