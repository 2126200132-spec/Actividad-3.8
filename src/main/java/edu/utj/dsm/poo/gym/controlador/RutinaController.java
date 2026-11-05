package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Rutina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class RutinaController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtObjetivo;
    @FXML private TextField txtRepeticiones;
    @FXML private ComboBox<String> cmbNivel;

    @FXML private Label lblErrorId;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorObjetivo;
    @FXML private Label lblErrorRepeticiones;
    @FXML private Label lblErrorNivel;

    @FXML private TableView<Rutina> tblRutinas;
    @FXML private TableColumn<Rutina, Integer> colId;
    @FXML private TableColumn<Rutina, String> colNombre;
    @FXML private TableColumn<Rutina, String> colObjetivo;
    @FXML private TableColumn<Rutina, String> colRepeticiones;
    @FXML private TableColumn<Rutina, String> colNivel;

    @FXML private Button btnNuevo;
    @FXML private Button btnValidar;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final ObservableList<Rutina> listaRutinas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colObjetivo != null) colObjetivo.setCellValueFactory(new PropertyValueFactory<>("objetivo"));
        if (colRepeticiones != null) colRepeticiones.setCellValueFactory(new PropertyValueFactory<>("repeticiones"));
        if (colNivel != null) colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));

        if (tblRutinas != null) {
            tblRutinas.setItems(listaRutinas);
            tblRutinas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean sel = (newVal != null);
                if (btnEditar != null) btnEditar.setDisable(!sel);
                if (btnEliminar != null) btnEliminar.setDisable(!sel);

                if (newVal != null) {
                    if (txtId != null) txtId.setText(String.valueOf(newVal.getId()));
                    if (txtNombre != null) txtNombre.setText(newVal.getNombre());
                    if (txtObjetivo != null) txtObjetivo.setText(newVal.getObjetivo());
                    if (txtRepeticiones != null) txtRepeticiones.setText(newVal.getRepeticiones());
                    if (cmbNivel != null) cmbNivel.setValue(newVal.getNivel());
                }
            });
        }

        if (cmbNivel != null) {
            cmbNivel.setItems(FXCollections.observableArrayList("Principiante", "Intermedio", "Avanzado"));
            cmbNivel.valueProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        }

        configurarListenersValidacion();
        restablecerEstadoBotones();
    }

    private void configurarListenersValidacion() {
        if (txtId != null) txtId.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtNombre != null) txtNombre.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtObjetivo != null) txtObjetivo.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtRepeticiones != null) txtRepeticiones.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
    }

    private boolean validarFormularioEnTiempoReal() {
        boolean esValido = true;

        if (txtId != null && lblErrorId != null) {
            String val = txtId.getText().trim();
            if (val.isEmpty()) { lblErrorId.setText("Obligatorio"); esValido = false; }
            else if (!val.matches("\\d+")) { lblErrorId.setText("Solo enteros"); esValido = false; }
            else { lblErrorId.setText(""); }
        }

        if (txtNombre != null && lblErrorNombre != null) {
            String val = txtNombre.getText().trim();
            if (val.isEmpty()) { lblErrorNombre.setText("Obligatorio"); esValido = false; }
            else { lblErrorNombre.setText(""); }
        }

        if (txtObjetivo != null && lblErrorObjetivo != null) {
            String val = txtObjetivo.getText().trim();
            if (val.isEmpty()) { lblErrorObjetivo.setText("Obligatorio"); esValido = false; }
            else { lblErrorObjetivo.setText(""); }
        }

        if (txtRepeticiones != null && lblErrorRepeticiones != null) {
            String val = txtRepeticiones.getText().trim();
            if (val.isEmpty()) { lblErrorRepeticiones.setText("Obligatorio"); esValido = false; }
            else { lblErrorRepeticiones.setText(""); }
        }

        if (cmbNivel != null && lblErrorNivel != null) {
            if (cmbNivel.getValue() == null) { lblErrorNivel.setText("Selecciona"); esValido = false; }
            else { lblErrorNivel.setText(""); }
        }

        if (btnGuardar != null) btnGuardar.setDisable(!esValido);
        return esValido;
    }

    @FXML private void handleNuevo() { handleLimpiar(); }

    @FXML
    private void handleValidar() {
        if (validarFormularioEnTiempoReal()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Validación Exitosa", "La rutina se configuró de forma válida.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de Validación", "Corrige los campos indicados.");
        }
    }

    @FXML
    private void handleGuardar() {
        if (!validarFormularioEnTiempoReal()) return;
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String objetivo = txtObjetivo.getText().trim();
            String repeticiones = txtRepeticiones.getText().trim();
            String nivel = cmbNivel.getValue();

            listaRutinas.add(new Rutina(id, nombre, objetivo, repeticiones, nivel));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Rutina registrada.");
            handleLimpiar();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al procesar.");
        }
    }

    @FXML private void handleEditar() { mostrarAlerta(Alert.AlertType.INFORMATION, "Edición", "Acción de edición simulada."); }

    @FXML
    private void handleEliminar() {
        if (tblRutinas != null) {
            Rutina sel = tblRutinas.getSelectionModel().getSelectedItem();
            if (sel != null) { listaRutinas.remove(sel); handleLimpiar(); }
        }
    }

    @FXML
    private void handleLimpiar() {
        if (txtId != null) txtId.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtObjetivo != null) txtObjetivo.clear();
        if (txtRepeticiones != null) txtRepeticiones.clear();
        if (cmbNivel != null) cmbNivel.setValue(null);

        if (lblErrorId != null) lblErrorId.setText("");
        if (lblErrorNombre != null) lblErrorNombre.setText("");
        if (lblErrorObjetivo != null) lblErrorObjetivo.setText("");
        if (lblErrorRepeticiones != null) lblErrorRepeticiones.setText("");
        if (lblErrorNivel != null) lblErrorNivel.setText("");

        if (tblRutinas != null) tblRutinas.getSelectionModel().clearSelection();
        restablecerEstadoBotones();
    }

    private void restablecerEstadoBotones() {
        if (btnNuevo != null) btnNuevo.setDisable(false);
        if (btnValidar != null) btnValidar.setDisable(false);
        if (btnGuardar != null) btnGuardar.setDisable(true);
        if (btnEditar != null) btnEditar.setDisable(true);
        if (btnEliminar != null) btnEliminar.setDisable(true);
        if (btnLimpiar != null) btnLimpiar.setDisable(false);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String msj) {
        Alert a = new Alert(tipo); a.setTitle(titulo); a.setHeaderText(null); a.setContentText(msj); a.showAndWait();
    }
}