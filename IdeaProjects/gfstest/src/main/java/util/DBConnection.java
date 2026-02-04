package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Параметры подключения к базе данных
    private static final String URL = "jdbc:mysql://localhost:3306/vermietung";
    private static final String USERNAME = "root";  // Ваш логин MySQL
    private static final String PASSWORD = "03082006";      // Ваш пароль MySQL

    private static Connection connection = null;

    // Приватный конструктор для Singleton паттерна
    private DBConnection() {}

    /**
     * Получить подключение к базе данных.
     * Если подключение не существует или закрыто - создаёт новое.
     *
     * @return Connection объект для работы с БД
     * @throws SQLException если произошла ошибка подключения
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Установка соединения
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        }
        return connection;
    }

    /**
     * Закрыть подключение к базе данных.
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
     * Проверка подключения к БД.
     *
     * @return true если подключение активно
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