package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductoController {

    // Componentes de la interfaz
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox<String> cmbEstado;

    // Labels de validación
    @FXML private Label lblErrorId;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorPrecio;
    @FXML private Label lblErrorCantidad;
    @FXML private Label lblErrorEstado;

    // Tabla
    @FXML private TableView<Producto> tblProductos;
    @FXML private TableColumn<Producto, Integer> colId;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colCantidad;
    @FXML private TableColumn<Producto, String> colEstado;

    // Botones
    @FXML private Button btnNuevo;
    @FXML private Button btnValidar;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Inicialización de columnas de la tabla
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colPrecio != null) colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        if (colCantidad != null) colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        if (colEstado != null) colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        if (tblProductos != null) {
            tblProductos.setItems(listaProductos);

            // Escuchador de selección en la tabla para habilitar/deshabilitar Editar y Eliminar
            tblProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean registroSeleccionado = (newVal != null);
                if (btnEditar != null) btnEditar.setDisable(!registroSeleccionado);
                if (btnEliminar != null) btnEliminar.setDisable(!registroSeleccionado);

                if (newVal != null) {
                    if (txtId != null) txtId.setText(String.valueOf(newVal.getId()));
                    if (txtNombre != null) txtNombre.setText(newVal.getNombre());
                    if (txtPrecio != null) txtPrecio.setText(String.valueOf(newVal.getPrecio()));
                    if (txtCantidad != null) txtCantidad.setText(String.valueOf(newVal.getCantidad()));
                    if (cmbEstado != null) cmbEstado.setValue(newVal.getEstado());
                }
            });
        }

        // Llenar ComboBox de estado
        if (cmbEstado != null) {
            cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo", "Agotado"));
            cmbEstado.valueProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        }

        // Configuración de validaciones en tiempo real
        configurarListenersValidacion();

        // Estado inicial de los botones
        restablecerEstadoBotones();
    }

    private void configurarListenersValidacion() {
        if (txtId != null) {
            txtId.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        }
        if (txtNombre != null) {
            txtNombre.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        }
        if (txtPrecio != null) {
            txtPrecio.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        }
        if (txtCantidad != null) {
            txtCantidad.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        }
    }

    // Validaciones en tiempo real de cada campo
    private boolean validarFormularioEnTiempoReal() {
        boolean esValido = true;

        // Validar ID
        if (txtId != null && lblErrorId != null) {
            String valorId = txtId.getText().trim();
            if (valorId.isEmpty()) {
                lblErrorId.setText("Obligatorio");
                esValido = false;
            } else if (!valorId.matches("\\d+")) {
                lblErrorId.setText("Solo enteros");
                esValido = false;
            } else {
                lblErrorId.setText("");
            }
        }

        // Validar Nombre
        if (txtNombre != null && lblErrorNombre != null) {
            String valorNombre = txtNombre.getText().trim();
            if (valorNombre.isEmpty()) {
                lblErrorNombre.setText("Obligatorio");
                esValido = false;
            } else {
                lblErrorNombre.setText("");
            }
        }

        // Validar Precio
        if (txtPrecio != null && lblErrorPrecio != null) {
            String valorPrecio = txtPrecio.getText().trim();
            if (valorPrecio.isEmpty()) {
                lblErrorPrecio.setText("Obligatorio");
                esValido = false;
            } else if (!valorPrecio.matches("\\d+(\\.\\d+)?")) {
                lblErrorPrecio.setText("Ej. 15.50");
                esValido = false;
            } else {
                lblErrorPrecio.setText("");
            }
        }

        // Validar Cantidad
        if (txtCantidad != null && lblErrorCantidad != null) {
            String valorCantidad = txtCantidad.getText().trim();
            if (valorCantidad.isEmpty()) {
                lblErrorCantidad.setText("Obligatorio");
                esValido = false;
            } else if (!valorCantidad.matches("\\d+")) {
                lblErrorCantidad.setText("Solo enteros");
                esValido = false;
            } else {
                lblErrorCantidad.setText("");
            }
        }

        // Validar Estado
        if (cmbEstado != null && lblErrorEstado != null) {
            if (cmbEstado.getValue() == null) {
                lblErrorEstado.setText("Selecciona estado");
                esValido = false;
            } else {
                lblErrorEstado.setText("");
            }
        }

        // Habilitar / Deshabilitar botón Guardar dinámicamente
        if (btnGuardar != null) {
            btnGuardar.setDisable(!esValido);
        }

        return esValido;
    }

    @FXML
    private void handleNuevo() {
        handleLimpiar();
    }

    @FXML
    private void handleValidar() {
        boolean todoValido = validarFormularioEnTiempoReal();
        if (todoValido) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Validación Exitosa", 
                "Todos los campos cumplen con los formatos y requisitos esperados.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Errores de Formulario", 
                "Por favor, corrige los errores señalados debajo de los campos.");
        }
    }

    @FXML
    private void handleGuardar() {
        if (!validarFormularioEnTiempoReal()) {
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            String estado = cmbEstado.getValue();

            Producto nuevo = new Producto(id, nombre, precio, cantidad, estado);
            listaProductos.add(nuevo);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro guardado", "El producto fue agregado a la lista.");
            handleLimpiar();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al procesar la información.");
        }
    }

    @FXML
    private void handleEditar() {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Acción de Edición", 
            "Comportamiento de edición simulado visualmente para el registro seleccionado.");
    }

    @FXML
    private void handleEliminar() {
        if (tblProductos != null) {
            Producto seleccionado = tblProductos.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                listaProductos.remove(seleccionado);
                handleLimpiar();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Registro eliminado", "El registro ha sido eliminado.");
            }
        }
    }

    @FXML
    private void handleLimpiar() {
        if (txtId != null) txtId.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtPrecio != null) txtPrecio.clear();
        if (txtCantidad != null) txtCantidad.clear();
        if (cmbEstado != null) cmbEstado.setValue(null);

        limpiarMensajesError();
        if (tblProductos != null) tblProductos.getSelectionModel().clearSelection();

        restablecerEstadoBotones();
    }

    private void limpiarMensajesError() {
        if (lblErrorId != null) lblErrorId.setText("");
        if (lblErrorNombre != null) lblErrorNombre.setText("");
        if (lblErrorPrecio != null) lblErrorPrecio.setText("");
        if (lblErrorCantidad != null) lblErrorCantidad.setText("");
        if (lblErrorEstado != null) lblErrorEstado.setText("");
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
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}