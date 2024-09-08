package com.bibliotheque.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bibliotheque", "postgres", "chaabat");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver PostgreSQL non trouvé.", e);
            }
        }
        return connection;
    }
}
