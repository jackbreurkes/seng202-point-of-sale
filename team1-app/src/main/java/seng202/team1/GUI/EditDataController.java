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
    private TableView<FoodItemDisplay> foodItemTable;

    TableColumn itemCode, itemName, unitType, stockLevel, isVegetarian, isVegan, isGlutenFree, calories;
    ObservableFoodItems items;

    private FoodItemDisplay selectedItem;

    /**
     * runs automatically when the window is created
     */
    public void initialize() {
        items = new ObservableFoodItems();
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
        items.buildFrom(itemStorage.getAllFoodItems());

        itemCode.setCellValueFactory(new PropertyValueFactory<FoodItemDisplay, String>("code"));
        itemName.setCellValueFactory(new PropertyValueFactory<FoodItemDisplay, String>("name"));
        unitType.setCellValueFactory(new PropertyValueFactory<FoodItemDisplay, String>("unit"));
        stockLevel.setCellValueFactory(new PropertyValueFactory<FoodItemDisplay, String>("stock"));
        isVegetarian.setCellValueFactory(new PropertyValueFactory<FoodItemDisplay, String>("isVegetarian"));
        isVegan.setCellValueFactory(new PropertyValueFactory<FoodItemDisplay, String>("isVegan"));
        isGlutenFree.setCellValueFactory(new PropertyValueFactory<FoodItemDisplay, String>("isGlutenFree"));
        calories.setCellValueFactory(new PropertyValueFactory<FoodItemDisplay, String>("caloriesPerUnit"));

        foodItemTable.setItems(items.getList());
    }

    /**
     * deletes the selected item
     */
    public void deleteSelectedItem() {
        selectedItem = foodItemTable.getSelectionModel().getSelectedItem();
        FoodItemDAO itemStorage = MemoryStorage.getInstance();

        itemStorage.removeFoodItem(selectedItem.getCode());
        updateTable();
    }

    /**
     * loads the values of selected item into the edit display ready to be edited
     */
    public void editSelectedItem() {
        return;
    }

    /**
     * confrims and saves changes made to item in the GUI
     */
    public void confirmChanges() {
        return;
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
