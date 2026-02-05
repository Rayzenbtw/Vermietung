package gfs.gfs2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

public class HelloController {

    private final VermieterDAO vermieterDAO = new VermieterDAO();


    @FXML private TableView<Vermieter> vermieterTable;
    @FXML private TableColumn<Vermieter, Integer> vNrCol;
    @FXML private TableColumn<Vermieter, String> nameCol;
    @FXML private TableColumn<Vermieter, String> vornameCol;

    // FXML Elemente - Input Felder
    @FXML private TextField nameField;
    @FXML private TextField vornameField;

    /**
     * Initialisierung - wird automatisch nach dem Laden der FXML aufgerufen
     */
    @FXML
    public void initialize() {
        System.out.println("Controller wird initialisiert...");

        // Tabellenspalten mit Modell-Properties verknüpfen
        vNrCol.setCellValueFactory(new PropertyValueFactory<>("vNr"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        vornameCol.setCellValueFactory(new PropertyValueFactory<>("vorname"));

        // Daten laden
        loadData();

        System.out.println("Controller initialisiert!");
    }

    /**
     * CREATE - Neuen Vermieter hinzufügen
     */
    @FXML
    private void onCreate() {

        String name = nameField.getText().trim();
        String vorname = vornameField.getText().trim();

        // Validierung
        if (name.isEmpty() || vorname.isEmpty()) {
            showAlert("Eingabefehler", "Bitte füllen Sie alle Felder aus!", Alert.AlertType.WARNING);
            return;
        }

        // Neues Objekt erstellen
        Vermieter vermieter = new Vermieter(name, vorname);

        // In Datenbank speichern
        if (vermieterDAO.createVermieter(vermieter)) {
            showAlert("Erfolg", "Vermieter wurde erfolgreich hinzugefügt!", Alert.AlertType.INFORMATION);
            loadData();
            clearFields();
        } else {
            showAlert("Fehler", "Vermieter konnte nicht hinzugefügt werden!", Alert.AlertType.ERROR);
        }
    }

    /**
     * UPDATE - Ausgewählten Vermieter bearbeiten
     */
    @FXML
    private void onUpdate() {

        Vermieter selected = vermieterTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Fehler", "Bitte wählen Sie einen Vermieter aus der Tabelle aus!", Alert.AlertType.WARNING);
            return;
        }

        // Dialog für Name
        TextInputDialog nameDialog = new TextInputDialog(selected.getName());
        nameDialog.setTitle("Bearbeiten");
        nameDialog.setHeaderText("Vermieter bearbeiten: " + selected.getVNr());
        nameDialog.setContentText("Name (Nachname):");

        Optional<String> nameResult = nameDialog.showAndWait();
        if (nameResult.isEmpty() || nameResult.get().trim().isEmpty()) {
            return;
        }

        // Dialog für Vorname
        TextInputDialog vornameDialog = new TextInputDialog(selected.getVorname());
        vornameDialog.setTitle("Bearbeiten");
        vornameDialog.setHeaderText("Vermieter bearbeiten: " + selected.getVNr());
        vornameDialog.setContentText("Vorname:");

        Optional<String> vornameResult = vornameDialog.showAndWait();
        if (vornameResult.isEmpty() || vornameResult.get().trim().isEmpty()) {
            return;
        }

        // Daten aktualisieren
        selected.setName(nameResult.get().trim());
        selected.setVorname(vornameResult.get().trim());

        // In Datenbank speichern
        if (vermieterDAO.updateVermieter(selected)) {
            showAlert("Erfolg", "Vermieter wurde aktualisiert!", Alert.AlertType.INFORMATION);
            loadData();
        } else {
            showAlert("Fehler", "Aktualisierung fehlgeschlagen!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onDelete() {
        Vermieter selected = vermieterTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Fehler", "Bitte wählen Sie einen Vermieter zum Löschen aus!", Alert.AlertType.WARNING);
            return;
        }

        // Bestätigungsdialog
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bestätigung");
        confirm.setHeaderText("Vermieter löschen?");
        confirm.setContentText("VNr: " + selected.getVNr() + "\n" +
                "Name: " + selected.getVorname() + " " + selected.getName() + "\n\n" +
                "Diese Aktion kann nicht rückgängig gemacht werden!");

        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (vermieterDAO.deleteVermieter(selected.getVNr())) {
                showAlert("Erfolg", "Vermieter wurde gelöscht!", Alert.AlertType.INFORMATION);
                loadData();
            } else {
                showAlert("Fehler",
                        "Löschen fehlgeschlagen!\nMöglicherweise existieren verknüpfte Datensätze.",
                        Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * READ - Daten aus Datenbank neu laden
     */
    @FXML
    private void onRefresh() {
        loadData();
        showAlert("Aktualisiert", "Daten wurden erfolgreich aktualisiert!", Alert.AlertType.INFORMATION);
    }


    private void loadData() {
        try {
            List<Vermieter> list = vermieterDAO.getAllVermieter();
            ObservableList<Vermieter> observableList = FXCollections.observableArrayList(list);
            vermieterTable.setItems(observableList);

            System.out.println("Geladen: " + list.size() + " Vermieter");
        } catch (Exception e) {
            System.err.println("Fehler beim Laden der Daten!");
            e.printStackTrace();
            showAlert("Fehler", "Daten konnten nicht geladen werden!\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Eingabefelder leeren
     */
    private void clearFields() {
        nameField.clear();
        vornameField.clear();
    }

    /**
     * Alert-Dialog anzeigen
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}