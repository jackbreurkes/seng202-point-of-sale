package seng202.team1.model;

import java.util.*;

/**
 * 
 */
public class Menu {

    /**
     * Default constructor
     */
    public Menu() {
    }

    private String name;
    private List<FoodItem> items;


    /**
     * adds a FoodItem to the menu
     * @param item
     */
    public void addItem(FoodItem item) {
        // TODO implement here
    }

    /**
     * removes a FoodItem from the menu
     * @param item
     */
    public void removeItem(FoodItem item) {
        // TODO implement here
    }

    /**
     * obtains menu name
     * @param name
     */
    public void setMenuName(String name) {
        this.name = name;
    }

    /**
     * obtains a list of food items in the menu
     * @return
     */
    public List<FoodItem> getMenuItems() {
        return items;
    }


    /**
     *  erases the content of a menu
     */
    public void removeMenu() {
        items.removeAll(items);
    }



}