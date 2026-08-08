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
                     (String supplier_name,
                      String contact_number,
                      String email_id,
                      String supplier_address)
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

            System.out.print (results.length + " suppliers added successfully.");

        
        } catch (SQLException e) {
            e.getStackTrace();
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
                        + resultSet.getString("supplier_contact_number"));

                System.out.println("Mail: "
                        + resultSet.getString("supplier_mail"));

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
