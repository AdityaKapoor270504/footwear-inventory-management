package com.aditya;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    
    public static final String URL = 
            "jdbc:postgresql://localhost:5432/footwear_inventory_management";
    
    public static final String USERNAME = "postgres";

    public static final String PASSWORD = "Aditya2004";

    public static Connection connect() throws SQLException {

        return DriverManager.getConnection (URL, USERNAME, PASSWORD);
    }
}
