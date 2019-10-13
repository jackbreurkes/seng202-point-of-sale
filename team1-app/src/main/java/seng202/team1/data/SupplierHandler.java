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
 * Deprecated as system does not use suppliers
 */
public class SupplierHandler {
    private DocumentBuilder builder = null;
    private Document parsedDoc = null;
    private String source;

//    private PrintStream dest; //Ask Tutors what this is for

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
     * @param filePath the file path to the XML file to parse
     * @param validating a boolean to validate an XML
     */
    public SupplierHandler(String filePath, boolean validating) {
        source = filePath;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(validating);

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
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
     * Selects each "supplier" XML element and constructs a Supplier object
     * by assigning its values from the "supplier" XML element.
     * Returns a dictionary, taking the Supplier's id as its key
     * and the Supplier itself as its value.
     * @return a Supplier dictionary
     */
    public Map<String, Supplier> getSuppliers() {
        suppliers = new HashMap<String, Supplier>();
        NodeList nodeList = parsedDoc.getElementsByTagName("supplier");
        int numNodes = nodeList.getLength();

        Node aNode;
        NodeList kids;
        NamedNodeMap attributes;

        // IF YOU CAN FUTURE EUAN MATCH THE BELOW TO THE FOOD ITEM STUFF
        // CAUSE I GUESS THAT READS BETTER

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
                    // error since phoneType is REQUIRED
                    // ask about phoneType
            }

            Supplier supplier = new Supplier(id, name);
            supplier.setAddress(address);
            supplier.setPhone(phone);
            supplier.setPhoneType(phoneType);
            supplier.setEmail(email);
            supplier.setUrl(url);
            suppliers.put(id, supplier);
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

//    /**
//     * Main function for testing.
//     * @param args
//     */
//    public static void main(String args[]) {
//        SupplierHandler sh = new SupplierHandler("resources/data/Supplier.xml", true);
//        sh.parseInput();
//        sh.getSuppliers().values();
//    }
}
