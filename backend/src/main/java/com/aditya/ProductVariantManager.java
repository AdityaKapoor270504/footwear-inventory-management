package com.aditya;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class ProductVariantManager {
    
    public void addProductVariant (List <ProductVariant> productVariants) {

        String sql = """
                    INSERT INTO product_variant
                    (product_id,
                     size_of_product,
                     colour)
                     VALUES (?, ?, ?)
                     """;
        
        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            for (ProductVariant product : productVariants) {

                preparedStatement.setInt (1, product.getProductId());
                preparedStatement.setString (2, product.getSizeOfProduct());
                preparedStatement.setString (3, product.getColor());

                preparedStatement.addBatch();
            }

            int result[] = preparedStatement.executeBatch();

            System.out.println (result.length + " Product variants added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getProductVariantById(int variantId) {

        String sql = """
                SELECT variant_id, product_id, size_of_product, colour
                FROM product_variant
                WHERE variant_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, variantId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("\nProduct Variant Details");
                System.out.println("-----------------------");
                System.out.println("Variant ID      : " + resultSet.getInt("variant_id"));
                System.out.println("Product ID      : " + resultSet.getInt("product_id"));
                System.out.println("Size            : " + resultSet.getString("size_of_product"));
                System.out.println("Colour          : " + resultSet.getString("colour"));

            } else {
                System.out.println("Product variant with ID "
                        + variantId + " does not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductVariant(int variantId, String updatedSize, String updatedColour) {

        String sql = """
                UPDATE product_variant
                SET size_of_product = ?,
                    colour = ?
                WHERE variant_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, updatedSize);
            preparedStatement.setString(2, updatedColour);
            preparedStatement.setInt(3, variantId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Product variant updated successfully.");
            } else {
                System.out.println("Product variant with ID "
                        + variantId + " does not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
