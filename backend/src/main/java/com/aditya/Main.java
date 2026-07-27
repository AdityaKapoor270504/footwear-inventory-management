package com.aditya;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    
    public static void main(String[] args) {


        DatabaseConnection.connect();
        try (Connection connection = DatabaseConnection.connect()) {

            if (connection != null) {
                System.out.println("Database is ready to use.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

