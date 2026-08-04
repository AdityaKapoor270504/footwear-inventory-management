package com.aditya;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    
    public static void main(String[] args) {


        DatabaseConnection.connect();
        ProductManager productManager = new ProductManager();

        productManager.addProduct(
                "Nike Air Max",
                "Nike",
                "Sports Shoes",
                "M",
                4000,
                6000
        );

        productManager.getProductById(1);
        try (Connection connection = DatabaseConnection.connect()) {

            if (connection != null) {
                System.out.println("Database is ready to use.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

