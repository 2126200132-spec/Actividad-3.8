package edu.utj.dsm.poo.gym.modelo;

public class Membresia {
    private int id;
    private String nombre;
    private double costo;
    private int duracionDias;
    private String estado;

    public Membresia(int id, String nombre, double costo, int duracionDias, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.costo = costo;
        this.duracionDias = duracionDias;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getCosto() { return costo; }
    public int getDuracionDias() { return duracionDias; }
    public String getEstado() { return estado; }
}