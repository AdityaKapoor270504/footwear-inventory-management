package com.aditya;

public class ProductVariant {
    
    private int VariantId;
    private int ProductId;
    private String size_of_product;
    private String color;

    public ProductVariant (int VariantId, int ProductId, String size_of_product, String color) {

        this.VariantId = VariantId;
        this.ProductId = ProductId;
        this.size_of_product = size_of_product;
        this.color = color;

    }

    public ProductVariant (int ProductId, String size_of_product, String color) {

        this.ProductId = ProductId;
        this.size_of_product = size_of_product;
        this.color = color;

    }

    public int VariantId () {
        return VariantId;
    }

    public int getProductId () {
        return ProductId;
    }

    public String getSizeOfProduct () {
        return size_of_product;
    }

    public String getColor () {
        return color;
    }    
}


