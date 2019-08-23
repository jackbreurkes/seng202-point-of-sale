package seng202.team1.data;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class DOMFoodItemHandler {
    public static void main(String[] args) throws Exception {

        File xmlFile = new File("resources/data/FoodItem.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();

        NodeList nodeList = document.getElementsByTagName("fooditem");

        System.out.println(nodeList);
    }
}
