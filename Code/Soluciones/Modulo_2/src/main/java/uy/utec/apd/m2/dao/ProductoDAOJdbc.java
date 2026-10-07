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
        // @sol INSERT INTO productos (id, nombre, precio, stock) VALUES (?, ?, ?, ?)
        String sql = "INSERT INTO productos (id, nombre, precio, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, producto.getId());
            ps.setString(2, producto.getNombre());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al insertar " + producto, e);
        }
        // @end
    }

    @Override
    public Optional<Producto> buscarPorId(int id) {
        // @sol SELECT ... WHERE id = ?
        String sql = "SELECT id, nombre, precio, stock FROM productos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar el producto " + id, e);
        }
        // @end
    }

    @Override
    public List<Producto> listarTodos() {
        // @sol SELECT ... ORDER BY id
        String sql = "SELECT id, nombre, precio, stock FROM productos ORDER BY id";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Producto> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new DataAccessException("Error al listar productos", e);
        }
        // @end
    }

    @Override
    public boolean actualizar(Producto producto) {
        // @sol UPDATE ... WHERE id = ? (usar las filas afectadas)
        String sql = "UPDATE productos SET nombre = ?, precio = ?, stock = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setInt(4, producto.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar " + producto, e);
        }
        // @end
    }

    @Override
    public boolean eliminar(int id) {
        // @sol DELETE ... WHERE id = ?
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar el producto " + id, e);
        }
        // @end
    }

    @Override
    public List<Producto> buscarPorNombre(String fragmento) {
        // @sol WHERE LOWER(nombre) LIKE ? ORDER BY id (el % va en el parámetro, no en el SQL)
        String sql = "SELECT id, nombre, precio, stock FROM productos WHERE LOWER(nombre) LIKE ? ORDER BY id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + fragmento.toLowerCase(Locale.ROOT) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Producto> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar por nombre", e);
        }
        // @end
    }

    // @sol-blank Sugerencia: un método privado que convierta la fila actual del ResultSet en un Producto
    private Producto mapear(ResultSet rs) throws SQLException {
        return new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"));
    }
    // @end
}
