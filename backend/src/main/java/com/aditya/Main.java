package com.aditya;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    
    public static void main(String[] args) {

        ProductManager productManager = new ProductManager();

        productManager.addProduct("Nike Air Force 1", "Nike", "sneakers", "M", 3800.00, 5000.0);

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

