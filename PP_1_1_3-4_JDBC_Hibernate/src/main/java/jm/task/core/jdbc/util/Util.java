package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

    private static Connection connection;
    private Util() {
    }
    public static Connection getConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/NewSchema1", "root", "StrongestPasswords");
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
