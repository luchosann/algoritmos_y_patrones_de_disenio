/*
 * ============================================================================
 *  MÓDULO 2 — PATRÓN DAO (Data Access Object)
 * ============================================================================
 *  Teoría: Teorico/Modulo_2/Resumen_Modulo_2.md  (sección 6)
 *  Ejecutar:  java Modulo2_DAO.java
 *             (usa la implementación EN MEMORIA; la JDBC compila pero sólo se
 *              ejecuta si hay un PostgreSQL y el driver en el classpath)
 *
 *  IDEA
 *  ----
 *  SEPARAR la lógica de acceso a datos de la lógica de negocio. Todo el SQL
 *  (o el acceso a archivos, a una API...) queda encapsulado en el DAO.
 *
 *     UsuarioService ──usa──▶ «UsuarioDAO»  (interfaz con el CRUD)
 *                                ▲        ▲
 *                 UsuarioDAOMemoria    UsuarioDAOJdbc
 *                   (ArrayList)        (PostgreSQL)
 *
 *  - El Service recibe el DAO por CONSTRUCTOR (inyección de dependencias):
 *    cambiar de memoria a BD = cambiar UNA línea en el main.
 *  - En tests se usa la implementación en memoria (o un mock).
 *
 *  JDBC / PreparedStatement
 *  ------------------------
 *  - Placeholders `?` + setString/setInt → protege contra INYECCIÓN SQL.
 *  - executeUpdate() para INSERT/UPDATE/DELETE (devuelve filas afectadas).
 *  - executeQuery() para SELECT (devuelve un ResultSet; se recorre con next()).
 *  - try-with-resources para cerrar todo automáticamente.
 *
 *  (El PDF crea Usuario con 3 argumentos en vez de 4; acá se corrige.)
 * ============================================================================
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Modulo2_DAO {

    // ---------------- ENTIDAD ----------------
    static class Usuario {
        private int id;
        private String nombre;
        private String email;
        private String password;

        Usuario(int id, String nombre, String email, String password) {
            this.id = id; this.nombre = nombre; this.email = email; this.password = password;
        }

        int getId() { return id; }
        void setId(int id) { this.id = id; }
        String getNombre() { return nombre; }
        void setNombre(String nombre) { this.nombre = nombre; }
        String getEmail() { return email; }
        void setEmail(String email) { this.email = email; }
        String getPassword() { return password; }

        @Override public String toString() { return "Usuario{id=" + id + ", nombre='" + nombre + "', email='" + email + "'}"; }
    }

    // ---------------- INTERFAZ DAO (el contrato CRUD) ----------------
    interface UsuarioDAO {
        void agregarUsuario(Usuario u);                 // Create
        Optional<Usuario> obtenerUsuarioPorId(int id);  // Read (uno)
        List<Usuario> obtenerTodosLosUsuarios();        // Read (todos)
        boolean actualizarUsuario(Usuario u);           // Update
        boolean eliminarUsuario(int id);                // Delete
    }

    // ---------------- IMPLEMENTACIÓN EN MEMORIA ----------------
    static class UsuarioDAOMemoria implements UsuarioDAO {
        private final List<Usuario> usuarios = new ArrayList<>();
        private int proximoId = 1;

        @Override
        public void agregarUsuario(Usuario u) {
            u.setId(proximoId++);          // simula el SERIAL / AUTO_INCREMENT de la BD
            usuarios.add(u);
        }

        @Override
        public Optional<Usuario> obtenerUsuarioPorId(int id) {
            return usuarios.stream().filter(u -> u.getId() == id).findFirst();
        }

        @Override
        public List<Usuario> obtenerTodosLosUsuarios() {
            return new ArrayList<>(usuarios);   // copia defensiva
        }

        @Override
        public boolean actualizarUsuario(Usuario u) {
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getId() == u.getId()) {
                    usuarios.set(i, u);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean eliminarUsuario(int id) {
            return usuarios.removeIf(u -> u.getId() == id);
        }
    }

    // ---------------- IMPLEMENTACIÓN JDBC ----------------
    /*
     * Tabla esperada:
     *   CREATE TABLE usuarios (id SERIAL PRIMARY KEY, nombre VARCHAR(100),
     *                          email VARCHAR(100), password VARCHAR(100));
     */
    static class UsuarioDAOJdbc implements UsuarioDAO {
        private final Connection conexion;

        UsuarioDAOJdbc(Connection conexion) { this.conexion = conexion; }

        @Override
        public void agregarUsuario(Usuario u) {
            String sql = "INSERT INTO usuarios (nombre, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, u.getNombre());     // el índice es la posición del `?` (empieza en 1)
                ps.setString(2, u.getEmail());
                ps.setString(3, u.getPassword());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) u.setId(keys.getInt(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al agregar usuario", e);
            }
        }

        @Override
        public Optional<Usuario> obtenerUsuarioPorId(int id) {
            String sql = "SELECT id, nombre, email, password FROM usuarios WHERE id = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al buscar usuario " + id, e);
            }
        }

        @Override
        public List<Usuario> obtenerTodosLosUsuarios() {
            List<Usuario> lista = new ArrayList<>();
            try (Statement st = conexion.createStatement();
                 ResultSet rs = st.executeQuery("SELECT id, nombre, email, password FROM usuarios ORDER BY id")) {
                while (rs.next()) lista.add(mapear(rs));    // next() avanza fila a fila
            } catch (SQLException e) {
                throw new RuntimeException("Error al listar usuarios", e);
            }
            return lista;
        }

        @Override
        public boolean actualizarUsuario(Usuario u) {
            String sql = "UPDATE usuarios SET nombre = ?, email = ?, password = ? WHERE id = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, u.getNombre());
                ps.setString(2, u.getEmail());
                ps.setString(3, u.getPassword());
                ps.setInt(4, u.getId());
                return ps.executeUpdate() > 0;              // filas afectadas
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar usuario", e);
            }
        }

        @Override
        public boolean eliminarUsuario(int id) {
            try (PreparedStatement ps = conexion.prepareStatement("DELETE FROM usuarios WHERE id = ?")) {
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RuntimeException("Error al eliminar usuario", e);
            }
        }

        private Usuario mapear(ResultSet rs) throws SQLException {
            return new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"), rs.getString("password"));
        }

        /*
         * ¿Por qué PreparedStatement y no concatenar?
         *   "SELECT * FROM usuarios WHERE email = '" + email + "'"
         * Si email = "' OR '1'='1", la consulta devuelve TODOS los usuarios.
         * Con `?` el valor viaja como DATO, nunca como parte del SQL.
         */
    }

    // ---------------- SERVICIO (lógica de negocio) ----------------
    /** No sabe si los datos están en memoria o en PostgreSQL. */
    static class UsuarioService {
        private final UsuarioDAO dao;

        UsuarioService(UsuarioDAO dao) { this.dao = dao; }   // inyección de dependencias

        void registrar(String nombre, String email, String password) {
            // Reglas de NEGOCIO: acá, no en el DAO.
            if (!email.contains("@")) throw new IllegalArgumentException("Email inválido: " + email);
            if (password.length() < 4) throw new IllegalArgumentException("Password muy corta");
            boolean repetido = dao.obtenerTodosLosUsuarios().stream().anyMatch(u -> u.getEmail().equals(email));
            if (repetido) throw new IllegalArgumentException("Email ya registrado: " + email);
            dao.agregarUsuario(new Usuario(0, nombre, email, password));
        }

        List<Usuario> listar() { return dao.obtenerTodosLosUsuarios(); }

        void cambiarNombre(int id, String nuevoNombre) {
            Usuario u = dao.obtenerUsuarioPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("No existe el usuario " + id));
            u.setNombre(nuevoNombre);
            dao.actualizarUsuario(u);
        }

        boolean darDeBaja(int id) { return dao.eliminarUsuario(id); }
    }

    // ========================================================================
    public static void main(String[] args) {
        // Para usar PostgreSQL en lugar de memoria, sólo cambiaría esta línea:
        //   try (Connection c = DriverManager.getConnection(url, user, pass)) {
        //       UsuarioService service = new UsuarioService(new UsuarioDAOJdbc(c));
        UsuarioService service = new UsuarioService(new UsuarioDAOMemoria());

        System.out.println("=== CREATE ===");
        service.registrar("Juan Pérez", "juan@example.com", "1234");
        service.registrar("María Gómez", "maria@example.com", "abcd");
        service.listar().forEach(u -> System.out.println("  " + u));

        System.out.println("\n=== UPDATE ===");
        service.cambiarNombre(1, "Juan P. Rodríguez");
        service.listar().forEach(u -> System.out.println("  " + u));

        System.out.println("\n=== DELETE ===");
        System.out.println("  ¿Eliminado id 2? " + service.darDeBaja(2));
        System.out.println("  ¿Eliminado id 99? " + service.darDeBaja(99));
        service.listar().forEach(u -> System.out.println("  " + u));

        System.out.println("\n=== Reglas de negocio en el Service ===");
        try {
            service.registrar("Otro Juan", "juan@example.com", "9999");
        } catch (IllegalArgumentException e) {
            System.out.println("  Error esperado: " + e.getMessage());
        }
    }
}
