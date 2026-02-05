package gfs.gfs2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/vermietung?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "03082006";

    private static Connection connection = null;

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        try {

            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                System.out.println("Verbindet!");
            }
        } catch (SQLException e) {
            System.err.println("   URL: " + URL);
            System.err.println("   User: " + USER);
            System.err.println("   Fehler: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return connection;
    }

    /**
     * Закрыть подключение к базе данных
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Verbindung geschlossen.");
            } catch (SQLException e) {
                System.err.println("Fehler!");
                e.printStackTrace();
            }
        } else {
            System.out.println("Es gab kein Verbindung.");
        }
    }


}