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
    // @sol-blank Estructura que mantenga el orden de inserción
    private final Map<ID, T> datos = new LinkedHashMap<>();
    // @end

    public CrudDAOMemoria(Function<T, ID> extractorId) {
        this.extractorId = extractorId;
    }

    @Override
    public void guardar(T entidad) {
        // @sol
        ID id = extractorId.apply(entidad);
        if (datos.containsKey(id)) {
            throw new IllegalStateException("Ya existe: " + id);
        }
        datos.put(id, entidad);
        // @end
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        // @sol
        return Optional.ofNullable(datos.get(id));
        // @end
    }

    @Override
    public List<T> listarTodos() {
        // @sol
        return new ArrayList<>(datos.values());
        // @end
    }

    @Override
    public boolean actualizar(T entidad) {
        // @sol
        ID id = extractorId.apply(entidad);
        if (!datos.containsKey(id)) {
            return false;
        }
        datos.put(id, entidad);
        return true;
        // @end
    }

    @Override
    public boolean eliminar(ID id) {
        // @sol
        return datos.remove(id) != null;
        // @end
    }
}
