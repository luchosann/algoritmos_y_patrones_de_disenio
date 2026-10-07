/*
 * ============================================================================
 *  MÓDULO 2 — PATRÓN ADAPTER (estructural)  + Principio Abierto/Cerrado
 * ============================================================================
 *  Teoría: Teorico/Modulo_2/Resumen_Modulo_2.md  (sección 1)
 *  Ejecutar:  java Modulo2_Adapter.java
 *
 *  IDEA
 *  ----
 *  Permitir que dos clases con interfaces INCOMPATIBLES trabajen juntas.
 *  El adaptador CONVIERTE la interfaz de una clase existente en la que el
 *  cliente espera. Como un adaptador de enchufe en un viaje.
 *
 *  PARTICIPANTES
 *  -------------
 *     Cliente  → Notebook (sólo sabe usar EnchufeEuropeo)
 *     Target   → EnchufeEuropeo.conectar()
 *     Adaptee  → EnchufeAmericano.plugIn()   (código legado: NO se modifica)
 *     Adapter  → AdaptadorEnchufe (implementa Target, envuelve al Adaptee)
 *
 *      Notebook ──usa──▶ «EnchufeEuropeo»
 *                              ▲ implements
 *                       AdaptadorEnchufe ──tiene──▶ EnchufeAmericano
 *                         conectar() { americano.plugIn(); }
 *
 *  TIPOS
 *  -----
 *  - De OBJETO (composición): el adapter TIENE un adaptee. Más flexible. ← el usual en Java
 *  - De CLASE  (herencia): el adapter ES un adaptee (extends) e implementa el target.
 * ============================================================================
 */
import java.util.List;

public class Modulo2_Adapter {

    // ---------------- TARGET: lo que el cliente espera ----------------
    interface EnchufeEuropeo {
        void conectar();
    }

    // ---------------- ADAPTEE: clase existente e incompatible ----------------
    /** Imaginá que viene de una librería de terceros: no la podemos tocar. */
    static class EnchufeAmericano {
        public void plugIn() {
            System.out.println("    ⚡ Enchufe americano conectado a 110V");
        }
    }

    // ---------------- ADAPTER DE OBJETO (composición) ----------------
    static class AdaptadorEnchufe implements EnchufeEuropeo {
        private final EnchufeAmericano enchufeAmericano;

        AdaptadorEnchufe(EnchufeAmericano e) { this.enchufeAmericano = e; }

        @Override
        public void conectar() {
            System.out.println("    [Adaptador] traduciendo conectar() → plugIn()");
            enchufeAmericano.plugIn();       // ← delega y traduce
        }
    }

    // ---------------- ADAPTER DE CLASE (herencia) ----------------
    static class AdaptadorEnchufeDeClase extends EnchufeAmericano implements EnchufeEuropeo {
        @Override
        public void conectar() {
            System.out.println("    [Adaptador de clase] heredo plugIn() y lo expongo como conectar()");
            plugIn();
        }
    }

    // Un enchufe europeo "nativo", para mostrar que el cliente los trata igual.
    static class EnchufeEuropeoNativo implements EnchufeEuropeo {
        @Override public void conectar() { System.out.println("    ⚡ Enchufe europeo conectado a 220V"); }
    }

    // ---------------- CLIENTE ----------------
    static class Notebook {
        void cargar(EnchufeEuropeo enchufe) {
            System.out.println("  Notebook cargando...");
            enchufe.conectar();
        }
    }

    // ========================================================================
    //  PRINCIPIO ABIERTO/CERRADO (OCP) — explicado en la misma sección
    //  "Abierto a la extensión, cerrado a la modificación"
    // ========================================================================

    /** ❌ MAL: cada tipo nuevo de empleado obliga a MODIFICAR este método. */
    static class CalculadoraSalarioMal {
        double calcularSalario(String tipo, double base, double extra) {
            if (tipo.equals("hora"))     return base * extra;        // valorHora * horas
            else if (tipo.equals("fijo")) return base;
            else if (tipo.equals("comision")) return base + extra * 0.10;
            throw new IllegalArgumentException("Tipo desconocido: " + tipo);
        }
    }

    /** ✔ BIEN: cada tipo es una subclase. Agregar uno = agregar una clase. */
    static abstract class Empleado {
        final String nombre;
        Empleado(String nombre) { this.nombre = nombre; }
        abstract double calcularSalario();
    }

    static class EmpleadoPorHora extends Empleado {
        final double valorHora; final int horas;
        EmpleadoPorHora(String n, double valorHora, int horas) { super(n); this.valorHora = valorHora; this.horas = horas; }
        @Override double calcularSalario() { return valorHora * horas; }
    }

    static class EmpleadoFijo extends Empleado {
        final double sueldo;
        EmpleadoFijo(String n, double sueldo) { super(n); this.sueldo = sueldo; }
        @Override double calcularSalario() { return sueldo; }
    }

    static class EmpleadoComision extends Empleado {
        final double base, ventas;
        EmpleadoComision(String n, double base, double ventas) { super(n); this.base = base; this.ventas = ventas; }
        @Override double calcularSalario() { return base + ventas * 0.10; }
    }

    // ========================================================================
    public static void main(String[] args) {
        Notebook notebook = new Notebook();

        System.out.println("=== Enchufe europeo nativo ===");
        notebook.cargar(new EnchufeEuropeoNativo());

        System.out.println("\n=== Enchufe americano a través de un Adapter de OBJETO ===");
        notebook.cargar(new AdaptadorEnchufe(new EnchufeAmericano()));

        System.out.println("\n=== Adapter de CLASE ===");
        notebook.cargar(new AdaptadorEnchufeDeClase());

        // notebook.cargar(new EnchufeAmericano());  ← NO compila: interfaz incompatible

        System.out.println("\n=== Principio Abierto/Cerrado ===");
        System.out.println("  Versión con if/else: " + new CalculadoraSalarioMal().calcularSalario("fijo", 50000, 0));
        List<Empleado> empleados = List.of(
                new EmpleadoPorHora("Ana", 400, 120),
                new EmpleadoFijo("Bruno", 60000),
                new EmpleadoComision("Carla", 30000, 200000));
        for (Empleado e : empleados) {
            // Polimorfismo: no hay if por tipo.
            System.out.printf("  %-6s → $ %.2f%n", e.nombre, e.calcularSalario());
        }
    }
}
