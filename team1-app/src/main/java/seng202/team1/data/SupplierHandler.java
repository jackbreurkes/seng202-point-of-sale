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

/**
 * Processes an XML file containing suppliers using DOM.
 */
public class SupplierHandler {
    private DocumentBuilder builder = null;
    private Document parsedDoc = null;
    private String source;

    private Map<String, Supplier> suppliers;
    private String id;
    private String name;
    private String address;
    private String phone;
    private PhoneType phoneType;
    private String email;
    private String url;


    /**
     * Constructor for SupplierHandler class.
     *
     * @param pathName
     * @param validating
     */
    public SupplierHandler(String pathName, boolean validating) {
        source = pathName;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(validating);
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            System.err.println(pce);
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
     * Selects each "supplier" element and constructs a Supplier object
     * by assigning its values from the "supplier" element.
     */
    public Map<String, Supplier> getSuppliers() {
        suppliers = new HashMap<String, Supplier>();
        NodeList nodeList = parsedDoc.getElementsByTagName("supplier");
        int numNodes = nodeList.getLength();

        Node aNode;
        NodeList kids;
        NamedNodeMap attributes;

        for (int i = 0; i < numNodes; i++) {
            reset();
            aNode = nodeList.item(i);
            kids = aNode.getChildNodes();
            attributes = aNode.getAttributes();
            id = kids.item(1).getTextContent();
            name = kids.item(3).getTextContent();
            address = kids.item(5).getTextContent();
            phone = kids.item(7).getTextContent();
            email = kids.item(9).getTextContent();
            url = kids.item(11).getTextContent();

            switch (attributes.getNamedItem("phoneType").getNodeValue()) {
                case "mobile":
                    phoneType = PhoneType.MOBILE;
                    break;
                case "work":
                    phoneType = PhoneType.WORK;
                    break;
                case "home":
                    phoneType = PhoneType.HOME;
                    break;
                default:
                    phoneType = PhoneType.UNKNOWN;
            }

            suppliers.put(id, new Supplier(id, name, address, phone, phoneType, email, url));
        }
        return suppliers;
    }

    /**
     * Resets the Supplier fields before constructing next Supplier.
     */
    public void reset() {
        id = "";
        name = "";
        address = "";
        phone = "";
        phoneType = PhoneType.UNKNOWN;
        email = "";
        url = "";
    }

}
