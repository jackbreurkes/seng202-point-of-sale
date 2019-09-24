package seng202.team1.data;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import seng202.team1.model.FoodItem;
import seng202.team1.model.Menu;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Processes an XML file containing suppliers using DOM.
 */
public class MenuHandler {

    private DocumentBuilder builder;
    private Document parsedDoc;
    private String source;
    private Map<String, Menu> menus;

    private String name;
    private String code;
    private FoodItem item;
    private Map<String, FoodItem> items;

    /**
     * Constructor for the MenuHandler class.
     *
     * @param filePath
     * @param validating
     */
    public MenuHandler(String filePath, boolean validating, Map<String, FoodItem> fooditems) {
        source = filePath;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(validating);
        items = fooditems;

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Uses DocumentBuilder builder to parse the input XML file
     * and generates a tree for processing.
     */
    public void parseInput() {
        try {
            parsedDoc = builder.parse(source);
        } catch (SAXException se) {
            System.err.println(se.getMessage());
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            System.exit(1);
        }
    }

    /**
     * Obtains parsed document Document.
     * @return parsedDoc
     */
    public Document parsedDoc() {
        return parsedDoc;
    }

    /**
     * Selects each "menu" element and constructs a Supplier object
     * by assigning its values from the "menu" element.
     */
    public Map<String, Menu> getMenu() {
        menus = new HashMap<String, Menu>();
        NodeList nodeList = parsedDoc.getElementsByTagName("fooditem");
        int numNodes = nodeList.getLength();

        Node aNode;
        NodeList kids;
        NamedNodeMap attributes;
        Element node;

        node = (Element) nodeList.item(0);
        name = node.getElementsByTagName("name").item(0).getTextContent();
        Menu menu = new Menu();
        menu.setMenuName(name);

        for (int i = 0; i < numNodes; i++) {
            reset();
            node = (Element) nodeList.item(i);

            code = node.getElementsByTagName("code").item(0).getTextContent();
            item = items.get(code);
            menu.addItem(item);

        }

        return menus;
    }

    /**
     * Resets the Menu fields before constructing next Menu.
     */
    private void reset() {
        name = "";
    }
}
