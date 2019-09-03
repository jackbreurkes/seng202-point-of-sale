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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.MemoryStorage;
import seng202.team1.data.UploadHandler;
import seng202.team1.model.FoodItem;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class ImportController {

    @FXML
    private static Text statusText;
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

    public static void setStatusText(String text) {
        statusText.setText(text);
    }

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
     * Changes scene to data type select
     */
    public void changeSceneToSelectType(javafx.event.ActionEvent event) throws IOException {
        Parent typeSelectParent = FXMLLoader.load(getClass().getResource("typeSelect.fxml"));
        Scene typeSelectScene = new Scene(typeSelectParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(typeSelectScene);
        window.show();
    }

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToEditData(javafx.event.ActionEvent event) throws IOException
    {
        Parent editDataParent = FXMLLoader.load(getClass().getResource("editData.fxml"));
        Scene editDataScene = new Scene(editDataParent);

        //This lines gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(editDataScene);
        window.show();
    }



}
