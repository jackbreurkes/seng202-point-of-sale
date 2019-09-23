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


}
