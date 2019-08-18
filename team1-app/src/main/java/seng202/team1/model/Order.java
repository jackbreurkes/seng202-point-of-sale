package seng202.team1.model;

import seng202.team1.model.FoodItem;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Order {
    private List<FoodItem> foodItems;
    private List<FoodItem> modifiedFoodItems;
    private String orderNote;

    public void addItem(FoodItem item) {
        throw new NotImplementedException();
    }

    public void removeItem(FoodItem item) {
        throw new NotImplementedException();
    }

    public void cancelOrder() {
        throw new NotImplementedException();
    }

    public void refundOrder() {
        throw new NotImplementedException();
    }
}
