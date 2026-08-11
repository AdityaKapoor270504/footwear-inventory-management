package com.aditya;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SupplierManager {

    public void addSupplier (List <Supplier> suppliers) {

        String sql = """
                     INSERT INTO Supplier
                     (supplier_name,
                      contact_number,
                      email_id,
                      supplier_address)
                      VALUES (?, ?, ?, ?)
                     """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Supplier supplier : suppliers) {

                preparedStatement.setString (1, supplier.getSupplierName());
                preparedStatement.setString (2, supplier.getSupplierContactNumber());
                preparedStatement.setString (3, supplier.getSupplierMail());
                preparedStatement.setString (4, supplier.getSupplierAddress());

                preparedStatement.addBatch();

            }
            
            int results[] = preparedStatement.executeBatch();

            System.out.println (results.length + " suppliers added successfully.");

        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void getSupplierById(int supplierId) {

        String sql = """
                SELECT *
                FROM Supplier
                WHERE supplier_id = ?
                """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, supplierId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("Supplier ID: "
                        + resultSet.getInt("supplier_id"));

                System.out.println("Supplier Name: "
                        + resultSet.getString("supplier_name"));

                System.out.println("Contact Number: "
                        + resultSet.getString("contact_number"));

                System.out.println("Mail: "
                        + resultSet.getString("email_id"));

                System.out.println("Address: "
                        + resultSet.getString("supplier_address"));

            } else {
                System.out.println("Supplier not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
