package com.aditya;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductManager {

    public void addProduct (String productName,
                            String brand,
                            String category,
                            String gender,
                            double costPrice,
                            double sellingPrice) {
        
        String sql = """
                INSERT INTO Product
                (product_name,
                product_brand,
                product_category,
                gender,
                cost_price,
                selling_price)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString (1, productName);
            preparedStatement.setString (2, brand);
            preparedStatement.setString (3, category);
            preparedStatement.setString (4, gender);
            preparedStatement.setDouble (5, costPrice);
            preparedStatement.setDouble (6, sellingPrice);

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println ("Product added successfully.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
