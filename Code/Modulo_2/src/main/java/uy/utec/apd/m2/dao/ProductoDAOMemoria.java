package uy.utec.apd.m2.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Implementación en memoria.
 * <p>IMPORTANTE: debe guardar y devolver COPIAS. Así, modificar un objeto
 * afuera no cambia lo "persistido" (igual que pasaría con una base de datos real).</p>
 */
public class ProductoDAOMemoria implements ProductoDAO {

    // TODO: Elegir una estructura que mantenga los productos ordenados por id

    @Override
    public void insertar(Producto producto) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Optional<Producto> buscarPorId(int id) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<Producto> listarTodos() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean actualizar(Producto producto) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean eliminar(int id) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<Producto> buscarPorNombre(String fragmento) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
