/*
 * ============================================================================
 *  MÓDULO 2 — PATRÓN PROXY (estructural)
 * ============================================================================
 *  Teoría: Teorico/Modulo_2/Resumen_Modulo_2.md  (sección 5)
 *  Ejecutar:  java Modulo2_Proxy.java
 *
 *  IDEA
 *  ----
 *  Un SUSTITUTO / intermediario que implementa la MISMA interfaz que el objeto
 *  real y CONTROLA EL ACCESO a él (puede hacer algo antes o después, o
 *  directamente no dejar pasar).
 *
 *       Cliente ──▶ «Imagen» ◀── ImagenReal
 *                      ▲
 *                  ImagenProxy ──(crea/usa)──▶ ImagenReal
 *
 *  TIPOS (los 5 del teórico; acá se implementan 4)
 *  -----
 *  - Virtual    → retrasa la creación de un objeto costoso (lazy loading)   ✔
 *  - Protección → controla permisos                                          ✔
 *  - Caché      → guarda resultados de operaciones costosas                  ✔
 *  - Registro   → audita/loguea las operaciones                              ✔
 *  - Remoto     → representa un objeto que vive en otro servidor (RMI, REST)
 *
 *  PROXY vs DECORATOR: los dos envuelven con la misma interfaz, pero
 *  el Proxy CONTROLA el acceso (y suele manejar el ciclo de vida del real);
 *  el Decorator AGREGA comportamiento y se apila.
 * ============================================================================
 */
import java.util.HashMap;
import java.util.Map;

public class Modulo2_Proxy {

    // ======================= PROXY VIRTUAL =======================
    interface Imagen {
        void mostrar();
    }

    static class ImagenReal implements Imagen {
        private final String archivo;

        ImagenReal(String archivo) {
            this.archivo = archivo;
            cargarDesdeDisco();          // operación costosa en el constructor
        }

        private void cargarDesdeDisco() {
            System.out.println("    💾 Cargando " + archivo + " desde disco (lento)...");
        }

        @Override public void mostrar() { System.out.println("    🖼️  Mostrando " + archivo); }
    }

    static class ImagenProxy implements Imagen {
        private final String archivo;
        private ImagenReal imagenReal;   // todavía no existe

        ImagenProxy(String archivo) { this.archivo = archivo; }   // crear el proxy es barato

        @Override
        public void mostrar() {
            if (imagenReal == null) {                // sólo la primera vez
                imagenReal = new ImagenReal(archivo);
            }
            imagenReal.mostrar();
        }
    }

    // ======================= PROXY DE PROTECCIÓN + REGISTRO + CACHÉ =======================
    interface CuentaBancaria {
        double consultarSaldo(String cuenta);
        void transferir(String desde, String hacia, double monto);
    }

    static class CuentaBancariaReal implements CuentaBancaria {
        private final Map<String, Double> saldos = new HashMap<>(Map.of("UY-001", 5000.0, "UY-002", 1000.0));

        @Override
        public double consultarSaldo(String cuenta) {
            System.out.println("    🏦 Consultando al core bancario (lento)...");
            return saldos.getOrDefault(cuenta, 0.0);
        }

        @Override
        public void transferir(String desde, String hacia, double monto) {
            saldos.merge(desde, -monto, Double::sum);
            saldos.merge(hacia, monto, Double::sum);
            System.out.println("    🏦 Transferidos $" + monto + " de " + desde + " a " + hacia);
        }
    }

    /** Protección: sólo un rol "ADMIN" puede transferir. */
    static class ProxyProteccion implements CuentaBancaria {
        private final CuentaBancaria real;
        private final String rolUsuario;

        ProxyProteccion(CuentaBancaria real, String rolUsuario) { this.real = real; this.rolUsuario = rolUsuario; }

        @Override public double consultarSaldo(String cuenta) { return real.consultarSaldo(cuenta); }

        @Override
        public void transferir(String desde, String hacia, double monto) {
            if (!"ADMIN".equals(rolUsuario)) {
                throw new SecurityException("El rol " + rolUsuario + " no puede transferir");
            }
            real.transferir(desde, hacia, monto);
        }
    }

    /** Registro: loguea antes y después de cada operación. */
    static class ProxyRegistro implements CuentaBancaria {
        private final CuentaBancaria real;

        ProxyRegistro(CuentaBancaria real) { this.real = real; }

        @Override
        public double consultarSaldo(String cuenta) {
            System.out.println("    📝 LOG: consultarSaldo(" + cuenta + ")");
            return real.consultarSaldo(cuenta);
        }

        @Override
        public void transferir(String desde, String hacia, double monto) {
            System.out.println("    📝 LOG: transferir(" + desde + " → " + hacia + ", $" + monto + ")");
            real.transferir(desde, hacia, monto);
        }
    }

    /** Caché: recuerda saldos consultados; se invalida al transferir. */
    static class ProxyCache implements CuentaBancaria {
        private final CuentaBancaria real;
        private final Map<String, Double> cache = new HashMap<>();

        ProxyCache(CuentaBancaria real) { this.real = real; }

        @Override
        public double consultarSaldo(String cuenta) {
            if (cache.containsKey(cuenta)) {
                System.out.println("    ⚡ Saldo de " + cuenta + " servido desde caché");
                return cache.get(cuenta);
            }
            double saldo = real.consultarSaldo(cuenta);
            cache.put(cuenta, saldo);
            return saldo;
        }

        @Override
        public void transferir(String desde, String hacia, double monto) {
            real.transferir(desde, hacia, monto);
            cache.remove(desde);      // los datos cambiaron → invalidar
            cache.remove(hacia);
        }
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== Proxy VIRTUAL (lazy loading) ===");
        Imagen foto = new ImagenProxy("vacaciones.png");
        System.out.println("  Proxy creado. Todavía no se cargó nada.");
        foto.mostrar();     // carga + muestra
        foto.mostrar();     // sólo muestra

        System.out.println("\n=== Proxies de caché + registro (encadenados) ===");
        CuentaBancaria servicio = new ProxyRegistro(new ProxyCache(new CuentaBancariaReal()));
        System.out.println("  Saldo: " + servicio.consultarSaldo("UY-001"));
        System.out.println("  Saldo: " + servicio.consultarSaldo("UY-001"));   // ← caché

        System.out.println("\n=== Proxy de PROTECCIÓN ===");
        CuentaBancaria comoCliente = new ProxyProteccion(servicio, "CLIENTE");
        try {
            comoCliente.transferir("UY-001", "UY-002", 500);
        } catch (SecurityException e) {
            System.out.println("    ⛔ " + e.getMessage());
        }
        CuentaBancaria comoAdmin = new ProxyProteccion(servicio, "ADMIN");
        comoAdmin.transferir("UY-001", "UY-002", 500);
        System.out.println("  Saldo luego de transferir: " + servicio.consultarSaldo("UY-001"));
    }
}
