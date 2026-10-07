package uy.utec.apd.m2.dao;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del DAO. Cualquier implementación (memoria, JDBC...) debe cumplirlo,
 * y los MISMOS tests se corren contra todas (ver ProductoDAOContractTest).
 */
public interface ProductoDAO {

    /** @throws DataAccessException si ya existe un producto con ese id */
    void insertar(Producto producto);

    Optional<Producto> buscarPorId(int id);

    /** Todos los productos ordenados por id ascendente. */
    List<Producto> listarTodos();

    /** @return false si no existía un producto con ese id */
    boolean actualizar(Producto producto);

    /** @return false si no existía un producto con ese id */
    boolean eliminar(int id);

    /** Productos cuyo nombre contiene el fragmento (sin distinguir mayúsculas), ordenados por id. */
    List<Producto> buscarPorNombre(String fragmento);
}
