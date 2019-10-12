package seng202.team1.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.UploadHandler;
import seng202.team1.model.FoodItem;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ImportController {

    @FXML
    private Text statusText;
    @FXML
    private TableView foodItemTable;
    @FXML
    private ComboBox dataTypeComboBox;

    TableColumn itemCode, itemName, itemCost, unitType, stockLevel, isVegetarian, isVegan, isGlutenFree, calories;
    ObservableFoodItems items;

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
        foodItemTable.getColumns().addAll(itemCode, itemName, itemCost, unitType, stockLevel, isVegetarian, isVegan, isGlutenFree, calories);
        updateTable();

        dataTypeComboBox.getItems().addAll("Suppliers", "Food Items");
        dataTypeComboBox.setValue("Food Items");
        dataTypeComboBox.setVisible(false); // hide this combobox from users for deliverable 2
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    /**
     * Update table with food item data
     */
    public void updateTable() {
        FoodItemDAO itemStorage = DAOFactory.getFoodItemDAO();
        items.buildFrom(itemStorage.getAllFoodItems());

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
                        statusText.setText("An error has occured while parsing: " + e.getMessage());
                        e.printStackTrace();
                    } catch (IOException e) {
                        statusText.setText("An IO exception occured: " + e.getMessage());
                        e.printStackTrace();
                    }
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

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToAnalysis(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "analysis.fxml", "ROSEMARY | Edit Data Screen");
    }

    public void popUpImportChanges() throws IOException
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

}
