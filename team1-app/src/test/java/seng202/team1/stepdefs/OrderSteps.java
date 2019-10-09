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
import seng202.team1.model.Recipe;
import seng202.team1.util.InvalidDataCodeException;
import seng202.team1.util.InvalidOrderStatusException;
import seng202.team1.util.UnitType;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OrderSteps {
    private Order order;
    private FoodItem foodItem;
    protected OrderDAO orderStorage;
    private List<FoodItem> testList = new ArrayList<FoodItem>();

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






    @Given("the order has an item")
    public void the_order_has_an_item() {

        order = new Order();
        foodItem = new FoodItem("WONTON", "Wonton Noodles", UnitType.COUNT);
        //initializes a list to check against the order
        testList.add(foodItem);
        //adds an item
        order.addItem(foodItem);
        //Assertions.assertEquals(order.getOrderContents(), testList);
        //throw new cucumber.api.PendingException();
    }


    @When("the user deletes an item")
    public void the_user_deletes_an_item() {
        order.removeItem(foodItem);
        testList.remove(foodItem);
        //throw new cucumber.api.PendingException();
    }


    @Then("the item is no longer in the order")
    public void the_item_is_no_longer_in_the_order() {
        Assertions.assertEquals(order.getOrderContents(), testList);
        //throw new cucumber.api.PendingException();
    }







    @Ignore
    @Given("an order with at least one food item exists")
    public void an_order_with_at_least_one_food_item_exists() {
        order = new Order();
        foodItem = new FoodItem("WATER", "Cup of water", UnitType.COUNT);
        //initializes a list to check against the order
        testList.add(foodItem);
        //adds an item
        order.addItem(foodItem);
        //throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the employee removes an ingredient from the food item")
    public void the_employee_removes_an_ingredient_from_the_food_item() {

        FoodItem foodItem2 = new FoodItem("GLASS", "Cup", UnitType.COUNT);

        Set<FoodItem> ingredients = new HashSet<>();
        Set<FoodItem> addableIngredients = new HashSet<>();
        ingredients.add(foodItem2);
        Map<String, Integer> ingredientAmounts = new HashMap<String, Integer>();
        ingredientAmounts.put("GLASS", 1);

        foodItem.setRecipe(new Recipe(ingredients, addableIngredients, ingredientAmounts, 1));
        foodItem.getRecipe().removeIngredient("GLASS");

        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the item in the order will no longer contain that ingredient")
    public void the_item_in_the_order_will_no_longer_contain_that_ingredient() {
        Assertions.assertEquals(order.getOrderContents(), testList);
        //throw new cucumber.api.PendingException();
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








    @Given("an order is being created")
    public void an_order_is_being_created() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @And("the order has not been confirmed")
    public void the_order_has_not_been_confirmed() {
        // Write code here that turns the phrase above into concrete actions
       //  throw new cucumber.api.PendingException();
    }

    @When("the user cancels the order")
    public void the_user_cancels_the_order() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Then("the order is removed from the list of orders")
    public void the_order_is_removed_from_the_list_of_orders() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }







    @Ignore
    @Given("an order exists in the orders list")
    public void an_order_exists_in_the_orders_list() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @And("the order has been confirmed")
    public void the_order_has_been_confirmed() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @And("the order has not been completed")
    public void the_order_has_not_been_completed() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the system removes the order from the order list")
    public void the_system_removes_the_order_from_the_order_list() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the user is prompted to select which items can be returned to stock")
    public void the_user_is_prompted_to_select_which_items_can_be_returned_to_stock() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @And("the user is prompted to enter a refund amount")
    public void the_user_is_prompted_to_enter_a_refund_amount() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @And("the system logs the cancellation of the order")
    public void the_system_logs_the_cancellation_of_the_order() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }








    @Ignore
    @Given("an order is being cancelled")
    public void an_order_is_being_cancelled() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @When("the user selects a food item to return to stock")
    public void the_user_selects_a_food_item_to_return_to_stock() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Ignore
    @Then("the system logs the cancellation of the order2")
    public void the_system_logs_the_cancellation_of_the_order2() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }


    @Ignore
    @And("the system updates the profit based on the cancellation reason")
    public void the_system_updates_the_profit_based_on_the_cancellation_reason() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }


    @Ignore
    @Then("the system updates the ingredient stocks based on the cancellation reason")
    public void the_system_updates_the_ingredient_stocks_based_on_the_cancellation_reason() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }






}
