package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Actividad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ActividadController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtHorario;
    @FXML private TextField txtCapacidad;
    @FXML private ComboBox<String> cmbEstado;

    @FXML private Label lblErrorId;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorHorario;
    @FXML private Label lblErrorCapacidad;
    @FXML private Label lblErrorEstado;

    @FXML private TableView<Actividad> tblActividades;
    @FXML private TableColumn<Actividad, Integer> colId;
    @FXML private TableColumn<Actividad, String> colNombre;
    @FXML private TableColumn<Actividad, String> colHorario;
    @FXML private TableColumn<Actividad, Integer> colCapacidad;
    @FXML private TableColumn<Actividad, String> colEstado;

    @FXML private Button btnNuevo;
    @FXML private Button btnValidar;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final ObservableList<Actividad> listaActividades = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colHorario != null) colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        if (colCapacidad != null) colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        if (colEstado != null) colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        if (tblActividades != null) {
            tblActividades.setItems(listaActividades);
            tblActividades.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean sel = (newVal != null);
                if (btnEditar != null) btnEditar.setDisable(!sel);
                if (btnEliminar != null) btnEliminar.setDisable(!sel);

                if (newVal != null) {
                    if (txtId != null) txtId.setText(String.valueOf(newVal.getId()));
                    if (txtNombre != null) txtNombre.setText(newVal.getNombre());
                    if (txtHorario != null) txtHorario.setText(newVal.getHorario());
                    if (txtCapacidad != null) txtCapacidad.setText(String.valueOf(newVal.getCapacidad()));
                    if (cmbEstado != null) cmbEstado.setValue(newVal.getEstado());
                }
            });
        }

        if (cmbEstado != null) {
            cmbEstado.setItems(FXCollections.observableArrayList("Disponible", "Llena", "Cancelada"));
            cmbEstado.valueProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        }

        configurarListenersValidacion();
        restablecerEstadoBotones();
    }

    private void configurarListenersValidacion() {
        if (txtId != null) txtId.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtNombre != null) txtNombre.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtHorario != null) txtHorario.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtCapacidad != null) txtCapacidad.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
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

        if (txtHorario != null && lblErrorHorario != null) {
            String val = txtHorario.getText().trim();
            if (val.isEmpty()) { lblErrorHorario.setText("Obligatorio"); esValido = false; }
            else { lblErrorHorario.setText(""); }
        }

        if (txtCapacidad != null && lblErrorCapacidad != null) {
            String val = txtCapacidad.getText().trim();
            if (val.isEmpty()) { lblErrorCapacidad.setText("Obligatorio"); esValido = false; }
            else if (!val.matches("\\d+")) { lblErrorCapacidad.setText("Solo enteros"); esValido = false; }
            else { lblErrorCapacidad.setText(""); }
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
            mostrarAlerta(Alert.AlertType.INFORMATION, "Validación Exitosa", "La actividad cumple con todos los datos requeridos.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de Validación", "Corrige las advertencias en rojo.");
        }
    }

    @FXML
    private void handleGuardar() {
        if (!validarFormularioEnTiempoReal()) return;
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String horario = txtHorario.getText().trim();
            int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
            String estado = cmbEstado.getValue();

            listaActividades.add(new Actividad(id, nombre, horario, capacidad, estado));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Actividad guardada correctamente.");
            handleLimpiar();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al procesar.");
        }
    }

    @FXML private void handleEditar() { mostrarAlerta(Alert.AlertType.INFORMATION, "Edición", "Edición visual efectuada."); }

    @FXML
    private void handleEliminar() {
        if (tblActividades != null) {
            Actividad sel = tblActividades.getSelectionModel().getSelectedItem();
            if (sel != null) { listaActividades.remove(sel); handleLimpiar(); }
        }
    }

    @FXML
    private void handleLimpiar() {
        if (txtId != null) txtId.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtHorario != null) txtHorario.clear();
        if (txtCapacidad != null) txtCapacidad.clear();
        if (cmbEstado != null) cmbEstado.setValue(null);

        if (lblErrorId != null) lblErrorId.setText("");
        if (lblErrorNombre != null) lblErrorNombre.setText("");
        if (lblErrorHorario != null) lblErrorHorario.setText("");
        if (lblErrorCapacidad != null) lblErrorCapacidad.setText("");
        if (lblErrorEstado != null) lblErrorEstado.setText("");

        if (tblActividades != null) tblActividades.getSelectionModel().clearSelection();
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