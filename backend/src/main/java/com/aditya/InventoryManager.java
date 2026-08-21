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

    public void viewInventory (int productId) {

        String sql = """
                SELECT
                    p.product_id,
                    p.product_name,
                    p.product_brand,
                    p.product_category,
                    p.gender,
                    p.cost_price,
                    p.selling_price,
                    pv.variant_id,
                    pv.size_of_product,
                    pv.colour,
                    i.quantity_in_stock
                FROM Product p
                INNER JOIN Product_Variant pv
                    ON p.product_id = pv.product_id
                INNER JOIN Inventory i
                    ON pv.variant_id = i.variant_id
                WHERE p.product_id = ?
                ORDER BY pv.variant_id
                """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, productId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                // Product details
                System.out.println("\n======================================");
                System.out.println("          PRODUCT INVENTORY");
                System.out.println("======================================");

                System.out.println("Product ID       : "
                        + resultSet.getInt("product_id"));

                System.out.println("Product Name     : "
                        + resultSet.getString("product_name"));

                System.out.println("Brand            : "
                        + resultSet.getString("product_brand"));

                System.out.println("Category         : "
                        + resultSet.getString("product_category"));

                System.out.println("Gender           : "
                        + resultSet.getString("gender"));

                System.out.println("Cost Price       : "
                        + resultSet.getDouble("cost_price"));

                System.out.println("Selling Price    : "
                        + resultSet.getDouble("selling_price"));

                System.out.println("\nVariants");
                System.out.println("--------------------------------------");

                // First variant
                printVariant(resultSet);

                // Remaining variants
                while (resultSet.next()) {
                    printVariant(resultSet);
                }

                System.out.println("======================================");

            } else {

                System.out.println(
                        "No product or inventory found for product ID: "
                        + productId
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkSaleAvailability(int variantId, int quantity) {

        String sql = """
                SELECT
                    i.quantity_in_stock,
                    p.product_name,
                    pv.size_of_product,
                    pv.colour
                FROM Inventory i
                INNER JOIN Product_Variant pv
                    ON i.variant_id = pv.variant_id
                INNER JOIN Product p
                    ON pv.product_id = p.product_id
                WHERE i.variant_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, variantId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {

                System.out.println();
                System.out.println("==========================================");
                System.out.println("             SALE CHECK");
                System.out.println("==========================================");
                System.out.println("Variant ID " + variantId
                        + " does not exist in inventory.");
                System.out.println("==========================================");

                return false;
            }

            int availableStock = resultSet.getInt("quantity_in_stock");

            String productName = resultSet.getString("product_name");

            String size = resultSet.getString("size_of_product");

            String colour = resultSet.getString("colour");

            System.out.println();
            System.out.println("==========================================");
            System.out.println("             SALE CHECK");
            System.out.println("==========================================");

            System.out.println("Product   : " + productName);
            System.out.println("Size      : " + size);
            System.out.println("Colour    : " + colour);
            System.out.println("Available : " + availableStock);
            System.out.println("Requested : " + quantity);

            if (quantity <= availableStock) {

                System.out.println();
                System.out.println("SALE CAN BE COMPLETED.");
                System.out.println("Remaining stock after sale: "
                        + (availableStock - quantity));

                System.out.println("==========================================");

                return true;

            } else {

                System.out.println();
                System.out.println("SALE CANNOT BE COMPLETED.");
                System.out.println("Insufficient stock.");
                System.out.println("Short by: "
                        + (quantity - availableStock));

                System.out.println("==========================================");

                return false;
            }

        } catch (SQLException e) {

            System.out.println();
            System.out.println("Unable to check inventory.");
            e.printStackTrace();

            return false;
        }
    }

    public boolean increaseInventory(Connection connection,
            int variantId,
            int quantity) throws SQLException {

        String sql = """
                UPDATE Inventory
                SET quantity_in_stock = quantity_in_stock + ?,
                    updated_at = CURRENT_DATE
                WHERE variant_id = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, variantId);

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        }
    }

    public boolean variantExists(Connection connection, int variantId)
            throws SQLException {

        String sql = """
                SELECT 1
                FROM Product_Variant
                WHERE variant_id = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, variantId);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        }
    }

    private void printVariant(ResultSet resultSet)
            throws SQLException {

        System.out.println(
                "Variant ID       : "
                        + resultSet.getInt("variant_id")
        );

        System.out.println(
                "Size             : "
                        + resultSet.getString("size_of_product")
        );

        System.out.println(
                "Colour           : "
                        + resultSet.getString("colour")
        );

        System.out.println(
                "Quantity in Stock: "
                        + resultSet.getInt("quantity_in_stock")
        );

        System.out.println("--------------------------------------");
    }
}
