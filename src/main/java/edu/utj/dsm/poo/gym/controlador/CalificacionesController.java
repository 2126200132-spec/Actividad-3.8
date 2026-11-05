package edu.utj.dsm.poo.gym.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalificacionesController {

    // --- UNIDAD 1 ---
    @FXML private TextField txtAct1_1, txtAct1_2, txtAct1_3, txtExamen1, txtSer1;
    @FXML private Label lblRes1;

    // --- UNIDAD 2 ---
    @FXML private TextField txtAct2_1, txtAct2_2, txtAct2_3, txtAct2_4, txtAct2_5, txtAct2_6, txtExamen2, txtSer2;
    @FXML private Label lblRes2;

    // --- UNIDAD 3 ---
    @FXML private TextField txtAct3_1, txtAct3_2, txtAct3_3, txtAct3_4, txtAct3_5, txtAct3_6, txtExamen3, txtSer3;
    @FXML private Label lblRes3;

    // --- UNIDAD 4 ---
    @FXML private TextField txtAct4_1, txtAct4_2, txtAct4_3, txtAct4_4, txtAct4_5, txtAct4_6, txtAct4_7, txtAct4_8, txtExamen4, txtSer4;
    @FXML private Label lblRes4;

    // --- RESULTADO FINAL ---
    @FXML private Label lblResFinal;

    // Variables para almacenar las calificaciones de cada unidad
    private double promU1 = -1;
    private double promU2 = -1;
    private double promU3 = -1;
    private double promU4 = -1;

    // Métodos auxiliares para parsear texto a número
    private double obtenerValor(TextField field) {
        if (field == null || field.getText().trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(field.getText().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @FXML
    public void calcularU1(ActionEvent event) {
        double act1 = obtenerValor(txtAct1_1);
        double act2 = obtenerValor(txtAct1_2);
        double act3 = obtenerValor(txtAct1_3);
        double examen = obtenerValor(txtExamen1);
        double ser = obtenerValor(txtSer1);

        double promActividades = (act1 + act2 + act3) / 3.0;
        
        // Ponderación de ejemplo (puedes ajustarla según tu criterio):
        // 40% Actividades, 40% Examen, 20% SER
        promU1 = (promActividades * 0.40) + (examen * 0.40) + (ser * 0.20);
        lblRes1.setText(String.format("Resultado: %.2f", promU1));
    }

    @FXML
    public void calcularU2(ActionEvent event) {
        double act1 = obtenerValor(txtAct2_1);
        double act2 = obtenerValor(txtAct2_2);
        double act3 = obtenerValor(txtAct2_3);
        double act4 = obtenerValor(txtAct2_4);
        double act5 = obtenerValor(txtAct2_5);
        double act6 = obtenerValor(txtAct2_6);
        double examen = obtenerValor(txtExamen2);
        double ser = obtenerValor(txtSer2);

        double promActividades = (act1 + act2 + act3 + act4 + act5 + act6) / 6.0;
        promU2 = (promActividades * 0.40) + (examen * 0.40) + (ser * 0.20);
        lblRes2.setText(String.format("Resultado: %.2f", promU2));
    }

    @FXML
    public void calcularU3(ActionEvent event) {
        double act1 = obtenerValor(txtAct3_1);
        double act2 = obtenerValor(txtAct3_2);
        double act3 = obtenerValor(txtAct3_3);
        double act4 = obtenerValor(txtAct3_4);
        double act5 = obtenerValor(txtAct3_5);
        double act6 = obtenerValor(txtAct3_6);
        double examen = obtenerValor(txtExamen3);
        double ser = obtenerValor(txtSer3);

        double promActividades = (act1 + act2 + act3 + act4 + act5 + act6) / 6.0;
        promU3 = (promActividades * 0.40) + (examen * 0.40) + (ser * 0.20);
        lblRes3.setText(String.format("Resultado: %.2f", promU3));
    }

    @FXML
    public void calcularU4(ActionEvent event) {
        double act1 = obtenerValor(txtAct4_1);
        double act2 = obtenerValor(txtAct4_2);
        double act3 = obtenerValor(txtAct4_3);
        double act4 = obtenerValor(txtAct4_4);
        double act5 = obtenerValor(txtAct4_5);
        double act6 = obtenerValor(txtAct4_6);
        double act7 = obtenerValor(txtAct4_7);
        double act8 = obtenerValor(txtAct4_8);
        double examen = obtenerValor(txtExamen4);
        double ser = obtenerValor(txtSer4);

        double promActividades = (act1 + act2 + act3 + act4 + act5 + act6 + act7 + act8) / 8.0;
        promU4 = (promActividades * 0.40) + (examen * 0.40) + (ser * 0.20);
        lblRes4.setText(String.format("Resultado: %.2f", promU4));
    }

    @FXML
    public void calcularCalificacionFinal(ActionEvent event) {
        if (promU1 < 0 || promU2 < 0 || promU3 < 0 || promU4 < 0) {
            lblResFinal.setText("Por favor, calcula primero todas las unidades.");
            return;
        }

        double finalProm = (promU1 + promU2 + promU3 + promU4) / 4.0;
        lblResFinal.setText(String.format("Promedio Final: %.2f", finalProm));
    }
}