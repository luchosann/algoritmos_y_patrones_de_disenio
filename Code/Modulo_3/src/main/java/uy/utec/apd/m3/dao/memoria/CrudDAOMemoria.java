package uy.utec.apd.m3.dao.memoria;

import uy.utec.apd.m3.dao.CrudDAO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Implementación genérica en memoria: se escribe UNA vez y sirve para cualquier entidad.
 * El constructor recibe una función que sabe obtener el id de una entidad
 * (por ejemplo {@code Estudiante::getCi}).
 */
public class CrudDAOMemoria<T, ID> implements CrudDAO<T, ID> {

    private final Function<T, ID> extractorId;
    // TODO: Estructura que mantenga el orden de inserción

    public CrudDAOMemoria(Function<T, ID> extractorId) {
        this.extractorId = extractorId;
    }

    @Override
    public void guardar(T entidad) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<T> listarTodos() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean actualizar(T entidad) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean eliminar(ID id) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
