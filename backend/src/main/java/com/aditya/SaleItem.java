package com.aditya;

public class SaleItem {
    
    private int saleItemId;
    private int saleId;
    private int quantitySold;
    private int variantId;
    private double sellingPrice;

    public SaleItem (int sale_item_id, int sale_id, int quantity, int variant_id, double selling_price) {

        this.saleItemId = sale_item_id;
        this.saleId = sale_id;
        this.quantitySold = quantity;
        this.variantId = variant_id;
        this.sellingPrice = selling_price;

    }

    public SaleItem (int sale_id, int quantity, int variant_id, double selling_price) {

        this.saleId = sale_id;
        this.quantitySold = quantity;
        this.variantId = variant_id;
        this.sellingPrice = selling_price;

    }

    public int getSaleItemId () {
        return saleItemId;
    }

    public int getSaleId () {
        return saleId;
    }

    public int getQuantitySold () {
        return quantitySold;
    }

    public int getVariantId () {
        return variantId;
    }

    public double getSellingPrice () {
        return sellingPrice;
    }
}
