package seng202.team1.data;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class FoodItemHandler {
    public static void main(String[] args) throws Exception {

        File xmlFile = new File("resources/data/FoodItem.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();

        NodeList nodeList = document.getElementsByTagName("fooditem");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            String code = node.getElementsByTagName("code").item(0).getTextContent();
            //String unit = node.getElementsByTagName("unit").item(0).getTextContent();
            
        }

        System.out.println(root.getElementsByTagName("name").item(0).getTextContent());
    }
}
