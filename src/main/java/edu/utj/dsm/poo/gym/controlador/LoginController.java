package edu.utj.dsm.poo.gym.controlador;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private void handleEntrar() throws IOException {
        // Verifica que los campos no estén vacíos
        if (txtUsuario.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, ingrese un usuario y una contraseña para entrar.");
            alert.showAndWait();
        } else {
            // Si tiene texto, accede a la vista principal
            App.setRoot("Vista4");
        }
    }

    @FXML
    private void handleCerrar() {
        Platform.exit();
        System.exit(0);
    }
}