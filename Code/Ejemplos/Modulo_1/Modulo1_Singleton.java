/*
 * ============================================================================
 *  MÓDULO 1 — PATRÓN SINGLETON (creacional)
 * ============================================================================
 *  Teoría: Teorico/Modulo_1/Resumen_Modulo_1.md  (sección 2)
 *  Ejecutar:  java Modulo1_Singleton.java
 *
 *  IDEA
 *  ----
 *  Garantizar que una clase tenga UNA ÚNICA instancia y ofrecer un punto de
 *  acceso global a ella.
 *
 *  PIEZAS CLAVE
 *  ------------
 *  1. Un atributo `private static` que guarda la única instancia.
 *  2. Un constructor PRIVADO  → nadie de afuera puede hacer `new`.
 *  3. Un método `public static obtenerInstancia()` que la crea (si hace falta)
 *     y la devuelve.
 *
 *  En este archivo se ven las 4 variantes del teórico:
 *     Eager · Lazy · Synchronized · Double-Checked Locking
 *  y un uso típico: un gestor de configuración (el mismo principio que la
 *  "conexión a la BD" del PDF, pero sin necesitar PostgreSQL).
 * ============================================================================
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Modulo1_Singleton {

    // ------------------------------------------------------------------------
    // VARIANTE 1: EAGER (temprana)
    // La instancia se crea cuando la JVM carga la clase.
    // + Simple y thread-safe "gratis" (la JVM garantiza que la carga de una
    //   clase ocurre una sola vez).
    // - Se crea aunque nunca se use.
    // ------------------------------------------------------------------------
    static class SingletonEager {
        private static final SingletonEager INSTANCIA = new SingletonEager();

        private SingletonEager() {
            System.out.println("  [Eager] constructor ejecutado");
        }

        public static SingletonEager obtenerInstancia() {
            return INSTANCIA;
        }
    }

    // ------------------------------------------------------------------------
    // VARIANTE 2: LAZY (perezosa)
    // Se crea recién la primera vez que alguien la pide.
    // + Ahorra recursos si nunca se usa.
    // - NO es thread-safe: si dos hilos entran a la vez al `if`, ambos ven
    //   `null` y se crean DOS instancias (¡se rompe el patrón!).
    // ------------------------------------------------------------------------
    static class SingletonLazy {
        private static SingletonLazy instancia;

        private SingletonLazy() {
            // Simulamos un constructor lento para que la carrera entre hilos
            // sea fácil de reproducir.
            try { Thread.sleep(20); } catch (InterruptedException ignored) { }
        }

        public static SingletonLazy obtenerInstancia() {
            if (instancia == null) {              // ← dos hilos pueden pasar acá a la vez
                instancia = new SingletonLazy();
            }
            return instancia;
        }

        static void reiniciarParaDemo() { instancia = null; }
    }

    // ------------------------------------------------------------------------
    // VARIANTE 3: SYNCHRONIZED
    // `synchronized` hace que sólo un hilo a la vez ejecute el método.
    // + Thread-safe.
    // - Paga el costo de sincronizar en CADA llamada, aunque la instancia ya
    //   exista (sólo hacía falta la primera vez).
    // ------------------------------------------------------------------------
    static class SingletonSynchronized {
        private static SingletonSynchronized instancia;

        private SingletonSynchronized() { }

        public static synchronized SingletonSynchronized obtenerInstancia() {
            if (instancia == null) {
                instancia = new SingletonSynchronized();
            }
            return instancia;
        }
    }

    // ------------------------------------------------------------------------
    // VARIANTE 4: DOUBLE-CHECKED LOCKING (la del teórico)
    // 1er if  → camino rápido, sin bloquear, cuando la instancia ya existe.
    // synchronized → sólo se entra la primera vez.
    // 2do if  → otro hilo pudo haberla creado mientras esperábamos el lock.
    // `volatile` → evita que otro hilo vea un objeto "a medio construir"
    //              (por reordenamiento de instrucciones de la JVM).
    // ------------------------------------------------------------------------
    static class Configuracion {
        private static volatile Configuracion instanciaUnica;

        // Estado compartido por toda la aplicación.
        private final Map<String, String> propiedades = new HashMap<>();

        private Configuracion() {
            // En la vida real: leer "config.properties" del disco UNA sola vez.
            System.out.println("  [Configuracion] cargando propiedades (operación costosa)...");
            propiedades.put("db.url", "jdbc:postgresql://localhost:5432/utec");
            propiedades.put("db.usuario", "admin");
            propiedades.put("app.idioma", "es");
        }

        public static Configuracion obtenerInstancia() {
            if (instanciaUnica == null) {                     // 1er chequeo (sin lock)
                synchronized (Configuracion.class) {
                    if (instanciaUnica == null) {             // 2do chequeo (con lock)
                        instanciaUnica = new Configuracion();
                    }
                }
            }
            return instanciaUnica;
        }

        public String get(String clave) { return propiedades.get(clave); }

        public void set(String clave, String valor) { propiedades.put(clave, valor); }
    }

    /*
     * NOTA — Singleton para la conexión a la BD (PDF "Singleton – Conexión a la BD").
     * Es exactamente la misma estructura; sólo cambia lo que guarda:
     *
     *   public class ConexionBDSingleton {
     *       private static ConexionBDSingleton instancia;
     *       private Connection conexion;
     *       private ConexionBDSingleton() throws SQLException {
     *           conexion = DriverManager.getConnection(url, usuario, contrasenia);
     *       }
     *       public static ConexionBDSingleton getInstancia() throws SQLException {
     *           if (instancia == null) instancia = new ConexionBDSingleton();
     *           return instancia;
     *       }
     *       public Connection getConexion() { return conexion; }
     *   }
     *
     * (Requiere el driver org.postgresql y la BD levantada con docker-compose.)
     */

    // ========================================================================
    public static void main(String[] args) throws Exception {
        System.out.println("=== 1) Eager ===");
        System.out.println("  Pido la instancia dos veces...");
        SingletonEager e1 = SingletonEager.obtenerInstancia();
        SingletonEager e2 = SingletonEager.obtenerInstancia();
        System.out.println("  ¿Misma instancia? " + (e1 == e2));

        System.out.println("\n=== 2) Configuración con Double-Checked Locking ===");
        Configuracion c1 = Configuracion.obtenerInstancia();   // acá se carga
        Configuracion c2 = Configuracion.obtenerInstancia();   // acá NO se vuelve a cargar
        System.out.println("  ¿Misma instancia? " + (c1 == c2));
        c1.set("app.idioma", "en");
        System.out.println("  Cambié el idioma desde c1; c2 ve: " + c2.get("app.idioma"));

        System.out.println("\n=== 3) Lazy SIN sincronizar con 10 hilos (puede romperse) ===");
        System.out.println("  Instancias distintas creadas: " + contarInstancias(true));

        System.out.println("\n=== 4) Synchronized con 10 hilos (siempre 1) ===");
        System.out.println("  Instancias distintas creadas: " + contarInstancias(false));

        // No se puede hacer: new Configuracion();  → error de compilación desde
        // otra clase, porque el constructor es privado.
    }

    /** Lanza 10 hilos que piden la instancia a la vez y cuenta cuántas distintas hubo. */
    private static int contarInstancias(boolean lazyInseguro) throws InterruptedException {
        SingletonLazy.reiniciarParaDemo();
        Set<Integer> distintas = ConcurrentHashMap.newKeySet();
        Thread[] hilos = new Thread[10];
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(() -> {
                Object o = lazyInseguro ? SingletonLazy.obtenerInstancia()
                                        : SingletonSynchronized.obtenerInstancia();
                distintas.add(System.identityHashCode(o));
            });
            hilos[i].start();
        }
        for (Thread h : hilos) h.join();
        return distintas.size();
    }
}
