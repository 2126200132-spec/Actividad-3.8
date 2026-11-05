package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Listas;
import edu.utj.dsm.poo.gym.servicio.IServicio;
import edu.utj.dsm.poo.gym.servicio.ListasService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class listasController implements Initializable {

    @FXML private TableView<Listas> tblListas;
    @FXML private TableColumn<Listas, Integer> colId;
    @FXML private TableColumn<Listas, String> colNombre;
    @FXML private TableColumn<Listas, Double> colPrecio;
    @FXML private TableColumn<Listas, String> colEstado;

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private ComboBox<String> cmbEstado;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final IServicio<Listas> listasService = new ListasService();
    private final ObservableList<Listas> listaObservable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cmbEstado.setItems(FXCollections.observableArrayList("ACTIVO", "INACTIVO", "DISPONIBLE", "APARTADO"));
        cmbEstado.setValue("ACTIVO");

        tblListas.setItems(listaObservable);
        tblListas.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> mostrarDetalle(newValue)
        );

        actualizarTabla();
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        if (!validarEntradas()) return;

        int id = Integer.parseInt(txtId.getText().trim());
        String nombre = txtNombre.getText().trim();
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        String estado = cmbEstado.getValue();

        Listas nuevoItem = new Listas(id, nombre, precio, estado, "");

        if (listasService.registrar(nuevoItem)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Registro guardado correctamente.");
            actualizarTabla();
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar el registro. El ID ya existe o los datos son inválidos.");
        }
    }

    @FXML
    private void handleActualizar(ActionEvent event) {
        if (!validarEntradas()) return;

        int id = Integer.parseInt(txtId.getText().trim());
        String nombre = txtNombre.getText().trim();
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        String estado = cmbEstado.getValue();

        Listas itemActualizar = new Listas(id, nombre, precio, estado, "");

        if (listasService.actualizar(itemActualizar)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Registro actualizado correctamente.");
            actualizarTabla();
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontró el registro a actualizar.");
        }
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        String strId = txtId.getText().trim();
        if (strId.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Seleccione un elemento o ingrese un ID válido.");
            return;
        }

        try {
            int id = Integer.parseInt(strId);
            if (listasService.eliminar(id)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Registro desactivado correctamente.");
                actualizarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontró el registro a eliminar.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Dato Inválido", "El ID debe ser un número entero.");
        }
    }

    @FXML
    private void handleLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    private boolean validarEntradas() {
        String strId = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String strPrecio = txtPrecio.getText().trim();

        if (strId.isEmpty() || nombre.isEmpty() || strPrecio.isEmpty() || cmbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor completa todos los campos.");
            return false;
        }

        try {
            int id = Integer.parseInt(strId);
            if (id <= 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "ID Inválido", "El ID debe ser un número entero mayor a 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Dato Inválido", "El ID solo debe contener números enteros.");
            return false;
        }

        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Nombre Inválido", "El campo Nombre solo debe contener letras.");
            return false;
        }

        try {
            double precio = Double.parseDouble(strPrecio);
            if (precio <= 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Precio Inválido", "El Precio debe ser mayor a 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Dato Inválido", "El Precio solo debe contener números.");
            return false;
        }

        return true;
    }

    private void mostrarDetalle(Listas item) {
        if (item != null) {
            txtId.setText(String.valueOf(item.getId()));
            txtId.setEditable(false);
            txtNombre.setText(item.getNombre());
            txtPrecio.setText(String.valueOf(item.getPrecio()));
            cmbEstado.setValue(item.getEstado());
        }
    }

    private void actualizarTabla() {
        listaObservable.setAll(listasService.listarTodos());
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtId.setEditable(true);
        txtNombre.setText("");
        txtPrecio.setText("");
        cmbEstado.setValue("ACTIVO");
        tblListas.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}