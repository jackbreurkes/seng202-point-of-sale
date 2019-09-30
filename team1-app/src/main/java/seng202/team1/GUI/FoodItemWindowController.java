package seng202.team1.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import java.io.IOException;

public class FoodItemWindowController {

    @FXML private Label itemName;
    @FXML private Label codeLabel;
    @FXML private TextField newName, newCost, newCalories;
    @FXML private CheckBox vegetarianCheckBox, veganCheckBox, glutenFreeCheckBox;

    FoodItemDAO foodStorage;
    FoodItem model;

    public FoodItemWindowController(FoodItem model) {

        this.model = model; // keep this above loader.load() or initialize() will throw a NullPointerException

        FXMLLoader loader = new FXMLLoader(getClass().getResource("editDataWindow.fxml"));
        loader.setController(this);

        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setTitle(model.getCode());
        stage.setScene(scene);
        stage.show();
    }

    private void updateModelFromStorage() {
        model = foodStorage.getFoodItemByCode(model.getCode());
        if (model == null) {
            throw new RuntimeException("model could not be found");
        }
    }

    public void initialize() {
        itemName.setText(model.getName());
        codeLabel.setText(model.getCode());
    }

}
