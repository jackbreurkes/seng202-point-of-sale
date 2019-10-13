package seng202.team1.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.joda.money.BigMoney;
import org.xml.sax.SAXException;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.UploadHandler;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Recipe;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * controller for the edit data screen.
 */
public class FoodItemStorageController {


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
    private Button deleteSelected, confirmChangesButton, importFoodItemsButton;

    @FXML private VBox editItemVBox, rightPanelBox;

    @FXML private RecipeView recipeView;

    TableColumn itemCode, itemName, itemCost, unitType, stockLevel, isVegetarian, isVegan, isGlutenFree, calories;
    ObservableFoodItems items;

    private FoodItemDisplay selectedItem;
    private FoodItemDAO foodStorage = DAOFactory.getFoodItemDAO();
    private boolean overwrite = false;

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

        importFoodItemsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    importFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        foodItemTable.getColumns().addAll(itemCode, itemName, itemCost, unitType, stockLevel, isVegetarian, isVegan, isGlutenFree, calories);
        updateTable();

        recipeView.setParent(this);

        setEditComponentsVisibility(false);
    }

    private void setEditComponentsVisibility(boolean visible) {
        editItemVBox.setVisible(visible);
        recipeView.setVisible(visible);
        if (!visible) {
            codeLabel.setText("EDITING: Unselected");
        }
    }

    public void setStatusText(String message) {
        setStatusText(message, false);
    }

    public void setStatusText(String message, boolean isError) {
        statusText.setText(message);
        if (isError) {
            statusText.setFill(Color.RED);
        } else {
            statusText.setFill(Color.BLACK);
        }
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    /**
     * Opens file chooser and then imports file if a file of the correct type is selected
     * also runs error control on file type
     */
    public void importFile() throws IOException {
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
                try {
                    // Check if duplicates exist
                    UploadHandler.parseFoodItems(selectedFile.getPath());
                    boolean duplicateFoodItem = UploadHandler.checkModifiedFoodItem();
                    if (!duplicateFoodItem) {
                        UploadHandler.uploadFoodItems(false);
                    } else {
                        popUpImportChanges();
//                            UploadHandler.uploadFoodItems(foodItems, overwrite);
                        if (!overwrite) {
                            UploadHandler.uploadFoodItems(false);
                        } else {
                            UploadHandler.uploadFoodItems(true);
                        }
                    }

                } catch (SAXException e) {
                    setStatusText("An error has occured while parsing: " + e.getMessage(), true);
                    e.printStackTrace();
                } catch (IOException e) {
                    setStatusText("An IO exception occured: " + e.getMessage(), true);
                    e.printStackTrace();
                }
                updateTable();
            } else {
                setStatusText("Incorrect file type.", true);
            }
        } else {
            setStatusText("No file selected.", true);
        }
    }


    private void popUpImportChanges() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("importChangesDisplay.fxml"));

        Parent newParent = fxmlLoader.load();

        ImportChangesController controller = fxmlLoader.getController();
        controller.setImportController(this);

        Scene scene = new Scene(newParent);
        Stage stage = new Stage();
        stage.setTitle("Import Changes");
        stage.setScene(scene);
        stage.showAndWait();
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
            alert.setContentText("are you sure you want to delete " + selectedItemD.getCode() + "? This will also remove it from any recipes it is part of.");
            Optional<ButtonType> action = alert.showAndWait();

            if(action.get() == ButtonType.OK) {
                //FoodItemDisplay selectedItemD = foodItemTable.getSelectionModel().getSelectedItem();
                foodStorage.removeFoodItem(selectedItemD.getCode());
                if (selectedItemD.equals(selectedItem)) {
                    setEditComponentsVisibility(false);
                }
                selectedItem = null;
                updateTable();
            }

        }


    }

    /**
     * loads the values of selected item into the edit display ready to be edited
     */
    public void editSelectedItem() {

        setEditComponentsVisibility(true);

        selectedItem = foodItemTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            setStatusText("No item selected.", true);
            return;
        }

        recipeView.updateModel(selectedItem.getRecipe());

        setStatusText("");

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
     * confirms and saves changes made to the selected item in the GUI
     */
    public void confirmChanges() {
        if (selectedItem == null) {
            setStatusText("No item selected.", true);
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
                editedItem.setRecipe(recipeView.getRecipe());
            } catch (Exception e) {
                //e.printStackTrace();
                setStatusText("error setting values: " + e.getMessage(), true);
                return;
            }
            foodStorage.updateFoodItem(editedItem);
            updateTable();
            setStatusText(editedItem.getCode() + " updated successfully.");
        }
    }

    /**
     * When this methods is called, it will change the scene to order screen controller view
     */
    public void changeSceneToOrder(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "order.fxml", "ROSEMARY | Order Screen");
    }

    /**
     * When this methods is called, it will change the scene to analysis controller view
     */
    public void changeSceneToAnalysis(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "analysis.fxml", "ROSEMARY | Order Analytics");
    }

}
