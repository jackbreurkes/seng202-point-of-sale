package seng202.team1.data;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import seng202.team1.model.Supplier;
import seng202.team1.util.PhoneType;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

// Hello future Euan or not future Euan
// To future Euan: Can you please remember to buy boneless chicken breasts
// for the butter chicken dinner on Friday thank you.
// To everybody:
// Just a heads up:
// The starter kit's DTD dictates that Email and URL are optional.
// But since that is not OUR DTD then the code below has not
// made choices regarding assigning 'NULL' values to email and url.
// Chur.

/**
 * Processes supplier using DOM
 */
public class SupplierHandler {
    private DocumentBuilder builder = null;
    private Document parsedDoc = null;
    private File xmlFile;

    private Map<String, Supplier> suppliers;
    private String id;
    private String name;
    private String address;
    private String phone;
    private PhoneType phoneType;
    private String email;
    private String url;


    public SupplierHandler(String pathName, boolean validating) {
        File xmlFile = new File("resources/data/Supplier.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(validating);
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            System.err.println(pce);
            System.exit(1);
        }
    }


    public void parseInput() {
        try {
            Document parsedDoc = builder.parse(xmlFile);
        } catch (SAXException se) {
            System.err.println(se.getMessage());
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }
    }


    public Document parsedDoc() {
        return parsedDoc;
    }

    public Map<String, Supplier> getSuppliers() {
        suppliers = new HashMap<String, Supplier>();
        NodeList nodes = parsedDoc.getElementsByTagName("supplier");
        int numNodes = nodes.getLength();

        Node aNode;
        NodeList kids;
        NamedNodeMap atts;

        for (int i = 0; i < numNodes; i++) {
            reset();
            aNode = nodes.item(i);
            kids = aNode.getChildNodes();
            atts = aNode.getAttributes();
            id = kids.item(1).getTextContent();
            name = kids.item(3).getTextContent();
            address = kids.item(5).getTextContent();
            phone = kids.item(7).getTextContent();
            email = kids.item(9).getTextContent();
            url = kids.item(11).getTextContent();

            switch (atts.getNamedItem("phoneType").getNodeValue()) {
                case "mobile":
                    phoneType = PhoneType.MOBILE;
                    break;
                case "work":
                    phoneType =PhoneType.WORK;
                    break;
                case "home":
                    phoneType =PhoneType.HOME;
                    break;
                default:
                    // error since phoneType is REQUIRED
            }
            suppliers.put(id, new Supplier(id, name, address, phone, phoneType, email, url));
        }
        return suppliers;
    }

    public void reset() {
        id = "";
        name = "";
        address = "";
        phone = "";
        phoneType = PhoneType.UNKNOWN;
        email = "";
        url = "";
    }



    public static void main(String[] args) {








//
//
//        document.getDocumentElement().normalize();
//
//        Element root = document.getDocumentElement();
//
//        NodeList nodeList = document.getElementsByTagName("supplier");
//
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Element node = (Element) nodeList.item(i);
//            String id = node.getElementsByTagName("id").item(0).getTextContent();
//            String name = node.getElementsByTagName("name").item(0).getTextContent();
//            String address = node.getElementsByTagName("address").item(0).getTextContent();
//
//        }
    }
}
