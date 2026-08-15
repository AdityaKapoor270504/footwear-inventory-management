package com.aditya;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerManager {

    // =========================================================
    // ADD MULTIPLE CUSTOMERS
    // =========================================================

    public void addCustomer(List<Customer> customers) {

        String sql = """
                INSERT INTO Customer
                (customer_name,
                 customer_contact_number)
                VALUES (?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Customer customer : customers) {

                preparedStatement.setString(
                        1,
                        customer.getCustomerName());

                preparedStatement.setString(
                        2,
                        customer.getCustomerContactNumber());

                preparedStatement.addBatch();
            }

            int[] results = preparedStatement.executeBatch();

            System.out.println(
                    results.length + " customers added successfully.");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // =========================================================
    // ADD ONE CUSTOMER AND RETURN GENERATED CUSTOMER ID
    // =========================================================

    public int addCustomer(Customer customer) {

        String sql = """
                INSERT INTO Customer
                (customer_name,
                 customer_contact_number)
                VALUES (?, ?)
                RETURNING customer_id
                """;

        try (
                Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(
                    1,
                    customer.getCustomerName());

            preparedStatement.setString(
                    2,
                    customer.getCustomerContactNumber());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int customerId = resultSet.getInt("customer_id");

                System.out.println(
                        "Customer added successfully.");

                System.out.println(
                        "Customer ID: " + customerId);

                return customerId;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return -1;
    }

    // =========================================================
    // GET CUSTOMER BY ID
    // =========================================================

    public Customer getCustomerById(int customerId) {

        String sql = """
                SELECT customer_id,
                       customer_name,
                       customer_contact_number
                FROM Customer
                WHERE customer_id = ?
                """;

        try (
                Connection connection = DatabaseConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(
                    1,
                    customerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int id = resultSet.getInt("customer_id");

                String name = resultSet.getString("customer_name");

                String contact = resultSet.getString(
                        "customer_contact_number");

                return new Customer(
                        id,
                        name,
                        contact);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    // =========================================================
    // DISPLAY CUSTOMER BY ID
    // =========================================================

    public void viewCustomer(int customerId) {

        Customer customer = getCustomerById(customerId);

        if (customer == null) {

            System.out.println(
                    "Customer not found.");

            return;
        }

        System.out.println();
        System.out.println(
                "==========================================");

        System.out.println(
                "             CUSTOMER DETAILS");

        System.out.println(
                "==========================================");

        System.out.println(
                "Customer ID      : "
                        + customer.getCustomerId());

        System.out.println(
                "Customer Name    : "
                        + customer.getCustomerName());

        System.out.println(
                "Contact Number   : "
                        + customer.getCustomerContactNumber());

        System.out.println(
                "==========================================");
    }
}