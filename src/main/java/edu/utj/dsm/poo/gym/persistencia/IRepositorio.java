package edu.utj.dsm.poo.gym.persistencia;

import java.util.List;

public interface IRepositorio<T> {
    boolean guardar(T entidad);
    T buscarPorId(int id);
    List<T> listarTodos();
    List<T> listarActivos();
    boolean actualizar(T entidad);
    boolean eliminar(int id);
}