package seng202.team1.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team1.data.DAOFactory;
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
        launch(args);
    }
}
