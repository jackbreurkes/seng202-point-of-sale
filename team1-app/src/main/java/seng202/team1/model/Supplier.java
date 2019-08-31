package seng202.team1.model;

import seng202.team1.util.PhoneType;

import java.util.*;

/**
 * Class Supplier contains methods to set and obtain attributes associated with a supplier.
 */
public class Supplier implements FoodSource {

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

    /**
     * Default constructor. Assigns id, name, address, phone, phoneType, email, and url values.
     */
    public Supplier(String id, String name, String address, String phone, PhoneType phoneType,
                    String email, String url) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.phoneType = phoneType;
        this.email = email;
        this.url = url;
    }

    @Override
    /**
     * creates FoodItems using money.
     * TODO we don't have a way of using money yet within the system.
     */
    public List<FoodItem> createFoodItems(String code, int amount) {
        return null;
    }

    /**
     * Obtains supplier ID.
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Sets supplier ID.
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtains name of supplier.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of supplier.
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtains supplier address.
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets supplier address.
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Obtains supplier phone number.
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets supplier phone number.
     * @param phone String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Obtains supplier phone type.
     * @return PhoneType
     */
    public PhoneType getPhoneType() {
        return phoneType;
    }

    /**
     * Sets supplier phone type.
     * @param phoneType PhoneType
     */
    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    /**
     * Obtains supplier email.
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets supplier email.
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtains supplier url.
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets supplier url.
     * @param url String
     */
    public void setUrl(String url) {
        this.url = url;
    }
}