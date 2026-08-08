package com.aditya;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class CustomerManager {
    
    public void addCustomer (List <Customer> customers) {

        String sql = """
                     INSERT INTO Customer
                     (customer_id,
                     customer_name,
                     customer_contact_number)
                     VALUES (?, ?, ?)
                     """;
        
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

             for (Customer customer : customers) {

                preparedStatement.setInt (1, customer.getCustomerId());
                preparedStatement.setString (2, customer.getCustomerName());
                preparedStatement.setString (3, customer.getCustomerContactNumber());

                preparedStatement.addBatch();

            }

            int result[] = preparedStatement.executeBatch();
            
            System.out.println (result.length + " customers added successfully.");
        
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}
