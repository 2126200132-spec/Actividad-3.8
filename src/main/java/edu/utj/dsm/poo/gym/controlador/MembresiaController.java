package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Membresia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MembresiaController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCosto;
    @FXML private TextField txtDuracionDias;
    @FXML private ComboBox<String> cmbEstado;

    @FXML private Label lblErrorId;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorCosto;
    @FXML private Label lblErrorDuracionDias;
    @FXML private Label lblErrorEstado;

    @FXML private TableView<Membresia> tblMembresias;
    @FXML private TableColumn<Membresia, Integer> colId;
    @FXML private TableColumn<Membresia, String> colNombre;
    @FXML private TableColumn<Membresia, Double> colCosto;
    @FXML private TableColumn<Membresia, Integer> colDuracionDias;
    @FXML private TableColumn<Membresia, String> colEstado;

    @FXML private Button btnNuevo;
    @FXML private Button btnValidar;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final ObservableList<Membresia> listaMembresias = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colCosto != null) colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        if (colDuracionDias != null) colDuracionDias.setCellValueFactory(new PropertyValueFactory<>("duracionDias"));
        if (colEstado != null) colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        if (tblMembresias != null) {
            tblMembresias.setItems(listaMembresias);
            tblMembresias.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean sel = (newVal != null);
                if (btnEditar != null) btnEditar.setDisable(!sel);
                if (btnEliminar != null) btnEliminar.setDisable(!sel);

                if (newVal != null) {
                    if (txtId != null) txtId.setText(String.valueOf(newVal.getId()));
                    if (txtNombre != null) txtNombre.setText(newVal.getNombre());
                    if (txtCosto != null) txtCosto.setText(String.valueOf(newVal.getCosto()));
                    if (txtDuracionDias != null) txtDuracionDias.setText(String.valueOf(newVal.getDuracionDias()));
                    if (cmbEstado != null) cmbEstado.setValue(newVal.getEstado());
                }
            });
        }

        if (cmbEstado != null) {
            cmbEstado.setItems(FXCollections.observableArrayList("Activa", "Inactiva", "Promocional"));
            cmbEstado.valueProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        }

        configurarListenersValidacion();
        restablecerEstadoBotones();
    }

    private void configurarListenersValidacion() {
        if (txtId != null) txtId.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtNombre != null) txtNombre.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtCosto != null) txtCosto.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtDuracionDias != null) txtDuracionDias.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
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

        if (txtCosto != null && lblErrorCosto != null) {
            String val = txtCosto.getText().trim();
            if (val.isEmpty()) { lblErrorCosto.setText("Obligatorio"); esValido = false; }
            else if (!val.matches("\\d+(\\.\\d+)?")) { lblErrorCosto.setText("Decimal ej. 499.00"); esValido = false; }
            else { lblErrorCosto.setText(""); }
        }

        if (txtDuracionDias != null && lblErrorDuracionDias != null) {
            String val = txtDuracionDias.getText().trim();
            if (val.isEmpty()) { lblErrorDuracionDias.setText("Obligatorio"); esValido = false; }
            else if (!val.matches("\\d+")) { lblErrorDuracionDias.setText("Solo enteros"); esValido = false; }
            else { lblErrorDuracionDias.setText(""); }
        }

        if (cmbEstado != null && lblErrorEstado != null) {
            if (cmbEstado.getValue() == null) { lblErrorEstado.setText("Selecciona"); esValido = false; }
            else { lblErrorEstado.setText(""); }
        }

        if (btnGuardar != null) btnGuardar.setDisable(!esValido);
        return esValido;
    }

    @FXML private void handleNuevo() { handleLimpiar(); }

    @FXML
    private void handleValidar() {
        if (validarFormularioEnTiempoReal()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Validación Exitosa", "La membresía cumple con todos los requerimientos.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de Validación", "Corrige los mensajes en rojo.");
        }
    }

    @FXML
    private void handleGuardar() {
        if (!validarFormularioEnTiempoReal()) return;
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            double costo = Double.parseDouble(txtCosto.getText().trim());
            int duracion = Integer.parseInt(txtDuracionDias.getText().trim());
            String estado = cmbEstado.getValue();

            listaMembresias.add(new Membresia(id, nombre, costo, duracion, estado));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Membresía guardada en la lista.");
            handleLimpiar();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Fallo al procesar información.");
        }
    }

    @FXML private void handleEditar() { mostrarAlerta(Alert.AlertType.INFORMATION, "Edición", "Acción de edición simulada."); }

    @FXML
    private void handleEliminar() {
        if (tblMembresias != null) {
            Membresia sel = tblMembresias.getSelectionModel().getSelectedItem();
            if (sel != null) { listaMembresias.remove(sel); handleLimpiar(); }
        }
    }

    @FXML
    private void handleLimpiar() {
        if (txtId != null) txtId.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtCosto != null) txtCosto.clear();
        if (txtDuracionDias != null) txtDuracionDias.clear();
        if (cmbEstado != null) cmbEstado.setValue(null);

        if (lblErrorId != null) lblErrorId.setText("");
        if (lblErrorNombre != null) lblErrorNombre.setText("");
        if (lblErrorCosto != null) lblErrorCosto.setText("");
        if (lblErrorDuracionDias != null) lblErrorDuracionDias.setText("");
        if (lblErrorEstado != null) lblErrorEstado.setText("");

        if (tblMembresias != null) tblMembresias.getSelectionModel().clearSelection();
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