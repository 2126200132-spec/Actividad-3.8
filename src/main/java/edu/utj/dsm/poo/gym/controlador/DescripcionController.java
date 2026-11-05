package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Producto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class DescripcionController implements Initializable {

    @FXML private TableView<Producto> tableView;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colCantidad;
    @FXML private TableColumn<Producto, String> colDescripcion;

    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtDescripcion;

    @FXML private Button btnAdd;
    @FXML private Button btnDel;
    @FXML private Button btnReset;

    private final ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Mapeo de columnas con los getters del objeto Producto
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Asignar lista a la tabla
        tableView.setItems(listaProductos);

        // Listener para llenar los campos al hacer clic en una fila de la tabla
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtNombre.setText(newSelection.getNombre());
                txtPrecio.setText(String.valueOf(newSelection.getPrecio()));
                txtCantidad.setText(String.valueOf(newSelection.getCantidad()));
                txtDescripcion.setText(newSelection.getDescripcion());
            }
        });
    }

    @FXML
    private void handleBtnAdd() {
        String nombre = txtNombre.getText().trim();
        String strPrecio = txtPrecio.getText().trim();
        String strCantidad = txtCantidad.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        if (nombre.isEmpty() || strPrecio.isEmpty() || strCantidad.isEmpty() || descripcion.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor llena todos los campos.");
            return;
        }

        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Dato inválido", "El campo Nombre solo debe contener letras.");
            return;
        }

        try {
            double precio = Double.parseDouble(strPrecio);
            int cantidad = Integer.parseInt(strCantidad);

            if (precio <= 0 || cantidad <= 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Dato inválido", "El precio y la cantidad deben ser mayores a 0.");
                return;
            }

            // Crear y agregar el nuevo producto a la lista observable
            Producto p = new Producto(nombre, precio, cantidad, descripcion);
            listaProductos.add(p);

            handleBtnReset();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Dato inválido", "Precio debe ser un número decimal y Cantidad un número entero.");
        }
    }

    @FXML
    private void handleBtnDel() {
        Producto seleccionado = tableView.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaProductos.remove(seleccionado);
            handleBtnReset();
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Selecciona un elemento de la tabla para borrar.");
        }
    }

    @FXML
    private void handleBtnReset() {
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        txtDescripcion.clear();
        tableView.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}