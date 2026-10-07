/*
 * ============================================================================
 *  MÓDULO 2 — PATRÓN DECORATOR (estructural)
 * ============================================================================
 *  Teoría: Teorico/Modulo_2/Resumen_Modulo_2.md  (sección 2)
 *  Ejecutar:  java Modulo2_Decorator.java
 *
 *  IDEA
 *  ----
 *  Agregar funcionalidades a UN objeto concreto de forma DINÁMICA,
 *  "envolviéndolo" en otro objeto que tiene la MISMA interfaz.
 *  Evita la explosión de subclases:
 *     VentanaConBorde, VentanaConScroll, VentanaConBordeYScroll,
 *     VentanaConBordeYScrollYTitulo...   ← con herencia, 2^n clases
 *
 *  ESTRUCTURA
 *  ----------
 *     Component          → Ventana { dibujar(); }
 *     Concrete Component → VentanaSimple
 *     Decorator          → VentanaDecorador (implementa Ventana y CONTIENE una Ventana)
 *     Concrete Decorators→ BordeDecorador, BarraDesplazamientoDecorador, TituloDecorador
 *
 *  Las capas se apilan como una cebolla:
 *
 *     BarraDesplazamiento( Borde( VentanaSimple ) )
 *        └─ dibujar(): super.dibujar() → Borde.dibujar() → VentanaSimple.dibujar()
 *                      + agrega su parte
 *
 *  ⚠ El ORDEN de los decoradores importa (ver demo con la cafetería).
 * ============================================================================
 */
public class Modulo2_Decorator {

    // ---------------- COMPONENT ----------------
    interface Ventana {
        void dibujar();
    }

    // ---------------- CONCRETE COMPONENT ----------------
    static class VentanaSimple implements Ventana {
        @Override public void dibujar() { System.out.println("  Dibujando ventana simple"); }
    }

    // ---------------- DECORATOR ABSTRACTO ----------------
    /** Implementa Ventana Y contiene una Ventana a la que delega por defecto. */
    static abstract class VentanaDecorador implements Ventana {
        protected final Ventana ventanaDecorada;

        VentanaDecorador(Ventana ventana) { this.ventanaDecorada = ventana; }

        @Override
        public void dibujar() {
            ventanaDecorada.dibujar();   // delega en lo que envuelve
        }
    }

    // ---------------- CONCRETE DECORATORS ----------------
    static class BordeDecorador extends VentanaDecorador {
        BordeDecorador(Ventana v) { super(v); }

        @Override
        public void dibujar() {
            super.dibujar();                                  // primero lo de adentro
            System.out.println("  + Agregando borde");        // después lo propio
        }
    }

    static class BarraDesplazamientoDecorador extends VentanaDecorador {
        BarraDesplazamientoDecorador(Ventana v) { super(v); }

        @Override
        public void dibujar() {
            super.dibujar();
            System.out.println("  + Agregando barra de desplazamiento");
        }
    }

    static class TituloDecorador extends VentanaDecorador {
        private final String titulo;

        TituloDecorador(Ventana v, String titulo) { super(v); this.titulo = titulo; }

        @Override
        public void dibujar() {
            super.dibujar();
            System.out.println("  + Agregando título \"" + titulo + "\"");
        }
    }

    // ========================================================================
    //  Segundo ejemplo: valores que se acumulan → muestra que el ORDEN importa
    // ========================================================================
    interface Bebida {
        String descripcion();
        double precio();
    }

    static class Cafe implements Bebida {
        public String descripcion() { return "Café"; }
        public double precio() { return 100; }
    }

    static abstract class Agregado implements Bebida {
        protected final Bebida bebida;
        Agregado(Bebida b) { this.bebida = b; }
    }

    static class ConLeche extends Agregado {
        ConLeche(Bebida b) { super(b); }
        public String descripcion() { return bebida.descripcion() + " + leche"; }
        public double precio() { return bebida.precio() + 30; }
    }

    static class TamanioDoble extends Agregado {
        TamanioDoble(Bebida b) { super(b); }
        public String descripcion() { return bebida.descripcion() + " (doble)"; }
        public double precio() { return bebida.precio() * 2; }   // duplica TODO lo que hay adentro
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== Ventana sin decorar ===");
        new VentanaSimple().dibujar();

        System.out.println("\n=== Ventana con borde + barra (ejemplo del teórico) ===");
        Ventana v = new BarraDesplazamientoDecorador(new BordeDecorador(new VentanaSimple()));
        v.dibujar();

        System.out.println("\n=== Se pueden combinar en tiempo de ejecución ===");
        Ventana w = new VentanaSimple();
        boolean usuarioQuiereTitulo = true;
        if (usuarioQuiereTitulo) w = new TituloDecorador(w, "Mis documentos");
        w = new BordeDecorador(w);
        w.dibujar();

        System.out.println("\n=== El orden de los decoradores importa ===");
        Bebida a = new TamanioDoble(new ConLeche(new Cafe()));   // (100 + 30) * 2
        Bebida b = new ConLeche(new TamanioDoble(new Cafe()));   // (100 * 2) + 30
        System.out.println("  " + a.descripcion() + " = $" + a.precio());
        System.out.println("  " + b.descripcion() + " = $" + b.precio());
    }
}
