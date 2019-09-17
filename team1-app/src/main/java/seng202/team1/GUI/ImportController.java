package seng202.team1.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
    private Text statusText; // TODO why is this static?
    @FXML
    private TableView foodItemTable;
    @FXML
    private ComboBox dataTypeComboBox;

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

        dataTypeComboBox.getItems().addAll("Suppliers", "Food Items");
    }

    /**
     * Update table with food item data
     */
    public void updateTable() {
        FoodItemDAO itemStorage = MemoryStorage.getInstance();

        ObservableList<FoodItem> items = FXCollections.observableArrayList(
                itemStorage.getAllFoodItems()
        );
        //System.out.println(items.get(0));
        System.out.println(items);
        itemCode.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("code"));
        itemName.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
        unitType.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("unit"));
        //stockLevel.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("stock"));
        isVegetarian.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("isVegetarian"));
        isVegan.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("isVegan"));
        isGlutenFree.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("isGlutenFree"));
        calories.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("caloriesPerUnit"));

        foodItemTable.setItems(items);

        System.out.println(foodItemTable);
    }

    /**
     * Opens file chooser and then imports file if a file of the correct type is selected
     * also runs error control on file type
     */
    public void importFile(javafx.event.ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String fileExtension = "";
            int i = fileName.lastIndexOf('.');
            if (i >= 0) {
                fileExtension = fileName.substring(i + 1);
            }
            if (fileExtension.equals("xml")) {
                if (dataTypeComboBox.getValue().toString().equals("Food Items")) {
                    UploadHandler.uploadFoodItems(selectedFile.getPath());
                    updateTable();
                } else if (dataTypeComboBox.getValue().toString().equals("Suppliers")) {
                    UploadHandler.uploadSuppliers(selectedFile.getPath());
                    updateTable();
                } else {
                    statusText.setText("No data type selected.");
                }
            } else {
                statusText.setText("Incorrect file type.");
            }
        } else {
            statusText.setText("No file selected.");
        }
    }

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToEditData(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "editData.fxml", "ROSEMARY | Edit Data Screen");
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
