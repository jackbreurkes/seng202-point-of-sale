package seng202.team1.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;

import java.awt.event.ActionListener;
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
        addToOrder.setVisible(false);
    }

    public void linkToOrder(Order order) {
        addToOrder.setVisible(true);
        addToOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                order.addItem(model);
            }
        });
    }

    public void unlinkFromOrder() {
        addToOrder.setOnAction(null);
        addToOrder.setVisible(false);
    }

}
