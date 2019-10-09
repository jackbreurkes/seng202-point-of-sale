package seng202.team1.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import seng202.team1.model.FoodItem;

import java.io.IOException;

public class MenuItemDisplay extends VBox {

    @FXML private Image itemImage;
    @FXML private Label itemName;
    @FXML private Button addToOrder;

    private FoodItem model;

    public MenuItemDisplay(FoodItem model) {

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
        addToOrder.setVisible(false);
    }

    public void linkToCreateOrderDisplay(CreateOrderDisplay createDisplay) {
        addToOrder.setVisible(true);
        addToOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                createDisplay.addItemToOrder(model.getCode());
            }
        });
    }

    public void unlinkFromOrder() {
        addToOrder.setOnAction(null);
        addToOrder.setVisible(false);
    }

}
