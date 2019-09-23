package seng202.team1.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import seng202.team1.data.OrderDAO;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.InvalidOrderStatusException;
import seng202.team1.util.UnitType;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OrderSteps {
    private Order order;
    private FoodItem foodItem;
    protected OrderDAO orderStorage;

    @Given("the user has an order to register")
    public void the_user_has_an_order_to_register() {
        order = new Order();
        //throw new cucumber.api.PendingException();
    }

    @When("the user adds {string} to an order")
    public void the_user_adds_food_items_to_order(String foodName) {
        switch(foodName) {
            case "Wonton Noodles":
                foodItem = new FoodItem("WONTON", foodName, UnitType.COUNT);
                break;
            default:
                throw new cucumber.api.PendingException(foodName + " is an invalid FoodItem");
        }
    }
    
    @Then("the order is submitted")
    public void the_order_is_submitted() {
        assertThrows(InvalidOrderStatusException.class, () -> order.submitOrder());
        order.addItem(foodItem);
        order.submitOrder();
        //throw new cucumber.api.PendingException();
    }






    @Ignore
    @Given("the list of food items available")
    public void the_list_of_food_items_available() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the user views the list of food items")
    public void the_user_views_the_list_of_food_items() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the list of food items is displayed")
    public void the_list_of_food_items_is_displayed() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @And("general information about each food item is displayed")
    public void general_information_about_each_food_item_is_displayed() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }







    @Ignore
    @Given("an order with at least one food item exists")
    public void an_order_with_at_least_one_food_item_exists() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the employee removes an ingredient from the food item")
    public void the_employee_removes_an_ingredient_from_the_food_item() {
        // Write code here that turns the phrase above into concrete actions
       // throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the item in the order will no longer contain that ingredient and will be marked as modified")
    public void the_item_in_the_order_will_no_longer_contain_that_ingredient_and_will_be_marked_as_modified() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }








    @Ignore
    @Given("an incomplete order exists in the orders list")
    public void an_incomplete_order_exists_in_the_orders_list() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the employee specifies details for the note")
    public void the_employee_specifies_details_for_the_note() {
        // Write code here that turns the phrase above into concrete actions
       //  throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the note will be displayed alongside the order")
    public void the_note_will_be_displayed_alongside_the_order() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }







    @Ignore
    @Given("a food item is selected")
    public void a_food_item_is_selected() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the user selects the ingredients to add to the recipe")
    public void the_user_selects_the_ingredients_to_add_to_the_recipe() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the recipe is updated to include those ingredients")
    public void the_recipe_is_updated_to_include_those_ingredients() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }






}
