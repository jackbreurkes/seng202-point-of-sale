package seng202.team1.gui;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class OrderDisplay extends VBox {

    @FXML
    private Label orderId;

    @FXML
    private Button orderActionButton;

    @FXML
    private Button secondaryActionButton;

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

    /**
     * runs automatically when loading FXML.
     */
    public void initialize() {
        Calendar cal = Calendar.getInstance();
        Date lastUpdatedDate = model.getLastUpdated();
        boolean fromStorage = true;
        if (lastUpdatedDate == null) {
            fromStorage = false;
            lastUpdatedDate = new Date();
        }
        cal.setTime(lastUpdatedDate);
        if (fromStorage) {
            cal.add(Calendar.HOUR_OF_DAY, 13);
        }

        DateFormat format = new SimpleDateFormat("E h:mm:ss a");
        orderId.setText(format.format(cal.getTime()) + " - " + model.getCost().toString());
        secondaryActionButton.setVisible(false);
        OrderDisplay display = this;

        for (FoodItem orderItem : model.getOrderContents()) {
            orderItems.getChildren().add(new Label(orderItem.getName()));
        }

        OrderDisplay thisDisplay = this;
        switch (model.getStatus()) {
            case SUBMITTED:
                orderActionButton.setText("Complete");
                orderActionButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        orderProgressDisplay.completeOrder(model, thisDisplay);
                    }
                });

                secondaryActionButton.setVisible(true);
                secondaryActionButton.setText("x");
                secondaryActionButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        orderProgressDisplay.cancelOrder(model, thisDisplay);
                    }
                });
                break;
            case COMPLETED:
                orderActionButton.setText("Refund");
                orderActionButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        orderProgressDisplay.refundOrder(model, thisDisplay);
                    }
                });
                break;
            default:
                orderActionButton.setVisible(false);
        }

    }

}
