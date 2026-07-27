package com.aditya;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    
    public static final String URL = 
            "jdbc:postgresql://localhost:5432/footwear_inventory_management";
    
    public static final String USERNAME = "postgres";

    public static final String password = "Aditya2004";

    public static Connection connect() {

        try {
            
            Connection connection = DriverManager.getConnection (URL, USERNAME, password);

            System.out.println ("Connection was successful");

            return connection;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
