package edu.utj.dsm.poo.gym.persistencia;

import edu.utj.dsm.poo.gym.modelo.Listas;
import java.util.ArrayList;
import java.util.List;

public class ListasRepositoryArrayList implements IRepositorio<Listas> {
    private final List<Listas> listaElementos;

    public ListasRepositoryArrayList() {
        this.listaElementos = new ArrayList<>();
    }

    @Override
    public boolean guardar(Listas entidad) {
        if (entidad == null || buscarPorId(entidad.getId()) != null) {
            return false;
        }
        return this.listaElementos.add(entidad);
    }

    @Override
    public Listas buscarPorId(int id) {
        for (Listas item : listaElementos) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<Listas> listarTodos() {
        return new ArrayList<>(this.listaElementos);
    }

    @Override
    public List<Listas> listarActivos() {
        List<Listas> activos = new ArrayList<>();
        for (Listas item : listaElementos) {
            if (!"INACTIVO".equalsIgnoreCase(item.getEstado())) {
                activos.add(item);
            }
        }
        return activos;
    }

    @Override
    public boolean actualizar(Listas entidad) {
        if (entidad == null) {
            return false;
        }
        for (int i = 0; i < listaElementos.size(); i++) {
            if (listaElementos.get(i).getId() == entidad.getId()) {
                listaElementos.set(i, entidad);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        Listas elemento = buscarPorId(id);
        if (elemento != null) {
            elemento.setEstado("INACTIVO");
            return true;
        }
        return false;
    }
}