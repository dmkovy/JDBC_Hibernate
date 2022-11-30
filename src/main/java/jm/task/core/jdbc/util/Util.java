package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/users_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection conn = null;

    private Util() {
    }

    public static Connection open() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
