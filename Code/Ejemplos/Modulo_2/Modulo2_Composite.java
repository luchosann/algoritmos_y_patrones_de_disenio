/*
 * ============================================================================
 *  MÓDULO 2 — PATRÓN COMPOSITE (estructural)
 * ============================================================================
 *  Teoría: Teorico/Modulo_2/Resumen_Modulo_2.md  (sección 3)
 *  Ejecutar:  java Modulo2_Composite.java
 *
 *  IDEA
 *  ----
 *  Componer objetos en estructuras de ÁRBOL (jerarquías PARTE-TODO) y tratar
 *  a las HOJAS y a los COMPUESTOS de la misma forma, a través de una interfaz
 *  común. El cliente no pregunta "¿esto es archivo o carpeta?".
 *
 *  ESTRUCTURA
 *  ----------
 *     Component → ComponenteSistemaArchivos { mostrarDetalles(); getTamanio(); }
 *     Leaf      → Archivo     (no tiene hijos)
 *     Composite → Directorio  (tiene una lista de Components y DELEGA en ellos)
 *
 *     📁 raiz
 *     ├── 📄 notas.txt
 *     └── 📁 fotos
 *         ├── 📄 playa.jpg
 *         └── 📁 2025
 *             └── 📄 cumple.jpg
 *
 *  (En el PDF la hoja se llama `File`, que choca con java.io.File; acá se
 *   renombró a `Archivo`.)
 * ============================================================================
 */
import java.util.ArrayList;
import java.util.List;

public class Modulo2_Composite {

    // ---------------- COMPONENT ----------------
    interface ComponenteSistemaArchivos {
        void mostrarDetalles(String sangria);
        long getTamanio();
    }

    // ---------------- LEAF ----------------
    static class Archivo implements ComponenteSistemaArchivos {
        private final String nombre;
        private final long tamanio;

        Archivo(String nombre, long tamanio) { this.nombre = nombre; this.tamanio = tamanio; }

        @Override
        public void mostrarDetalles(String sangria) {
            System.out.println(sangria + "📄 " + nombre + " (" + tamanio + " KB)");
        }

        @Override
        public long getTamanio() { return tamanio; }
    }

    // ---------------- COMPOSITE ----------------
    static class Directorio implements ComponenteSistemaArchivos {
        private final String nombre;
        private final List<ComponenteSistemaArchivos> hijos = new ArrayList<>();

        Directorio(String nombre) { this.nombre = nombre; }

        /** Devuelve `this` para poder encadenar. Acepta hojas Y compuestos. */
        Directorio agregar(ComponenteSistemaArchivos c) { hijos.add(c); return this; }

        void eliminar(ComponenteSistemaArchivos c) { hijos.remove(c); }

        @Override
        public void mostrarDetalles(String sangria) {
            System.out.println(sangria + "📁 " + nombre + " (" + getTamanio() + " KB)");
            for (ComponenteSistemaArchivos hijo : hijos) {
                hijo.mostrarDetalles(sangria + "    ");   // delega (recursión natural)
            }
        }

        /** El tamaño de un directorio es la suma de sus hijos, sean lo que sean. */
        @Override
        public long getTamanio() {
            long total = 0;
            for (ComponenteSistemaArchivos hijo : hijos) total += hijo.getTamanio();
            return total;
        }
    }

    // ========================================================================
    public static void main(String[] args) {
        Directorio anio2025 = new Directorio("2025")
                .agregar(new Archivo("cumple.jpg", 2048));

        Directorio fotos = new Directorio("fotos")
                .agregar(new Archivo("playa.jpg", 3500))
                .agregar(anio2025);                        // un Composite dentro de otro

        Archivo notas = new Archivo("notas.txt", 4);
        Directorio raiz = new Directorio("raiz")
                .agregar(notas)
                .agregar(fotos);

        System.out.println("=== Árbol completo ===");
        raiz.mostrarDetalles("  ");

        System.out.println("\n=== Trato UNIFORME: mismo código para hoja y compuesto ===");
        List<ComponenteSistemaArchivos> elementos = List.of(notas, fotos, raiz);
        for (ComponenteSistemaArchivos c : elementos) {
            System.out.println("  tamaño = " + c.getTamanio() + " KB");
        }

        System.out.println("\n=== Elimino 'fotos' de la raíz ===");
        raiz.eliminar(fotos);
        raiz.mostrarDetalles("  ");
    }
}
