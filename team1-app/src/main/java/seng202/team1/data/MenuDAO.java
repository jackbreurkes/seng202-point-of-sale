package seng202.team1.data;

import seng202.team1.model.Menu;

import java.util.Set;

/**
 * Data access object interface specification for interacting with Menus stored within the system.
 */
public interface MenuDAO {

    /**
     * returns all the Menus stored in the system.
     * @return a List of all Menus stored in the system
     */
    Set<Menu> getAllMenus();

    /**
     * gets a single Menu from the system.
     * @param code the Menu's unique code
     * @return the desired Menu or null if not found
     */
    Menu getMenuById(String code);

    /**
     * adds a Menu to storage. the menu will be stored using its code attribute.
     * @param menu the Menu to store
     */
    void addMenu(Menu menu);

    /**
     * sets the properties of a Menu to those of a new Menu.
     * the new menu's code should be the same as the one that is being changed.
     * @param code the code of the menu to edit
     * @param alteredMenu the new Menu whose information should replace the old menu's
     */
    void editMenu(String code, Menu alteredMenu);

    /**
     * remove a Menu from storage.
     * @param code the code of the Menu to remove
     */
    void removeMenu(String code);

}
