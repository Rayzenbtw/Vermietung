module org.example.gfstest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Открываем ВСЕ пакеты для JavaFX и рефлексии
    opens org.example.gfstest to javafx.fxml, javafx.graphics;
    opens ui to javafx.fxml, javafx.graphics;
    opens model to javafx.fxml, javafx.graphics, javafx.base;
    opens dao to javafx.fxml;
    opens util to javafx.fxml;

    exports org.example.gfstest;
}