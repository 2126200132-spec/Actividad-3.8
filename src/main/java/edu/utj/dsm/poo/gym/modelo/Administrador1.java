package edu.utj.dsm.poo.gym.modelo;

public class Administrador1 {
    private int id;
    private String nombre;
    private String email;
    private String rol;
    private String rfc;
    private String primerApellido;

    // Constructor vacío (necesitado por la capa de persistencia)
    public Administrador1() {
        this.nombre = "";
        this.email = "";
        this.rol = "";
        this.rfc = "";
        this.primerApellido = "";
    }

    // Constructor completo para la interfaz JavaFX
    public Administrador1(int id, String nombre, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.rfc = "";
        this.primerApellido = "";
    }

    // Getters y Setters requeridos por el controlador y la persistencia
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
}