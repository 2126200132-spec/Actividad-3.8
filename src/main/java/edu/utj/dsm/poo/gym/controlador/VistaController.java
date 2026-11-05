package edu.utj.dsm.poo.gym.controlador;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class VistaController {

    @FXML
    private BorderPane mainContainer;

    @FXML
    private void abrirCliente() throws IOException {
        mainContainer.setCenter(App.loadFXML("Cliente"));
    }

    @FXML
    private void abrirProducto() throws IOException {
        mainContainer.setCenter(App.loadFXML("Producto"));
    }

    @FXML
    private void abrirMembresia() throws IOException {
        mainContainer.setCenter(App.loadFXML("Membresia"));
    }

    @FXML
    private void handleSalir() throws IOException {
        // Regresa a la ventana de Login sin cerrar la aplicación
        App.setRoot("Login");
    }
}