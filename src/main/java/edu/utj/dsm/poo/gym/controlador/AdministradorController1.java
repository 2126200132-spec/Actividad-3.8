package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Administrador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdministradorController1 {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cmbRol;

    @FXML private Label lblErrorId;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorEmail;
    @FXML private Label lblErrorRol;

    @FXML private TableView<Administrador> tblAdministradores;
    @FXML private TableColumn<Administrador, Integer> colId;
    @FXML private TableColumn<Administrador, String> colNombre;
    @FXML private TableColumn<Administrador, String> colEmail;
    @FXML private TableColumn<Administrador, String> colRol;

    @FXML private Button btnNuevo;
    @FXML private Button btnValidar;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final ObservableList<Administrador> listaAdministradores = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colEmail != null) colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (colRol != null) colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        if (tblAdministradores != null) {
            tblAdministradores.setItems(listaAdministradores);
            tblAdministradores.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean sel = (newVal != null);
                if (btnEditar != null) btnEditar.setDisable(!sel);
                if (btnEliminar != null) btnEliminar.setDisable(!sel);

                if (newVal != null) {
                    if (txtId != null) txtId.setText(String.valueOf(newVal.getId()));
                    if (txtNombre != null) txtNombre.setText(newVal.getNombre());
                    if (txtEmail != null) txtEmail.setText(newVal.getEmail());
                    if (cmbRol != null) cmbRol.setValue(newVal.getRol());
                }
            });
        }

        if (cmbRol != null) {
            cmbRol.setItems(FXCollections.observableArrayList("Super Admin", "Gerente", "Recepcionista", "Soporte"));
            cmbRol.valueProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        }

        configurarListenersValidacion();
        restablecerEstadoBotones();
    }

    private void configurarListenersValidacion() {
        if (txtId != null) txtId.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtNombre != null) txtNombre.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtEmail != null) txtEmail.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
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

        // Validar Email
        if (txtEmail != null && lblErrorEmail != null) {
            String val = txtEmail.getText().trim();
            if (val.isEmpty()) { 
                lblErrorEmail.setText("Obligatorio"); 
                esValido = false; 
            } else if (!val.matches("^[A-Za-z0-9+_.-]+@(.+)$")) { 
                lblErrorEmail.setText("Formato inválido (ej. admin@gym.com)"); 
                esValido = false; 
            } else { 
                lblErrorEmail.setText(""); 
            }
        }

        // Validar ComboBox Rol
        if (cmbRol != null && lblErrorRol != null) {
            if (cmbRol.getValue() == null) { 
                lblErrorRol.setText("Selecciona un rol"); 
                esValido = false; 
            } else { 
                lblErrorRol.setText(""); 
            }
        }

        if (btnGuardar != null) btnGuardar.setDisable(!esValido);
        return esValido;
    }

    @FXML private void handleNuevo() { handleLimpiar(); }

    @FXML
    private void handleValidar() {
        if (validarFormularioEnTiempoReal()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Validación Exitosa", "Los datos del administrador son válidos.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de Validación", "Por favor revisa los mensajes indicados.");
        }
    }

    @FXML
    private void handleGuardar() {
        if (!validarFormularioEnTiempoReal()) return;
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String rol = cmbRol.getValue();

            listaAdministradores.add(new Administrador(id, nombre, email, rol));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Administrador registrado correctamente.");
            handleLimpiar();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al procesar el registro.");
        }
    }

    @FXML private void handleEditar() { mostrarAlerta(Alert.AlertType.INFORMATION, "Edición", "Edición visual ejecutada."); }

    @FXML
    private void handleEliminar() {
        if (tblAdministradores != null) {
            Administrador sel = tblAdministradores.getSelectionModel().getSelectedItem();
            if (sel != null) { 
                listaAdministradores.remove(sel); 
                handleLimpiar(); 
            }
        }
    }

    @FXML
    private void handleLimpiar() {
        if (txtId != null) txtId.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtEmail != null) txtEmail.clear();
        if (cmbRol != null) cmbRol.setValue(null);

        if (lblErrorId != null) lblErrorId.setText("");
        if (lblErrorNombre != null) lblErrorNombre.setText("");
        if (lblErrorEmail != null) lblErrorEmail.setText("");
        if (lblErrorRol != null) lblErrorRol.setText("");

        if (tblAdministradores != null) tblAdministradores.getSelectionModel().clearSelection();
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