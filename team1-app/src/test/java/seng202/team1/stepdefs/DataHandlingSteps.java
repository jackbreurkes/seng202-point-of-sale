package seng202.team1.stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

public class DataHandlingSteps {

    String source;
    @Given("the user has a data file to upload")
    public void the_user_has_a_data_file_to_upload() {
        source = "src/test/resources/xml/TESTXML1.xml";
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("the file is the right type and correctly formatted")
    public void the_file_is_the_right_type_and_correctly_formatted() {
        assertEquals(source.contains(".xml"), true);
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("the user uploads the file")
    public void the_user_uploads_the_file() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the system adds the new data to the current data")
    public void the_system_adds_the_new_data_to_the_current_data() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("prompts the user to decide what to keep for each duplicate entry")
    public void prompts_the_user_to_decide_what_to_keep_for_each_duplicate_entry() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
