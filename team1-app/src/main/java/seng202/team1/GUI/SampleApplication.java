package seng202.team1.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.MemoryStorage;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Recipe;
import seng202.team1.util.UnitType;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SampleApplication extends Application {


    /**
     * Runs automatically to start primary state (import.fxml)
     * @param primaryStage is the stage it will call
     * @throws IOException exception for any IO errors
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("import.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("order.fxml"));
        primaryStage.setTitle("ROSEMARY | Import a file");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        FoodItem bun = new FoodItem("1111", "bun", UnitType.COUNT);
        FoodItem pattie = new FoodItem("2222", "pattie", UnitType.COUNT);
        FoodItem tofuPattie = new FoodItem("3333", "tofuPattie", UnitType.COUNT);
        FoodItem lettuce = new FoodItem("4444", "lettuce", UnitType.COUNT);
        FoodItem glutenFreeBun = new FoodItem("5555", "glutenFreeBun", UnitType.COUNT);

        Set<FoodItem> ingredients = new HashSet<>();
        ingredients.add(bun);
        ingredients.add(pattie);
        ingredients.add(tofuPattie);

        Set<FoodItem> addableIngredients = new HashSet<>();
        addableIngredients.add(lettuce);
        addableIngredients.add(glutenFreeBun);

        Map<String, Integer> ingredientAmount = new HashMap<>();
        ingredientAmount.put(bun.getCode(), 1);
        ingredientAmount.put(pattie.getCode(), 1);
        ingredientAmount.put(tofuPattie.getCode(), 1);
        ingredientAmount.put(lettuce.getCode(), 1);
        ingredientAmount.put(glutenFreeBun.getCode(), 1);

        Recipe recipe = new Recipe(ingredients, addableIngredients, ingredientAmount, 1);
        FoodItem burger = new FoodItem("BURGER", "Burger", UnitType.COUNT);
        burger.setRecipe(recipe);

        FoodItemDAO foodStorage = MemoryStorage.getInstance();
        foodStorage.addFoodItem(burger, 0);
        foodStorage.addFoodItem(new FoodItem("EGG", "Eggs", UnitType.COUNT), 10);
        foodStorage.addFoodItem(new FoodItem("FLOUR", "Flour", UnitType.GRAM), 1000);
        launch(args);
    }
}
