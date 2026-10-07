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

    // @sol-blank Elegir una estructura que mantenga los productos ordenados por id
    private final TreeMap<Integer, Producto> tabla = new TreeMap<>();
    // @end

    @Override
    public void insertar(Producto producto) {
        // @sol
        if (tabla.containsKey(producto.getId())) {
            throw new DataAccessException("Ya existe el producto con id " + producto.getId());
        }
        tabla.put(producto.getId(), producto.copia());
        // @end
    }

    @Override
    public Optional<Producto> buscarPorId(int id) {
        // @sol
        return Optional.ofNullable(tabla.get(id)).map(Producto::copia);
        // @end
    }

    @Override
    public List<Producto> listarTodos() {
        // @sol
        List<Producto> lista = new ArrayList<>();
        for (Producto p : tabla.values()) {
            lista.add(p.copia());
        }
        return lista;
        // @end
    }

    @Override
    public boolean actualizar(Producto producto) {
        // @sol
        if (!tabla.containsKey(producto.getId())) {
            return false;
        }
        tabla.put(producto.getId(), producto.copia());
        return true;
        // @end
    }

    @Override
    public boolean eliminar(int id) {
        // @sol
        return tabla.remove(id) != null;
        // @end
    }

    @Override
    public List<Producto> buscarPorNombre(String fragmento) {
        // @sol
        String buscado = fragmento.toLowerCase(Locale.ROOT);
        List<Producto> lista = new ArrayList<>();
        for (Producto p : tabla.values()) {
            if (p.getNombre().toLowerCase(Locale.ROOT).contains(buscado)) {
                lista.add(p.copia());
            }
        }
        return lista;
        // @end
    }
}
