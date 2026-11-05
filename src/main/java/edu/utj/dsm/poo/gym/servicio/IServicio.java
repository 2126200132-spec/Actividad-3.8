package edu.utj.dsm.poo.gym.servicio;

import java.util.List;

public interface IServicio<T> {
    boolean registrar(T entidad);
    T buscarPorId(int id);
    List<T> listarTodos();
    List<T> listarActivos();
    boolean actualizar(T entidad);
    boolean eliminar(int id);
}