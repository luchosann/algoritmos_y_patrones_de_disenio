package uy.utec.apd.m1.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ejercicio 1 — Singleton thread-safe.
 *
 * Configuración global de la aplicación. Solo puede existir UNA instancia,
 * incluso si muchos hilos la piden al mismo tiempo.
 */
public final class Configuracion {

    private static final AtomicInteger INSTANCIAS_CREADAS = new AtomicInteger();

    // @sol-blank Declarar el atributo estático que guarda la instancia única (¿qué modificador necesita para DCL?)
    private static volatile Configuracion instancia;
    // @end

    private final Map<String, String> propiedades = new ConcurrentHashMap<>();

    private Configuracion() {
        INSTANCIAS_CREADAS.incrementAndGet();
        simularCargaLenta();
        propiedades.put("app.nombre", "Gestor UTEC");
        propiedades.put("app.version", "1.0");
    }

    /**
     * Devuelve la instancia única, creándola la primera vez (lazy)
     * de forma segura para múltiples hilos (double-checked locking).
     */
    public static Configuracion getInstancia() {
        // @sol Implementar lazy initialization con double-checked locking
        if (instancia == null) {
            synchronized (Configuracion.class) {
                if (instancia == null) {
                    instancia = new Configuracion();
                }
            }
        }
        return instancia;
        // @end
    }

    public String get(String clave) {
        return propiedades.get(clave);
    }

    public String get(String clave, String porDefecto) {
        return propiedades.getOrDefault(clave, porDefecto);
    }

    public void set(String clave, String valor) {
        propiedades.put(clave, valor);
    }

    /** Cantidad de veces que se ejecutó el constructor (debería ser 1). */
    public static int getInstanciasCreadas() {
        return INSTANCIAS_CREADAS.get();
    }

    /** Simula leer un archivo de configuración: hace más probable la condición de carrera. */
    private static void simularCargaLenta() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
