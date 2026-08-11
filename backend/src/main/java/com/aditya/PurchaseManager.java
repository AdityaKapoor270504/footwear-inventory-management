package com.aditya;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

            System.out.println (results.length + " purchases added to the database successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getPurchaseById(int purchaseId) {

        String sql = """
                SELECT *
                FROM Purchase
                WHERE purchase_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, purchaseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("Purchase ID: "
                        + resultSet.getInt("purchase_id"));

                System.out.println("Supplier ID: "
                        + resultSet.getInt("supplier_id"));

                System.out.println("Purchase Date: "
                        + resultSet.getDate("purchase_date"));

                System.out.println("Invoice Number: "
                        + resultSet.getString("invoice_number"));

                System.out.println("Payment Method: "
                        + resultSet.getString("payment_method"));

                System.out.println("Total Payment Amount: "
                        + resultSet.getDouble("total_payment_amount"));

            } else {
                System.out.println("Purchase not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
