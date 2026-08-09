package com.aditya;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class PurchaseItemManager {
    
    public void addPurchaseItem (List <PurchaseItem> purchaseitems) {

        String sql = """
                    INSERT INTO Purchase_item
                    (purchase_id,
                    quantity,
                    variant_id,
                    cost_price)
                    VALUES (?, ?, ?, ?)
                    """;
        
        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (PurchaseItem purchaseItem : purchaseitems) {

                preparedStatement.setInt (1, purchaseItem.getPurchaseId());
                preparedStatement.setInt (2, purchaseItem.getQuantity());
                preparedStatement.setInt (3, purchaseItem.getVariantId());
                preparedStatement.setDouble (4, purchaseItem.getCostPrice());

                preparedStatement.addBatch();
            }

            int results [] = preparedStatement.executeBatch();

            System.out.println (results.length + " purchase items added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
