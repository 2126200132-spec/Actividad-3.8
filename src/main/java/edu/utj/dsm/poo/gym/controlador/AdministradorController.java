package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Administrador;
import edu.utj.dsm.poo.gym.persistencia.AdministradorArrayList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdministradorController implements Initializable {

    // Componentes FXML del Formulario
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cmbRol;

    // Mensajes de Validación
    @FXML private Label lblErrorId;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorEmail;
    @FXML private Label lblErrorRol;

    // Tabla de Datos y Columnas
    @FXML private TableView<Administrador> tblAdministradores;
    @FXML private TableColumn<Administrador, Integer> colId;
    @FXML private TableColumn<Administrador, String> colNombre;
    @FXML private TableColumn<Administrador, String> colEmail;
    @FXML private TableColumn<Administrador, String> colRol;

    // Botones de Acción
    @FXML private Button btnNuevo;
    @FXML private Button btnValidar;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    // Instancia de la Capa de Persistencia y Lista Observable
    private final AdministradorArrayList dao = new AdministradorArrayList();
    private final ObservableList<Administrador> listaObservable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Vinculación de columnas de la tabla con los atributos de Administrador
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        // Opciones del ComboBox de Roles
        cmbRol.setItems(FXCollections.observableArrayList("Administrador", "Gerente", "Recepcionista", "Soporte"));

        tblAdministradores.setItems(listaObservable);

        // Listener para seleccionar un registro de la tabla
        tblAdministradores.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean seleccionado = (newVal != null);
            if (btnEditar != null) btnEditar.setDisable(!seleccionado);
            if (btnEliminar != null) btnEliminar.setDisable(!seleccionado);

            if (newVal != null) {
                txtId.setText(String.valueOf(newVal.getId()));
                txtNombre.setText(newVal.getNombre());
                txtEmail.setText(newVal.getEmail());
                cmbRol.setValue(newVal.getRol());
            }
        });

        // Configuración de validaciones dinámicas
        configurarListenersValidacion();
        actualizarTabla();
        restablecerEstadoBotones();
    }

    private void configurarListenersValidacion() {
        if (txtId != null) txtId.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtNombre != null) txtNombre.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtEmail != null) txtEmail.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (cmbRol != null) cmbRol.valueProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
    }

    private boolean validarFormularioEnTiempoReal() {
        boolean esValido = true;

        // Validación ID
        if (txtId != null && lblErrorId != null) {
            String val = txtId.getText().trim();
            if (val.isEmpty()) {
                lblErrorId.setText("Obligatorio");
                esValido = false;
            } else if (!val.matches("\\d+")) {
                lblErrorId.setText("Solo números");
                esValido = false;
            } else {
                lblErrorId.setText("");
            }
        }

        // Validación Nombre
        if (txtNombre != null && lblErrorNombre != null) {
            String val = txtNombre.getText().trim();
            if (val.isEmpty()) {
                lblErrorNombre.setText("Obligatorio");
                esValido = false;
            } else {
                lblErrorNombre.setText("");
            }
        }

        // Validación Email
        if (txtEmail != null && lblErrorEmail != null) {
            String val = txtEmail.getText().trim();
            if (val.isEmpty()) {
                lblErrorEmail.setText("Obligatorio");
                esValido = false;
            } else if (!val.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                lblErrorEmail.setText("Formato inválido");
                esValido = false;
            } else {
                lblErrorEmail.setText("");
            }
        }

        // Validación Rol
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

    @FXML
    private void handleNuevo() {
        handleLimpiar();
    }

    @FXML
    private void handleValidar() {
        if (validarFormularioEnTiempoReal()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Validación Exitosa", "Los datos del administrador son válidos.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de Validación", "Por favor revisa los campos señalados.");
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

            Administrador nuevoAdmin = new Administrador(id, nombre, email, rol);

            if (dao.agregar(nuevoAdmin)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Administrador registrado correctamente.");
                actualizarTabla();
                handleLimpiar();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar el registro.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Datos", "El ID debe ser un número entero.");
        }
    }

    @FXML
    private void handleEditar() {
        int indiceSeleccionado = tblAdministradores.getSelectionModel().getSelectedIndex();
        if (indiceSeleccionado < 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Selecciona un elemento de la tabla para editar.");
            return;
        }

        if (!validarFormularioEnTiempoReal()) return;

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String rol = cmbRol.getValue();

            Administrador adminActualizado = new Administrador(id, nombre, email, rol);
            Administrador adminExistente = tblAdministradores.getSelectionModel().getSelectedItem();

            if (dao.actualizar(adminActualizado, indiceSeleccionado) || dao.eliminar(adminExistente) && dao.agregar(adminActualizado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Administrador actualizado correctamente.");
                actualizarTabla();
                handleLimpiar();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el registro.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Datos", "El ID debe ser un número entero.");
        }
    }

    @FXML
    private void handleEliminar() {
        Administrador seleccionado = tblAdministradores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            if (dao.eliminar(seleccionado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Administrador eliminado.");
                actualizarTabla();
                handleLimpiar();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el administrador.");
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

    private void actualizarTabla() {
        listaObservable.setAll(dao.listar());
    }

    private void restablecerEstadoBotones() {
        if (btnNuevo != null) btnNuevo.setDisable(false);
        if (btnValidar != null) btnValidar.setDisable(false);
        if (btnGuardar != null) btnGuardar.setDisable(true);
        if (btnEditar != null) btnEditar.setDisable(true);
        if (btnEliminar != null) btnEliminar.setDisable(true);
        if (btnLimpiar != null) btnLimpiar.setDisable(false);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}