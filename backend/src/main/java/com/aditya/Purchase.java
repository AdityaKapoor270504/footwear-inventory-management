package com.aditya;

public class Purchase {
    
    private int purchaseId;
    private int supplierId;
    private String invoiceNumber;
    private String paymentMethod;
    private double totalPaymentAmount;

    public Purchase (int purchase_Id, int supplier_Id, String invoice_number, String payment_method, double total_payment_amount) {

        this.purchaseId = purchase_Id;
        this.supplierId = supplier_Id;
        this.invoiceNumber = invoice_number;
        this.paymentMethod = payment_method;
        this.totalPaymentAmount = total_payment_amount;

    }

    public Purchase (int supplier_Id, String invoice_number, String payment_method, double total_payment_amount) {

        this.supplierId = supplier_Id;
        this.invoiceNumber = invoice_number;
        this.paymentMethod = payment_method;
        this.totalPaymentAmount = total_payment_amount;
        
    }

    public int getPurchaseId () {
        return purchaseId;
    }
    
    public int getSupplierId () {
        return supplierId;
    }

    public String getInvoiceNumber () {
        return invoiceNumber;
    }

    public String getPaymentMethod () {
        return paymentMethod;
    }

    public double getTotalPaymentAmount () {
        return totalPaymentAmount;
    }
}
