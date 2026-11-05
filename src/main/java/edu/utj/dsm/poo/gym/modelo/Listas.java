package edu.utj.dsm.poo.gym.modelo;

public class Listas {
    private int id;
    private String nombre;
    private double precio;
    private String estado;
    private String rutaImagen;

    public Listas() {
    }

    public Listas(int id, String nombre, double precio, String estado, String rutaImagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = estado;
        this.rutaImagen = rutaImagen;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
}