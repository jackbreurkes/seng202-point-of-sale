package seng202.team1.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.team1.model.FoodItem;

import java.util.Iterator;
import java.util.Set;

public class ObservableFoodItems {

    private ObservableList<FoodItemDisplay> items;

    public ObservableFoodItems() {
        items = FXCollections.observableArrayList();
    }

    /**
     *If the list is not empty empties it then builds the list from a list of food items
     * @param currentItems the set of foodItems to build the observable list from
     */
    public void buildFrom(Set<FoodItem> currentItems) {
        if (!items.isEmpty()) {
            items.removeAll();
        }
        Iterator<FoodItem> itr = currentItems.iterator();
        while (itr.hasNext()) {
            items.add(new FoodItemDisplay(itr.next().getCode()));
        }
    }

    /**
     *
     * @return items observable list fo food item displays
     */
    public ObservableList<FoodItemDisplay> getList() {
        return items;
    }

}
