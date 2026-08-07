package com.aditya;

public class Inventory {

    private int inventory_id;
    private int variant_id;
    private int quantity_in_stock;

    public Inventory (int inventory_id, int variant_id, int quantity_in_stock) {

        this.inventory_id = inventory_id;
        this.variant_id = variant_id;
        this.quantity_in_stock = quantity_in_stock;

    }

    public Inventory (int variant_id, int quantity_in_stock) {
        
        this.variant_id = variant_id;
        this.quantity_in_stock = quantity_in_stock;

    }
    
    public int getInventoryId () {
        return inventory_id;
    }

    public int getVariantId () {
        return variant_id;
    }

    public int getQuantityInStock () {
        return quantity_in_stock;
    }
}
