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

/**
 * displays a menu item on the order screen.
 */
public class MenuItemDisplay extends VBox {

    @FXML private Image itemImage;
    @FXML private Label itemName;
    @FXML private Button addToOrder;

    private FoodItem model;

    /**
     * default constructor.
     * @param model the FoodItem the MenuItemDisplay should model
     */
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

    /**
     * called automatically when loading FXML.
     */
    public void initialize() {
        itemName.setText(model.getName());
        addToOrder.setVisible(false);
    }

    /**
     * links the MenuItemDisplay to a CreateOrderDisplay to allow adding of items to orders.
     * @param createDisplay the CreateOrderDisplay to link this item's add to order button to
     */
    public void linkToCreateOrderDisplay(CreateOrderDisplay createDisplay) {
        addToOrder.setVisible(true);
        addToOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                createDisplay.addItemToOrder(model.getCode());
            }
        });
    }

    /**
     * takes the MenuItemDisplay out of add mode.
     */
    public void unlinkFromOrder() {
        addToOrder.setOnAction(null);
        addToOrder.setVisible(false);
    }

}
