package edu.utj.dsm.poo.gym.modelo;

public class Pago {
    private int id;
    private String socio;
    private double monto;
    private String fecha;
    private String metodo;

    public Pago(int id, String socio, double monto, String fecha, String metodo) {
        this.id = id;
        this.socio = socio;
        this.monto = monto;
        this.fecha = fecha;
        this.metodo = metodo;
    }

    public int getId() { return id; }
    public String getSocio() { return socio; }
    public double getMonto() { return monto; }
    public String getFecha() { return fecha; }
    public String getMetodo() { return metodo; }
}