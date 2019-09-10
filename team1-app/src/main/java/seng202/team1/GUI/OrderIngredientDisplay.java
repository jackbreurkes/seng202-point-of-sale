package seng202.team1.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng202.team1.model.FoodItem;

import java.io.IOException;

public class OrderIngredientDisplay extends HBox {

    @FXML
    private Label ingredientName;

    @FXML
    private Button removeIngredient;

    private FoodItem model;

    public OrderIngredientDisplay(FoodItem model) {

        this.model = model; // keep this above loader.load() or initialize() will throw a NullPointerException

        FXMLLoader loader = new FXMLLoader(getClass().getResource("orderIngredientDisplay.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void initialize() {
        ingredientName.setText(model.getName());
    }

}
