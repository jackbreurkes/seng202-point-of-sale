package seng202.team1.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import seng202.team1.data.OrderDAO;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.util.*;

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

    @When("the employee removes an ingredient from the food item")
    public void the_employee_removes_an_ingredient_from_the_food_item() {

        FoodItem foodItem2 = new FoodItem("GLASS", "Cup", UnitType.COUNT);
        RecipeBuilder builder = new RecipeBuilder();
        builder.addIngredient(foodItem2, 1);

        foodItem.setRecipe(builder.generateRecipe(1));
        foodItem.getRecipe().removeIngredient(foodItem2.getCode());

        // throw new cucumber.api.PendingException();
    }

    @Then("the item in the order will no longer contain that ingredient")
    public void the_item_in_the_order_will_no_longer_contain_that_ingredient() {
        Assertions.assertEquals(order.getOrderContents(), testList);
    }








    @Given("an order has at least one food item")
    public void an_order_has_at_least_one_food_item() {
        order = new Order();
        foodItem = new FoodItem("WATER", "Glass of water", UnitType.COUNT);
        //adds an item
        order.addItem(foodItem);
        //initializes a list to check against the order
        testList.add(foodItem);
        //throw new cucumber.api.PendingException();
    }

    @When("the user selects the ingredients to add to the recipe")
    public void the_user_selects_the_ingredients_to_add_to_the_recipe() {
        FoodItem foodItem2 = new FoodItem("SALT", "Salt", UnitType.GRAM);
        FoodItem foodItem3 = new FoodItem("TAPWATER", "Tap Water", UnitType.ML);

        RecipeBuilder builder = new RecipeBuilder();
        builder.addIngredient(foodItem3, 1);
        builder.addAddableIngredient(foodItem2, 1);

        foodItem.setRecipe(builder.generateRecipe(1));
        foodItem.getRecipe().addIngredient(foodItem2.getCode());

    }

    @Then("the recipe is updated to include those ingredients")
    public void the_recipe_is_updated_to_include_those_ingredients() {
        Assertions.assertEquals(order.getOrderContents(), testList);
        // throw new cucumber.api.PendingException();
    }








    @Given("an order is being created")
    public void an_order_is_being_created() {
        // Write code here that turns the phrase above into concrete actions

        //By default it is creating
        order = new Order();
        foodItem = new FoodItem("WATER", "Glass of water", UnitType.COUNT);
        //adds an item
        order.addItem(foodItem);
        // throw new cucumber.api.PendingException();

        //initializes a list to check against the order
        testList.add(foodItem);
    }

    @And("the order has not been confirmed")
    public void the_order_has_not_been_confirmed() {
        // Write code here that turns the phrase above into concrete actions
       order.submitOrder();
       assertEquals(order.getStatus(), OrderStatus.SUBMITTED);
       //  throw new cucumber.api.PendingException();
    }

    @When("the user cancels the order")
    public void the_user_cancels_the_order() {
        order.cancelOrder();
        // throw new cucumber.api.PendingException();
    }

    @Then("the order is removed from the list of orders")
    public void the_order_is_removed_from_the_list_of_orders() {
        Assertions.assertEquals(order.getOrderContents(), testList);
        // throw new cucumber.api.PendingException();
    }







}
