package com.aditya;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
public class Main {
    
    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

        int numberOfProducts;

        System.out.print ("Enter the number of products you wish to enter : ");

        List <Product> products = new ArrayList<>();

        numberOfProducts = sc.nextInt();

        sc.nextLine();

        for (int i = 1; i <= numberOfProducts; i++) {

            System.out.println ("\nEnter details for product " + i);

            System.out.print ("Enter product name : ");
            String productName = sc.nextLine();

            System.out.print ("Enter product brand : ");
            String productBrand = sc.nextLine();

            System.out.print ("Enter product category : ");
            String productCategory = sc.nextLine();

            System.out.print ("Enter gender : ");
            String gender = sc.nextLine();

            System.out.print ("Enter cost price : ");
            double costPrice = sc.nextDouble();

            System.out.print ("Enter selling price : ");
            double sellingPrice = sc.nextDouble();

            sc.nextLine();

            Product product = new Product(productName, productBrand, productCategory, gender, costPrice, sellingPrice);

            products.add (product);

        }

        ProductManager productManager = new ProductManager();

        productManager.addProducts(products);

        int viewproductId;

        System.out.print ("Enter the id of the product whose details you wish to view : ");
        viewproductId = sc.nextInt();

        productManager.getProductById (viewproductId);
        sc.nextLine();

        int updateProductId;
        double updatedSellingPrice;
        System.out.print ("Enter the id of the product whose selling price you wish to modify : ");
        updateProductId = sc.nextInt();
        
        System.out.print ("Enter the updated selling price : ");
        updatedSellingPrice = sc.nextDouble();

        sc.nextLine();

        productManager.updateProductPrice (updateProductId, updatedSellingPrice);

        System.out.print ("Enter number of product variants : ");

        int numberOfVariants = sc.nextInt();
        sc.nextLine();

        List <ProductVariant> productVariants = new ArrayList<>();

        for (int i = 1; i <= numberOfVariants; i++) {

            System.out.println("\nEnter details for variant " + i);

            System.out.print("Enter product ID: ");
            int productId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter size of product: ");
            String sizeOfProduct = sc.nextLine();

            System.out.print("Enter colour: ");
            String colour = sc.nextLine();

            ProductVariant variant = new ProductVariant(
                    productId,
                    sizeOfProduct,
                    colour
            );

            productVariants.add (variant);

        }

        ProductVariantManager productVariantManager = new ProductVariantManager();

        productVariantManager.addProductVariant(productVariants);

        int variantId;

        System.out.print("Enter the ID of the product variant whose details you wish to view: ");
        variantId = sc.nextInt();

        productVariantManager.getProductVariantById(variantId);

        int updatevariantId;
        String updatedSize;
        String updatedColour;

        System.out.print("Enter the ID of the product variant you wish to correct: ");
        updatevariantId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the corrected size: ");
        updatedSize = sc.nextLine();

        System.out.print("Enter the corrected colour: ");
        updatedColour = sc.nextLine();

        productVariantManager.updateProductVariant(
                updatevariantId,
                updatedSize,
                updatedColour
        );

        InventoryManager inventoryManager = new InventoryManager();

        List<Inventory> inventoryList = new ArrayList<>();

        System.out.print("Enter number of inventory records: ");
        int numberOfRecords = sc.nextInt();

        for (int i = 1; i <= numberOfRecords; i++) {

            System.out.println("\nEnter details for inventory " + i);

            System.out.print("Enter variant ID: ");
            variantId = sc.nextInt();

            System.out.print("Enter quantity in stock: ");
            int quantityInStock = sc.nextInt();

            Inventory inventory = new Inventory(
                    variantId,
                    quantityInStock
            );

            inventoryList.add(inventory);
        }

        inventoryManager.addInventory(inventoryList);

        System.out.print("Enter the variant ID whose inventory you wish to view: ");
        variantId = sc.nextInt();

        inventoryManager.viewInventory(variantId);

        List<Customer> customerList = new ArrayList<>();

