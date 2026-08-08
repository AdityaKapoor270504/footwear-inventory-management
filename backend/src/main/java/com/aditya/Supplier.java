package com.aditya;

public class Supplier {
    
    private int supplierId;
    private String supplierName;
    private String supplierContactNumber;
    private String suppliermail;
    private String supplierAddress;

    public Supplier (int supplier_Id, String supplier_name, String supplier_contact_number, String supplier_mail, String supplier_address) {
        
        this.supplierId = supplier_Id;
        this.supplierName = supplier_name;
        this.supplierContactNumber = supplier_contact_number;
        this.suppliermail = supplier_mail;
        this.supplierAddress = supplier_address;

    }

    public Supplier (String supplier_name, String supplier_contact_number, String supplier_mail, String supplier_address) {
        
        this.supplierName = supplier_name;
        this.supplierContactNumber = supplier_contact_number;
        this.suppliermail = supplier_mail;
        this.supplierAddress = supplier_address;

    }

    public int getSupplierId () {
        return supplierId;
    }

    public String getSupplierName () {
        return supplierName;
    }

    public String getSupplierContactNumber () {
        return supplierContactNumber;
    }

    public String getSupplierMail () {
        return suppliermail;
    }    

    public String getSupplierAddress () {
        return supplierAddress;
    }
}
