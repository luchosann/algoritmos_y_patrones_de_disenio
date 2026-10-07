package uy.utec.apd.m3.dao;

import java.util.List;
import java.util.Optional;

/**
 * DAO genérico: T es la entidad, ID el tipo de su clave.
 */
public interface CrudDAO<T, ID> {

    /** @throws IllegalStateException si ya existe una entidad con ese id */
    void guardar(T entidad);

    Optional<T> buscarPorId(ID id);

    /** En orden de inserción. */
    List<T> listarTodos();

    /** @return false si no existía */
    boolean actualizar(T entidad);

    /** @return false si no existía */
    boolean eliminar(ID id);

    default boolean existe(ID id) {
        return buscarPorId(id).isPresent();
    }
}
