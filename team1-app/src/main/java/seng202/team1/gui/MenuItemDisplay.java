package seng202.team1.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import seng202.team1.data.DAOFactory;
import seng202.team1.model.FoodItem;

import java.io.IOException;

/**
 * displays a menu item on the order screen.
 */
public class MenuItemDisplay extends VBox {

    @FXML private Label itemName, itemCost, isVegetarian, isVegan, isGlutenFree, stockCount;
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
        itemCost.setText(model.getCost().toString());
        isVegetarian.setText("vegetarian: " + boolToNaturalLanguage(model.getIsVegetarian()));
        isVegan.setText("vegan: " + boolToNaturalLanguage(model.getIsVegan()));
        isGlutenFree.setText("gluten free: " + boolToNaturalLanguage(model.getIsGlutenFree()));
        addToOrder.setVisible(false);
    }

    /**
     * converts a boolean value to a human-friendly string ("yes" or "no")
     * @param bool the boolean to convert to a String
     * @return "yes" if bool is true, "no" otherwise
     */
    private String boolToNaturalLanguage(boolean bool) {
        if (bool) {
            return "yes";
        } else {
            return "no";
        }
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
