package seng202.team1.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Menu;
import seng202.team1.model.Order;
import seng202.team1.util.UnitType;

import java.io.IOException;

public class OrderController {

    @FXML private Label menuName;
    @FXML private TilePane menuItems;
    @FXML private VBox ordersDisplay;
    @FXML private VBox createOrderDisplay;
    @FXML private Button cancelOrderButton;

    private Menu activeMenu;
    private Order activeOrder;


    public void initialize() {
        Menu testMenu = new Menu();
        testMenu.setMenuName("Test menu");
        testMenu.addItem(new FoodItem("CODE1", "test item 1", UnitType.COUNT));
        testMenu.addItem(new FoodItem("CODE2", "test item 2", UnitType.COUNT));
        testMenu.addItem(new FoodItem("CODE3", "test item 3", UnitType.COUNT));
        activeMenu = testMenu;

        createOrderDisplay.setVisible(false); // TODO put ordersDisplay and createOrderDisplay in custom components

        menuName.setText(activeMenu.getMenuName());
        populateMenuItemsDisplay(activeMenu);
    }

    private void populateMenuItemsDisplay(Menu menu) {
        for (FoodItem item : menu.getMenuItems()) {
            FoodItemDisplay itemDisplay = new FoodItemDisplay(item);
            menuItems.getChildren().add(itemDisplay);
        }
    }

    @FXML
    private void createOrderButtonPressed() {
        activeOrder = new Order();
        showOrderCreationElements();
    }

    @FXML
    private void cancelOrderButtonPressed() {
        hideOrderCreationElements();
    }

    private void showOrderCreationElements() {
        menuName.setText(activeMenu.getMenuName() + " \u2014 creating order");
        ordersDisplay.setVisible(false);
        createOrderDisplay.setVisible(true);
        for (Node node : menuItems.getChildren()) {
            FoodItemDisplay display = (FoodItemDisplay)node;
            display.linkToOrder(activeOrder);
        }
    }

    private void hideOrderCreationElements() {
        menuName.setText(activeMenu.getMenuName());
        ordersDisplay.setVisible(true);
        createOrderDisplay.setVisible(false);
        for (Node node : menuItems.getChildren()) {
            FoodItemDisplay display = (FoodItemDisplay)node;
            display.unlinkFromOrder();
        }
    }





    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToImport(javafx.event.ActionEvent event) throws IOException
    {
        Parent editDataParent = FXMLLoader.load(getClass().getResource("import.fxml"));
        Scene editDataScene = new Scene(editDataParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //This line sets the screen title
        window.setTitle("ROSEMARY | Import Screen");

        window.setScene(editDataScene);
        window.show();
    }

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToEditData(javafx.event.ActionEvent event) throws IOException
    {
        Parent editDataParent = FXMLLoader.load(getClass().getResource("editData.fxml"));
        Scene editDataScene = new Scene(editDataParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //This line sets the screen title
        window.setTitle("ROSEMARY | Edit Data Screen");

        window.setScene(editDataScene);
        window.show();
    }



}
