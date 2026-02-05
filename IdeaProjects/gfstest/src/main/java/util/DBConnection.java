package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Datenbank-Verbindungsparameter
    private static final String URL = "jdbc:mysql://localhost:3306/vermietung";
    private static final String USERNAME = "root";  // Ihr MySQL-Benutzername
    private static final String PASSWORD = "03082006";      // Ihr MySQL-Passwort

    private static Connection connection = null;

    // Privater Konstruktor für Singleton-Muster
    private DBConnection() {}

    /**
     * Datenbankverbindung abrufen.
     * Wenn die Verbindung nicht existiert oder geschlossen ist, wird eine neue erstellt.
     *
     * @return Connection-Objekt für die Arbeit mit der Datenbank
     * @throws SQLException wenn ein Verbindungsfehler auftritt
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Verbindung herstellen
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        }
        return connection;
    }

    /**
     * Datenbankverbindung schließen.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Überprüfung der Datenbankverbindung.
     *
     * @return true wenn die Verbindung aktiv ist
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}