package edu.utj.dsm.poo.gym.modelo;

public class Equipo {
    private int id;
    private String nombre;
    private int cantidad;
    private String ubicacion;
    private String estado;

    public Equipo(int id, String nombre, int cantidad, String ubicacion, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public String getUbicacion() { return ubicacion; }
    public String getEstado() { return estado; }
}