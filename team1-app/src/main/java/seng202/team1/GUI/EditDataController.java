package seng202.team1.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.joda.money.BigMoney;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.DAOFactory;
import seng202.team1.model.FoodItem;

import java.io.IOException;

public class EditDataController {


    @FXML
    private TableView<FoodItemDisplay> foodItemTable;

    @FXML
    private Label codeLabel;

    @FXML
    private TextField newName;

    @FXML
    private TextField newCost;

    @FXML
    private TextField newCalories;

    @FXML
    private CheckBox vegetarianCheckBox;

    @FXML
    private CheckBox veganCheckBox;

    @FXML
    private CheckBox glutenFreeCheckBox;

    @FXML
    private Text statusText;

    TableColumn itemCode, itemName, itemCost, unitType, stockLevel, isVegetarian, isVegan, isGlutenFree, calories;
    ObservableFoodItems items;

    private FoodItemDisplay selectedItem;
    private FoodItemDAO foodStorage = DAOFactory.getFoodItemDAO();

    /**
     * runs automatically when the window is created
     */
    public void initialize() {
        items = new ObservableFoodItems();
        itemCode = new TableColumn("Code");
        itemName = new TableColumn("Name");
        itemCost = new TableColumn("Cost");
        unitType = new TableColumn("Unit");
        stockLevel = new TableColumn("Stock");
        isVegetarian = new TableColumn("Vegetarian");
        isVegan = new TableColumn("Vegan");
        isGlutenFree = new TableColumn("Gluten Free");
        calories = new TableColumn("kcal/unit");

        selectedItem = null;

        foodItemTable.getColumns().addAll(itemCode, itemName, itemCost, unitType, stockLevel, isVegetarian, isVegan, isGlutenFree, calories);
        updateTable();
    }

    /**
     * Updates table with foodItem data
     */
    public void updateTable() {
        items.buildFrom(foodStorage.getAllFoodItems());

        itemCode.setCellValueFactory(new PropertyValueFactory<FoodItemDisplay, String>("code"));
        itemName.setCellValueFactory(new PropertyValueFactory<FoodItemDisplay, String>("name"));
        itemCost.setCellValueFactory(new PropertyValueFactory<FoodItemDisplay, String>("cost"));
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
        FoodItemDisplay selectedItemD = foodItemTable.getSelectionModel().getSelectedItem();
        foodStorage.removeFoodItem(selectedItemD.getCode());
        selectedItem = null;
        updateTable();
    }

    /**
     * loads the values of selected item into the edit display ready to be edited
     */
    public void editSelectedItem() {
        selectedItem = foodItemTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            statusText.setText("No item selected.");
            return;
        }
        statusText.setText("editing " + selectedItem.getCode());

        codeLabel.setText(selectedItem.getCode());
        newName.setText(selectedItem.getName());
        newCost.setText(selectedItem.getCost().toString());
        newCalories.setText(Double.toString(selectedItem.getCaloriesPerUnit()));
        vegetarianCheckBox.setSelected(selectedItem.getIsVegetarian());
        veganCheckBox.setSelected(selectedItem.getIsVegan());
        glutenFreeCheckBox.setSelected(selectedItem.getIsGlutenFree());
    }

    /**
     * confirms and saves changes made to item in the GUI
     */
    public void confirmChanges() {
        if (selectedItem == null) {
            statusText.setText("No item selected.");
        } else {
            FoodItem editedItem = foodStorage.getFoodItemByCode(selectedItem.getCode());
            try {
                editedItem.setName(newName.getText());
                editedItem.setCost(BigMoney.parse(newCost.getText()));
                editedItem.setCaloriesPerUnit(Double.valueOf(newCalories.getText()));
                editedItem.setIsVegetarian(vegetarianCheckBox.isSelected());
                // for some reason the vegan checkbox doesnt work gets null pointer exception
                // even though code is the same as the others
                 editedItem.setIsVegan(veganCheckBox.isSelected());
                editedItem.setIsGlutenFree(glutenFreeCheckBox.isSelected());
            } catch (Exception e) {
                statusText.setText("error setting values: " + e.getMessage());
                return;
            }
            foodStorage.updateFoodItem(editedItem);
            updateTable();
            statusText.setText(editedItem.getCode() + " updated successfully.");
        }
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
