package seng202.team1.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class OrderProgressDisplay extends VBox {

    @FXML
    private Button createOrder;

    private OrderController orderController;


    public OrderProgressDisplay(OrderController orderController) {

        this.orderController = orderController;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("orderProgress.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void initialize() {
        createOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                orderController.startCreatingOrder();
            }
        });
    }

}
