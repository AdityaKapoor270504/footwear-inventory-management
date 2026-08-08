package com.aditya;

public class Customer {
    
    private int customer_id;
    private String customer_name;
    private String customer_contact_number;

    public Customer (int customerId, String customerName, String customerContactNumber) {

        this.customer_id = customerId;
        this.customer_name = customerName;
        this.customer_contact_number = customerContactNumber;
    } 
    
    public int getCustomerId () {
        return customer_id;
    }

    public String getCustomerName () {
        return customer_name;
    }

    public String getCustomerContactNumber () {
        return customer_contact_number;
    }
}
