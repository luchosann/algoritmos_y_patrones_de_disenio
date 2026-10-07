/*
 * ============================================================================
 *  MÓDULO 1 — PATRÓN PROTOTYPE (creacional)
 * ============================================================================
 *  Teoría: Teorico/Modulo_1/Resumen_Modulo_1.md  (sección 6)
 *  Ejecutar:  java Modulo1_Prototype.java
 *
 *  IDEA
 *  ----
 *  Crear objetos CLONANDO un prototipo existente en vez de construirlos desde
 *  cero. Útil cuando crear el objeto es costoso (lecturas de disco, cálculos,
 *  consultas a BD...) o cuando se quieren muchas variantes de una plantilla.
 *
 *  PARTICIPANTES
 *  -------------
 *     Prototype          → Cloneable + clone()
 *     Concrete Prototype → Coche
 *     Cliente            → pide clones (main / RegistroPrototipos)
 *
 *  CONCEPTO CLAVE: COPIA SUPERFICIAL vs PROFUNDA
 *  ---------------------------------------------
 *  Shallow copy (super.clone()):
 *      original ─┐
 *                ├──▶ Motor   ← el MISMO objeto compartido
 *      clon ─────┘
 *
 *  Deep copy (super.clone() + clonar cada referencia):
 *      original ───▶ Motor
 *      clon ───────▶ Motor'  ← copia independiente
 *
 *  (El PDF usa cocheClon.getMotor() sin definir el getter; acá sí está.)
 * ============================================================================
 */
import java.util.HashMap;
import java.util.Map;

public class Modulo1_Prototype {

    // ---------------- Objeto referenciado ----------------
    static class Motor implements Cloneable {
        private String tipo;

        Motor(String tipo) { this.tipo = tipo; }

        String getTipo() { return tipo; }
        void setTipo(String tipo) { this.tipo = tipo; }

        @Override
        protected Motor clone() {
            try {
                return (Motor) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e); // no puede pasar: implementa Cloneable
            }
        }
    }

    // ---------------- CONCRETE PROTOTYPE (versión SHALLOW) ----------------
    static class CocheShallow implements Cloneable {
        String modelo;
        Motor motor;

        CocheShallow(String modelo, Motor motor) { this.modelo = modelo; this.motor = motor; }

        Motor getMotor() { return motor; }

        @Override
        protected CocheShallow clone() {
            try {
                // Object.clone() copia campo a campo. Para `motor` copia la
                // REFERENCIA → original y clon apuntan al mismo Motor.
                return (CocheShallow) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e);
            }
        }
    }

    // ---------------- CONCRETE PROTOTYPE (versión DEEP) ----------------
    static class Coche implements Cloneable {
        String modelo;
        String color;
        Motor motor;

        Coche(String modelo, String color, Motor motor) {
            // Simulamos que crear un coche "de cero" es caro.
            System.out.println("  (construyendo " + modelo + " desde cero... costoso)");
            this.modelo = modelo;
            this.color = color;
            this.motor = motor;
        }

        Motor getMotor() { return motor; }

        @Override
        protected Coche clone() {
            try {
                Coche c = (Coche) super.clone();     // 1) copia superficial (no llama al constructor)
                c.motor = this.motor.clone();        // 2) DEEP: copio también el objeto referenciado
                return c;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e);
            }
        }

        @Override
        public String toString() {
            return modelo + " " + color + " con motor " + motor.getTipo();
        }
    }

    // ---------------- Registro de prototipos (uso típico) ----------------
    /** Guarda plantillas preconfiguradas y entrega CLONES de ellas. */
    static class RegistroPrototipos {
        private final Map<String, Coche> prototipos = new HashMap<>();

        void registrar(String clave, Coche prototipo) { prototipos.put(clave, prototipo); }

        Coche crear(String clave) { return prototipos.get(clave).clone(); }
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== 1) Copia SUPERFICIAL ===");
        CocheShallow original = new CocheShallow("Fiat Uno", new Motor("Nafta"));
        CocheShallow clon = original.clone();
        clon.getMotor().setTipo("Eléctrico");                 // modifico el motor del CLON...
        System.out.println("  Motor del original: " + original.getMotor().getTipo()
                + "   ← ¡también cambió! (comparten el Motor)");
        System.out.println("  ¿Mismo Motor? " + (original.getMotor() == clon.getMotor()));

        System.out.println("\n=== 2) Copia PROFUNDA ===");
        Coche base = new Coche("Toyota Corolla", "blanco", new Motor("Nafta"));
        Coche copia = base.clone();                           // no imprime "construyendo": no usa el constructor
        copia.getMotor().setTipo("Híbrido");
        copia.color = "rojo";
        System.out.println("  Original: " + base);
        System.out.println("  Clon:     " + copia);
        System.out.println("  ¿Mismo Motor? " + (base.getMotor() == copia.getMotor()));

        System.out.println("\n=== 3) Registro de prototipos ===");
        RegistroPrototipos registro = new RegistroPrototipos();
        registro.registrar("taxi", new Coche("Chevrolet Onix", "amarillo", new Motor("GNC")));
        Coche taxi1 = registro.crear("taxi");
        Coche taxi2 = registro.crear("taxi");
        taxi2.color = "negro";
        System.out.println("  taxi1: " + taxi1);
        System.out.println("  taxi2: " + taxi2);
    }
}
