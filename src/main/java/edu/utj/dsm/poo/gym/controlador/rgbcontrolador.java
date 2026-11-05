package edu.utj.dsm.poo.gym.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class rgbcontrolador {

    // Componentes Sección 1 (TextFields)
    @FXML private TextField txtRed;
    @FXML private TextField txtGreen;
    @FXML private TextField txtBlue;
    @FXML private Pane paneTextField;

    // Componentes Sección 2 (Spinners)
    @FXML private Spinner<Integer> spnRed;
    @FXML private Spinner<Integer> spnGreen;
    @FXML private Spinner<Integer> spnBlue;
    @FXML private Pane paneSpinner;

    @FXML
    public void initialize() {
        // Configurar los rangos de los Spinners de 0 a 255
        configurarSpinner(spnRed);
        configurarSpinner(spnGreen);
        configurarSpinner(spnBlue);

        // Escuchadores de cambio de valor para Spinners
        spnRed.valueProperty().addListener((obs, oldVal, newVal) -> actualizarColorSpinners());
        spnGreen.valueProperty().addListener((obs, oldVal, newVal) -> actualizarColorSpinners());
        spnBlue.valueProperty().addListener((obs, oldVal, newVal) -> actualizarColorSpinners());

        // Escuchadores de cambio de texto para TextFields
        txtRed.textProperty().addListener((obs, oldVal, newVal) -> actualizarColorTextFields());
        txtGreen.textProperty().addListener((obs, oldVal, newVal) -> actualizarColorTextFields());
        txtBlue.textProperty().addListener((obs, oldVal, newVal) -> actualizarColorTextFields());
    }

    private void configurarSpinner(Spinner<Integer> spinner) {
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0);
        spinner.setValueFactory(valueFactory);
    }

    private void actualizarColorSpinners() {
        if (spnRed.getValue() == null || spnGreen.getValue() == null || spnBlue.getValue() == null) return;
        
        int r = spnRed.getValue();
        int g = spnGreen.getValue();
        int b = spnBlue.getValue();
        paneSpinner.setStyle(String.format("-fx-background-color: rgb(%d, %d, %d); -fx-border-color: #777777; -fx-border-width: 1px;", r, g, b));
    }

    private void actualizarColorTextFields() {
        try {
            int r = parseAndClamp(txtRed.getText());
            int g = parseAndClamp(txtGreen.getText());
            int b = parseAndClamp(txtBlue.getText());

            paneTextField.setStyle(String.format("-fx-background-color: rgb(%d, %d, %d); -fx-border-color: #777777; -fx-border-width: 1px;", r, g, b));
        } catch (NumberFormatException e) {
            // Ignora texto no numérico mientras el usuario escribe
        }
    }

    private int parseAndClamp(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        int val = Integer.parseInt(text.trim());
        return Math.max(0, Math.min(255, val));
    }
}