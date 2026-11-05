package edu.utj.dsm.poo.gym.modelo;

public class Entrenador {
    private int id;
    private String nombre;
    private String especialidad;
    private String telefono;

    public Entrenador(int id, String nombre, String especialidad, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }
    public String getTelefono() { return telefono; }
}