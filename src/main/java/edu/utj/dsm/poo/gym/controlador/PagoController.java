package edu.utj.dsm.poo.gym.controlador;

import edu.utj.dsm.poo.gym.modelo.Pago;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PagoController {

    @FXML private TextField txtId;
    @FXML private TextField txtSocio;
    @FXML private TextField txtMonto;
    @FXML private TextField txtFecha;
    @FXML private ComboBox<String> cmbMetodo;

    @FXML private Label lblErrorId;
    @FXML private Label lblErrorSocio;
    @FXML private Label lblErrorMonto;
    @FXML private Label lblErrorFecha;
    @FXML private Label lblErrorMetodo;

    @FXML private TableView<Pago> tblPagos;
    @FXML private TableColumn<Pago, Integer> colId;
    @FXML private TableColumn<Pago, String> colSocio;
    @FXML private TableColumn<Pago, Double> colMonto;
    @FXML private TableColumn<Pago, String> colFecha;
    @FXML private TableColumn<Pago, String> colMetodo;

    @FXML private Button btnNuevo;
    @FXML private Button btnValidar;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final ObservableList<Pago> listaPagos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (colSocio != null) colSocio.setCellValueFactory(new PropertyValueFactory<>("socio"));
        if (colMonto != null) colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        if (colFecha != null) colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        if (colMetodo != null) colMetodo.setCellValueFactory(new PropertyValueFactory<>("metodo"));

        if (tblPagos != null) {
            tblPagos.setItems(listaPagos);
            tblPagos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                boolean sel = (newVal != null);
                if (btnEditar != null) btnEditar.setDisable(!sel);
                if (btnEliminar != null) btnEliminar.setDisable(!sel);

                if (newVal != null) {
                    if (txtId != null) txtId.setText(String.valueOf(newVal.getId()));
                    if (txtSocio != null) txtSocio.setText(newVal.getSocio());
                    if (txtMonto != null) txtMonto.setText(String.valueOf(newVal.getMonto()));
                    if (txtFecha != null) txtFecha.setText(newVal.getFecha());
                    if (cmbMetodo != null) cmbMetodo.setValue(newVal.getMetodo());
                }
            });
        }

        if (cmbMetodo != null) {
            cmbMetodo.setItems(FXCollections.observableArrayList("Efectivo", "Tarjeta Crédito/Débito", "Transferencia"));
            cmbMetodo.valueProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        }

        configurarListenersValidacion();
        restablecerEstadoBotones();
    }

    private void configurarListenersValidacion() {
        if (txtId != null) txtId.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtSocio != null) txtSocio.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtMonto != null) txtMonto.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
        if (txtFecha != null) txtFecha.textProperty().addListener((obs, oldVal, newVal) -> validarFormularioEnTiempoReal());
    }

    private boolean validarFormularioEnTiempoReal() {
        boolean esValido = true;

        if (txtId != null && lblErrorId != null) {
            String val = txtId.getText().trim();
            if (val.isEmpty()) { lblErrorId.setText("Obligatorio"); esValido = false; }
            else if (!val.matches("\\d+")) { lblErrorId.setText("Solo enteros"); esValido = false; }
            else { lblErrorId.setText(""); }
        }

        if (txtSocio != null && lblErrorSocio != null) {
            String val = txtSocio.getText().trim();
            if (val.isEmpty()) { lblErrorSocio.setText("Obligatorio"); esValido = false; }
            else { lblErrorSocio.setText(""); }
        }

        if (txtMonto != null && lblErrorMonto != null) {
            String val = txtMonto.getText().trim();
            if (val.isEmpty()) { lblErrorMonto.setText("Obligatorio"); esValido = false; }
            else if (!val.matches("\\d+(\\.\\d+)?")) { lblErrorMonto.setText("Decimal positivo"); esValido = false; }
            else { lblErrorMonto.setText(""); }
        }

        if (txtFecha != null && lblErrorFecha != null) {
            String val = txtFecha.getText().trim();
            if (val.isEmpty()) { lblErrorFecha.setText("Obligatorio"); esValido = false; }
            else if (!val.matches("\\d{4}-\\d{2}-\\d{2}")) { lblErrorFecha.setText("Formato AAAA-MM-DD"); esValido = false; }
            else { lblErrorFecha.setText(""); }
        }

        if (cmbMetodo != null && lblErrorMetodo != null) {
            if (cmbMetodo.getValue() == null) { lblErrorMetodo.setText("Selecciona"); esValido = false; }
            else { lblErrorMetodo.setText(""); }
        }

        if (btnGuardar != null) btnGuardar.setDisable(!esValido);
        return esValido;
    }

    @FXML private void handleNuevo() { handleLimpiar(); }

    @FXML
    private void handleValidar() {
        if (validarFormularioEnTiempoReal()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Validación Exitosa", "Los datos del pago están correctos.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de Validación", "Corrige las advertencias marcadas.");
        }
    }

    @FXML
    private void handleGuardar() {
        if (!validarFormularioEnTiempoReal()) return;
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String socio = txtSocio.getText().trim();
            double monto = Double.parseDouble(txtMonto.getText().trim());
            String fecha = txtFecha.getText().trim();
            String metodo = cmbMetodo.getValue();

            listaPagos.add(new Pago(id, socio, monto, fecha, metodo));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Pago registrado correctamente.");
            handleLimpiar();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al guardar el pago.");
        }
    }

    @FXML private void handleEditar() { mostrarAlerta(Alert.AlertType.INFORMATION, "Edición", "Edición visual efectuada."); }

    @FXML
    private void handleEliminar() {
        if (tblPagos != null) {
            Pago sel = tblPagos.getSelectionModel().getSelectedItem();
            if (sel != null) { listaPagos.remove(sel); handleLimpiar(); }
        }
    }

    @FXML
    private void handleLimpiar() {
        if (txtId != null) txtId.clear();
        if (txtSocio != null) txtSocio.clear();
        if (txtMonto != null) txtMonto.clear();
        if (txtFecha != null) txtFecha.clear();
        if (cmbMetodo != null) cmbMetodo.setValue(null);

        if (lblErrorId != null) lblErrorId.setText("");
        if (lblErrorSocio != null) lblErrorSocio.setText("");
        if (lblErrorMonto != null) lblErrorMonto.setText("");
        if (lblErrorFecha != null) lblErrorFecha.setText("");
        if (lblErrorMetodo != null) lblErrorMetodo.setText("");

        if (tblPagos != null) tblPagos.getSelectionModel().clearSelection();
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