package com.aditya;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InventoryManager {

    public void addInventory (List <Inventory> inventory) {

        String sql = """
                    INSERT INTO Inventory
                    (variant_id,
                    quantity_in_stock)
                    VALUES (?, ?)                
                     """;

        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Inventory inventory_item : inventory) {

                preparedStatement.setInt (1, inventory_item.getVariantId());
                preparedStatement.setInt (2, inventory_item.getQuantityInStock());

                preparedStatement.addBatch();
                
            }

            int result [] = preparedStatement.executeBatch();
            
            System.out.println (result.length + " inventory records added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void viewInventory (int variantId) {

        String sql = """
                SELECT inventory_id,
                       variant_id,
                       quantity_in_stock
                FROM Inventory
                WHERE variant_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, variantId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("\nInventory Details");
                System.out.println("-----------------");
                System.out.println("Inventory ID      : "
                    + resultSet.getInt("inventory_id"));

                System.out.println("Variant ID        : "
                        + resultSet.getInt("variant_id"));

                System.out.println("Quantity in Stock : "
                        + resultSet.getInt("quantity_in_stock"));

            } else {
                System.out.println(
                        "No inventory record found for variant ID: " + variantId
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    public void increaseStock(int variantId, int quantityPurchased) {

        String sql = """
                UPDATE Inventory
                SET quantity_in_stock = quantity_in_stock + ?,
                    updated_at = CURRENT_DATE
                WHERE variant_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, quantityPurchased);
            preparedStatement.setInt(2, variantId);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Inventory updated successfully.");
            } else {
                System.out.println("Inventory record not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
