package seng202.team1.model;

import seng202.team1.util.UnitType;

import java.util.*;

/**
 * 
 */
public class Menu {

    private String name;
    private List<FoodItem> items = new ArrayList<FoodItem>();

    /**
     * Default constructor
     */
    public Menu() {
    }


    /**
     * sets name of menu
     * @param name name of menu to be set
     */
    public void setMenuName(String name) {
        this.name = name;
    }


    /**
     * obtains name of menu
     * @return name of menu
     */
    public String getMenuName() {
        return name;
    }


    /**
     * obtains a list of food items in the menu
     * @return a list of FoodItems
     */
    public List<FoodItem> getMenuItems() {
        return items;
    }


    /**
     * obtains length of a menu
     * @return an int that indicates menu length
     */
    public int getMenuLength() {
        return items.size();
    }


    /**
     * adds a FoodItem to the menu
     * @param item FoodItem to be added
     */
    public void addItem(FoodItem item) {
        items.add(item);
    }


    /**
     * removes a FoodItem from the menu
     * @param item FoodItem to be removed
     */
    public void removeItem(FoodItem item) {
        items.remove(item);
    }

    /**
     *  erases the content of a menu
     */
    public void removeMenu() {
        items.clear();
    }
}