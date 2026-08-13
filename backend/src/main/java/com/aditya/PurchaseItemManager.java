package com.aditya;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PurchaseItemManager {

    public void addPurchaseItem(List<PurchaseItem> purchaseItems) {

        String sql = """
                INSERT INTO Purchase_item
                (purchase_id,
                quantity,
                variant_id,
                cost_price)
                VALUES (?, ?, ?, ?)
                """;

        Connection connection = null;

        try {
            connection = DatabaseConnection.connect();

            // Turn off auto-commit
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                for (PurchaseItem purchaseItem : purchaseItems) {

                    preparedStatement.setInt(
                            1,
                            purchaseItem.getPurchaseId());

                    preparedStatement.setInt(
                            2,
                            purchaseItem.getQuantity());

                    preparedStatement.setInt(
                            3,
                            purchaseItem.getVariantId());

                    preparedStatement.setDouble(
                            4,
                            purchaseItem.getCostPrice());

                    preparedStatement.addBatch();
                }

                // Insert all purchase items
                int[] results = preparedStatement.executeBatch();

                System.out.println(
                        results.length
                                + " purchase items added successfully.");
            }

            // Update inventory using THE SAME connection
            String inventorySql = """
                    UPDATE Inventory
                    SET quantity_in_stock = quantity_in_stock + ?,
                        updated_at = CURRENT_DATE
                    WHERE variant_id = ?
                    """;

            try (PreparedStatement inventoryStatement = connection.prepareStatement(inventorySql)) {

                for (PurchaseItem purchaseItem : purchaseItems) {

                    inventoryStatement.setInt(
                            1,
                            purchaseItem.getQuantity());

                    inventoryStatement.setInt(
                            2,
                            purchaseItem.getVariantId());

                    int rows = inventoryStatement.executeUpdate();

                    if (rows == 0) {
                        throw new SQLException(
                                "Inventory record not found for variant ID: "
                                        + purchaseItem.getVariantId());
                    }
                }
            }

            // Everything succeeded
            connection.commit();

            System.out.println("Purchase transaction completed successfully.");
            System.out.println("Inventory updated successfully.");

        } catch (SQLException e) {

            // Something failed
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println(
                            "Transaction failed. All changes have been rolled back.");
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            e.printStackTrace();

        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getPurchaseItemById(int purchaseItemId) {

        String sql = """
                SELECT *
                FROM Purchase_item
                WHERE purchase_item_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, purchaseItemId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("Purchase Item ID: "
                        + resultSet.getInt("purchase_item_id"));

                System.out.println("Purchase ID: "
                        + resultSet.getInt("purchase_id"));

                System.out.println("Quantity: "
                        + resultSet.getInt("quantity"));

                System.out.println("Variant ID: "
                        + resultSet.getInt("variant_id"));

                System.out.println("Cost Price: "
                        + resultSet.getDouble("cost_price"));

            } else {
                System.out.println("Purchase item not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}