        System.out.print("Enter number of customer records: ");
        int numberOfCustomers = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= numberOfCustomers; i++) {

            System.out.println("\nEnter details for customer " + i);

            System.out.print("Enter customer name: ");
            String customerName = sc.nextLine();

            System.out.print("Enter the customer's contact number: ");
            String contactNumber = sc.nextLine();

            Customer customer = new Customer(
                    customerName,
                    contactNumber
            );

            customerList.add(customer);
        }

        CustomerManager customerManager = new CustomerManager();

        customerManager.addCustomer(customerList);

        int customerId;

        System.out.print("Enter the ID of the customer whose details you wish to view: ");
        customerId = sc.nextInt();

        customerManager.getCustomerById(customerId);

        List<Supplier> supplierList = new ArrayList<>();

        System.out.print("Enter number of supplier records: ");
        int numberOfSuppliers = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= numberOfSuppliers; i++) {

            System.out.println("\nEnter details for supplier " + i);

            System.out.print("Enter supplier name: ");
            String supplierName = sc.nextLine();

            System.out.print("Enter supplier contact number: ");
            String contactNumber = sc.nextLine();

            System.out.print("Enter supplier mail: ");
            String supplierMail = sc.nextLine();

            System.out.print("Enter supplier address: ");
            String supplierAddress = sc.nextLine();

            Supplier supplier = new Supplier(
                    supplierName,
                    contactNumber,
                    supplierMail,
                    supplierAddress
            );

            supplierList.add(supplier);
        }

        SupplierManager supplierManager = new SupplierManager();

        supplierManager.addSupplier(supplierList);

        System.out.print("Enter supplier ID: ");
        int supplierId = sc.nextInt();

        supplierManager.getSupplierById(supplierId);

        List<Purchase> purchaseList = new ArrayList<>();

        System.out.print("Enter number of purchase records: ");
        int numberOfPurchases = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= numberOfPurchases; i++) {

            System.out.println("\nEnter details for purchase " + i);

            System.out.print("Enter supplier ID: ");
            supplierId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter invoice number: ");
            String invoiceNumber = sc.nextLine();

            System.out.print("Enter payment method: ");
            String paymentMethod = sc.nextLine();

            System.out.print("Enter total payment amount: ");
            double totalPaymentAmount = sc.nextDouble();
            sc.nextLine();

            Purchase purchase = new Purchase(
                    supplierId,
                    invoiceNumber,
                    paymentMethod,
                    totalPaymentAmount
            );

            purchaseList.add(purchase);
        }

        PurchaseManager purchaseManager = new PurchaseManager();

        purchaseManager.addPurchase(purchaseList);

        System.out.print("Enter the ID of the purchase whose details you wish to view: ");
        int purchaseId = sc.nextInt();

        purchaseManager.getPurchaseById(purchaseId);

        List<PurchaseItem> purchaseItemList = new ArrayList<>();

        System.out.print("Enter number of purchase item records: ");
        int numberOfPurchaseItems = sc.nextInt();

        for (int i = 1; i <= numberOfPurchaseItems; i++) {

            System.out.println("\nEnter details for purchase item " + i);

            System.out.print("Enter purchase ID: ");
            purchaseId = sc.nextInt();

            System.out.print("Enter quantity: ");
            int quantity = sc.nextInt();

            System.out.print("Enter variant ID: ");
            variantId = sc.nextInt();

            System.out.print("Enter cost price: ");
            double costPrice = sc.nextDouble();

            PurchaseItem purchaseItem = new PurchaseItem(
                purchaseId,
                quantity,
                variantId,
                costPrice
            );

            purchaseItemList.add(purchaseItem);
        }

        PurchaseItemManager purchaseItemManager = new PurchaseItemManager();

        purchaseItemManager.addPurchaseItem(purchaseItemList);

        System.out.print("Enter the ID of the purchase item whose details you wish to view: ");
        int purchaseItemId = sc.nextInt();

        purchaseItemManager.getPurchaseItemById(purchaseItemId);

        List<Sale> saleList = new ArrayList<>();

        System.out.print("Enter number of sale records: ");
        int numberOfSales = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= numberOfSales; i++) {

            System.out.println("\nEnter details for sale " + i);

            System.out.print("Enter customer ID: ");
            customerId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter payment method: ");
            String paymentMethod = sc.nextLine();

            System.out.print("Enter discount offered: ");
            double discountOffered = sc.nextDouble();

            System.out.print("Enter total net amount: ");
            double totalNetAmount = sc.nextDouble();

            sc.nextLine();

            Sale sale = new Sale(
                        customerId,
                        paymentMethod,
                        discountOffered,
                        totalNetAmount
            );

            saleList.add(sale);
        }

        SaleManager saleManager = new SaleManager();

        saleManager.addSale(saleList);

        System.out.print("Enter the ID of the sale whose details you wish to view: ");
        int saleId = sc.nextInt();

        saleManager.getSaleById(saleId);
        
        sc.close();

        try (Connection connection = DatabaseConnection.connect()) {

            if (connection != null) {
                System.out.println("Database is ready to use.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

