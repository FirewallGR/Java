package ru.meowmure.javacreditest.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SessionController {
    private static Connection connection;
    private static final String USERNAME = "meowmure";
    private static final String PASSWORD = "Machina22!";
    private static final String URL = "jdbc:mysql://localhost:3306/clockschema?serverTimezone=Europe/Moscow";

    private SessionController() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return connection;
    }

}
