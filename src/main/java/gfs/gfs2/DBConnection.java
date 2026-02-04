package gfs.gfs2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Параметры подключения к базе данных
    private static final String URL = "jdbc:mysql://localhost:3306/vermietung?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "03082006";

    // Единственный экземпляр подключения (Singleton)
    private static Connection connection = null;

    // Приватный конструктор для Singleton
    private DBConnection() {}

    /**
     * Получить подключение к базе данных
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Проверяем, существует ли подключение и не закрыто ли оно
            if (connection == null || connection.isClosed()) {
                System.out.println("🔌 Попытка подключения к БД...");
                System.out.println("   URL: " + URL);
                System.out.println("   User: " + USER);

                // Создаём новое подключение
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                System.out.println("✅ Подключение к БД успешно установлено!");
            }
        } catch (SQLException e) {
            System.err.println("❌ ОШИБКА ПОДКЛЮЧЕНИЯ К БД!");
            System.err.println("   URL: " + URL);
            System.err.println("   User: " + USER);
            System.err.println("   Ошибка: " + e.getMessage());
            System.err.println("\n⚠️ ПРОВЕРЬТЕ:");
            System.err.println("   1. MySQL Server запущен?");
            System.err.println("   2. База данных 'vermietung' существует?");
            System.err.println("   3. Пароль '03082006' правильный?");
            System.err.println("   4. Пользователь 'root' имеет доступ?");
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
                System.out.println("🔌 Подключение к БД закрыто.");
            } catch (SQLException e) {
                System.err.println("❌ Ошибка при закрытии подключения!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ℹ️ Подключение уже закрыто или не было установлено.");
        }
    }

    /**
     * Проверить подключение к базе данных
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();

            // Проверяем, что подключение живо
            boolean isValid = conn != null && !conn.isClosed() && conn.isValid(2);

            if (isValid) {
                System.out.println("✅ Тест подключения к БД пройден!");

                // Дополнительная информация о БД
                System.out.println("   Каталог: " + conn.getCatalog());
                System.out.println("   Автокоммит: " + conn.getAutoCommit());
            } else {
                System.err.println("❌ Тест подключения к БД не пройден!");
            }

            return isValid;

        } catch (SQLException e) {
            System.err.println("❌ Тест подключения к БД не пройден!");
            System.err.println("   Причина: " + e.getMessage());
            return false;
        }
    }
}