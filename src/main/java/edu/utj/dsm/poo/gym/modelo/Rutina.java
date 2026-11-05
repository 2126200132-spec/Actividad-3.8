package edu.utj.dsm.poo.gym.modelo;

public class Rutina {
    private int id;
    private String nombre;
    private String objetivo;
    private String repeticiones;
    private String nivel;

    public Rutina(int id, String nombre, String objetivo, String repeticiones, String nivel) {
        this.id = id;
        this.nombre = nombre;
        this.objetivo = objetivo;
        this.repeticiones = repeticiones;
        this.nivel = nivel;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getObjetivo() { return objetivo; }
    public String getRepeticiones() { return repeticiones; }
    public String getNivel() { return nivel; }
}