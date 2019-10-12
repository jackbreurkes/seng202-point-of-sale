package seng202.team1.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Kitchen;
import seng202.team1.model.Order;
import seng202.team1.util.RecipeBuilder;
import seng202.team1.util.UnitType;

import static org.junit.Assert.assertEquals;

public class KitchenSteps {

    private FoodItemDAO foodStorage;
    private FoodItem rootItem;
    private FoodItem rootItemIngredient;
    private FoodItem rootItemIngredientIngredient;
    private Order order;
    private Kitchen kitchen;


    @Given("there is a {string} with a recipe containing a {string} that is made of {string}")
    public void there_is_a_with_a_recipe_containing_a_that_is_made_of(String rootItemName, String ingredientName, String ingredientIngredientName) {
        rootItem = new FoodItem("PIZZA", rootItemName, UnitType.COUNT);
        rootItemIngredient = new FoodItem("PIZZABASE", ingredientName, UnitType.COUNT);
        rootItemIngredientIngredient = new FoodItem("DOUGH", ingredientIngredientName, UnitType.COUNT);

        RecipeBuilder rootItemRecipeBuilder = new RecipeBuilder();
        rootItemRecipeBuilder.addIngredient(rootItemIngredient, 1);
        rootItem.setRecipe(rootItemRecipeBuilder.generateRecipe(1));

        RecipeBuilder ingredientRecipeBuilder = new RecipeBuilder();
        ingredientRecipeBuilder.addIngredient(rootItemIngredientIngredient, 1);
        rootItemIngredient.setRecipe(ingredientRecipeBuilder.generateRecipe(1));
    }

    @And("there are no pizzas or pizza bases in storage, but there are 2 doughs in storage")
    public void there_are_no_pizzas_or_pizza_bases_in_storage_but_there_are_2_doughs_in_storage() {
        foodStorage = DAOFactory.getFoodItemDAO();
        DAOFactory.resetInstances();
        foodStorage.addFoodItem(rootItemIngredientIngredient, 2);
        foodStorage.addFoodItem(rootItemIngredient, 0);
        foodStorage.addFoodItem(rootItem, 0);
    }

    @When("the kitchen creates the pizza")
    public void the_kitchen_creates_the_pizza() {
        order = new Order();
        order.addItem(rootItem);
        kitchen = new Kitchen(foodStorage);
        kitchen.createFoodItem(rootItem);
    }

    @Then("a dough is removed from the database")
    public void a_dough_is_removed_from_the_database() {
        assertEquals(0, foodStorage.getFoodItemStock(rootItem.getCode()));
        assertEquals(0, foodStorage.getFoodItemStock(rootItemIngredient.getCode()));
        assertEquals(1, foodStorage.getFoodItemStock(rootItemIngredientIngredient.getCode()));
    }
}
