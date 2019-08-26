package seng202.team1.model;

import seng202.team1.util.PhoneType;

import java.util.*;

/**
 * 
 */
public class Supplier {

    private String id;
    private String name;
    private String address;
    private String phone;
    private PhoneType phoneType;
    private String email;
    private String url;

    /**
     * Default constructor
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