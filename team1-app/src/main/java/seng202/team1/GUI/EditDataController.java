package seng202.team1.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.MemoryStorage;
import seng202.team1.model.FoodItem;

import java.io.IOException;

public class EditDataController {


    @FXML
    private TableView foodItemTable;

    TableColumn itemCode, itemName, unitType, stockLevel, isVegetarian, isVegan, isGlutenFree, calories;

    /**
     * runs automatically when the window is created
     */
    public void initialize() {
        itemCode = new TableColumn("Code");
        itemName = new TableColumn("Name");
        unitType = new TableColumn("Unit");
        stockLevel = new TableColumn("Stock");
        isVegetarian = new TableColumn("Vegetarian");
        isVegan = new TableColumn("Vegan");
        isGlutenFree = new TableColumn("Gluten Free");
        calories = new TableColumn("kcal/unit");
        foodItemTable.getColumns().addAll(itemCode, itemName, unitType, stockLevel, isVegetarian, isVegan, isGlutenFree, calories);
        updateTable();
    }

    /**
     * Updates table with foodItem data
     */
    public void updateTable() {
        FoodItemDAO itemStorage = MemoryStorage.getInstance();

        ObservableList<FoodItem> items = FXCollections.observableArrayList(
                itemStorage.getAllFoodItems()
        );

        itemCode.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("code"));
        itemName.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
        unitType.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("unit"));
        //stockLevel.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("unit"));
        isVegetarian.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("isVegetarian"));
        isVegan.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("isVegan"));
        isGlutenFree.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("isGlutenFree"));
        calories.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("caloriesPerUnit"));

        foodItemTable.setItems(items);
    }



    /**
     * Changes scene back to import with table
     */
    private void changeSceneToImport(javafx.event.ActionEvent event) throws IOException {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "import.fxml", "ROSEMARY | Import Screen");
    }

    /**
     * goes back to the import table screen called by the back button
     */
    public void goBack(javafx.event.ActionEvent event) throws IOException {
        changeSceneToImport(event);
    }

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToOrder(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "order.fxml", "ROSEMARY | Order Screen");
    }

}
