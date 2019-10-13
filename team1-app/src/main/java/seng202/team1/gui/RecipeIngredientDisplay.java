package seng202.team1.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seng202.team1.model.FoodItem;

import java.io.IOException;

/**
 * display to show a FoodItem in the create recipe view component in the edit data screen.
 */
public class RecipeIngredientDisplay extends VBox {

    @FXML
    private Label ingredientName;

    @FXML
    private Button addOne, removeOne;

    private RecipeView parent;
    private FoodItem model;

    public RecipeIngredientDisplay(RecipeView parent, FoodItem model) {

        this.parent = parent;
        this.model = model; // keep this above loader.load() or initialize() will throw a NullPointerException

        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipeIngredientDisplay.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void initialize() {
        ingredientName.setText(model.getName() + " (x" + parent.getModel().getIngredientAmounts().get(model.getCode()) + ")");

        RecipeIngredientDisplay display = this;
        removeOne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                parent.removeOneIngredient(model.getCode());
            }
        });

        addOne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                parent.addOneOfIngredient(model.getCode());
            }
        });
    }

}
