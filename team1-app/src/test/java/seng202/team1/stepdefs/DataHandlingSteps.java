package seng202.team1.stepdefs;

//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.joda.money.BigMoney;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.xml.sax.SAXException;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.UploadHandler;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataHandlingSteps {

    String source;
    String testName;
    List<FoodItem> expectedItems;
    FoodItemDAO itemStorage;

    @Given("the user has a data {string} to upload")
    public void the_user_has_a_data_file_to_upload(String source) {
        this.source = source;
        //throw new cucumber.api.PendingException();
    }

    @And("the file is the right type and correctly formatted")
    public void the_file_is_the_right_type_and_correctly_formatted() {
        if (source.contains("TESTXML1.xml")) {
            testName = "TESTXML1";
        } else if (source.contains("TESTXML2.xml")) {
            testName = "TESTXML2";
        } else {
            throw new cucumber.api.PendingException(source + " not a valid directory");
        }
    }

    @When("the user uploads the file")
    public void the_user_uploads_the_file() {
        itemStorage = DAOFactory.getFoodItemDAO();
        DAOFactory.resetInstances();
        try {
            UploadHandler.uploadFoodItems(source);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            throw new cucumber.api.PendingException();
        }
    }

    // Might change this description to something else
    // like data uploaded equal to test data?
    // Not 100% sure what this test is meant to do.
    @Then("the system adds the new data to the current data")
    public void the_system_adds_the_new_data_to_the_current_data() {
        // Write code here that turns the phrase above into concrete actions
        List<FoodItem> items = new ArrayList<FoodItem>(itemStorage.getAllFoodItems());
        items.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));

        switch (testName) {
            case "TESTXML1":
                FoodItem beefburg = new FoodItem("BEEFBURG", "Hamburger", UnitType.GRAM);
                beefburg.setCost(BigMoney.parse("NZD 21.00"));
                beefburg.setCaloriesPerUnit(295);
                FoodItem cheeseburg = new FoodItem("CHEESEBURG", "Cheeseburger", UnitType.GRAM);
                cheeseburg.setCost(BigMoney.parse("NZD 22.00"));
                cheeseburg.setCaloriesPerUnit(303);
                FoodItem tofuburg1 = new FoodItem("TOFUBURG", "Tofu Burger", UnitType.GRAM);
                tofuburg1.setCost(BigMoney.parse("NZD 50.00"));
                tofuburg1.setCaloriesPerUnit(168.4);
                tofuburg1.setIsVegetarian(true);
                FoodItem applesoda = new FoodItem("APPLESODA", "Sparkling Apple", UnitType.ML);
                applesoda.setCost(BigMoney.parse("NZD 96.00"));
                applesoda.setCaloriesPerUnit(87);
                applesoda.setIsVegan(true);
                applesoda.setIsGlutenFree(true);
                expectedItems = Arrays.asList(
                        beefburg, cheeseburg, tofuburg1, applesoda
                );
                expectedItems.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));
                break;
            case "TESTXML2":
                FoodItem banana = new FoodItem("BANANA", "Banana", UnitType.COUNT);
                banana.setCost(BigMoney.parse("NZD 1.00"));
                banana.setCaloriesPerUnit(100);
                FoodItem applepie = new FoodItem("APPLEPIE", "Apple Pie", UnitType.COUNT);
                applepie.setCost(BigMoney.parse("NZD 21.00"));
                applepie.setCaloriesPerUnit(200);
                FoodItem tofuburg2 = new FoodItem("TOFUBURG", "Tofu Burger", UnitType.GRAM);
                tofuburg2.setCost(BigMoney.parse("NZD 50.00"));
                tofuburg2.setCaloriesPerUnit(268.4);
                tofuburg2.setIsVegetarian(true);
                FoodItem water = new FoodItem("WATER", "Glass of water", UnitType.ML);
                water.setCost(BigMoney.parse("NZD 200.00"));
                water.setCaloriesPerUnit(0);
                water.setIsVegan(true);
                water.setIsGlutenFree(true);
                expectedItems = Arrays.asList(
                        banana, applepie, tofuburg2, water
                );
                expectedItems.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));
                break;
            default:
                throw new cucumber.api.PendingException(testName + " is an invalid file");
        }

        Assertions.assertEquals(items.size(), expectedItems.size());
        Assertions.assertEquals(items, expectedItems);

    }

    @Ignore
    @And("prompts the user to decide what to keep for each duplicate entry")
    public void prompts_the_user_to_decide_what_to_keep_for_each_duplicate_entry() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }
}
