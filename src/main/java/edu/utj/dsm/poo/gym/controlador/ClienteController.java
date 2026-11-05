package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefono;

    @FXML private Label lblErrorId;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorEmail;
    @FXML private Label lblErrorTelefono;

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colTelefono;

    @FXML private Button btnNuevo;
    @FXML private Button btnValidar;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colEmail != null) colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (colTelefono != null) colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        if (tblClientes != null) {
            tblClientes.setItems(listaClientes);
            tblClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean registroSeleccionado = (newVal != null);
                if (btnEditar != null) btnEditar.setDisable(!registroSeleccionado);
                if (btnEliminar != null) btnEliminar.setDisable(!registroSeleccionado);

                if (newVal != null) {
                    if (txtId != null) txtId.setText(String.valueOf(newVal.getId()));
                    if (txtNombre != null) txtNombre.setText(newVal.getNombre());
                    if (txtEmail != null) txtEmail.setText(newVal.getEmail());
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
        if (txtEmail != null) txtEmail.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtTelefono != null) txtTelefono.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
    }

    private boolean validarFormularioEnTiempoReal() {
        boolean esValido = true;

        // Validar ID
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

        // Validar Correo Electrónico (Regex de email)
        if (txtEmail != null && lblErrorEmail != null) {
            String val = txtEmail.getText().trim();
            if (val.isEmpty()) {
                lblErrorEmail.setText("Obligatorio");
                esValido = false;
            } else if (!val.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                lblErrorEmail.setText("Formato inválido (ej. a@b.com)");
                esValido = false;
            } else {
                lblErrorEmail.setText("");
            }
        }

        // Validar Teléfono
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
            mostrarAlerta(Alert.AlertType.INFORMATION, "Validación Exitosa", "Todos los campos del cliente son válidos.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de Validación", "Corrige los campos marcados antes de continuar.");
        }
    }

    @FXML
    private void handleGuardar() {
        if (!validarFormularioEnTiempoReal()) return;

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();

            listaClientes.add(new Cliente(id, nombre, email, telefono));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente registrado exitosamente.");
            handleLimpiar();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Fallo al procesar el registro.");
        }
    }

    @FXML private void handleEditar() { mostrarAlerta(Alert.AlertType.INFORMATION, "Edición", "Acción de edición simulada."); }

    @FXML
    private void handleEliminar() {
        if (tblClientes != null) {
            Cliente selec = tblClientes.getSelectionModel().getSelectedItem();
            if (selec != null) {
                listaClientes.remove(selec);
                handleLimpiar();
            }
        }
    }

    @FXML
    private void handleLimpiar() {
        if (txtId != null) txtId.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtEmail != null) txtEmail.clear();
        if (txtTelefono != null) txtTelefono.clear();

        if (lblErrorId != null) lblErrorId.setText("");
        if (lblErrorNombre != null) lblErrorNombre.setText("");
        if (lblErrorEmail != null) lblErrorEmail.setText("");
        if (lblErrorTelefono != null) lblErrorTelefono.setText("");

        if (tblClientes != null) tblClientes.getSelectionModel().clearSelection();
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