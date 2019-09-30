package seng202.team1.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.model.FoodItem;

import java.io.IOException;

public class FoodItemWindowController {

    @FXML private Label itemName;

    FoodItemDAO foodStorage;
    FoodItem model;

    public FoodItemWindowController(FoodItem item) {

        this.model = model; // keep this above loader.load() or initialize() will throw a NullPointerException

        FXMLLoader loader = new FXMLLoader(getClass().getResource("editDataWindow.fxml"));
        //loader.setRoot(this);
        //loader.setController(this);

        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(loader.getController());
        Stage stage = new Stage();
        stage.setTitle(model.getCode());
        stage.setScene(scene);
        stage.show();

        itemName.setText(item.getName());
    }

    public void initialize() {
        itemName.setText(model.getName());

    }

}
