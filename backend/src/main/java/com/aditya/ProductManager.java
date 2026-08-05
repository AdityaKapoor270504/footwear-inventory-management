package com.aditya;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

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
