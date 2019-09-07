package seng202.team1.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;

import java.io.IOException;

public class CreateOrderDisplay extends VBox {

    @FXML
    private Label orderTotal;

    @FXML
    private Button cancelOrder;

    @FXML
    private VBox orderItems;

    private OrderController orderController;
    private Order model;


    public CreateOrderDisplay(OrderController orderController, Order model) {

        this.orderController = orderController;
        this.model = model;

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
                orderController.stopCreatingOrder();
            }
        });
    }

    public void addItemToOrder(FoodItem item) {
        model.addItem(item);
        orderItems.getChildren().add(new OrderItemDisplay(this, item));
    }

    public void removeItemFromOrder(FoodItem item, OrderItemDisplay display) {
        model.removeItem(item);
        orderItems.getChildren().remove(display);
    }

}
