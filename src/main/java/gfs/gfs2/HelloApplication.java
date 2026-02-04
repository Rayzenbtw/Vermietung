package gfs.gfs2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("🚀 Anwendung startet...");

        // FXML laden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/hello-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("🏢 Vermieter Verwaltung - JDBC CRUD");
        stage.setScene(scene);
        stage.show();

        System.out.println("✅ Anwendung gestartet!");
    }

    @Override
    public void stop() throws SQLException {
        DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}