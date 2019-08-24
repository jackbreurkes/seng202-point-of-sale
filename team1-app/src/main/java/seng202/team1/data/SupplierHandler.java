package seng202.team1.data;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

/**
 * Processes supplier using DOM
 */
public class SupplierHandler {
    public static void main(String[] args) throws Exception {
        File xmlFile = new File("resources/data/Supplier.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();

        NodeList nodeList = document.getElementsByTagName("supplier");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element) nodeList.item(i);
            String id = node.getElementsByTagName("id").item(0).getTextContent();
            String name = node.getElementsByTagName("name").item(0).getTextContent();
            String address = node.getElementsByTagName("address").item(0).getTextContent();

        }
    }
}
