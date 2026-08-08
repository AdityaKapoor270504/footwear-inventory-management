package com.aditya;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    
}
