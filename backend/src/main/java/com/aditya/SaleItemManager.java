package com.aditya;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

}
