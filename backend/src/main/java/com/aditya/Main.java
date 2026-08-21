package com.aditya;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // =========================================================
    // MANAGERS
    // =========================================================

    private static final ProductManager productManager = new ProductManager();
    private static final ProductVariantManager productVariantManager = new ProductVariantManager();
    private static final InventoryManager inventoryManager = new InventoryManager();
    private static final CustomerManager customerManager = new CustomerManager();
    private static final PurchaseManager purchaseManager = new PurchaseManager();
    private static final SaleManager saleManager = new SaleManager();

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            printMainMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    productMenu();
                    break;

                case 2:
                    productVariantMenu();
                    break;

                case 3:
                    inventoryMenu();
                    break;

                case 4:
                    purchaseMenu();
                    break;

                case 5:
                    saleMenu();
                    break;

                case 0:
                    running = false;

                    System.out.println();
                    System.out.println("==========================================");
                    System.out.println("Thank you for using the system.");
                    System.out.println("==========================================");
                    break;

                default:
                    System.out.println();
                    System.out.println("Invalid choice.");
                    System.out.println("Please enter a number between 0 and 5.");
            }
        }

        scanner.close();
    }

    // =========================================================
    // MAIN MENU
    // =========================================================

    private static void printMainMenu() {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("       INVENTORY MANAGEMENT SYSTEM");
        System.out.println("==========================================");
        System.out.println("1. Products");
        System.out.println("2. Product Variants");
        System.out.println("3. Inventory");
        System.out.println("4. Purchases");
        System.out.println("5. Sales");
        System.out.println("0. Exit");
        System.out.println("==========================================");
    }

    // =========================================================
    // PRODUCT MENU
    // =========================================================

    private static void productMenu() {

        boolean back = false;

        while (!back) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("              PRODUCT MENU");
            System.out.println("==========================================");
            System.out.println("1. Add Product");
            System.out.println("2. View Product");
            System.out.println("3. Update Selling Price");
            System.out.println("4. Delete Product");
            System.out.println("0. Back");
            System.out.println("==========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addProducts();
                    break;

                case 2:
                    viewProduct();
                    break;

                case 3:
                    updateProductPrice();
                    break;

                case 4:
                    deleteProduct();
                    break;

                case 0:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addProducts() {

        int number = readNonNegativeInt(
                "Enter the number of products you wish to enter: ");

        if (number == 0) {

            System.out.println("No products added.");
            return;
        }

        List<Product> products = new ArrayList<>();

        for (int i = 0; i < number; i++) {

            System.out.println();
            System.out.println("Enter details for product " + (i + 1));

            String productName = readString(
                    "Enter product name: ");

            String productBrand = readString(
                    "Enter product brand: ");

            String productCategory = readString(
                    "Enter product category: ");

            String gender = readString(
                    "Enter gender (M/F): ")
                    .toUpperCase();

            while (!gender.equals("M") && !gender.equals("F")) {

                System.out.println("Gender must be M or F.");

                gender = readString(
                        "Enter gender (M/F): ")
                        .toUpperCase();
            }

            double costPrice = readNonNegativeDouble(
                    "Enter cost price: ");

            double sellingPrice = readNonNegativeDouble(
                    "Enter selling price: ");

            Product product = new Product(
                    productName,
                    productBrand,
                    productCategory,
                    gender,
                    costPrice,
                    sellingPrice);

            products.add(product);
        }

        productManager.addProducts(products);
    }

    private static void viewProduct() {

        int productId = readPositiveInt(
                "Enter the product ID: ");

        productManager.getProductById(productId);
    }

    private static void updateProductPrice() {

        int productId = readPositiveInt(
                "Enter the product ID whose selling price "
                        + "you wish to modify: ");

        double newPrice = readNonNegativeDouble(
                "Enter the updated selling price: ");

        productManager.updateProductPrice(
                productId,
                newPrice);
    }

    private static void deleteProduct() {

        int productId = readPositiveInt(
                "Enter the product ID you wish to delete: ");

        System.out.println();
        System.out.println(
                "WARNING: Deleting a product may fail if");
        System.out.println(
                "product variants still reference it.");

        String confirmation = readString(
                "Are you sure? (Y/N): ")
                .toUpperCase();

        if (confirmation.equals("Y")) {

            productManager.deleteProduct(productId);

        } else {

            System.out.println("Deletion cancelled.");
        }
    }

    // =========================================================
    // PRODUCT VARIANT MENU
    // =========================================================

    private static void productVariantMenu() {

        boolean back = false;

        while (!back) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("          PRODUCT VARIANT MENU");
            System.out.println("==========================================");
            System.out.println("1. Add Product Variant");
            System.out.println("2. View Product Variant");
            System.out.println("3. Update Product Variant");
            System.out.println("0. Back");
            System.out.println("==========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addProductVariants();
                    break;

                case 2:
                    viewProductVariant();
                    break;

                case 3:
                    updateProductVariant();
                    break;

                case 0:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addProductVariants() {

        int number = readNonNegativeInt(
                "Enter the number of product variants: ");

        if (number == 0) {

            System.out.println(
                    "No product variants added.");

            return;
        }

        List<ProductVariant> variants = new ArrayList<>();

        for (int i = 0; i < number; i++) {

            System.out.println();
            System.out.println(
                    "Enter details for product variant "
                            + (i + 1));

            int productId = readPositiveInt(
                    "Enter product ID: ");

            String size = readString(
                    "Enter size: ");

            String colour = readString(
                    "Enter colour: ");

            ProductVariant variant = new ProductVariant(
                    productId,
                    size,
                    colour);

            variants.add(variant);
        }

        productVariantManager.addProductVariant(variants);
    }

    private static void viewProductVariant() {

        int variantId = readPositiveInt(
                "Enter the product variant ID: ");

        productVariantManager.getProductVariantById(
                variantId);
    }

    private static void updateProductVariant() {

        int variantId = readPositiveInt(
                "Enter the product variant ID "
                        + "you wish to correct: ");

        String size = readString(
                "Enter the corrected size: ");

        String colour = readString(
                "Enter the corrected colour: ");

        productVariantManager.updateProductVariant(
                variantId,
                size,
                colour);
    }

    // =========================================================
    // INVENTORY MENU
    // =========================================================

    private static void inventoryMenu() {

        boolean back = false;

        while (!back) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("             INVENTORY MENU");
            System.out.println("==========================================");
            System.out.println("1. Add Inventory");
            System.out.println("2. View Inventory");
            System.out.println("0. Back");
            System.out.println("==========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addInventory();
                    break;

                case 2:
                    viewInventory();
                    break;

                case 0:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addInventory() {

        int number = readNonNegativeInt(
                "Enter the number of inventory records: ");

        if (number == 0) {

            System.out.println(
                    "No inventory records added.");

            return;
        }

        List<Inventory> inventoryList = new ArrayList<>();

        for (int i = 0; i < number; i++) {

            System.out.println();
            System.out.println(
                    "Enter details for inventory record "
                            + (i + 1));

            int variantId = readPositiveInt(
                    "Enter variant ID: ");

            int quantity = readNonNegativeInt(
                    "Enter quantity in stock: ");

            Inventory inventory = new Inventory(
                    variantId,
                    quantity);

            inventoryList.add(inventory);
        }

        inventoryManager.addInventory(inventoryList);
    }

    private static void viewInventory() {

        int productId = readPositiveInt(
                "Enter the product ID whose inventory "
                        + "you wish to view: ");

        inventoryManager.viewInventory(productId);
    }

    // =========================================================
    // PURCHASE MENU
    // =========================================================

    private static void purchaseMenu() {

        boolean back = false;

        while (!back) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("             PURCHASE MENU");
            System.out.println("==========================================");
            System.out.println("1. Create Purchase");
            System.out.println("2. View Purchase");
            System.out.println("0. Back");
            System.out.println("==========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    createPurchase();
                    break;

                case 2:
                    viewPurchase();
                    break;

                case 0:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =========================================================
    // CREATE PURCHASE
    // =========================================================

    private static void createPurchase() {

        purchaseManager.createPurchase(scanner);
    }

    // =========================================================
    // VIEW PURCHASE
    // =========================================================

    private static void viewPurchase() {

        int purchaseId = readPositiveInt(
                "Enter the ID of the purchase "
                        + "whose details you wish to view: ");

        purchaseManager.getPurchaseById(purchaseId);
    }

    // =========================================================
    // SALE MENU
    // =========================================================

    private static void saleMenu() {

        boolean back = false;

        while (!back) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("                SALE MENU");
            System.out.println("==========================================");
            System.out.println("1. Create Sale");
            System.out.println("2. View Sale");
            System.out.println("0. Back");
            System.out.println("==========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    createSale();
                    break;

                case 2:
                    viewSale();
                    break;

                case 0:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =========================================================
    // CREATE SALE
    // =========================================================

    private static void createSale() {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("              CREATE SALE");
        System.out.println("==========================================");

        // -----------------------------------------------------
        // STEP 1: SELECT CUSTOMER
        // -----------------------------------------------------

        int customerId = selectCustomer();

        if (customerId == -1) {

            System.out.println("Sale cancelled.");
            return;
        }

        // -----------------------------------------------------
        // STEP 2: PAYMENT METHOD
        // -----------------------------------------------------

        String paymentMethod = readString(
                "Enter payment method: ");

        // -----------------------------------------------------
        // STEP 3: SALE ITEMS
        // -----------------------------------------------------

        int numberOfItems = readPositiveInt(
                "Enter the number of different sale items: ");

        List<SaleItem> saleItems = new ArrayList<>();

        double grossAmount = 0;

        for (int i = 0; i < numberOfItems; i++) {

            System.out.println();
            System.out.println("------------------------------------------");
            System.out.println(
                    "Enter details for sale item " + (i + 1));
            System.out.println("------------------------------------------");

            int variantId = readPositiveInt(
                    "Enter variant ID: ");

            int quantity = readPositiveInt(
                    "Enter quantity sold: ");

            boolean canSell = inventoryManager.checkSaleAvailability(
                    variantId,
                    quantity);

            if (!canSell) {

                System.out.println(
                        "Insufficient stock for this variant.");

                i--;
                continue;
            }

            double sellingPrice = readNonNegativeDouble(
                    "Enter selling price: ");

            double itemTotal = sellingPrice * quantity;

            grossAmount += itemTotal;

            SaleItem saleItem = new SaleItem(
                    0,
                    quantity,
                    variantId,
                    sellingPrice);

            saleItems.add(saleItem);

            System.out.printf(
                    "Item total: ₹%.2f%n",
                    itemTotal);
        }

        // -----------------------------------------------------
        // STEP 4: DISCOUNT
        // -----------------------------------------------------

        System.out.println();
        System.out.println("------------------------------------------");

        System.out.printf(
                "Gross Amount: ₹%.2f%n",
                grossAmount);

        double discountPercentage = readDiscountPercentage(
                "Enter discount percentage: ");

        double discountAmount = grossAmount
                * discountPercentage
                / 100.0;

        double totalNetAmount = grossAmount - discountAmount;

        // -----------------------------------------------------
        // STEP 5: SALE SUMMARY
        // -----------------------------------------------------

        System.out.println();
        System.out.println("==========================================");
        System.out.println("              SALE SUMMARY");
        System.out.println("==========================================");

        System.out.println(
                "Customer ID       : " + customerId);

        System.out.println(
                "Payment Method    : " + paymentMethod);

        System.out.printf(
                "Gross Amount      : ₹%.2f%n",
                grossAmount);

        System.out.printf(
                "Discount          : %.2f%%%n",
                discountPercentage);

        System.out.printf(
                "Discount Amount   : ₹%.2f%n",
                discountAmount);

        System.out.printf(
                "Net Amount        : ₹%.2f%n",
                totalNetAmount);

        System.out.println(
                "==========================================");

        // -----------------------------------------------------
        // STEP 6: CONFIRM SALE
        // -----------------------------------------------------

        String confirmation = readString(
                "Confirm sale? (Y/N): ")
                .toUpperCase();

        if (!confirmation.equals("Y")) {

            System.out.println("Sale cancelled.");
            return;
        }

        // -----------------------------------------------------
        // STEP 7: CREATE SALE
        // -----------------------------------------------------

        Sale sale = new Sale(
                customerId,
                paymentMethod,
                discountPercentage,
                totalNetAmount);

        int saleId = saleManager.createSale(
                sale,
                saleItems);

        if (saleId == -1) {

            System.out.println(
                    "Sale could not be created.");

            return;
        }

        System.out.println(
                "Sale ID: " + saleId);

        System.out.println(
                "Sale created successfully.");
    }

    // =========================================================
    // SELECT CUSTOMER
    // =========================================================

    private static int selectCustomer() {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("             SELECT CUSTOMER");
        System.out.println("==========================================");
        System.out.println("1. Use Existing Customer");
        System.out.println("2. Create New Customer");
        System.out.println("0. Cancel Sale");
        System.out.println("==========================================");

        int choice = readInt("Enter your choice: ");

        switch (choice) {

            case 1:

                return readPositiveInt(
                        "Enter existing customer ID: ");

            case 2:

                System.out.println();
                System.out.println("------------------------------------------");
                System.out.println("           CREATE NEW CUSTOMER");
                System.out.println("------------------------------------------");

                String name = readString(
                        "Enter customer name: ");

                String contact = readString(
                        "Enter customer contact number: ");

                Customer customer = new Customer(
                        name,
                        contact);

                List<Customer> customers = new ArrayList<>();

                customers.add(customer);

                customerManager.addCustomer(
                        customers);

                System.out.println();
                System.out.println(
                        "Customer created successfully.");

                System.out.println(
                        "Enter the generated customer ID.");

                return readPositiveInt(
                        "Enter new customer ID: ");

            case 0:

                return -1;

            default:

                System.out.println(
                        "Invalid choice.");

                return selectCustomer();
        }
    }

    // =========================================================
    // VIEW SALE
    // =========================================================

    private static void viewSale() {

        int saleId = readPositiveInt(
                "Enter the ID of the sale whose details "
                        + "you wish to view: ");

        saleManager.getSaleById(saleId);
    }

    // =========================================================
    // INPUT METHODS
    // =========================================================

    private static String readString(
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

    private static int readInt(
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

    private static int readPositiveInt(
            String message) {

        while (true) {

            int value = readInt(message);

            if (value > 0) {

                return value;
            }

            System.out.println(
                    "Please enter a number greater than 0.");
        }
    }

    private static int readNonNegativeInt(
            String message) {

        while (true) {

            int value = readInt(message);

            if (value >= 0) {

                return value;
            }

            System.out.println(
                    "Please enter 0 or a positive number.");
        }
    }

    private static double readDouble(
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

    private static double readNonNegativeDouble(
            String message) {

        while (true) {

            double value = readDouble(message);

            if (value >= 0) {

                return value;
            }

            System.out.println(
                    "Please enter 0 or a positive number.");
        }
    }

    private static double readDiscountPercentage(
            String message) {

        while (true) {

            double value = readDouble(message);

            if (value >= 0 && value <= 100) {

                return value;
            }

            System.out.println(
                    "Discount percentage must be between 0 and 100.");
        }
    }
}