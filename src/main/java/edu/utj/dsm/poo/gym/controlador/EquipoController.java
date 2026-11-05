package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Equipo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class EquipoController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtUbicacion;
    @FXML private ComboBox<String> cmbEstado;

    @FXML private Label lblErrorId;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorCantidad;
    @FXML private Label lblErrorUbicacion;
    @FXML private Label lblErrorEstado;

    @FXML private TableView<Equipo> tblEquipos;
    @FXML private TableColumn<Equipo, Integer> colId;
    @FXML private TableColumn<Equipo, String> colNombre;
    @FXML private TableColumn<Equipo, Integer> colCantidad;
    @FXML private TableColumn<Equipo, String> colUbicacion;
    @FXML private TableColumn<Equipo, String> colEstado;

    @FXML private Button btnNuevo;
    @FXML private Button btnValidar;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final ObservableList<Equipo> listaEquipos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colCantidad != null) colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        if (colUbicacion != null) colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        if (colEstado != null) colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        if (tblEquipos != null) {
            tblEquipos.setItems(listaEquipos);
            tblEquipos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean sel = (newVal != null);
                if (btnEditar != null) btnEditar.setDisable(!sel);
                if (btnEliminar != null) btnEliminar.setDisable(!sel);

                if (newVal != null) {
                    if (txtId != null) txtId.setText(String.valueOf(newVal.getId()));
                    if (txtNombre != null) txtNombre.setText(newVal.getNombre());
                    if (txtCantidad != null) txtCantidad.setText(String.valueOf(newVal.getCantidad()));
                    if (txtUbicacion != null) txtUbicacion.setText(newVal.getUbicacion());
                    if (cmbEstado != null) cmbEstado.setValue(newVal.getEstado());
                }
            });
        }

        if (cmbEstado != null) {
            cmbEstado.setItems(FXCollections.observableArrayList("Excelente", "Operativo", "En Mantenimiento"));
            cmbEstado.valueProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        }

        configurarListenersValidacion();
        restablecerEstadoBotones();
    }

    private void configurarListenersValidacion() {
        if (txtId != null) txtId.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtNombre != null) txtNombre.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtCantidad != null) txtCantidad.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtUbicacion != null) txtUbicacion.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
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

        if (txtCantidad != null && lblErrorCantidad != null) {
            String val = txtCantidad.getText().trim();
            if (val.isEmpty()) { lblErrorCantidad.setText("Obligatorio"); esValido = false; }
            else if (!val.matches("\\d+")) { lblErrorCantidad.setText("Solo enteros"); esValido = false; }
            else { lblErrorCantidad.setText(""); }
        }

        if (txtUbicacion != null && lblErrorUbicacion != null) {
            String val = txtUbicacion.getText().trim();
            if (val.isEmpty()) { lblErrorUbicacion.setText("Obligatorio"); esValido = false; }
            else { lblErrorUbicacion.setText(""); }
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
            mostrarAlerta(Alert.AlertType.INFORMATION, "Validación Exitosa", "Los datos del equipo son correctos.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de Validación", "Por favor revisa los errores indicados.");
        }
    }

    @FXML
    private void handleGuardar() {
        if (!validarFormularioEnTiempoReal()) return;
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            String ubicacion = txtUbicacion.getText().trim();
            String estado = cmbEstado.getValue();

            listaEquipos.add(new Equipo(id, nombre, cantidad, ubicacion, estado));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Equipo registrado en la lista.");
            handleLimpiar();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al procesar.");
        }
    }

    @FXML private void handleEditar() { mostrarAlerta(Alert.AlertType.INFORMATION, "Edición", "Edición visual efectuada."); }

    @FXML
    private void handleEliminar() {
        if (tblEquipos != null) {
            Equipo sel = tblEquipos.getSelectionModel().getSelectedItem();
            if (sel != null) { listaEquipos.remove(sel); handleLimpiar(); }
        }
    }

    @FXML
    private void handleLimpiar() {
        if (txtId != null) txtId.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtCantidad != null) txtCantidad.clear();
        if (txtUbicacion != null) txtUbicacion.clear();
        if (cmbEstado != null) cmbEstado.setValue(null);

        if (lblErrorId != null) lblErrorId.setText("");
        if (lblErrorNombre != null) lblErrorNombre.setText("");
        if (lblErrorCantidad != null) lblErrorCantidad.setText("");
        if (lblErrorUbicacion != null) lblErrorUbicacion.setText("");
        if (lblErrorEstado != null) lblErrorEstado.setText("");

        if (tblEquipos != null) tblEquipos.getSelectionModel().clearSelection();
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