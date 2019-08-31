package seng202.team1.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.MemoryStorage;
import seng202.team1.data.UploadHandler;
import seng202.team1.model.FoodItem;
import seng202.team1.util.UnitType;

import java.io.File;

public class ImportController {

    @FXML
    private Text statusText;
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
     * Opens file chooser and then imports file if a file of the correct type is selected
     */
    public void importFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String fileExtension = "";
            int i = fileName.lastIndexOf('.');
            if (i >= 0) { fileExtension = fileName.substring(i+1); }
            if (fileExtension.equals("xml")) {
                statusText.setText("File import started.");
                // call to import method from xml import class

                // to upload filename if it's food item
                UploadHandler.uploadFoodItems(fileName);



                //
            } else {
                statusText.setText("Incorrect file type.");
            }
        } else {
            statusText.setText("No file selected.");
        }
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

}
