package ui;

import dao.MieterDAO;
import dao.ObjektDAO;
import dao.VermieterDAO;
import dao.VermietungDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Mieter;
import model.Objekt;
import model.Vermieter;
import model.Vermietung;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controller für die UI-Verwaltung.
 * Verbindet FXML-Elemente mit der Geschäftslogik.
 */
public class HelloController {

    // ============ DAO Objekte ============
    private final VermieterDAO vermieterDAO = new VermieterDAO();
    private final ObjektDAO objektDAO = new ObjektDAO();
    private final MieterDAO mieterDAO = new MieterDAO();
    private final VermietungDAO vermietungDAO = new VermietungDAO();

    // ============ FXML Elemente für Vermieter ============
    @FXML private TableView<Vermieter> vermieterTable;
    @FXML private TableColumn<Vermieter, Integer> vermieterVNrCol;
    @FXML private TableColumn<Vermieter, String> vermieterNameCol;
    @FXML private TableColumn<Vermieter, String> vermieterVornameCol;

    @FXML private TextField vermieterNameField;
    @FXML private TextField vermieterVornameField;

    // ============ FXML Elemente für Objekt ============
    @FXML private TableView<Objekt> objektTable;
    @FXML private TableColumn<Objekt, Integer> objektONrCol;
    @FXML private TableColumn<Objekt, BigDecimal> objektGroesseCol;
    @FXML private TableColumn<Objekt, BigDecimal> objektMietpreisCol;
    @FXML private TableColumn<Objekt, Integer> objektVNrCol;
    @FXML private TableColumn<Objekt, String> objektVermieterCol;

    @FXML private TextField objektGroesseField;
    @FXML private TextField objektMietpreisField;
    @FXML private ComboBox<Vermieter> objektVermieterCombo;

    // ============ FXML Elemente für Mieter ============
    @FXML private TableView<Mieter> mieterTable;
    @FXML private TableColumn<Mieter, Integer> mieterMNrCol;
    @FXML private TableColumn<Mieter, String> mieterNameCol;
    @FXML private TableColumn<Mieter, String> mieterVornameCol;

    @FXML private TextField mieterNameField;
    @FXML private TextField mieterVornameField;

    // ============ FXML Elemente für Vermietung ============
    @FXML private TableView<Vermietung> vermietungTable;
    @FXML private TableColumn<Vermietung, Integer> vermietungVMNrCol;
    @FXML private TableColumn<Vermietung, String> vermietungMieterCol;
    @FXML private TableColumn<Vermietung, String> vermietungObjektCol;
    @FXML private TableColumn<Vermietung, LocalDate> vermietungADatumCol;
    @FXML private TableColumn<Vermietung, LocalDate> vermietungEDatumCol;

    @FXML private ComboBox<Mieter> vermietungMieterCombo;
    @FXML private ComboBox<Objekt> vermietungObjektCombo;
    @FXML private DatePicker vermietungADatumPicker;
    @FXML private DatePicker vermietungEDatumPicker;

    /**
     * Initialisierung des Controllers.
     * Wird automatisch nach dem Laden der FXML aufgerufen.
     */
    @FXML
    public void initialize() {
        setupVermieterTable();
        setupObjektTable();
        setupMieterTable();
        setupVermietungTable();

        loadAllData();
    }

    // ========================================
    // VERMIETER (Vermieter) - Methoden
    // ========================================

    private void setupVermieterTable() {
        vermieterVNrCol.setCellValueFactory(new PropertyValueFactory<>("vNr"));
        vermieterNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        vermieterVornameCol.setCellValueFactory(new PropertyValueFactory<>("vorname"));
    }

