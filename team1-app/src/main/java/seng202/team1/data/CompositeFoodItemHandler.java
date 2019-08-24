package seng202.team1.data;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

/**
 * Processes FoodItem using DOM
 */
public class CompositeFoodItemHandler {

    private DocumentBuilder builder;
    private Document parsedDoc;
    private String path;
    private File xmlFile;

    public CompositeFoodItemHandler(String filePath, boolean validate) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(validate);
        xmlFile = new File("resources/data/CompositeFoodItem.xml");
        path = filePath;

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException parseE) {
            parseE.printStackTrace();
            System.exit(1);
        }
    }

    public void parseInput() {
        try {
            parsedDoc = builder.parse(xmlFile);
        } catch (SAXException se) {
            System.err.println(se.getMessage());
            System.exit(1);
        } catch (IOException ie) {
            System.err.println(ie);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception {

        File xmlFile = new File("resources/data/CompositeFoodItem.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();

        NodeList nodeList = document.getElementsByTagName("fooditem");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            String code = node.getElementsByTagName("code").item(0).getTextContent();
            String unit = node.getAttribute("unit");
            String isVegetarian = node.getAttribute("isVeg");
            String isVegan = node.getAttribute("isVegan");
            String isGlutenFree = node.getAttribute("isgf");
            String name = node.getElementsByTagName("name").item(0).getTextContent();
            double caloriesPerUnit = Double.parseDouble(node.getElementsByTagName("calorieCount").item(0).getTextContent());
            System.out.println(caloriesPerUnit);

        }

        System.out.println(root.getElementsByTagName("name").item(0).getTextContent());
    }
}
