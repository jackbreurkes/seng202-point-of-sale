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
    private List<FoodItem> items;

    /**
     * Constructor for the MenuHandler class.
     *
     * @param filePath
     * @param validating
     */
    public MenuHandler(String filePath, boolean validating) {
        source = filePath;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(validating);

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
     *
     * @return Map<String, Menu>
     */
    public Map<String, Menu> getMenu() {
        menus = new HashMap<String, Menu>();
        NodeList nodeList = parsedDoc.getElementsByTagName("fooditem");
        int numNodes = nodeList.getLength();

        Node aNode;
        NodeList kids;
        NamedNodeMap attributes;
        Element node;

        for (int i = 0; i < numNodes; i++) {
            reset();
            node = (Element) nodeList.item(i);

            name = node.getElementsByTagName("name").item(0).getTextContent();
            //items = node.getElementsByTagName("items").item(0).getTextContent();

            Menu menu = new Menu();
            menu.setMenuName(name);
            //to whoever implements this
            //separate the string into some sort of array
            //and then use a loop to add them to the items array in menu
            //I realised we don't have a clear structure for menu xml and dtd so I'll get on that
            //and finish this later
            //or someone else can do it
            //shouldn't be hard
        }

        return menus;
    }

    /**
     * Resets the Menu fields before constructing next Menu.
     */
    private void reset() {
        name = "";
        //item = null
    }
}
