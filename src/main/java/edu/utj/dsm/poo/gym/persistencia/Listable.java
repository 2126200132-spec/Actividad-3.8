package edu.utj.dsm.poo.gym.persistencia;

import java.util.ArrayList;

public interface Listable<T> {
    public boolean agregar(T elemento);
    public boolean actualizar(T elemento, int posicion);
    public boolean eliminar(T elemento);
    public ArrayList<T> listar();
    public ArrayList<T> consultar(String dato);
    public T traer(String dato);
}