/*
 * ============================================================================
 *  MÓDULO 1 — PATRÓN BUILDER (creacional)
 * ============================================================================
 *  Teoría: Teorico/Modulo_1/Resumen_Modulo_1.md  (sección 5)
 *  Ejecutar:  java Modulo1_Builder.java
 *
 *  IDEA
 *  ----
 *  Construir objetos COMPLEJOS PASO A PASO, separando la construcción de la
 *  representación final. Evita el "constructor telescópico":
 *
 *      new Casa(4, 10, true, false, true, 2, "teja")   // ¿qué era cada valor?
 *
 *  PARTICIPANTES
 *  -------------
 *     Producto          → Casa (inmutable: sin setters)
 *     Builder           → CasaBuilder (interfaz con los pasos + build())
 *     Concrete Builder  → CasaConcretaBuilder
 *     Director (opc.)   → DirectorObra: conoce "recetas" de construcción
 *
 *  Cada paso devuelve `this` → INTERFAZ FLUIDA (encadenamiento):
 *      new CasaConcretaBuilder().setPuertas(4).setVentanas(10).build();
 * ============================================================================
 */
public class Modulo1_Builder {

    // ---------------- PRODUCTO ----------------
    /** Objeto final. Los atributos son `final`: una vez construida, no cambia. */
    static class Casa {
        private final int puertas;
        private final int ventanas;
        private final boolean garaje;
        private final boolean piscina;
        private final boolean jardin;
        private final String techo;

        // Constructor de paquete: sólo el builder lo usa.
        Casa(int puertas, int ventanas, boolean garaje, boolean piscina, boolean jardin, String techo) {
            this.puertas = puertas;
            this.ventanas = ventanas;
            this.garaje = garaje;
            this.piscina = piscina;
            this.jardin = jardin;
            this.techo = techo;
        }

        @Override
        public String toString() {
            return "Casa{puertas=" + puertas + ", ventanas=" + ventanas
                    + ", garaje=" + garaje + ", piscina=" + piscina
                    + ", jardin=" + jardin + ", techo='" + techo + "'}";
        }
    }

    // ---------------- BUILDER (interfaz) ----------------
    interface CasaBuilder {
        CasaBuilder setPuertas(int puertas);
        CasaBuilder setVentanas(int ventanas);
        CasaBuilder setGaraje(boolean garaje);
        CasaBuilder setPiscina(boolean piscina);
        CasaBuilder setJardin(boolean jardin);
        CasaBuilder setTecho(String techo);
        Casa build();
    }

    // ---------------- CONCRETE BUILDER ----------------
    static class CasaConcretaBuilder implements CasaBuilder {
        // Valores por defecto: el cliente sólo configura lo que le importa.
        private int puertas = 1;
        private int ventanas = 2;
        private boolean garaje;
        private boolean piscina;
        private boolean jardin;
        private String techo = "losa";

        @Override public CasaBuilder setPuertas(int puertas)    { this.puertas = puertas;   return this; }
        @Override public CasaBuilder setVentanas(int ventanas)  { this.ventanas = ventanas; return this; }
        @Override public CasaBuilder setGaraje(boolean garaje)  { this.garaje = garaje;     return this; }
        @Override public CasaBuilder setPiscina(boolean piscina){ this.piscina = piscina;   return this; }
        @Override public CasaBuilder setJardin(boolean jardin)  { this.jardin = jardin;     return this; }
        @Override public CasaBuilder setTecho(String techo)     { this.techo = techo;       return this; }

        /** Último paso: valida y crea el producto. */
        @Override
        public Casa build() {
            if (puertas < 1) {
                throw new IllegalStateException("Una casa necesita al menos una puerta");
            }
            if (piscina && !jardin) {
                throw new IllegalStateException("No se puede tener piscina sin jardín");
            }
            return new Casa(puertas, ventanas, garaje, piscina, jardin, techo);
        }
    }

    // ---------------- DIRECTOR (opcional) ----------------
    /** Encapsula recetas de construcción reutilizables. */
    static class DirectorObra {
        Casa construirCasaEconomica(CasaBuilder b) {
            return b.setPuertas(1).setVentanas(3).build();
        }

        Casa construirCasaDeLujo(CasaBuilder b) {
            return b.setPuertas(4).setVentanas(12).setGaraje(true)
                    .setJardin(true).setPiscina(true).setTecho("teja").build();
        }
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== Construcción paso a paso (interfaz fluida) ===");
        Casa casa = new CasaConcretaBuilder()
                .setPuertas(4)
                .setVentanas(10)
                .setGaraje(true)
                .setJardin(true)
                .setPiscina(true)
                .build();
        System.out.println("  " + casa);

        System.out.println("\n=== Sólo lo necesario (el resto usa valores por defecto) ===");
        System.out.println("  " + new CasaConcretaBuilder().setVentanas(5).build());

        System.out.println("\n=== Usando un Director ===");
        DirectorObra director = new DirectorObra();
        System.out.println("  Económica: " + director.construirCasaEconomica(new CasaConcretaBuilder()));
        System.out.println("  De lujo:   " + director.construirCasaDeLujo(new CasaConcretaBuilder()));

        System.out.println("\n=== build() valida antes de crear ===");
        try {
            new CasaConcretaBuilder().setPiscina(true).build();
        } catch (IllegalStateException e) {
            System.out.println("  Error esperado: " + e.getMessage());
        }
    }
}
