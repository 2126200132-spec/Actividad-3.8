package edu.utj.dsm.poo.gym.modelo;

public class Actividad {
    private int id;
    private String nombre;
    private String horario;
    private int capacidad;
    private String estado;

    public Actividad(int id, String nombre, String horario, int capacidad, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.horario = horario;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getHorario() { return horario; }
    public int getCapacidad() { return capacidad; }
    public String getEstado() { return estado; }
}