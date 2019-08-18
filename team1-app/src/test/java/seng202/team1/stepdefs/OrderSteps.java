package seng202.team1.stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng202.team1.model.Menu;
import seng202.team1.model.Order;

import static org.junit.Assert.assertEquals;


public class OrderSteps {
    private Menu menu;
    private Order order;

    @Given("there is a menu with food items available")
    public void there_are_menu_items_available_to_order() {
        throw new cucumber.api.PendingException();
//        menu = new Menu();
//        menu.add(new FoodItem());
    }

    @When("the user adds food items to an order")
    public void the_user_selects_items_to_order() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the order is added to the pending orders")
    public void the_order_is_added_to_the_pending_orders() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

}
