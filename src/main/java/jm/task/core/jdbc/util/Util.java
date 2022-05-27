package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static Util util;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    private Util() {

    }

    public static synchronized Util getUtil() {
        if (util == null) {
            util = new Util();
        }
        return util;
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            connection.setAutoCommit(false);
            if (!connection.isClosed()) {
                System.out.println("EST' PROBITIE!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("CONNECTION FAILED...");
        }
        return connection;
    }
}