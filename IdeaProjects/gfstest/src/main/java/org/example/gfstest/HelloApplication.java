package org.example.gfstest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DBConnection;

import java.io.IOException;

/**
 * Главный класс для запуска JavaFX приложения.
 * Загружает FXML файл и инициализирует UI.
 */
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        if (!DBConnection.testConnection()) {
            System.err.println("Verbindung fehlt");
            System.err.println("Datenbank im util/DBConnection.java");
        } else {
            System.out.println("Datenbank +");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("/ui/hello-view.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 1400, 750);
        stage.setTitle("jdbc crud operations GFS");
        stage.setScene(scene);
        stage.setMinWidth(1200);
        stage.setMinHeight(700);

        // wir schliessen die verbindung
        stage.setOnCloseRequest(event -> {
            DBConnection.closeConnection();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}