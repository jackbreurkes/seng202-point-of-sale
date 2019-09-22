package seng202.team1.stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.xml.sax.SAXException;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.UploadHandler;
import seng202.team1.model.FoodItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataHandlingSteps {

    String source;
    List<FoodItem> expectedItems;

    @Given("the user has a data file to upload")
    public void the_user_has_a_data_file_to_upload() {
        source = "src/test/resources/xml/TESTXML1.xml";
        throw new cucumber.api.PendingException();
    }

    @Given("the file is the right type and correctly formatted")
    public void the_file_is_the_right_type_and_correctly_formatted() {
        assertEquals(source.contains(".xml"), true);
        throw new cucumber.api.PendingException();
    }

    @When("the user uploads the file")
    public void the_user_uploads_the_file() {
        try {
            UploadHandler.uploadFoodItems(source);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        throw new cucumber.api.PendingException();
    }

    @Then("the system adds the new data to the current data")
    public void the_system_adds_the_new_data_to_the_current_data() {
        // Write code here that turns the phrase above into concrete actions
        //UNSURE ABOUT THIS PART - WILL NEED TO ASK WHOEVER DID UPLOAD HANDLER

        FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
        DAOFactory.resetInstances();

        List<FoodItem> items = new ArrayList<FoodItem>(itemStorage.getAllFoodItems());
        items.sort((item1, item2) -> item1.getCode().compareTo(item2.getCode()));

        Assertions.assertEquals(items.size(), expectedItems.size());
        Assertions.assertEquals(items, expectedItems);

        throw new cucumber.api.PendingException();
    }

    @Then("prompts the user to decide what to keep for each duplicate entry")
    public void prompts_the_user_to_decide_what_to_keep_for_each_duplicate_entry() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
