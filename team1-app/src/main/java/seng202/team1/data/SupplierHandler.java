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

// Hello from Euan
// To everybody:
// Just a heads up:
// The starter kit's DTD dictates that Email and URL are optional.
// But since that is not OUR DTD then the code below has not
// made choices regarding assigning 'NULL' values to email and url.
// Chur.

/**
 * Processes an XML file containing suppliers using DOM.
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
     * @param pathName
     * @param validating
     */
    public SupplierHandler(String pathName, boolean validating) {
        source = pathName;

//        dest = System.out;
//        this.dest = new PrintStream(dest);

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
     *
     * @return Map<String, Supplier>
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
                    // error since phoneType is REQUIRED
                    // ask about phoneType
            }

//            //FOR TESTING
//            System.out.println("ID is: " + id);
//            System.out.println("Name is: " + name);
//            System.out.println("Address is: " + address);
//            System.out.println("Phone: " + phone);
//            System.out.println("Email: " + email);
//            System.out.println("URL: " + url);
//            System.out.println("PhoneType: " + phoneType);
//            System.out.println();
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
