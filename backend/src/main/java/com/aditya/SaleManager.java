package com.aditya;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class SaleManager {
    
    public void addSale (List <Sale> sales) {
        String sql = """
                    INSERT INTO Sale
                    (customer_id,
                    payment_method,
	                discount_offered,
	                total_net_amount)
                    VALUES (?, ?, ?, ?)
                    """;
        
        try (Connection connnection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connnection.prepareStatement(sql)) {

            for (Sale sale : sales) {

                preparedStatement.setInt (1, sale.getCustomerId());
                preparedStatement.setString (2, sale.getPaymentMethod());
                preparedStatement.setDouble (3, sale.getDiscountOffered());
                preparedStatement.setDouble (4, sale.getTotalNetAmount());

                preparedStatement.addBatch();

            }

            int results [] = preparedStatement.executeBatch();

            System.out.println (results.length + " sales added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
