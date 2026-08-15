package com.aditya;

public class Sale {
    
    private int saleId;
    private int customerId;
    private String paymentMethod;
    private double discountPercentage;
    private double totalNetAmount;

    public Sale (int sale_id, int customer_id, String payment_method, double discount_percentage, double total_net_amount) {

        this.saleId = sale_id;
        this.customerId = customer_id;
        this.paymentMethod = payment_method;
        this.discountPercentage = discount_percentage;
        this.totalNetAmount = total_net_amount;

    }

    public Sale (int customer_id, String payment_method, double discount_percentage, double total_net_amount) {

        this.customerId = customer_id;
        this.paymentMethod = payment_method;
        this.discountPercentage = discount_percentage;
        this.totalNetAmount = total_net_amount;

    }

    public int getSaleId () {
        return saleId;
    }

    public int getCustomerId () {
        return customerId;
    }

    public String getPaymentMethod () {
        return paymentMethod;
    }
    
    public double getDiscountPercentage () {
        return discountPercentage;
    }

    public double getTotalNetAmount () {
        return totalNetAmount;
    }
}
