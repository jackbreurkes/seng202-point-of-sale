package seng202.team1.model;

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

}