package com.aditya;

public class Product {
    
    private String product_name;
    private String product_brand;
    private String product_category;
    private String gender;
    private double cost_price;
    private double selling_price;

    public Product (String productname, String productbrand, String productcategory, String gender, double costprice, double sellingprice) {
        
        this.product_name = productname;
        this.product_brand = productbrand;
        this.product_category = productcategory;
        this.gender = gender;
        this.cost_price = costprice;
        this.selling_price = sellingprice;

    }

    public String getProductName () {
        return product_name;
    }

    public String getProductBrand () {
        return product_brand;
    }

    public String getProductCategory () {
        return product_category;
    }

    public String getGender () {
        return gender;
    }

    public double getCostPrice () {
        return cost_price;
    }

    public double getSellingPrice() {
        return selling_price;
    }

}
