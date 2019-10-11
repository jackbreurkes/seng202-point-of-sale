package seng202.team1.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.OrderDAO;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Kitchen;
import seng202.team1.model.Order;
import seng202.team1.util.InvalidOrderStatusException;

import java.io.IOException;

/**
 * display shown when creating an order on the order screen.
 */
public class CreateOrderDisplay extends VBox {

    @FXML
    private Label orderTotal;

    @FXML
    private Button cancelOrder, submitOrder;

    @FXML
    private Label orderTotalCost, statusText;

    @FXML
    private VBox orderItems;

    private OrderController orderController;
    private Order model;
    private OrderDAO orderStorage;
    private Kitchen kitchen;

    /**
     * default constructor
     * @param orderController the parent OrderController that this CreateOrderDisplay is a part of
     * @param model the Order the CreateOrderDisplay is creating
     */
    public CreateOrderDisplay(OrderController orderController, Order model) {

        this.orderController = orderController;
        this.model = model;
        this.orderStorage = DAOFactory.getOrderDAO();
        this.kitchen = new Kitchen(DAOFactory.getFoodItemDAO());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("createOrderDisplay.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void initialize() {
        cancelOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                closeCreateOrderPanel();
            }
        });
        submitOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                submitOrder();
            }
        });
    }

    /**
     * add a food item with the given code to the Order.
     * @param code the code of the FoodItem to add to the Order
     */
    public void addItemToOrder(String code) {
        FoodItem item = DAOFactory.getFoodItemDAO().getFoodItemByCode(code);
        model.addItem(item);
        orderTotalCost.setText("Order Cost - " + model.getCost().toString());
        orderItems.getChildren().add(new OrderItemDisplay(this, item));
    }

    /**
     * remove the given food item from the Order.
     * @param item FoodItem from the Order to remove
     * @param display the OrderItemDisplay modelling the item being removed
     */
    public void removeItemFromOrder(FoodItem item, OrderItemDisplay display) {
        model.removeItem(item);
        orderTotalCost.setText("Order Total - " + model.getCost().toString());
        System.out.println("pls");
        orderItems.getChildren().remove(display);
    }

    /**
     * submits the order and closes the CreateOrderDisplay.
     */
    public void submitOrder() {
        try {
            model.submitOrder();
            for (FoodItem orderedItem : model.getOrderContents()) {
                kitchen.createFoodItem(orderedItem); // create the items for the customer
            }
            orderStorage.addOrder(model);
        } catch (InvalidOrderStatusException e) {
            statusText.setText("Error submitting order: " + e.getMessage());
            return;
        }
        closeCreateOrderPanel();
    }

    /**
     * closes the CreateOrderDisplay.
     */
    public void closeCreateOrderPanel() {
        orderController.stopCreatingOrder();
    }


}
