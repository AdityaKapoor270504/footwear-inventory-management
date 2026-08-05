package com.aditya;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
public class Main {
    
    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

        int numberOfProducts;

        System.out.print ("Enter the number of products you wish to enter : ");

        List <Product> products = new ArrayList<>();

        numberOfProducts = sc.nextInt();

        sc.nextLine();

        for (int i = 1; i <= numberOfProducts; i++) {

            System.out.println ("\nEnter details for product " + i);

            System.out.print ("Enter product name : ");
            String productName = sc.nextLine();

            System.out.print ("Enter product brand : ");
            String productBrand = sc.nextLine();

            System.out.print ("Enter product category : ");
            String productCategory = sc.nextLine();

            System.out.print ("Enter gender : ");
            String gender = sc.nextLine();

            System.out.print ("Enter cost price : ");
            double costPrice = sc.nextDouble();

            System.out.print ("Enter selling price : ");
            double sellingPrice = sc.nextDouble();

            sc.nextLine();

            Product product = new Product(productName, productBrand, productCategory, gender, costPrice, sellingPrice);

            products.add (product);

        }

        ProductManager productManager = new ProductManager();

        productManager.addProducts(products);

        productManager.getProductById(1);

        try (Connection connection = DatabaseConnection.connect()) {

            if (connection != null) {
                System.out.println("Database is ready to use.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        sc.close();

    }
}

