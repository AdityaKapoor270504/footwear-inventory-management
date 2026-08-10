package com.aditya;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
