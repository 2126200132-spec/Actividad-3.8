package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Entrenador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class EntrenadorController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEspecialidad;
    @FXML private TextField txtTelefono;

    @FXML private Label lblErrorId;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorEspecialidad;
    @FXML private Label lblErrorTelefono;

    @FXML private TableView<Entrenador> tblEntrenadores;
    @FXML private TableColumn<Entrenador, Integer> colId;
    @FXML private TableColumn<Entrenador, String> colNombre;
    @FXML private TableColumn<Entrenador, String> colEspecialidad;
    @FXML private TableColumn<Entrenador, String> colTelefono;

    @FXML private Button btnNuevo;
    @FXML private Button btnValidar;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final ObservableList<Entrenador> listaEntrenadores = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colEspecialidad != null) colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        if (colTelefono != null) colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        if (tblEntrenadores != null) {
            tblEntrenadores.setItems(listaEntrenadores);
            tblEntrenadores.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean registroSeleccionado = (newVal != null);
                if (btnEditar != null) btnEditar.setDisable(!registroSeleccionado);
                if (btnEliminar != null) btnEliminar.setDisable(!registroSeleccionado);

                if (newVal != null) {
                    if (txtId != null) txtId.setText(String.valueOf(newVal.getId()));
                    if (txtNombre != null) txtNombre.setText(newVal.getNombre());
                    if (txtEspecialidad != null) txtEspecialidad.setText(newVal.getEspecialidad());
                    if (txtTelefono != null) txtTelefono.setText(newVal.getTelefono());
                }
            });
        }

        configurarListenersValidacion();
        restablecerEstadoBotones();
    }

    private void configurarListenersValidacion() {
        if (txtId != null) txtId.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtNombre != null) txtNombre.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtEspecialidad != null) txtEspecialidad.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtTelefono != null) txtTelefono.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
    }

    private boolean validarFormularioEnTiempoReal() {
        boolean esValido = true;

        // Validar ID (Entero)
        if (txtId != null && lblErrorId != null) {
            String val = txtId.getText().trim();
            if (val.isEmpty()) {
                lblErrorId.setText("Obligatorio");
                esValido = false;
            } else if (!val.matches("\\d+")) {
                lblErrorId.setText("Solo enteros");
                esValido = false;
            } else {
                lblErrorId.setText("");
            }
        }

        // Validar Nombre
        if (txtNombre != null && lblErrorNombre != null) {
            String val = txtNombre.getText().trim();
            if (val.isEmpty()) {
                lblErrorNombre.setText("Obligatorio");
                esValido = false;
            } else {
                lblErrorNombre.setText("");
            }
        }

        // Validar Especialidad
        if (txtEspecialidad != null && lblErrorEspecialidad != null) {
            String val = txtEspecialidad.getText().trim();
            if (val.isEmpty()) {
                lblErrorEspecialidad.setText("Obligatorio");
                esValido = false;
            } else {
                lblErrorEspecialidad.setText("");
            }
        }

        // Validar Teléfono (10 dígitos)
        if (txtTelefono != null && lblErrorTelefono != null) {
            String val = txtTelefono.getText().trim();
            if (val.isEmpty()) {
                lblErrorTelefono.setText("Obligatorio");
                esValido = false;
            } else if (!val.matches("\\d{10}")) {
                lblErrorTelefono.setText("10 dígitos numéricos");
                esValido = false;
            } else {
                lblErrorTelefono.setText("");
            }
        }

        if (btnGuardar != null) btnGuardar.setDisable(!esValido);
        return esValido;
    }

    @FXML private void handleNuevo() { handleLimpiar(); }

    @FXML
    private void handleValidar() {
        if (validarFormularioEnTiempoReal()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Validación Exitosa", "Todos los campos de entrenador son válidos.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de Validación", "Revisa los mensajes señalados debajo de los campos.");
        }
    }

    @FXML
    private void handleGuardar() {
        if (!validarFormularioEnTiempoReal()) return;

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();
            String telefono = txtTelefono.getText().trim();

            listaEntrenadores.add(new Entrenador(id, nombre, especialidad, telefono));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Entrenador agregado a la lista visual.");
            handleLimpiar();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un fallo al procesar los datos.");
        }
    }

    @FXML private void handleEditar() { mostrarAlerta(Alert.AlertType.INFORMATION, "Edición", "Edición visual ejecutada."); }

    @FXML
    private void handleEliminar() {
        if (tblEntrenadores != null) {
            Entrenador selec = tblEntrenadores.getSelectionModel().getSelectedItem();
            if (selec != null) {
                listaEntrenadores.remove(selec);
                handleLimpiar();
            }
        }
    }

    @FXML
    private void handleLimpiar() {
        if (txtId != null) txtId.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtEspecialidad != null) txtEspecialidad.clear();
        if (txtTelefono != null) txtTelefono.clear();

        if (lblErrorId != null) lblErrorId.setText("");
        if (lblErrorNombre != null) lblErrorNombre.setText("");
        if (lblErrorEspecialidad != null) lblErrorEspecialidad.setText("");
        if (lblErrorTelefono != null) lblErrorTelefono.setText("");

        if (tblEntrenadores != null) tblEntrenadores.getSelectionModel().clearSelection();
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
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msj);
        a.showAndWait();
    }
}