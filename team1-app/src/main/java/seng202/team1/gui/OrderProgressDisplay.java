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

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class OrderProgressDisplay extends VBox {

    @FXML
    private Button createOrder;

    @FXML
    private VBox pendingOrdersVBox, completedOrdersVBox;

    private OrderController orderController;
    private OrderDAO orderStorage = DAOFactory.getOrderDAO();
    private Set<Order> submittedOrders = new HashSet<>();
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

    }

    public void initialize() {
        createOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                orderController.startCreatingOrder();
            }
        });
    }

    public void displaySubmittedOrder(Order order) {
        submittedOrders.add(order);
        updateSubmittedOrders();
    }

    private void updateSubmittedOrders() {
        pendingOrdersVBox.getChildren().clear();
        for (Order displayOrder : submittedOrders) {
            pendingOrdersVBox.getChildren().add(new OrderDisplay(this, displayOrder));
        }
    }

    public void completeOrder(Order order) {
        if (!submittedOrders.contains(order)) {
            throw new IllegalArgumentException("only submitted orders can be completed");
        }
        submittedOrders.remove(order);
        order.completeOrder();
        completedOrders.add(order);
        orderStorage.updateOrder(order);

        updateSubmittedOrders();
        updateCompletedOrders();
    }

    public void cancelOrder(Order order) {
        if (!submittedOrders.contains(order)) {
            throw new IllegalArgumentException("only submitted orders can be cancelled");
        }
        submittedOrders.remove(order);
        order.cancelOrder();
        orderStorage.updateOrder(order);

        FoodItemDAO foodStorage = DAOFactory.getFoodItemDAO();
        for (FoodItem orderedItem : order.getOrderContents()) {
            foodStorage.setFoodItemStock(orderedItem.getCode(), foodStorage.getFoodItemStock(orderedItem.getCode()) + 1); // put back the items we created because the customer does not want them
        }

        updateSubmittedOrders();
    }

    public void refundOrder(Order order) {
        if (!completedOrders.contains(order)) {
            throw new IllegalArgumentException("only completed orders can be refunded");
        }
        completedOrders.remove(order);
        order.refundOrder();
        orderStorage.updateOrder(order);
        updateCompletedOrders();
    }

    private void updateCompletedOrders() {
        completedOrdersVBox.getChildren().clear();
        for (Order displayOrder : completedOrders) {
            completedOrdersVBox.getChildren().add(new OrderDisplay(this, displayOrder));
        }
    }

}
