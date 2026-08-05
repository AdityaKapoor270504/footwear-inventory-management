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
}
