package com.aditya;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
            e.getStackTrace();
        }

    }
    
}
