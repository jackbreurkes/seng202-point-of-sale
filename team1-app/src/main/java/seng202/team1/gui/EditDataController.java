package seng202.team1.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.joda.money.BigMoney;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.DAOFactory;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Recipe;

import java.io.IOException;
import java.util.Optional;

/**
 * controller for the edit data screen.
 */
public class EditDataController {


    @FXML
    private TableView<FoodItemDisplay> foodItemTable;

    @FXML
    private Label codeLabel;

    @FXML
    private TextField newName, newCost, newCalories, newStockCount;

    @FXML
    private CheckBox vegetarianCheckBox, veganCheckBox, glutenFreeCheckBox;

    @FXML
    private Text statusText;

    @FXML
    private Button deleteSelected;

    @FXML private VBox rightPanelBox;

    @FXML private RecipeView recipeView;

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

        foodItemTable.setRowFactory(tv -> {
            TableRow<FoodItemDisplay> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    editSelectedItem();
                }
            });
            return row;
        });

        foodItemTable.getColumns().addAll(itemCode, itemName, itemCost, unitType, stockLevel, isVegetarian, isVegan, isGlutenFree, calories);
        updateTable();

        recipeView.setParent(this);
    }

    /**
     * returns the currently selected FoodItemDisplay's model FoodItem.
     * @return the FoodItem that the currently selected FoodItemDisplay is modelling
     */
    protected FoodItem getSelectionAsFoodItem() {
        if (foodItemTable.getSelectionModel().getSelectedItem() == null) {
            return null;
        }
        return foodItemTable.getSelectionModel().getSelectedItem().getModelFoodItem();
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
     * Deletes the selected item
     * If null pointer to the selectedItem then it throws an information box
     * Else a confirmation alert to confirm changes.
     */
    public void deleteSelectedItem() {

        FoodItemDisplay selectedItemD = foodItemTable.getSelectionModel().getSelectedItem();

        if (selectedItemD == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Error: No data selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Click OK to confirm data deletion");
            Optional<ButtonType> action = alert.showAndWait();

            if(action.get() == ButtonType.OK) {
                //FoodItemDisplay selectedItemD = foodItemTable.getSelectionModel().getSelectedItem();
                foodStorage.removeFoodItem(selectedItemD.getCode());
                selectedItem = null;
                updateTable();
            }

        }

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

        recipeView.updateModel(selectedItem.getRecipe());

        statusText.setText("");

        codeLabel.setText("EDITING: " + selectedItem.getCode());
        newName.setText(selectedItem.getName());
        newCost.setText(selectedItem.getCost().toString());
        newCalories.setText(Double.toString(selectedItem.getCaloriesPerUnit()));
        newStockCount.setText(Integer.toString(foodStorage.getFoodItemStock(selectedItem.getCode())));
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
                System.out.println(newStockCount.getText());
                foodStorage.setFoodItemStock(selectedItem.getCode(), Integer.parseInt(newStockCount.getText()));
                 editedItem.setIsVegan(veganCheckBox.isSelected());
                editedItem.setIsGlutenFree(glutenFreeCheckBox.isSelected());
            } catch (Exception e) {
                e.printStackTrace();
                statusText.setText("error setting values: " + e.getMessage());
                return;
            }
            foodStorage.updateFoodItem(editedItem);
            updateTable();
            statusText.setText(editedItem.getCode() + " updated successfully.");
        }
    }

    public void updateSelectedItemRecipe(Recipe recipe) {
        if (selectedItem == null) {
            statusText.setText("No item selected.");
            return;
        }

        try {
            selectedItem.getModelFoodItem().setRecipe(recipe);
        } catch (IllegalArgumentException e) {
            statusText.setText("an item's recipe cannot contain itself.");
        }
        foodStorage.updateFoodItem(selectedItem.getModelFoodItem());
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

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToAnalysis(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "analysis.fxml", "ROSEMARY | Edit Data Screen");
    }

}
