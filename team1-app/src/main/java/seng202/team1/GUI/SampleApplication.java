package seng202.team1.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.MemoryStorage;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import java.io.IOException;

public class SampleApplication extends Application {


    /**
     * Runs automatically to start primary state (import.fxml)
     * @param primaryStage is the stage it will call
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("import.fxml"));
        primaryStage.setTitle("Import a file");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        FoodItemDAO foodStorage = MemoryStorage.getInstance();
        foodStorage.addFoodItem(new FoodItem("EGG", "Eggs", UnitType.COUNT), 10);
        foodStorage.addFoodItem(new FoodItem("FLOUR", "Flour", UnitType.GRAM), 1000);
        launch(args);
    }
}
