module cotizacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Exporta y permite reflexión en el paquete de controladores y App
    exports edu.utj.dsm.poo.gym.controlador;
    opens edu.utj.dsm.poo.gym.controlador to javafx.fxml, javafx.graphics;

    // Modelos para TableView
    exports edu.utj.dsm.poo.gym.modelo;
    opens edu.utj.dsm.poo.gym.modelo to javafx.base;

    // Persistencia
    exports edu.utj.dsm.poo.gym.persistencia;
    opens edu.utj.dsm.poo.gym.persistencia to javafx.fxml;
}