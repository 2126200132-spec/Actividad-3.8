package edu.utj.dsm.poo.gym.servicio;

import edu.utj.dsm.poo.gym.modelo.Listas;
import edu.utj.dsm.poo.gym.persistencia.IRepositorio;
import edu.utj.dsm.poo.gym.persistencia.ListasRepositoryArrayList;
import java.io.File;
import java.util.List;

public class ListasService implements IServicio<Listas> {
    private final IRepositorio<Listas> repositorio;

    public ListasService() {
        this.repositorio = new ListasRepositoryArrayList();
    }

    @Override
    public boolean registrar(Listas entidad) {
        if (entidad == null || entidad.getId() <= 0) {
            return false;
        }
        if (entidad.getNombre() == null || entidad.getNombre().trim().isEmpty()) {
            return false;
        }
        if (entidad.getPrecio() < 0) {
            return false;
        }
        if (!validarEstructuraImagen(entidad.getRutaImagen())) {
            return false;
        }
        return this.repositorio.guardar(entidad);
    }

    @Override
    public boolean actualizar(Listas entidad) {
        if (entidad == null || entidad.getId() <= 0) {
            return false;
        }
        if (entidad.getNombre() == null || entidad.getNombre().trim().isEmpty()) {
            return false;
        }
        if (entidad.getPrecio() < 0) {
            return false;
        }
        if (!validarEstructuraImagen(entidad.getRutaImagen())) {
            return false;
        }
        if (this.repositorio.buscarPorId(entidad.getId()) == null) {
            return false;
        }
        return this.repositorio.actualizar(entidad);
    }

    @Override
    public Listas buscarPorId(int id) {
        if (id <= 0) return null;
        return this.repositorio.buscarPorId(id);
    }

    @Override
    public boolean eliminar(int id) {
        if (id <= 0) return false;
        Listas existente = this.repositorio.buscarPorId(id);
        if (existente == null) return false;
        return this.repositorio.eliminar(id);
    }

    @Override
    public List<Listas> listarTodos() {
        return this.repositorio.listarTodos();
    }

    @Override
    public List<Listas> listarActivos() {
        return this.repositorio.listarActivos();
    }

    private boolean validarEstructuraImagen(String rutaImagen) {
        if (rutaImagen == null || rutaImagen.trim().isEmpty()) {
            return true;
        }
        File archivo = new File(rutaImagen);
        return archivo.exists() && archivo.isFile();
    }
}