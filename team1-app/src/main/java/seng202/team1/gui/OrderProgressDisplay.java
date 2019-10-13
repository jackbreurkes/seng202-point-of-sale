package seng202.team1.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import seng202.team1.data.DAOFactory;
import seng202.team1.data.FoodItemDAO;
import seng202.team1.data.OrderDAO;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Order;
import seng202.team1.util.OrderStatus;
import seng202.team1.util.UseThreading;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrderProgressDisplay extends VBox {

    @FXML
    private Button createOrder;

    @FXML
    private VBox pendingOrdersVBox, completedOrdersVBox;

    private OrderController orderController;
    private OrderDAO orderStorage = DAOFactory.getOrderDAO();
    private Set<Order> completedOrders = new HashSet<>();


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

        displaySubmittedOrders();
    }

    public void initialize() {
        createOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                orderController.startCreatingOrder();
            }
        });
    }

    public void displayOrder(Order order) {
        OrderDisplay display = new OrderDisplay(this, order);
        if (order.getStatus() == OrderStatus.SUBMITTED) {
            pendingOrdersVBox.getChildren().add(0, display);
        } else if (order.getStatus() == OrderStatus.COMPLETED) {
            completedOrdersVBox.getChildren().add(0, display);
        }
    }

    public void displaySubmittedOrders() {
        pendingOrdersVBox.getChildren().clear();
        for (Order order : orderStorage.getAllSubmittedOrders()) {
            displayOrder(order);
        }
    }

    public void completeOrder(Order order, OrderDisplay display) {
        order.completeOrder();

        pendingOrdersVBox.getChildren().remove(display);
        displayOrder(order);
        completedOrders.add(order);
        updateCompletedOrders();

        Runnable updateStorage = new Runnable() {
            @Override
            public void run() {
                orderStorage.updateOrder(order);
            }
        };
        if (UseThreading.using) {
            Thread t = new Thread(updateStorage);
            t.start();
        } else {
            updateStorage.run();
        }

    }

    public void cancelOrder(Order order, OrderDisplay display) {
        order.cancelOrder();
        //displaySubmittedOrders();
        pendingOrdersVBox.getChildren().remove(display);
        displayOrder(order);

        Runnable updateStorage = new Runnable() {
            @Override
            public void run() {
                orderStorage.updateOrder(order);
                FoodItemDAO foodStorage = DAOFactory.getFoodItemDAO();
                for (FoodItem orderedItem : order.getOrderContents()) {
                    foodStorage.setFoodItemStock(orderedItem.getCode(), foodStorage.getFoodItemStock(orderedItem.getCode()) + 1); // put back the items we created because the customer does not want them
                }
            }
        };

        if (UseThreading.using) {
            Thread t = new Thread(updateStorage);
            t.start();
        } else {
            updateStorage.run();
        }

    }

    public void refundOrder(Order order, OrderDisplay display) {
//        if (!completedOrders.contains(order)) {
//            throw new IllegalArgumentException("only completed orders can be refunded");
//        }
//        completedOrders.remove(order);
        order.refundOrder();
        orderStorage.updateOrder(order);
        completedOrdersVBox.getChildren().remove(display);
        displayOrder(order);
//        updateCompletedOrders();
    }

    private void updateCompletedOrders() {
        completedOrdersVBox.getChildren().clear();
        for (Order displayOrder : completedOrders) {
            completedOrdersVBox.getChildren().add(new OrderDisplay(this, displayOrder));
        }
    }

}
