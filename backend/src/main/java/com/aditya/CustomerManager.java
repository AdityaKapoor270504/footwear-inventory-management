package com.aditya;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CustomerManager {

    public void addCustomer(List<Customer> customers) {

        String sql = """
                     INSERT INTO Customer
                     (customer_name,
                      customer_contact_number)
                     VALUES (?, ?)
                     """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Customer customer : customers) {

                preparedStatement.setString(
                        1,
                        customer.getCustomerName()
                );

                preparedStatement.setString(
                        2,
                        customer.getCustomerContactNumber()
                );

                preparedStatement.addBatch();
            }

            int result[] = preparedStatement.executeBatch();

            System.out.println(
                    result.length + " customers added successfully."
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCustomerById(int customerId) {

        String sql = """
                     SELECT customer_id,
                            customer_name,
                            customer_contact_number
                     FROM Customer
                     WHERE customer_id = ?
                     """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, customerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("\nCustomer Details");
                System.out.println("----------------");

                System.out.println("Customer ID      : "
                        + resultSet.getInt("customer_id"));

                System.out.println("Customer Name    : "
                        + resultSet.getString("customer_name"));

                System.out.println("Contact Number   : "
                        + resultSet.getString("customer_contact_number"));

            } else {

                System.out.println(
                        "No customer found with ID: " + customerId
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}