package com.aditya;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PurchaseManager {

    // =========================================================
    // CREATE PURCHASE
    // =========================================================

    public void createPurchase(Scanner scanner) {

        Connection connection = null;

        try {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("             CREATE PURCHASE");
            System.out.println("==========================================");

            // -------------------------------------------------
            // STEP 1: SELECT SUPPLIER
            // -------------------------------------------------

            int supplierId = selectSupplier(scanner);

            if (supplierId == -1) {

                System.out.println("Purchase cancelled.");
                return;
            }

            // -------------------------------------------------
            // STEP 2: PURCHASE DETAILS
            // -------------------------------------------------

            String invoiceNumber = readString(
                    scanner,
                    "Enter invoice number: ");

            String paymentMethod = readString(
                    scanner,
                    "Enter payment method: ");

            // -------------------------------------------------
            // STEP 3: PURCHASE ITEMS
            // -------------------------------------------------

            int numberOfItems = readPositiveInt(
                    scanner,
                    "Enter number of different items: ");

            List<PurchaseItemData> purchaseItems = new ArrayList<>();

            double totalAmount = 0;

            for (int i = 0; i < numberOfItems; i++) {

                System.out.println();
                System.out.println(
                        "---------- ITEM " + (i + 1) + " ----------");

                int variantId;

                // -------------------------------------------------
                // SELECT VALID VARIANT
                // -------------------------------------------------

                while (true) {

                    variantId = readPositiveInt(
                            scanner,
                            "Enter variant ID: ");

                    VariantDetails variant = getVariantDetails(variantId);

                    if (variant == null) {

                        System.out.println();
                        System.out.println(
                                "Invalid variant ID.");

                        System.out.println(
                                "Please enter a valid variant ID.");

                        continue;
                    }

                    // Display variant information
                    printVariantDetails(variant);

                    break;
                }

                // -------------------------------------------------
                // QUANTITY AND COST
                // -------------------------------------------------

                int quantity = readPositiveInt(
                        scanner,
                        "Enter quantity: ");

                double costPrice = readNonNegativeDouble(
                        scanner,
                        "Enter cost price: ");

                double itemTotal = quantity * costPrice;

                totalAmount += itemTotal;

                PurchaseItemData item = new PurchaseItemData(
                        variantId,
                        quantity,
                        costPrice);

                purchaseItems.add(item);

                System.out.printf(
                        "Item Total: ₹%.2f%n",
                        itemTotal);
            }

            // -------------------------------------------------
            // STEP 4: PURCHASE SUMMARY
            // -------------------------------------------------

            System.out.println();
            System.out.println("==========================================");
            System.out.println("           PURCHASE SUMMARY");
            System.out.println("==========================================");

            System.out.println(
                    "Supplier ID       : " + supplierId);

            System.out.println(
                    "Invoice Number    : " + invoiceNumber);

            System.out.println(
                    "Payment Method    : " + paymentMethod);

            System.out.println();

            for (int i = 0; i < purchaseItems.size(); i++) {

                PurchaseItemData item = purchaseItems.get(i);

                System.out.println(
                        "Item " + (i + 1));

                System.out.println(
                        "Variant ID        : "
                                + item.variantId);

                System.out.println(
                        "Quantity          : "
                                + item.quantity);

                System.out.printf(
                        "Cost Price        : Rs. %.2f%n",
                        item.costPrice);

                System.out.printf(
                        "Item Total        : Rs. %.2f%n",
                        item.getItemTotal());

                System.out.println(
                        "------------------------------------------");
            }

            System.out.printf(
                    "Total Amount      : Rs. %.2f%n",
                    totalAmount);

            System.out.println(
                    "==========================================");

            // -------------------------------------------------
            // STEP 5: CONFIRM PURCHASE
            // -------------------------------------------------

            String confirmation = readString(
                    scanner,
                    "Confirm purchase? (Y/N): ")
                    .toUpperCase();

            if (!confirmation.equals("Y")) {

                System.out.println(
                        "Purchase cancelled.");

                return;
            }

            // -------------------------------------------------
            // STEP 6: START TRANSACTION
            // -------------------------------------------------

            connection = DatabaseConnection.connect();

            connection.setAutoCommit(false);

            // -------------------------------------------------
            // STEP 7: INSERT PURCHASE
            // -------------------------------------------------

            String purchaseSql = """
                    INSERT INTO Purchase
                    (
                        supplier_id,
                        invoice_number,
                        payment_method,
                        total_payment_amount
                    )
                    VALUES (?, ?, ?, ?)
                    RETURNING purchase_id
                    """;

            int purchaseId;

            try (PreparedStatement purchaseStatement = connection.prepareStatement(purchaseSql)) {

                purchaseStatement.setInt(
                        1,
                        supplierId);

                purchaseStatement.setString(
                        2,
                        invoiceNumber);

                purchaseStatement.setString(
                        3,
                        paymentMethod);

                purchaseStatement.setDouble(
                        4,
                        totalAmount);

                ResultSet resultSet = purchaseStatement.executeQuery();

                if (!resultSet.next()) {

                    throw new SQLException(
                            "Purchase could not be created.");
                }

                purchaseId = resultSet.getInt("purchase_id");
            }

            // -------------------------------------------------
            // STEP 8: INSERT PURCHASE ITEMS
            // -------------------------------------------------

            String purchaseItemSql = """
                    INSERT INTO Purchase_item
                    (
                        purchase_id,
                        quantity,
                        variant_id,
                        cost_price
                    )
                    VALUES (?, ?, ?, ?)
                    """;

            try (PreparedStatement itemStatement = connection.prepareStatement(purchaseItemSql)) {

                for (PurchaseItemData item : purchaseItems) {

                    itemStatement.setInt(
                            1,
                            purchaseId);

                    itemStatement.setInt(
                            2,
                            item.quantity);

                    itemStatement.setInt(
                            3,
                            item.variantId);

                    itemStatement.setDouble(
                            4,
                            item.costPrice);

                    itemStatement.addBatch();
                }

                itemStatement.executeBatch();
            }

            // -------------------------------------------------
            // STEP 9: UPDATE INVENTORY
            // -------------------------------------------------

            String inventorySql = """
                    UPDATE Inventory
                    SET quantity_in_stock =
                            quantity_in_stock + ?,
                        updated_at = CURRENT_DATE
                    WHERE variant_id = ?
                    """;

            try (PreparedStatement inventoryStatement = connection.prepareStatement(inventorySql)) {

                for (PurchaseItemData item : purchaseItems) {

                    inventoryStatement.setInt(
                            1,
                            item.quantity);

                    inventoryStatement.setInt(
                            2,
                            item.variantId);

                    int rows = inventoryStatement.executeUpdate();

                    if (rows == 0) {

                        throw new SQLException(
                                "Inventory record not found "
                                        + "for variant ID: "
                                        + item.variantId);
                    }
                }
            }

            // -------------------------------------------------
            // STEP 10: COMMIT
            // -------------------------------------------------

            connection.commit();

            System.out.println();
            System.out.println("==========================================");
            System.out.println(
                    "       PURCHASE COMPLETED SUCCESSFULLY");
            System.out.println("==========================================");

            System.out.println(
                    "Purchase ID       : " + purchaseId);

            System.out.println(
                    "Supplier ID       : " + supplierId);

            System.out.printf(
                    "Total Amount      : Rs. %.2f%n",
                    totalAmount);

            System.out.println(
                    "Inventory Updated : YES");

            System.out.println(
                    "==========================================");

        } catch (SQLException e) {

            // -------------------------------------------------
            // ROLLBACK
            // -------------------------------------------------

            if (connection != null) {

                try {

                    connection.rollback();

                    System.out.println();
                    System.out.println(
                            "Purchase transaction failed.");

                    System.out.println(
                            "All database changes have "
                                    + "been rolled back.");

                } catch (SQLException rollbackException) {

                    rollbackException.printStackTrace();
                }
            }

            System.out.println();
            System.out.println(
                    "Unable to complete purchase.");

            e.printStackTrace();

        } finally {

            // -------------------------------------------------
            // CLOSE CONNECTION
            // -------------------------------------------------

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
    // GET VARIANT DETAILS
    // =========================================================

    private VariantDetails getVariantDetails(int variantId) {

        String sql = """
                SELECT
                    p.product_id,
                    p.product_name,
                    p.product_brand,
                    p.product_category,
                    pv.variant_id,
                    pv.size_of_product,
                    pv.colour,
                    i.quantity_in_stock
                FROM Product p
                INNER JOIN Product_Variant pv
                    ON p.product_id = pv.product_id
                INNER JOIN Inventory i
                    ON pv.variant_id = i.variant_id
                WHERE pv.variant_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(
                    1,
                    variantId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return new VariantDetails(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_brand"),
                        resultSet.getString("product_category"),
                        resultSet.getInt("variant_id"),
                        resultSet.getString("size_of_product"),
                        resultSet.getString("colour"),
                        resultSet.getInt("quantity_in_stock"));
            }

        } catch (SQLException e) {

            System.out.println();
            System.out.println(
                    "Unable to retrieve variant details.");

            e.printStackTrace();
        }

        return null;
    }

    // =========================================================
    // PRINT VARIANT DETAILS
    // =========================================================

    private void printVariantDetails(
            VariantDetails variant) {

        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("             VARIANT DETAILS");
        System.out.println("------------------------------------------");

        System.out.println(
                "Product ID    : "
                        + variant.productId);

        System.out.println(
                "Product       : "
                        + variant.productName);

        System.out.println(
                "Brand         : "
                        + variant.productBrand);

        System.out.println(
                "Category      : "
                        + variant.productCategory);

        System.out.println(
                "Variant ID    : "
                        + variant.variantId);

        System.out.println(
                "Size          : "
                        + variant.size);

        System.out.println(
                "Colour        : "
                        + variant.colour);

        System.out.println(
                "Current Stock : "
                        + variant.currentStock);

        System.out.println("------------------------------------------");
    }

    // =========================================================
    // SELECT SUPPLIER
    // =========================================================

    private int selectSupplier(Scanner scanner) {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("             SELECT SUPPLIER");
        System.out.println("==========================================");
        System.out.println("1. Use Existing Supplier");
        System.out.println("2. Create New Supplier");
        System.out.println("0. Cancel Purchase");
        System.out.println("==========================================");

        int choice = readInt(
                scanner,
                "Enter your choice: ");

        switch (choice) {

            case 1:

                int supplierId = readPositiveInt(
                        scanner,
                        "Enter existing supplier ID: ");

                if (!supplierExists(supplierId)) {

                    System.out.println();
                    System.out.println(
                            "Supplier with ID "
                                    + supplierId
                                    + " does not exist.");

                    return -1;
                }

                return supplierId;

            case 2:

                return createSupplier(scanner);

            case 0:

                return -1;

            default:

                System.out.println(
                        "Invalid choice.");

                return selectSupplier(scanner);
        }
    }

    // =========================================================
    // CHECK SUPPLIER
    // =========================================================

    private boolean supplierExists(int supplierId) {

        String sql = """
                SELECT supplier_id
                FROM Supplier
                WHERE supplier_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(
                    1,
                    supplierId);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            System.out.println(
                    "Unable to verify supplier.");

            e.printStackTrace();

            return false;
        }
    }

    // =========================================================
    // CREATE NEW SUPPLIER
    // =========================================================

    private int createSupplier(Scanner scanner) {

        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("           CREATE NEW SUPPLIER");
        System.out.println("------------------------------------------");

        String name = readString(
                scanner,
                "Enter supplier name: ");

        String contact = readString(
                scanner,
                "Enter supplier contact number: ");

        String mail = readString(
                scanner,
                "Enter supplier mail: ");

        String address = readString(
                scanner,
                "Enter supplier address: ");

        // IMPORTANT:
        // These column names match your actual Supplier table:
        //
        // supplier_id
        // supplier_name
        // contact_number
        // email_id
        // supplier_address

        String sql = """
                INSERT INTO Supplier
                (
                    supplier_name,
                    contact_number,
                    email_id,
                    supplier_address
                )
                VALUES (?, ?, ?, ?)
                RETURNING supplier_id
                """;

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(
                    1,
                    name);

            preparedStatement.setString(
                    2,
                    contact);

            preparedStatement.setString(
                    3,
                    mail);

            preparedStatement.setString(
                    4,
                    address);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int supplierId = resultSet.getInt("supplier_id");

                System.out.println();
                System.out.println(
                        "Supplier created successfully.");

                System.out.println(
                        "Supplier ID: "
                                + supplierId);

                return supplierId;
            }

        } catch (SQLException e) {

            System.out.println();
            System.out.println(
                    "Unable to create supplier.");

            e.printStackTrace();
        }

        return -1;
    }

    // =========================================================
    // VIEW PURCHASE
    // =========================================================

    public void getPurchaseById(int purchaseId) {

        String sql = """
                SELECT
                    p.purchase_id,
                    p.supplier_id,
                    s.supplier_name,
                    s.contact_number,
                    s.email_id,
                    s.supplier_address,
                    p.purchase_date,
                    p.invoice_number,
                    p.payment_method,
                    p.total_payment_amount
                FROM Purchase p
                INNER JOIN Supplier s
                    ON p.supplier_id = s.supplier_id
                WHERE p.purchase_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(
                    1,
                    purchaseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println();
                System.out.println("==========================================");
                System.out.println("             PURCHASE DETAILS");
                System.out.println("==========================================");

                System.out.println(
                        "Purchase ID       : "
                                + resultSet.getInt(
                                        "purchase_id"));

                System.out.println(
                        "Supplier ID       : "
                                + resultSet.getInt(
                                        "supplier_id"));

                System.out.println(
                        "Supplier Name     : "
                                + resultSet.getString(
                                        "supplier_name"));

                System.out.println(
                        "Contact Number    : "
                                + resultSet.getString(
                                        "contact_number"));

                System.out.println(
                        "Supplier Mail     : "
                                + resultSet.getString(
                                        "email_id"));

                System.out.println(
                        "Supplier Address  : "
                                + resultSet.getString(
                                        "supplier_address"));

                System.out.println(
                        "Purchase Date     : "
                                + resultSet.getDate(
                                        "purchase_date"));

                System.out.println(
                        "Invoice Number    : "
                                + resultSet.getString(
                                        "invoice_number"));

                System.out.println(
                        "Payment Method    : "
                                + resultSet.getString(
                                        "payment_method"));

                System.out.printf(
                        "Total Amount      : Rs. %.2f%n",
                        resultSet.getDouble(
                                "total_payment_amount"));

                System.out.println(
                        "==========================================");

            } else {

                System.out.println(
                        "Purchase not found.");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // =========================================================
    // INPUT METHODS
    // =========================================================

    private String readString(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {

                return input;
            }

            System.out.println(
                    "Input cannot be empty.");
        }
    }

    private int readInt(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. "
                                + "Please enter a valid integer.");
            }
        }
    }

    private int readPositiveInt(
            Scanner scanner,
            String message) {

        while (true) {

            int value = readInt(scanner, message);

            if (value > 0) {

                return value;
            }

            System.out.println(
                    "Please enter a number greater than 0.");
        }
    }

    private double readDouble(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {

                return Double.parseDouble(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. "
                                + "Please enter a valid number.");
            }
        }
    }

    private double readNonNegativeDouble(
            Scanner scanner,
            String message) {

        while (true) {

            double value = readDouble(scanner, message);

            if (value >= 0) {

                return value;
            }

            System.out.println(
                    "Please enter 0 or a positive number.");
        }
    }

    // =========================================================
    // VARIANT DETAILS CLASS
    // =========================================================

    private static class VariantDetails {

        private final int productId;
        private final String productName;
        private final String productBrand;
        private final String productCategory;
        private final int variantId;
        private final String size;
        private final String colour;
        private final int currentStock;

        private VariantDetails(
                int productId,
                String productName,
                String productBrand,
                String productCategory,
                int variantId,
                String size,
                String colour,
                int currentStock) {

            this.productId = productId;
            this.productName = productName;
            this.productBrand = productBrand;
            this.productCategory = productCategory;
            this.variantId = variantId;
            this.size = size;
            this.colour = colour;
            this.currentStock = currentStock;
        }
    }

    // =========================================================
    // PURCHASE ITEM DATA CLASS
    // =========================================================

    private static class PurchaseItemData {

        private final int variantId;
        private final int quantity;
        private final double costPrice;

        private PurchaseItemData(
                int variantId,
                int quantity,
                double costPrice) {

            this.variantId = variantId;
            this.quantity = quantity;
            this.costPrice = costPrice;
        }

        private double getItemTotal() {

            return quantity * costPrice;
        }
    }
}