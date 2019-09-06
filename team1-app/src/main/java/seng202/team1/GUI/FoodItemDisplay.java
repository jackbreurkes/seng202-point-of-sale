package seng202.team1.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng202.team1.model.FoodItem;

import java.io.IOException;

public class FoodItemDisplay extends VBox {

    @FXML private Image itemImage;
    @FXML private Label itemName;
    @FXML private Button addToOrder;

    private FoodItem model;

    public FoodItemDisplay(FoodItem model) {

        this.model = model; // keep this above loader.load() or initialize() will throw a NullPointerException

        FXMLLoader loader = new FXMLLoader(getClass().getResource("foodItemDisplay.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // note: initialize() is called as part of loader.load() I think?
    public void initialize() {
        itemName.setText(model.getName());
        addToOrder.setText("add to order");
    }

}
