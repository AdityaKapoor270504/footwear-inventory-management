package com.aditya;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SaleManager {

    public void addSale(List<Sale> sales) {

        String sql = """
                INSERT INTO Sale
                (customer_id,
                 payment_method,
                 discount_percentage,
                 total_net_amount)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Sale sale : sales) {

                preparedStatement.setInt(
                        1,
                        sale.getCustomerId());

                preparedStatement.setString(
                        2,
                        sale.getPaymentMethod());

                preparedStatement.setDouble(
                        3,
                        sale.getDiscountPercentage());

                preparedStatement.setDouble(
                        4,
                        sale.getTotalNetAmount());

                preparedStatement.addBatch();
            }

            int[] results = preparedStatement.executeBatch();

            System.out.println(
                    results.length + " sales added successfully.");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void getSaleById(int saleId) {

        String sql = """
                SELECT *
                FROM Sale
                WHERE sale_id = ?
                """;

        try (
                Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, saleId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("Sale ID: "
                        + resultSet.getInt("sale_id"));

                System.out.println("Customer ID: "
                        + resultSet.getInt("customer_id"));

                System.out.println("Sale Date: "
                        + resultSet.getDate("sale_date"));

                System.out.println("Payment Method: "
                        + resultSet.getString("payment_method"));

                System.out.println("Discount Percentage: "
                        + resultSet.getDouble("discount_percentage")
                        + "%");

                System.out.println("Total Net Amount: ₹"
                        + resultSet.getDouble("total_net_amount"));

            } else {

                System.out.println("Sale not found.");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}