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
import seng202.team1.util.UnitType;

import java.io.IOException;

public class OrderDisplay extends VBox {

    @FXML
    private Label orderId;

    @FXML
    private Button completeOrder;

    @FXML
    private VBox orderItems;

    private OrderProgressDisplay orderProgressDisplay;
    private Order model;

    public OrderDisplay(OrderProgressDisplay orderProgressDisplay, Order model) {

        this.orderProgressDisplay = orderProgressDisplay;
        this.model = model; // keep this above loader.load() or initialize() will throw a NullPointerException

        FXMLLoader loader = new FXMLLoader(getClass().getResource("orderDisplay.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void initialize() {
        orderId.setText(Integer.toString(model.getOrderID()));
        OrderDisplay display = this;

            for (FoodItem orderItem : model.getOrderContents()) {
                orderItems.getChildren().add(new Label(orderItem.getName()));
            }

        completeOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                orderProgressDisplay.completeOrder(model);
            }
        });

    }

}
