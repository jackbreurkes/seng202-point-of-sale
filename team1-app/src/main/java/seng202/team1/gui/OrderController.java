package seng202.team1.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import seng202.team1.data.DAOFactory;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Menu;
import seng202.team1.model.Order;

import java.io.IOException;

/**
 * controller class for the order screen.
 */
public class OrderController {

    @FXML private Label menuName;
    @FXML private TilePane menuItems;
    @FXML private VBox ordersInfo;
    @FXML private Button cancelOrderButton;

    private Menu activeMenu;
    private OrderProgressDisplay orderProgressDisplay;

    /**
     * called automatically when loading FXML.
     */
    public void initialize() {
        Menu testMenu = new Menu();
        testMenu.setMenuName("Menu");
        for (FoodItem item : DAOFactory.getFoodItemDAO().getAllFoodItems()) {
            testMenu.addItem(item);
        }
        activeMenu = testMenu;

        orderProgressDisplay = new OrderProgressDisplay(this);
        ordersInfo.getChildren().add(orderProgressDisplay);

        menuName.setText(activeMenu.getMenuName());
        populateMenuItemsDisplay(activeMenu);
    }

    /**
     * displays the items in a menu in the order-able items display.
     * @param menu the Menu to display the items of
     */
    private void populateMenuItemsDisplay(Menu menu) {
        for (FoodItem item : menu.getMenuItems()) {
            MenuItemDisplay itemDisplay = new MenuItemDisplay(item);
            menuItems.getChildren().add(itemDisplay);
        }
    }

    /**
     * allows users to create an order.
     */
    public void startCreatingOrder() {
        showOrderCreationElements();
    }

    /**
     * stops the create order process.
     */
    public void stopCreatingOrder() {
        hideOrderCreationElements();
    }

    /**
     * shows  the javaFX components related to the creation of orders
     */
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

    /**
     * hides the javaFX components related to the creation of orders
     */
    private void hideOrderCreationElements() {
        menuName.setText(activeMenu.getMenuName());
        ordersInfo.getChildren().clear();
        ordersInfo.getChildren().add(orderProgressDisplay);
        orderProgressDisplay.displaySubmittedOrders();

        for (Node node : menuItems.getChildren()) {
            MenuItemDisplay display = (MenuItemDisplay)node;
            display.unlinkFromOrder();
        }
    }


    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToFoodItemStorage(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "foodItemStorage.fxml", "ROSEMARY | Food Item Storage");
    }

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToAnalysis(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "analysis.fxml", "ROSEMARY | Order Analytics");
    }



}
