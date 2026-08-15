package com.aditya;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SaleManager {

        // =========================================================
        // CREATE COMPLETE SALE
        // =========================================================

        public int createSale(Sale sale, List<SaleItem> saleItems) {

                String saleSql = """
                                INSERT INTO Sale
                                (customer_id,
                                 payment_method,
                                 discount_percentage,
                                 total_net_amount)
                                VALUES (?, ?, ?, ?)
                                RETURNING sale_id
                                """;

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

                        // =================================================
                        // STEP 1: CONNECT TO DATABASE
                        // =================================================

                        connection = DatabaseConnection.connect();

                        // Turn off auto-commit
                        connection.setAutoCommit(false);

                        // =================================================
                        // STEP 2: CREATE SALE
                        // =================================================

                        int saleId;

                        try (PreparedStatement saleStatement = connection.prepareStatement(saleSql)) {

                                saleStatement.setInt(
                                                1,
                                                sale.getCustomerId());

                                saleStatement.setString(
                                                2,
                                                sale.getPaymentMethod());

                                saleStatement.setDouble(
                                                3,
                                                sale.getDiscountPercentage());

                                saleStatement.setDouble(
                                                4,
                                                sale.getTotalNetAmount());

                                ResultSet resultSet = saleStatement.executeQuery();

                                if (!resultSet.next()) {

                                        throw new SQLException(
                                                        "Sale could not be created.");
                                }

                                saleId = resultSet.getInt("sale_id");
                        }

                        // =================================================
                        // STEP 3: ADD SALE ITEMS
                        // =================================================

                        try (PreparedStatement saleItemStatement = connection.prepareStatement(saleItemSql)) {

                                for (SaleItem saleItem : saleItems) {

                                        saleItemStatement.setInt(
                                                        1,
                                                        saleId);

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

                                saleItemStatement.executeBatch();
                        }

                        // =================================================
                        // STEP 4: UPDATE INVENTORY
                        // =================================================

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
                                         * If no row was updated:
                                         *
                                         * - Inventory record does not exist
                                         * OR
                                         * - There is insufficient stock
                                         */

                                        if (rows == 0) {

                                                throw new SQLException(
                                                                "Insufficient stock or inventory record "
                                                                                + "not found for variant ID: "
                                                                                + saleItem.getVariantId());
                                        }
                                }
                        }

                        // =================================================
                        // STEP 5: COMMIT EVERYTHING
                        // =================================================

                        connection.commit();

                        System.out.println();
                        System.out.println(
                                        "==========================================");
                        System.out.println(
                                        "          SALE COMPLETED");
                        System.out.println(
                                        "==========================================");
                        System.out.println(
                                        "Sale ID: " + saleId);
                        System.out.println(
                                        "Sale items added successfully.");
                        System.out.println(
                                        "Inventory updated successfully.");
                        System.out.println(
                                        "==========================================");

                        return saleId;

                } catch (SQLException e) {

                        // =================================================
                        // ROLLBACK EVERYTHING
                        // =================================================

                        if (connection != null) {

                                try {

                                        connection.rollback();

                                        System.out.println();
                                        System.out.println(
                                                        "Sale transaction failed.");
                                        System.out.println(
                                                        "All changes have been rolled back.");

                                } catch (SQLException rollbackException) {

                                        rollbackException.printStackTrace();
                                }
                        }

                        e.printStackTrace();

                        return -1;

                } finally {

                        // =================================================
                        // CLOSE CONNECTION
                        // =================================================

                        if (connection != null) {

                                try {

                                        connection.close();

                                } catch (SQLException e) {

                                        e.printStackTrace();
                                }
                        }
                }
        }

        // =========================================================
        // VIEW SALE
        // =========================================================

        public void getSaleById(int saleId) {

                String sql = """
                                SELECT *
                                FROM Sale
                                WHERE sale_id = ?
                                """;

                try (
                                Connection connection = DatabaseConnection.connect();

                                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                        preparedStatement.setInt(
                                        1,
                                        saleId);

                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {

                                System.out.println();
                                System.out.println(
                                                "==========================================");
                                System.out.println(
                                                "              SALE DETAILS");
                                System.out.println(
                                                "==========================================");

                                System.out.println(
                                                "Sale ID: "
                                                                + resultSet.getInt("sale_id"));

                                System.out.println(
                                                "Customer ID: "
                                                                + resultSet.getInt("customer_id"));

                                System.out.println(
                                                "Sale Date: "
                                                                + resultSet.getDate("sale_date"));

                                System.out.println(
                                                "Payment Method: "
                                                                + resultSet.getString("payment_method"));

                                System.out.println(
                                                "Discount Percentage: "
                                                                + resultSet.getDouble(
                                                                                "discount_percentage")
                                                                + "%");

                                System.out.println(
                                                "Total Net Amount: ₹"
                                                                + resultSet.getDouble(
                                                                                "total_net_amount"));

                                System.out.println(
                                                "==========================================");

                        } else {

                                System.out.println(
                                                "Sale not found.");
                        }

                } catch (SQLException e) {

                        e.printStackTrace();
                }
        }
}