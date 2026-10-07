/*
 * ============================================================================
 *  MÓDULO 3 — APLICACIÓN CRUD COMPLETA (Singleton + DAO + Service + Facade + Consola)
 * ============================================================================
 *  Teoría: Teorico/Modulo_3/Resumen_Modulo_3.md  (secciones 1 a 5)
 *  Ejecutar:
 *     java Modulo3_AplicacionCRUD.java        → corre una DEMO automática del menú
 *     java Modulo3_AplicacionCRUD.java -i     → menú INTERACTIVO (escribís vos)
 *
 *  OBJETIVO
 *  --------
 *  CRUD de consola para gestionar LIBROS y AUTORES (relación uno-a-muchos:
 *  un autor tiene muchos libros; cada libro tiene un único autor).
 *
 *  CAPAS (en el proyecto real cada una es un paquete: modelo, dao, servicio,
 *  consola, util). Acá están como clases anidadas para que el archivo sea
 *  autocontenido:
 *
 *     Main/MenuConsola  ──▶  GestionBibliotecaFacade  ──▶  AutorService / LibroService
 *          (consola)              (FACADE)                     (servicio: reglas)
 *                                                                    │
 *                                                                    ▼
 *                                                       AutorDAO / LibroDAO  (DAO)
 *                                                                    │
 *                                                                    ▼
 *                                                       BaseDeDatos (SINGLETON)
 *
 *  En el proyecto original BaseDeDatos es `DatabaseConnection`, un Singleton
 *  que guarda una `Connection` JDBC a PostgreSQL, y los DAOs usan
 *  PreparedStatement (ver Modulo2_DAO.java). Acá se simula la BD en memoria
 *  para poder ejecutarlo sin instalar nada; la ESTRUCTURA es la misma.
 *
 *  Además se corrigen los errores del material:
 *   - El Facade que pedía el enunciado SÍ está implementado.
 *   - Al agregar un libro se BUSCA el autor por id (no se inventa uno falso).
 *   - Se valida la entrada (ids existentes, campos vacíos, números).
 * ============================================================================
 */
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Modulo3_AplicacionCRUD {

    // ======================================================================
    //  PAQUETE modelo
    // ======================================================================
    static class Autor {
        private int id;
        private String nombre;
        private String apellido;
        private final List<Libro> libros = new ArrayList<>();   // lado "muchos" de la relación

        Autor(int id, String nombre, String apellido) {
            this.id = id; this.nombre = nombre; this.apellido = apellido;
        }

        int getId() { return id; }
        void setId(int id) { this.id = id; }
        String getNombre() { return nombre; }
        void setNombre(String n) { this.nombre = n; }
        String getApellido() { return apellido; }
        void setApellido(String a) { this.apellido = a; }
        List<Libro> getLibros() { return libros; }
        String nombreCompleto() { return nombre + " " + apellido; }

        @Override public String toString() { return "[" + id + "] " + nombreCompleto() + " (" + libros.size() + " libros)"; }
    }

    static class Libro {
        private int id;
        private String titulo;
        private int anioPublicacion;
        private Autor autor;          // FK autor_id en la tabla libros

        Libro(int id, String titulo, int anioPublicacion, Autor autor) {
            this.id = id; this.titulo = titulo; this.anioPublicacion = anioPublicacion; this.autor = autor;
        }

        int getId() { return id; }
        void setId(int id) { this.id = id; }
        String getTitulo() { return titulo; }
        void setTitulo(String t) { this.titulo = t; }
        int getAnioPublicacion() { return anioPublicacion; }
        void setAnioPublicacion(int a) { this.anioPublicacion = a; }
        Autor getAutor() { return autor; }
        void setAutor(Autor a) { this.autor = a; }

        @Override public String toString() { return "[" + id + "] \"" + titulo + "\" (" + anioPublicacion + ") — " + autor.nombreCompleto(); }
    }

    // ======================================================================
    //  PAQUETE util — SINGLETON
    // ======================================================================
    /**
     * Simula `DatabaseConnection`: UNA sola "base de datos" para toda la app.
     * Lazy: se crea en el primer getInstance().
     */
    static class BaseDeDatos {
        private static BaseDeDatos instancia;

        // "Tablas" en memoria (LinkedHashMap mantiene el orden de inserción)
        final Map<Integer, Autor> tablaAutores = new LinkedHashMap<>();
        final Map<Integer, Libro> tablaLibros = new LinkedHashMap<>();
        private int secuenciaAutores = 1;
        private int secuenciaLibros = 1;

        private BaseDeDatos() { }

        static BaseDeDatos getInstance() {
            if (instancia == null) instancia = new BaseDeDatos();
            return instancia;
        }

        int siguienteIdAutor() { return secuenciaAutores++; }
        int siguienteIdLibro() { return secuenciaLibros++; }
    }

    // ======================================================================
    //  PAQUETE dao — DAO
    // ======================================================================
    interface AutorDAO {
        Autor guardar(Autor a);
        Optional<Autor> buscarPorId(int id);
        List<Autor> listar();
        boolean actualizar(Autor a);
        boolean eliminar(int id);
    }

    interface LibroDAO {
        Libro guardar(Libro l);
        Optional<Libro> buscarPorId(int id);
        List<Libro> listar();
        boolean actualizar(Libro l);
        boolean eliminar(int id);
    }

    static class AutorDAOImpl implements AutorDAO {
        private final BaseDeDatos bd = BaseDeDatos.getInstance();   // usa el Singleton

        @Override public Autor guardar(Autor a) {
            a.setId(bd.siguienteIdAutor());
            bd.tablaAutores.put(a.getId(), a);
            return a;
        }
        @Override public Optional<Autor> buscarPorId(int id) { return Optional.ofNullable(bd.tablaAutores.get(id)); }
        @Override public List<Autor> listar() { return new ArrayList<>(bd.tablaAutores.values()); }
        @Override public boolean actualizar(Autor a) { return bd.tablaAutores.replace(a.getId(), a) != null; }
        @Override public boolean eliminar(int id) { return bd.tablaAutores.remove(id) != null; }
    }

    static class LibroDAOImpl implements LibroDAO {
        private final BaseDeDatos bd = BaseDeDatos.getInstance();

        @Override public Libro guardar(Libro l) {
            l.setId(bd.siguienteIdLibro());
            bd.tablaLibros.put(l.getId(), l);
            return l;
        }
        @Override public Optional<Libro> buscarPorId(int id) { return Optional.ofNullable(bd.tablaLibros.get(id)); }
        @Override public List<Libro> listar() { return new ArrayList<>(bd.tablaLibros.values()); }
        @Override public boolean actualizar(Libro l) { return bd.tablaLibros.replace(l.getId(), l) != null; }
        @Override public boolean eliminar(int id) { return bd.tablaLibros.remove(id) != null; }
    }

    // ======================================================================
    //  PAQUETE servicio — reglas de negocio
    // ======================================================================
    static class NegocioException extends RuntimeException {
        NegocioException(String msg) { super(msg); }
    }

    static class AutorService {
        private final AutorDAO dao;
        AutorService(AutorDAO dao) { this.dao = dao; }

        Autor crear(String nombre, String apellido) {
            requerido(nombre, "nombre");
            requerido(apellido, "apellido");
            return dao.guardar(new Autor(0, nombre.trim(), apellido.trim()));
        }

        Autor obtener(int id) {
            return dao.buscarPorId(id).orElseThrow(() -> new NegocioException("No existe el autor con id " + id));
        }

        List<Autor> listar() { return dao.listar(); }

        void actualizar(int id, String nombre, String apellido) {
            Autor a = obtener(id);
            requerido(nombre, "nombre");
            requerido(apellido, "apellido");
            a.setNombre(nombre.trim());
            a.setApellido(apellido.trim());
            dao.actualizar(a);
        }

        void eliminar(int id) {
            Autor a = obtener(id);
            // Integridad referencial (en la BD sería la FK): no borrar autores con libros.
            if (!a.getLibros().isEmpty()) {
                throw new NegocioException("El autor tiene " + a.getLibros().size() + " libro(s); eliminelos primero");
            }
            dao.eliminar(id);
        }
    }

    static class LibroService {
        private final LibroDAO dao;
        private final AutorService autores;
        LibroService(LibroDAO dao, AutorService autores) { this.dao = dao; this.autores = autores; }

        Libro crear(String titulo, int anio, int autorId) {
            requerido(titulo, "título");
            validarAnio(anio);
            Autor autor = autores.obtener(autorId);          // ← se BUSCA el autor real
            Libro libro = dao.guardar(new Libro(0, titulo.trim(), anio, autor));
            autor.getLibros().add(libro);                     // mantener ambos lados de la relación
            return libro;
        }

        List<Libro> listar() { return dao.listar(); }

        void actualizar(int id, String titulo, int anio, int autorId) {
            Libro l = dao.buscarPorId(id).orElseThrow(() -> new NegocioException("No existe el libro con id " + id));
            requerido(titulo, "título");
            validarAnio(anio);
            Autor nuevoAutor = autores.obtener(autorId);
            l.getAutor().getLibros().remove(l);
            nuevoAutor.getLibros().add(l);
            l.setTitulo(titulo.trim());
            l.setAnioPublicacion(anio);
            l.setAutor(nuevoAutor);
            dao.actualizar(l);
        }

        void eliminar(int id) {
            Libro l = dao.buscarPorId(id).orElseThrow(() -> new NegocioException("No existe el libro con id " + id));
            l.getAutor().getLibros().remove(l);
            dao.eliminar(id);
        }

        private void validarAnio(int anio) {
            if (anio < 1450 || anio > 2100) throw new NegocioException("Año fuera de rango: " + anio);
        }
    }

    static void requerido(String valor, String campo) {
        if (valor == null || valor.isBlank()) throw new NegocioException("El campo '" + campo + "' es obligatorio");
    }

    // ======================================================================
    //  FACADE — un único punto de entrada para la consola
    // ======================================================================
    static class GestionBibliotecaFacade {
        private final AutorService autorService;
        private final LibroService libroService;

        GestionBibliotecaFacade() {
            // La fachada también arma el grafo de objetos (DAO → Service).
            this.autorService = new AutorService(new AutorDAOImpl());
            this.libroService = new LibroService(new LibroDAOImpl(), autorService);
        }

        Autor agregarAutor(String n, String a)                 { return autorService.crear(n, a); }
        List<Autor> listarAutores()                            { return autorService.listar(); }
        void actualizarAutor(int id, String n, String a)       { autorService.actualizar(id, n, a); }
        void eliminarAutor(int id)                             { autorService.eliminar(id); }
        Libro agregarLibro(String t, int anio, int autorId)    { return libroService.crear(t, anio, autorId); }
        List<Libro> listarLibros()                             { return libroService.listar(); }
        void actualizarLibro(int id, String t, int anio, int a){ libroService.actualizar(id, t, anio, a); }
        void eliminarLibro(int id)                             { libroService.eliminar(id); }
    }

    // ======================================================================
    //  PAQUETE consola — menú interactivo
    // ======================================================================
    static class MenuConsola {
        private final GestionBibliotecaFacade facade;
        private final Scanner scanner;
        private final boolean eco;    // en modo demo, imprime lo "tecleado"

        MenuConsola(GestionBibliotecaFacade facade, InputStream entrada, boolean eco) {
            this.facade = facade;
            this.scanner = new Scanner(entrada);
            this.eco = eco;
        }

        void iniciar() {
            int opcion;
            do {
                mostrarMenu();
                opcion = leerEntero("Opción: ");
                try {
                    switch (opcion) {
                        case 1 -> { var a = facade.agregarAutor(leerTexto("Nombre: "), leerTexto("Apellido: "));
                                    System.out.println("  ✔ Autor creado con id " + a.getId()); }
                        case 2 -> listar(facade.listarAutores(), "No hay autores");
                        case 3 -> { facade.actualizarAutor(leerEntero("Id del autor: "), leerTexto("Nuevo nombre: "), leerTexto("Nuevo apellido: "));
                                    System.out.println("  ✔ Autor actualizado"); }
                        case 4 -> { facade.eliminarAutor(leerEntero("Id del autor: "));
                                    System.out.println("  ✔ Autor eliminado"); }
                        case 5 -> { var l = facade.agregarLibro(leerTexto("Título: "), leerEntero("Año: "), leerEntero("Id del autor: "));
                                    System.out.println("  ✔ Libro creado con id " + l.getId()); }
                        case 6 -> listar(facade.listarLibros(), "No hay libros");
                        case 7 -> { facade.actualizarLibro(leerEntero("Id del libro: "), leerTexto("Nuevo título: "), leerEntero("Nuevo año: "), leerEntero("Id del autor: "));
                                    System.out.println("  ✔ Libro actualizado"); }
                        case 8 -> { facade.eliminarLibro(leerEntero("Id del libro: "));
                                    System.out.println("  ✔ Libro eliminado"); }
                        case 0 -> System.out.println("¡Hasta luego!");
                        default -> System.out.println("  ⚠ Opción inválida");
                    }
                } catch (NegocioException e) {
                    // Los errores de negocio se muestran sin cortar el programa.
                    System.out.println("  ✘ " + e.getMessage());
                }
            } while (opcion != 0);
        }

        private void mostrarMenu() {
            System.out.println("\n──────── BIBLIOTECA ────────");
            System.out.println(" 1) Agregar autor    5) Agregar libro");
            System.out.println(" 2) Listar autores   6) Listar libros");
            System.out.println(" 3) Editar autor     7) Editar libro");
            System.out.println(" 4) Eliminar autor   8) Eliminar libro");
            System.out.println(" 0) Salir");
        }

        private <T> void listar(List<T> items, String siVacio) {
            if (items.isEmpty()) System.out.println("  (" + siVacio + ")");
            items.forEach(i -> System.out.println("  " + i));
        }

        /** Lee una línea completa → evita el problema de nextInt() dejando el '\n' en el buffer. */
        private String leerTexto(String prompt) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) return "0";
            String linea = scanner.nextLine();
            if (eco) System.out.println(linea);
            return linea;
        }

        /** Validación de entrada: repregunta hasta recibir un número. */
        private int leerEntero(String prompt) {
            while (true) {
                String txt = leerTexto(prompt).trim();
                try {
                    return Integer.parseInt(txt);
                } catch (NumberFormatException e) {
                    System.out.println("  ⚠ '" + txt + "' no es un número válido");
                }
            }
        }
    }

    // ========================================================================
    public static void main(String[] args) {
        boolean interactivo = args.length > 0 && args[0].equals("-i");

        InputStream entrada;
        if (interactivo) {
            entrada = System.in;
        } else {
            // Guion de demo: cada línea es lo que "escribiría" el usuario.
            String guion = String.join("\n",
                    "1", "Gabriel", "García Márquez",            // agregar autor 1
                    "1", "Julio", "Cortázar",                     // agregar autor 2
                    "5", "Cien años de soledad", "1967", "1",     // libro del autor 1
                    "5", "Rayuela", "1963", "2",                  // libro del autor 2
                    "5", "Libro fantasma", "1990", "99",          // autor inexistente → error
                    "abc", "2",                                   // entrada no numérica → repregunta
                    "6",                                          // listar libros
                    "4", "1",                                     // borrar autor con libros → error
                    "8", "1",                                     // borrar libro 1
                    "4", "1",                                     // ahora sí borra el autor 1
                    "2",                                          // listar autores
                    "0");
            entrada = new ByteArrayInputStream(guion.getBytes());
            System.out.println("(Modo DEMO — usá `-i` para el modo interactivo)");
        }

        new MenuConsola(new GestionBibliotecaFacade(), entrada, !interactivo).iniciar();
    }
}
