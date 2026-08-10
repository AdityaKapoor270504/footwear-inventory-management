package com.aditya;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SaleItemManager {

    public void addSaleItem(List<SaleItem> saleItems) {

        String sql = """
                INSERT INTO Sale_Item
                (sale_id,
                quantity,
                variant_id,
                selling_price)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (SaleItem saleItem : saleItems) {

                preparedStatement.setInt(1, saleItem.getSaleId());
                preparedStatement.setInt(2, saleItem.getQuantitySold());
                preparedStatement.setInt(3, saleItem.getVariantId());
                preparedStatement.setDouble(4, saleItem.getSellingPrice());

                preparedStatement.addBatch();
            }

            int[] results = preparedStatement.executeBatch();

            System.out.println(results.length + " sale items added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getSaleItemById(int saleItemId) {

        String sql = """
                SELECT *
                FROM Sale_Item
                WHERE sale_item_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, saleItemId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("Sale Item ID: "
                        + resultSet.getInt("sale_item_id"));

                System.out.println("Sale ID: "
                        + resultSet.getInt("sale_id"));

                System.out.println("Quantity Sold: "
                        + resultSet.getInt("quantity"));

                System.out.println("Variant ID: "
                        + resultSet.getInt("variant_id"));

                System.out.println("Selling Price: "
                        + resultSet.getDouble("selling_price"));

            } else {
                System.out.println("Sale item not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