    @FXML
    private void onVermieterCreate() {
        String name = vermieterNameField.getText().trim();
        String vorname = vermieterVornameField.getText().trim();

        if (name.isEmpty() || vorname.isEmpty()) {
            showAlert("Eingabefehler", "Bitte füllen Sie alle Felder aus!", Alert.AlertType.WARNING);
            return;
        }

        Vermieter vermieter = new Vermieter(name, vorname);

        if (vermieterDAO.createVermieter(vermieter)) {
            showAlert("Erfolg", "Vermieter wurde hinzugefügt!", Alert.AlertType.INFORMATION);
            loadVermieterData();
            clearVermieterFields();
        } else {
            showAlert("Fehler", "Vermieter konnte nicht hinzugefügt werden!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onVermieterUpdate() {
        Vermieter selected = vermieterTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Fehler", "Bitte wählen Sie einen Vermieter aus der Tabelle aus!", Alert.AlertType.WARNING);
            return;
        }

        // Dialog zur Bearbeitung
        TextInputDialog nameDialog = new TextInputDialog(selected.getName());
        nameDialog.setTitle("Bearbeitung");
        nameDialog.setHeaderText("Nachname ändern");
        nameDialog.setContentText("Nachname:");
        Optional<String> nameResult = nameDialog.showAndWait();

        if (nameResult.isEmpty()) return;

        TextInputDialog vornameDialog = new TextInputDialog(selected.getVorname());
        vornameDialog.setTitle("Bearbeitung");
        vornameDialog.setHeaderText("Vorname ändern");
        vornameDialog.setContentText("Vorname:");
        Optional<String> vornameResult = vornameDialog.showAndWait();

        if (vornameResult.isEmpty()) return;

        selected.setName(nameResult.get());
        selected.setVorname(vornameResult.get());

        if (vermieterDAO.updateVermieter(selected)) {
            showAlert("Erfolg", "Vermieter wurde aktualisiert!", Alert.AlertType.INFORMATION);
            loadVermieterData();
        } else {
            showAlert("Fehler", "Aktualisierung fehlgeschlagen!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onVermieterDelete() {
        Vermieter selected = vermieterTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Fehler", "Bitte wählen Sie einen Vermieter zum Löschen aus!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bestätigung");
        confirm.setHeaderText("Vermieter löschen?");
        confirm.setContentText(selected.getVorname() + " " + selected.getName());

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (vermieterDAO.deleteVermieter(selected.getVNr())) {
                showAlert("Erfolg", "Vermieter wurde gelöscht!", Alert.AlertType.INFORMATION);
                loadVermieterData();
            } else {
                showAlert("Fehler", "Löschen fehlgeschlagen! Möglicherweise existieren verknüpfte Objekte.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void onVermieterRefresh() {
        loadVermieterData();
        showAlert("Aktualisiert", "Vermieterdaten wurden aktualisiert!", Alert.AlertType.INFORMATION);
    }

    private void loadVermieterData() {
        List<Vermieter> list = vermieterDAO.getAllVermieter();
        vermieterTable.setItems(FXCollections.observableArrayList(list));

        // ComboBox im Objekt-Bereich aktualisieren
        objektVermieterCombo.setItems(FXCollections.observableArrayList(list));
        setupVermieterComboBox(objektVermieterCombo);
    }

    private void clearVermieterFields() {
        vermieterNameField.clear();
        vermieterVornameField.clear();
    }

    // ========================================
    // OBJEKT (Immobilienobjekt) - Methoden
    // ========================================

    private void setupObjektTable() {
        objektONrCol.setCellValueFactory(new PropertyValueFactory<>("oNr"));
        objektGroesseCol.setCellValueFactory(new PropertyValueFactory<>("groesse"));
        objektMietpreisCol.setCellValueFactory(new PropertyValueFactory<>("mietpreis"));
        objektVNrCol.setCellValueFactory(new PropertyValueFactory<>("vNr"));
        objektVermieterCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVermieterName()));
    }

    @FXML
    private void onObjektCreate() {
        try {
            String groesseText = objektGroesseField.getText().trim();
            String mietpreisText = objektMietpreisField.getText().trim();
            Vermieter vermieter = objektVermieterCombo.getValue();

            if (groesseText.isEmpty() || mietpreisText.isEmpty() || vermieter == null) {
                showAlert("Fehler", "Bitte füllen Sie alle Felder aus!", Alert.AlertType.WARNING);
                return;
            }

            BigDecimal groesse = new BigDecimal(groesseText);
            BigDecimal mietpreis = new BigDecimal(mietpreisText);

            Objekt objekt = new Objekt(groesse, mietpreis, vermieter.getVNr());

            if (objektDAO.createObjekt(objekt)) {
                showAlert("Erfolg", "Objekt wurde hinzugefügt!", Alert.AlertType.INFORMATION);
                loadObjektData();
                clearObjektFields();
            } else {
                showAlert("Fehler", "Objekt konnte nicht hinzugefügt werden!", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            showAlert("Fehler", "Bitte überprüfen Sie das Zahlenformat!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onObjektUpdate() {
        Objekt selected = objektTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Fehler", "Bitte wählen Sie ein Objekt aus der Tabelle aus!", Alert.AlertType.WARNING);
            return;
        }

        Dialog<Objekt> dialog = createObjektEditDialog(selected);
        Optional<Objekt> result = dialog.showAndWait();

        result.ifPresent(objekt -> {
            if (objektDAO.updateObjekt(objekt)) {
                showAlert("Erfolg", "Objekt wurde aktualisiert!", Alert.AlertType.INFORMATION);
                loadObjektData();
            } else {
                showAlert("Fehler", "Aktualisierung fehlgeschlagen!", Alert.AlertType.ERROR);
            }
        });
    }

    @FXML
    private void onObjektDelete() {
        Objekt selected = objektTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Fehler", "Bitte wählen Sie ein Objekt zum Löschen aus!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bestätigung");
        confirm.setHeaderText("Objekt #" + selected.getONr() + " löschen?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (objektDAO.deleteObjekt(selected.getONr())) {
                showAlert("Erfolg", "Objekt wurde gelöscht!", Alert.AlertType.INFORMATION);
                loadObjektData();
            } else {
                showAlert("Fehler", "Löschen fehlgeschlagen!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void onObjektRefresh() {
        loadObjektData();
        showAlert("Aktualisiert", "Objektdaten wurden aktualisiert!", Alert.AlertType.INFORMATION);
    }

    private void loadObjektData() {
        List<Objekt> list = objektDAO.getAllObjekte();
        objektTable.setItems(FXCollections.observableArrayList(list));

        // ComboBox im Vermietungs-Bereich aktualisieren
        vermietungObjektCombo.setItems(FXCollections.observableArrayList(list));
        setupObjektComboBox(vermietungObjektCombo);
    }

    private void clearObjektFields() {
        objektGroesseField.clear();
        objektMietpreisField.clear();
        objektVermieterCombo.getSelectionModel().clearSelection();
    }

    private Dialog<Objekt> createObjektEditDialog(Objekt objekt) {
        Dialog<Objekt> dialog = new Dialog<>();
        dialog.setTitle("Objektbearbeitung");
        dialog.setHeaderText("Objekt #" + objekt.getONr());

        ButtonType saveButton = new ButtonType("Speichern", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField groesseField = new TextField(objekt.getGroesse().toString());
        TextField mietpreisField = new TextField(objekt.getMietpreis().toString());
        ComboBox<Vermieter> vermieterBox = new ComboBox<>();

        List<Vermieter> vermieterList = vermieterDAO.getAllVermieter();
        vermieterBox.setItems(FXCollections.observableArrayList(vermieterList));
        setupVermieterComboBox(vermieterBox);

        vermieterList.stream()
                .filter(v -> v.getVNr() == objekt.getVNr())
                .findFirst()
                .ifPresent(vermieterBox::setValue);

        grid.add(new Label("Größe (m²):"), 0, 0);
        grid.add(groesseField, 1, 0);
        grid.add(new Label("Mietpreis (€):"), 0, 1);
        grid.add(mietpreisField, 1, 1);
        grid.add(new Label("Vermieter:"), 0, 2);
        grid.add(vermieterBox, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                try {
                    objekt.setGroesse(new BigDecimal(groesseField.getText()));
                    objekt.setMietpreis(new BigDecimal(mietpreisField.getText()));
                    objekt.setVNr(vermieterBox.getValue().getVNr());
                    return objekt;
                } catch (Exception e) {
                    showAlert("Fehler", "Ungültiges Datenformat!", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        return dialog;
    }

    // ========================================
    // MIETER (Mieter) - Methoden
    // ========================================

    private void setupMieterTable() {
        mieterMNrCol.setCellValueFactory(new PropertyValueFactory<>("mNr"));
        mieterNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mieterVornameCol.setCellValueFactory(new PropertyValueFactory<>("vorname"));
    }

    @FXML
    private void onMieterCreate() {
        String name = mieterNameField.getText().trim();
        String vorname = mieterVornameField.getText().trim();

        if (name.isEmpty() || vorname.isEmpty()) {
            showAlert("Fehler", "Bitte füllen Sie alle Felder aus!", Alert.AlertType.WARNING);
            return;
        }

        Mieter mieter = new Mieter(name, vorname);

        if (mieterDAO.createMieter(mieter)) {
            showAlert("Erfolg", "Mieter wurde hinzugefügt!", Alert.AlertType.INFORMATION);
            loadMieterData();
            clearMieterFields();
        } else {
            showAlert("Fehler", "Hinzufügen fehlgeschlagen!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onMieterUpdate() {
        Mieter selected = mieterTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Fehler", "Bitte wählen Sie einen Mieter aus der Tabelle aus!", Alert.AlertType.WARNING);
            return;
        }

        TextInputDialog nameDialog = new TextInputDialog(selected.getName());
        nameDialog.setTitle("Bearbeitung");
        nameDialog.setHeaderText("Nachname ändern");
        nameDialog.setContentText("Nachname:");
        Optional<String> nameResult = nameDialog.showAndWait();

        if (nameResult.isEmpty()) return;

        TextInputDialog vornameDialog = new TextInputDialog(selected.getVorname());
        vornameDialog.setTitle("Bearbeitung");
        vornameDialog.setHeaderText("Vorname ändern");
        vornameDialog.setContentText("Vorname:");
        Optional<String> vornameResult = vornameDialog.showAndWait();

        if (vornameResult.isEmpty()) return;

        selected.setName(nameResult.get());
        selected.setVorname(vornameResult.get());

        if (mieterDAO.updateMieter(selected)) {
            showAlert("Erfolg", "Mieter wurde aktualisiert!", Alert.AlertType.INFORMATION);
            loadMieterData();
        } else {
            showAlert("Fehler", "Aktualisierung fehlgeschlagen!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onMieterDelete() {
        Mieter selected = mieterTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Fehler", "Bitte wählen Sie einen Mieter zum Löschen aus!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bestätigung");
        confirm.setHeaderText("Mieter löschen?");
        confirm.setContentText(selected.getVorname() + " " + selected.getName());

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (mieterDAO.deleteMieter(selected.getMNr())) {
                showAlert("Erfolg", "Mieter wurde gelöscht!", Alert.AlertType.INFORMATION);
                loadMieterData();
            } else {
                showAlert("Fehler", "Löschen fehlgeschlagen! Möglicherweise existieren aktive Vermietungen.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void onMieterRefresh() {
        loadMieterData();
        showAlert("Aktualisiert", "Mieterdaten wurden aktualisiert!", Alert.AlertType.INFORMATION);
    }

    private void loadMieterData() {
        List<Mieter> list = mieterDAO.getAllMieter();
        mieterTable.setItems(FXCollections.observableArrayList(list));

        // ComboBox im Vermietungs-Bereich aktualisieren
        vermietungMieterCombo.setItems(FXCollections.observableArrayList(list));
        setupMieterComboBox(vermietungMieterCombo);
    }

    private void clearMieterFields() {
        mieterNameField.clear();
        mieterVornameField.clear();
    }

    // ========================================
    // VERMIETUNG (Vermietung) - Methoden
    // ========================================

    private void setupVermietungTable() {
        vermietungVMNrCol.setCellValueFactory(new PropertyValueFactory<>("vmNr"));
        vermietungMieterCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMieterName()));
        vermietungObjektCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getObjektInfo()));
        vermietungADatumCol.setCellValueFactory(new PropertyValueFactory<>("aDatum"));
        vermietungEDatumCol.setCellValueFactory(new PropertyValueFactory<>("eDatum"));
    }

    @FXML
    private void onVermietungCreate() {
        Mieter mieter = vermietungMieterCombo.getValue();
        Objekt objekt = vermietungObjektCombo.getValue();
        LocalDate aDatum = vermietungADatumPicker.getValue();
        LocalDate eDatum = vermietungEDatumPicker.getValue();

        if (mieter == null || objekt == null || aDatum == null) {
            showAlert("Fehler", "Bitte füllen Sie die Pflichtfelder aus!", Alert.AlertType.WARNING);
            return;
        }

        Vermietung vermietung = new Vermietung(mieter.getMNr(), objekt.getONr(), aDatum, eDatum);

        if (vermietungDAO.createVermietung(vermietung)) {
            showAlert("Erfolg", "Vermietung wurde erstellt!", Alert.AlertType.INFORMATION);
            loadVermietungData();
            clearVermietungFields();
        } else {
            showAlert("Fehler", "Vermietung konnte nicht erstellt werden!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onVermietungUpdate() {
        Vermietung selected = vermietungTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Fehler", "Bitte wählen Sie eine Vermietung aus der Tabelle aus!", Alert.AlertType.WARNING);
            return;
        }

        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Bearbeitung");
        dialog.setHeaderText("Enddatum der Vermietung ändern");

        ButtonType saveButton = new ButtonType("Speichern", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        DatePicker datePicker = new DatePicker(selected.getEDatum());
        dialog.getDialogPane().setContent(datePicker);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                return datePicker.getValue();
            }
            return null;
        });

        Optional<LocalDate> result = dialog.showAndWait();

        result.ifPresent(date -> {
            selected.setEDatum(date);
            if (vermietungDAO.updateVermietung(selected)) {
                showAlert("Erfolg", "Vermietung wurde aktualisiert!", Alert.AlertType.INFORMATION);
                loadVermietungData();
            } else {
                showAlert("Fehler", "Aktualisierung fehlgeschlagen!", Alert.AlertType.ERROR);
            }
        });
    }

    @FXML
    private void onVermietungDelete() {
        Vermietung selected = vermietungTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Fehler", "Bitte wählen Sie eine Vermietung zum Löschen aus!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bestätigung");
        confirm.setHeaderText("Vermietung #" + selected.getVmNr() + " löschen?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (vermietungDAO.deleteVermietung(selected.getVmNr())) {
                showAlert("Erfolg", "Vermietung wurde gelöscht!", Alert.AlertType.INFORMATION);
                loadVermietungData();
            } else {
                showAlert("Fehler", "Löschen fehlgeschlagen!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void onVermietungRefresh() {
        loadVermietungData();
        showAlert("Aktualisiert", "Vermietungsdaten wurden aktualisiert!", Alert.AlertType.INFORMATION);
    }

    private void loadVermietungData() {
        List<Vermietung> list = vermietungDAO.getAllVermietungen();
        vermietungTable.setItems(FXCollections.observableArrayList(list));
    }

    private void clearVermietungFields() {
        vermietungMieterCombo.getSelectionModel().clearSelection();
        vermietungObjektCombo.getSelectionModel().clearSelection();
        vermietungADatumPicker.setValue(null);
        vermietungEDatumPicker.setValue(null);
    }

    // ========================================
    // Hilfsmethoden
    // ========================================

    private void loadAllData() {
        loadVermieterData();
        loadObjektData();
        loadMieterData();
        loadVermietungData();
    }

    private void setupVermieterComboBox(ComboBox<Vermieter> comboBox) {
        comboBox.setCellFactory(param -> new ListCell<Vermieter>() {
            @Override
            protected void updateItem(Vermieter item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null :
                        item.getVNr() + " - " + item.getVorname() + " " + item.getName());
            }
        });

        comboBox.setButtonCell(new ListCell<Vermieter>() {
            @Override
            protected void updateItem(Vermieter item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null :
                        item.getVNr() + " - " + item.getVorname() + " " + item.getName());
            }
        });
    }

    private void setupMieterComboBox(ComboBox<Mieter> comboBox) {
        comboBox.setCellFactory(param -> new ListCell<Mieter>() {
            @Override
            protected void updateItem(Mieter item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null :
                        item.getMNr() + " - " + item.getVorname() + " " + item.getName());
            }
        });

        comboBox.setButtonCell(new ListCell<Mieter>() {
            @Override
            protected void updateItem(Mieter item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null :
                        item.getMNr() + " - " + item.getVorname() + " " + item.getName());
            }
        });
    }

    private void setupObjektComboBox(ComboBox<Objekt> comboBox) {
        comboBox.setCellFactory(param -> new ListCell<Objekt>() {
            @Override
            protected void updateItem(Objekt item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null :
                        "Objekt #" + item.getONr() + " (" + item.getGroesse() + "m², " + item.getMietpreis() + "€)");
            }
        });

        comboBox.setButtonCell(new ListCell<Objekt>() {
            @Override
            protected void updateItem(Objekt item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null :
                        "Objekt #" + item.getONr() + " (" + item.getGroesse() + "m², " + item.getMietpreis() + "€)");
            }
        });
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}