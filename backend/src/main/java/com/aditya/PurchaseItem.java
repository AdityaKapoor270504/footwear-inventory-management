package com.aditya;

public class PurchaseItem {
    
    private int purchaseItemId;
    private int purchaseId;
    private int quantity;
    private int variantId;
    private double costPrice;

    public PurchaseItem (int purchase_item_id, int purchase_id, int quantity, int variant_id, double cost_price) {

        this.purchaseItemId = purchase_item_id;
        this.purchaseId = purchase_id;
        this.quantity = quantity;
        this.variantId = variant_id;
        this.costPrice = cost_price;

    }

    public PurchaseItem (int purchase_id, int quantity, int variant_id, double cost_price) {

        this.purchaseId = purchase_id;
        this.quantity = quantity;
        this.variantId = variant_id;
        this.costPrice = cost_price;

    }

    public int getPurchaseItemId () {
        return purchaseItemId;
    }

    public int getPurchaseId () {
        return purchaseId;
    }

    public int getQuantity () {
        return quantity;
    }

    public int getVariantId () {
        return variantId;
    }

    public double getCostPrice () {
        return costPrice;
    }
}
