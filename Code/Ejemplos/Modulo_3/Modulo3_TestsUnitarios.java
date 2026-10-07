/*
 * ============================================================================
 *  MÓDULO 3 — TESTS UNITARIOS (JUnit 5 + mocks)
 * ============================================================================
 *  Teoría: Teorico/Modulo_3/Resumen_Modulo_3.md  (sección 6)
 *  Ejecutar:  java Modulo3_TestsUnitarios.java
 *
 *  ¿POR QUÉ NO HAY @Test DE JUNIT ACÁ?
 *  -----------------------------------
 *  JUnit y Mockito son librerías externas: necesitan Maven (ver Code/Modulo_3).
 *  Para que este ejemplo corra con un solo `java Archivo.java`, se implementa
 *  un MINI-framework de tests (assertEquals, assertThrows, assertTrue) y un
 *  MOCK hecho a mano. Así se ve QUÉ hacen JUnit y Mockito por dentro.
 *  Al lado de cada test, en comentarios, está la versión JUnit/Mockito real.
 *
 *  CONCEPTOS
 *  ---------
 *  - Test unitario: prueba la unidad más chica (un método) de forma AISLADA.
 *  - Patrón AAA:  Arrange (preparar) → Act (ejecutar) → Assert (verificar).
 *  - Mock: objeto FALSO que reemplaza una dependencia (BD, red...) para que el
 *    test sea rápido, predecible y pruebe SÓLO la lógica de la clase.
 *       when(mock.metodo()).thenReturn(valor)   → programar respuestas
 *       verify(mock).metodo(args)               → comprobar que se llamó
 *  - Un test unitario NUNCA toca la base de datos real.
 *
 *  Dependencias Maven reales (pom.xml, scope test):
 *     org.junit.jupiter:junit-jupiter   ·   org.mockito:mockito-core
 * ============================================================================
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Modulo3_TestsUnitarios {

    // ======================================================================
    //  CÓDIGO BAJO PRUEBA
    // ======================================================================
    static class Calculadora {
        int sumar(int a, int b) { return a + b; }

        int dividir(int a, int b) {
            if (b == 0) throw new IllegalArgumentException("No se puede dividir por cero");
            return a / b;
        }
    }

    record Autor(int id, String nombre, String apellido) { }

    interface AutorDAO {
        boolean guardar(Autor a);
        Optional<Autor> buscarPorId(int id);
    }

    /** Servicio que DEPENDE de un DAO → en el test lo reemplazamos por un mock. */
    static class AutorService {
        private final AutorDAO dao;
        AutorService(AutorDAO dao) { this.dao = dao; }

        boolean registrar(Autor a) {
            if (a.nombre() == null || a.nombre().isBlank()) {
                throw new IllegalArgumentException("El nombre del autor es obligatorio");
            }
            return dao.guardar(a);
        }

        String nombreCompleto(int id) {
            return dao.buscarPorId(id)
                    .map(a -> a.nombre() + " " + a.apellido())
                    .orElse("Desconocido");
        }
    }

    // ======================================================================
    //  MOCK HECHO A MANO (lo que Mockito genera automáticamente)
    // ======================================================================
    static class AutorDAOMock implements AutorDAO {
        // "when(...).thenReturn(...)"
        Optional<Autor> respuestaBuscar = Optional.empty();
        boolean respuestaGuardar = true;
        // registro de llamadas para "verify(...)"
        final List<Autor> llamadasGuardar = new ArrayList<>();

        @Override public boolean guardar(Autor a) { llamadasGuardar.add(a); return respuestaGuardar; }
        @Override public Optional<Autor> buscarPorId(int id) { return respuestaBuscar; }
    }

    // ======================================================================
    //  LOS TESTS
    // ======================================================================
    static class CalculadoraTest {
        private final Calculadora calc = new Calculadora();

        // @Test void testSumar() { assertEquals(5, calc.sumar(2, 3)); }
        void testSumar() {
            assertEquals(5, calc.sumar(2, 3));
        }

        // @Test void testDividir() { assertEquals(3, calc.dividir(6, 2)); }
        void testDividir() {
            assertEquals(3, calc.dividir(6, 2));
        }

        // @Test void testDividirPorCero() {
        //     Exception e = assertThrows(IllegalArgumentException.class, () -> calc.dividir(6, 0));
        //     assertEquals("No se puede dividir por cero", e.getMessage());
        // }
        void testDividirPorCero() {
            Exception e = assertThrows(IllegalArgumentException.class, () -> calc.dividir(6, 0));
            assertEquals("No se puede dividir por cero", e.getMessage());
        }
    }

    static class AutorServiceTest {
        // @Mock AutorDAO dao;   @InjectMocks AutorService service;
        private AutorDAOMock dao;
        private AutorService service;

        // @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }
        void setUp() {
            dao = new AutorDAOMock();
            service = new AutorService(dao);
        }

        // @Test void registrarNombreValido() {
        //     Autor a = new Autor(1, "Jorge", "Borges");
        //     when(dao.guardar(a)).thenReturn(true);
        //     assertTrue(service.registrar(a));
        //     verify(dao).guardar(a);
        // }
        void registrarNombreValido() {
            Autor a = new Autor(1, "Jorge", "Borges");          // Arrange
            dao.respuestaGuardar = true;
            boolean ok = service.registrar(a);                  // Act
            assertTrue(ok);                                     // Assert
            assertEquals(List.of(a), dao.llamadasGuardar);      // "verify"
        }

        // @Test void registrarNombreVacioLanzaExcepcion() {
        //     assertThrows(IllegalArgumentException.class, () -> service.registrar(new Autor(1, "", "X")));
        //     verify(dao, never()).guardar(any());
        // }
        void registrarNombreVacioLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> service.registrar(new Autor(1, "", "X")));
            assertTrue(dao.llamadasGuardar.isEmpty());          // "verify(dao, never())"
        }

        void registrarNombreNullLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> service.registrar(new Autor(1, null, "X")));
        }

        // @Test void nombreCompletoAutorExistente() {
        //     when(dao.buscarPorId(7)).thenReturn(Optional.of(new Autor(7, "Mario", "Benedetti")));
        //     assertEquals("Mario Benedetti", service.nombreCompleto(7));
        // }
        void nombreCompletoAutorExistente() {
            dao.respuestaBuscar = Optional.of(new Autor(7, "Mario", "Benedetti"));
            assertEquals("Mario Benedetti", service.nombreCompleto(7));
        }

        void nombreCompletoAutorInexistente() {
            dao.respuestaBuscar = Optional.empty();
            assertEquals("Desconocido", service.nombreCompleto(99));
        }

        // Este test FALLA a propósito, para ver cómo se reporta un fallo.
        void testQueFallaAPropositoParaVerElReporte() {
            assertEquals("Mario", service.nombreCompleto(1));
        }
    }

    // ======================================================================
    //  MINI-FRAMEWORK (lo que hace org.junit.jupiter.api.Assertions)
    // ======================================================================
    static void assertEquals(Object esperado, Object actual) {
        if (esperado == null ? actual != null : !esperado.equals(actual)) {
            throw new AssertionError("esperado <" + esperado + "> pero fue <" + actual + ">");
        }
    }

    static void assertTrue(boolean condicion) {
        if (!condicion) throw new AssertionError("se esperaba true");
    }

    interface Ejecutable { void ejecutar() throws Exception; }

    static <T extends Throwable> T assertThrows(Class<T> tipo, Ejecutable codigo) {
        try {
            codigo.ejecutar();
        } catch (Throwable t) {
            if (tipo.isInstance(t)) return tipo.cast(t);
            throw new AssertionError("se esperaba " + tipo.getSimpleName() + " pero se lanzó " + t.getClass().getSimpleName());
        }
        throw new AssertionError("se esperaba " + tipo.getSimpleName() + " pero no se lanzó nada");
    }

    // Runner mínimo (lo que hace IntelliJ / `mvn test`)
    private static int ok, fallos;

    static void correr(String nombre, Runnable antes, Runnable test) {
        try {
            if (antes != null) antes.run();     // @BeforeEach
            test.run();
            ok++;
            System.out.println("  ✔ " + nombre);
        } catch (AssertionError | RuntimeException e) {
            fallos++;
            System.out.println("  ✘ " + nombre + " → " + e.getMessage());
        }
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("CalculadoraTest");
        CalculadoraTest ct = new CalculadoraTest();
        correr("testSumar", null, ct::testSumar);
        correr("testDividir", null, ct::testDividir);
        correr("testDividirPorCero", null, ct::testDividirPorCero);

        System.out.println("\nAutorServiceTest (con mock del DAO)");
        AutorServiceTest st = new AutorServiceTest();
        correr("registrarNombreValido", st::setUp, st::registrarNombreValido);
        correr("registrarNombreVacioLanzaExcepcion", st::setUp, st::registrarNombreVacioLanzaExcepcion);
        correr("registrarNombreNullLanzaExcepcion", st::setUp, st::registrarNombreNullLanzaExcepcion);
        correr("nombreCompletoAutorExistente", st::setUp, st::nombreCompletoAutorExistente);
        correr("nombreCompletoAutorInexistente", st::setUp, st::nombreCompletoAutorInexistente);
        correr("testQueFallaAPropositoParaVerElReporte", st::setUp, st::testQueFallaAPropositoParaVerElReporte);

        System.out.println("\nResultado: " + ok + " OK, " + fallos + " fallido(s)");

        /*
         * NOTA sobre mockStatic (error del material):
         * Para mockear el Singleton `DatabaseConnection.getInstance()` se usa
         *     MockedStatic<DatabaseConnection> m = mockStatic(DatabaseConnection.class);
         * y HAY QUE CERRARLO, si no el 2do test falla con
         * "static mocking is already registered in the current thread":
         *
         *     @AfterEach void tearDown() { m.close(); }
         *  ó  try (MockedStatic<DatabaseConnection> m = mockStatic(DatabaseConnection.class)) { ... }
         */
    }
}
