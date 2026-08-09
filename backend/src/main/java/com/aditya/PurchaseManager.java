package com.aditya;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class PurchaseManager {
    
    public void addPurchase (List <Purchase> purchases) {

        String sql = """
                    INSERT INTO Purchase
                    (supplier_id,
                    invoice_number,
                    payment_method,
                    total_payment_amount)
                    VALUES (?, ?, ?, ?)
                     """;

        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Purchase purchase : purchases) {

                preparedStatement.setInt (1, purchase.getSupplierId());
                preparedStatement.setString (2, purchase.getInvoiceNumber());
                preparedStatement.setString (3, purchase.getPaymentMethod());
                preparedStatement.setDouble (4, purchase.getTotalPaymentAmount());

                preparedStatement.addBatch();
                
            }

            int results [] = preparedStatement.executeBatch();

            System.out.println (results.length + "purchases added to the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
