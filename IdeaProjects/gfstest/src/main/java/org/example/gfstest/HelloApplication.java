package org.example.gfstest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DBConnection;

import java.io.IOException;

/**
 * Hauptklasse zum Starten der JavaFX-Anwendung.
 * Lädt die FXML-Datei und initialisiert die Benutzeroberfläche.
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

        // Wir schließen die Verbindung
        stage.setOnCloseRequest(event -> {
            DBConnection.closeConnection();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}