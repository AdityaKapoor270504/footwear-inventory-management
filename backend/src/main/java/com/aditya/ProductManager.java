package com.aditya;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.*;

public class ProductManager {

    public void addProducts (List <Product> products) {
        
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

        for (Product product : products) {

                preparedStatement.setString (1, product.getProductName());
                preparedStatement.setString (2, product.getProductBrand());        
                preparedStatement.setString (3, product.getProductCategory());
                preparedStatement.setString (4, product.getGender());
                preparedStatement.setDouble (5, product.getCostPrice());
                preparedStatement.setDouble (6, product.getSellingPrice());
                
                preparedStatement.addBatch();

        }

        int result [] = preparedStatement.executeBatch();
        
        System.out.println(result.length + " products added successfully.");

        } catch (SQLException e) {
                e.getStackTrace();
        }      

    }

    public void getProductById (int productId) {

        String sql = """
                SELECT * 
                FROM Product
                WHERE product_id = ?
                """;
        
        try (Connection connection = DatabaseConnection.connect() ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                
                preparedStatement.setInt (1, productId);
                
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {

                System.out.println("Product ID: "
                        + resultSet.getInt("product_id"));

                System.out.println("Product Name: "
                        + resultSet.getString("product_name"));

                System.out.println("Brand: "
                        + resultSet.getString("product_brand"));

                System.out.println("Category: "
                        + resultSet.getString("product_category"));

                System.out.println("Gender: "
                        + resultSet.getString("gender"));

                System.out.println("Cost Price: "
                        + resultSet.getDouble("cost_price"));

                System.out.println("Selling Price: "
                        + resultSet.getDouble("selling_price"));

                System.out.println("Created At: "
                        + resultSet.getDate("created_at"));

                System.out.println("Updated At: "
                        + resultSet.getDate("updated_at"));

            } else {
                System.out.println("Product not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateProductPrice (int productId, double sellingPrice) {

        String sql = """
                UPDATE Product
                SET selling_price = ?
                WHERE product_id = ?
                """;
        
        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setDouble (1, sellingPrice);
                preparedStatement.setInt (2, productId);

                int rows = preparedStatement.executeUpdate();

                if (rows > 0) {
                        System.out.println ("Product details updated successfully");
                }
                
                else  {
                        System.out.println ("Product not found");
                }
                
        } catch (SQLException e) {
             e.printStackTrace();
        }                
    }

    public void deleteProduct (int productId) {

        String sql = """
                     DELETE FROM Product
                     WHERE product_id = ?   
                     """;
        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt (1, productId);

                int rows = preparedStatement.executeUpdate();
                
                if (rows > 0) {
                        System.out.println ("Product deleted successfully.");
                }

                else {
                        System.out.println ("Product not found.");
                }
        
        } catch (SQLException e) {
              e.printStackTrace();
        }
    }
}
