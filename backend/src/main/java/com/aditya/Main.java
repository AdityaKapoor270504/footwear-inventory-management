package com.aditya;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    
    public static void main(String[] args) {


        DatabaseConnection.connect();
        ProductManager productManager = new ProductManager();

        productManager.getProductById(1);

        productManager.updateProductPrice(1, 3800.00);

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

