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

        System.out.print ("Enter number of product variants : ");

        int numberOfVariants = sc.nextInt();
        sc.nextLine();

        List <ProductVariant> productVariants = new ArrayList<>();

        for (int i = 1; i <= numberOfVariants; i++) {

             System.out.println("\nEnter details for variant " + i);

            System.out.print("Enter product ID: ");
            int productId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter size of product: ");
            String sizeOfProduct = sc.nextLine();

            System.out.print("Enter colour: ");
            String colour = sc.nextLine();

            ProductVariant variant = new ProductVariant(
                    productId,
                    sizeOfProduct,
                    colour
            );

            productVariants.add (variant);

        }

        int productId;

        System.out.print ("Enter the id of the product whose details you wish to view : ");
        productId = sc.nextInt();

        productManager.getProductById (productId);
        sc.nextLine();

        int updateProductId;
        double updatedSellingPrice;
        System.out.print ("Enter the id of the product whose selling price you wish to modify : ");
        updateProductId = sc.nextInt();
        
        System.out.print ("Enter the updated selling price : ");
        updatedSellingPrice = sc.nextDouble();

        sc.nextLine();

        productManager.updateProductPrice (updateProductId, updatedSellingPrice);

        ProductVariantManager productVariantManager = new ProductVariantManager();

        productVariantManager.addProductVariant(productVariants);

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

