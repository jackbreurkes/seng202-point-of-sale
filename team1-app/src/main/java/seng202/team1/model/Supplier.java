package seng202.team1.model;

import org.joda.money.Money;
import seng202.team1.util.PhoneType;

import java.util.*;

/**
 * Class Supplier contains methods to set and obtain attributes associated with a supplier.
 */
public class Supplier {

    /**
     * A unique ID to identify a supplier. Attribute must be a String.
     */
    private String id;

    /**
     * Name of a supplier. Attribute must be a String.
     */
    private String name;

    /**
     * Address of a supplier. Attribute must be a String.
     */
    private String address;

    /**
     * Phone number of a supplier. Attribute must be a String.
     */
    private String phone;

    /**
     * Type of the supplier's given phone number.
     * Attribute must be of type PhoneType; either MOBILE, WORK, HOME, UNKNOWN.
     */
    private PhoneType phoneType;

    /**
     * E-mail of supplier. Attribute must be a String.
     */
    private String email;

    /**
     * URL of a supplier. Attribute must be a String.
     */
    private String url;
    private Set<FoodItem> itemsStocked;

    private Map<String, Money> itemCosts;

    /**
     * Default constructor. Assigns id, name, address, phone, phoneType, email, and url values.
     * @param id the Supplier's unique ID
     * @param name the Supplier's name
     */
    public Supplier(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Obtains supplier ID.
     * @return supplier's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets supplier ID.
     * @param id id to be set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtains name of supplier.
     * @return supplier's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of supplier.
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtains supplier address.
     * @return supplier's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets supplier address.
     * @param address address to be set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Obtains supplier phone number.
     * @return supplier's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets supplier phone number.
     * @param phone phone number to be set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Obtains supplier phone type.
     * @return supplier's PhoneType
     */
    public PhoneType getPhoneType() {
        return phoneType;
    }

    /**
     * Sets supplier phone type.
     * @param phoneType PhoneType to be set
     */
    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    /**
     * Obtains supplier email.
     * @return supplier's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets supplier email.
     * @param email supplier's email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtains supplier URL.
     * @return String supplier's URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets supplier url.
     * @param url URL to be set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}