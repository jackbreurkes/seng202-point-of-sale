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
     * @param name String name
     */
    public void setMenuName(String name) {

    }


    /**
     * obtains name of menu
     * @return a String value
     */
    public String getMenuName() {
        return null;
    }


    /**
     * obtains a list of food items in the menu
     * @return a List<FoodItem>
     */
    public List<FoodItem> getMenuItems() {
        return null;
    }


    /**
     * obtains length of a menu
     * @return an int
     */
    public int getMenuLength() {
        return 0;
    }


    /**
     * adds a FoodItem to the menu
     * @param item FoodItem
     */
    public void addItem(FoodItem item) {

    }


    /**
     * removes a FoodItem from the menu
     * @param item FoodItem
     */
    public void removeItem(FoodItem item) {
    }

    /**
     *  erases the content of a menu
     */
    public void removeMenu() {
    }
}