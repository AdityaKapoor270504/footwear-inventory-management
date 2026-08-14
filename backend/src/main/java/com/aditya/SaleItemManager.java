package com.aditya;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SaleItemManager {

        public void addSaleItem(List<SaleItem> saleItems) {

                String saleItemSql = """
                                INSERT INTO Sale_Item
                                (sale_id,
                                quantity,
                                variant_id,
                                selling_price)
                                VALUES (?, ?, ?, ?)
                                """;

                String inventorySql = """
                                UPDATE Inventory
                                SET quantity_in_stock = quantity_in_stock - ?,
                                    updated_at = CURRENT_DATE
                                WHERE variant_id = ?
                                  AND quantity_in_stock >= ?
                                """;

                Connection connection = null;

                try {

                        // Create ONE connection
                        connection = DatabaseConnection.connect();

                        // Turn off auto-commit
                        connection.setAutoCommit(false);

                        // ------------------------------------------------
                        // STEP 1: Add Sale Items
                        // ------------------------------------------------

                        try (PreparedStatement saleItemStatement = connection.prepareStatement(saleItemSql)) {

                                for (SaleItem saleItem : saleItems) {

                                        saleItemStatement.setInt(
                                                        1,
                                                        saleItem.getSaleId());

                                        saleItemStatement.setInt(
                                                        2,
                                                        saleItem.getQuantitySold());

                                        saleItemStatement.setInt(
                                                        3,
                                                        saleItem.getVariantId());

                                        saleItemStatement.setDouble(
                                                        4,
                                                        saleItem.getSellingPrice());

                                        saleItemStatement.addBatch();
                                }

                                int[] results = saleItemStatement.executeBatch();

                                System.out.println(
                                                results.length
                                                                + " sale items added successfully.");
                        }

                        // ------------------------------------------------
                        // STEP 2: Decrease Inventory
                        // ------------------------------------------------

                        try (PreparedStatement inventoryStatement = connection.prepareStatement(inventorySql)) {

                                for (SaleItem saleItem : saleItems) {

                                        inventoryStatement.setInt(
                                                        1,
                                                        saleItem.getQuantitySold());

                                        inventoryStatement.setInt(
                                                        2,
                                                        saleItem.getVariantId());

                                        inventoryStatement.setInt(
                                                        3,
                                                        saleItem.getQuantitySold());

                                        int rows = inventoryStatement.executeUpdate();

                                        /*
                                         * If rows == 0, either:
                                         *
                                         * 1. The inventory record doesn't exist
                                         * OR
                                         * 2. There isn't enough stock
                                         */

                                        if (rows == 0) {

                                                throw new SQLException(
                                                                "Insufficient stock or inventory record "
                                                                                + "not found for variant ID: "
                                                                                + saleItem.getVariantId());
                                        }
                                }
                        }

                        // ------------------------------------------------
                        // STEP 3: Commit
                        // ------------------------------------------------

                        connection.commit();

                        System.out.println(
                                        "Sale transaction completed successfully.");

                        System.out.println(
                                        "Inventory updated successfully.");

                } catch (SQLException e) {

                        // ------------------------------------------------
                        // STEP 4: Rollback if anything fails
                        // ------------------------------------------------

                        if (connection != null) {

                                try {

                                        connection.rollback();

                                        System.out.println(
                                                        "Sale transaction failed.");

                                        System.out.println(
                                                        "All changes have been rolled back.");

                                } catch (SQLException rollbackException) {

                                        rollbackException.printStackTrace();
                                }
                        }

                        e.printStackTrace();

                } finally {

                        // ------------------------------------------------
                        // STEP 5: Close connection
                        // ------------------------------------------------

                        if (connection != null) {

                                try {
                                        connection.close();

                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                        }
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

                                System.out.println(
                                                "Sale item not found.");
                        }

                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
}