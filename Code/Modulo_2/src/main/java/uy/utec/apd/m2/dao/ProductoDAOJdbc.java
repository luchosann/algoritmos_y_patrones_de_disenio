package uy.utec.apd.m2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Implementación JDBC sobre la tabla {@code productos} (ver src/main/resources/schema.sql).
 * <p>Usar SIEMPRE PreparedStatement y try-with-resources.
 * Envolver cada SQLException en una DataAccessException.</p>
 */
public class ProductoDAOJdbc implements ProductoDAO {

    private final Connection connection;

    public ProductoDAOJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertar(Producto producto) {
        // TODO: INSERT INTO productos (id, nombre, precio, stock) VALUES (?, ?, ?, ?)
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Optional<Producto> buscarPorId(int id) {
        // TODO: SELECT ... WHERE id = ?
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<Producto> listarTodos() {
        // TODO: SELECT ... ORDER BY id
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean actualizar(Producto producto) {
        // TODO: UPDATE ... WHERE id = ? (usar las filas afectadas)
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean eliminar(int id) {
        // TODO: DELETE ... WHERE id = ?
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<Producto> buscarPorNombre(String fragmento) {
        // TODO: WHERE LOWER(nombre) LIKE ? ORDER BY id (el % va en el parámetro, no en el SQL)
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: Sugerencia: un método privado que convierta la fila actual del ResultSet en un Producto
}
