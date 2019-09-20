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
import javafx.stage.Stage;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.MemoryStorage;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Menu;
import seng202.team1.model.Order;
import seng202.team1.util.UnitType;

import java.io.IOException;

public class OrderController {

    @FXML private Label menuName;
    @FXML private TilePane menuItems;
    @FXML private VBox ordersInfo;
    @FXML private Button cancelOrderButton;

    private Menu activeMenu;
    private OrderProgressDisplay orderProgressDisplay;


    public void initialize() {
        Menu testMenu = new Menu();
        testMenu.setMenuName("Test menu");
        for (FoodItem item : DAOFactory.getFoodItemDAO().getAllFoodItems()) {
            testMenu.addItem(item);
        }
        activeMenu = testMenu;

        orderProgressDisplay = new OrderProgressDisplay(this);
        ordersInfo.getChildren().add(orderProgressDisplay);

        menuName.setText(activeMenu.getMenuName());
        populateMenuItemsDisplay(activeMenu);
    }

    private void populateMenuItemsDisplay(Menu menu) {
        for (FoodItem item : menu.getMenuItems()) {
            MenuItemDisplay itemDisplay = new MenuItemDisplay(item);
            menuItems.getChildren().add(itemDisplay);
        }
    }

    public void startCreatingOrder() {
        showOrderCreationElements();
    }

    public void stopCreatingOrder() {
        hideOrderCreationElements();
    }

    private void showOrderCreationElements() {
        menuName.setText(activeMenu.getMenuName() + " \u2014 creating order");
        ordersInfo.getChildren().clear();
        CreateOrderDisplay createOrderDisplay = new CreateOrderDisplay(this, new Order());
        ordersInfo.getChildren().add(createOrderDisplay);

        for (Node node : menuItems.getChildren()) {
            MenuItemDisplay display = (MenuItemDisplay)node;
            display.linkToCreateOrderDisplay(createOrderDisplay);
        }
    }

    private void hideOrderCreationElements() {
        menuName.setText(activeMenu.getMenuName());
        ordersInfo.getChildren().clear();
        ordersInfo.getChildren().add(orderProgressDisplay);

        for (Node node : menuItems.getChildren()) {
            MenuItemDisplay display = (MenuItemDisplay)node;
            display.unlinkFromOrder();
        }
    }





    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToImport(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "import.fxml", "ROSEMARY | Import Screen");
    }

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToEditData(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "editData.fxml", "ROSEMARY | Edit Data Screen");
    }



}
