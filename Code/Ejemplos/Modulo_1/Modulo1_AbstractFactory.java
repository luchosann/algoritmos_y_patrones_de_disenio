/*
 * ============================================================================
 *  MÓDULO 1 — PATRÓN ABSTRACT FACTORY (creacional)
 * ============================================================================
 *  Teoría: Teorico/Modulo_1/Resumen_Modulo_1.md  (sección 4)
 *  Ejecutar:  java Modulo1_AbstractFactory.java
 *
 *  IDEA
 *  ----
 *  Crear FAMILIAS de objetos relacionados que deben funcionar juntos, sin que
 *  el cliente conozca sus clases concretas. Garantiza COMPATIBILIDAD: nunca se
 *  mezcla una rueda de moto con un motor de coche.
 *
 *  ESTRUCTURA
 *  ----------
 *     Abstract Factory   → VehiculoFactory { crearRueda(); crearMotor(); }
 *     Concrete Factory   → CocheFactory, MotoFactory
 *     Producto abstracto → Rueda, Motor
 *     Producto concreto  → RuedaCoche, MotorCoche, RuedaMoto, MotorMoto
 *     Cliente            → Ensamblaje (recibe UNA fábrica)
 *
 *                     VehiculoFactory
 *                 ┌──────────┴──────────┐
 *            CocheFactory           MotoFactory
 *           crea ↓   ↓              crea ↓   ↓
 *      RuedaCoche  MotorCoche   RuedaMoto  MotorMoto
 *
 *  FACTORY METHOD vs ABSTRACT FACTORY
 *  ----------------------------------
 *  - Factory Method: UN producto, lo decide una subclase.
 *  - Abstract Factory: una FAMILIA de productos (varios métodos fábrica) que
 *    están diseñados para trabajar juntos.
 *  De hecho, cada método de una Abstract Factory es un pequeño factory method.
 * ============================================================================
 */
public class Modulo1_AbstractFactory {

    // ---------------- PRODUCTOS ABSTRACTOS ----------------
    interface Rueda {
        String descripcion();
    }

    interface Motor {
        String descripcion();
        int cilindrada();
    }

    // ---------------- FAMILIA "COCHE" ----------------
    static class RuedaCoche implements Rueda {
        @Override public String descripcion() { return "Rueda de coche R16"; }
    }

    static class MotorCoche implements Motor {
        @Override public String descripcion() { return "Motor de coche 4 cilindros"; }
        @Override public int cilindrada() { return 1600; }
    }

    // ---------------- FAMILIA "MOTO" ----------------
    static class RuedaMoto implements Rueda {
        @Override public String descripcion() { return "Rueda de moto 17\""; }
    }

    static class MotorMoto implements Motor {
        @Override public String descripcion() { return "Motor de moto monocilíndrico"; }
        @Override public int cilindrada() { return 250; }
    }

    // ---------------- ABSTRACT FACTORY ----------------
    interface VehiculoFactory {
        Rueda crearRueda();
        Motor crearMotor();
        int cantidadRuedas();
    }

    // ---------------- CONCRETE FACTORIES ----------------
    // Cada fábrica produce SOLO piezas de su familia → compatibilidad asegurada.
    static class CocheFactory implements VehiculoFactory {
        @Override public Rueda crearRueda() { return new RuedaCoche(); }
        @Override public Motor crearMotor() { return new MotorCoche(); }
        @Override public int cantidadRuedas() { return 4; }
    }

    static class MotoFactory implements VehiculoFactory {
        @Override public Rueda crearRueda() { return new RuedaMoto(); }
        @Override public Motor crearMotor() { return new MotorMoto(); }
        @Override public int cantidadRuedas() { return 2; }
    }

    // ---------------- CLIENTE ----------------
    /**
     * El Ensamblaje sólo conoce las INTERFACES (VehiculoFactory, Rueda, Motor).
     * Para armar otro tipo de vehículo se le pasa otra fábrica; su código
     * no cambia.
     */
    static class Ensamblaje {
        private final VehiculoFactory fabrica;

        Ensamblaje(VehiculoFactory fabrica) { this.fabrica = fabrica; }

        void ensamblar() {
            Motor motor = fabrica.crearMotor();
            System.out.println("  Montando: " + motor.descripcion() + " (" + motor.cilindrada() + " cc)");
            for (int i = 1; i <= fabrica.cantidadRuedas(); i++) {
                Rueda r = fabrica.crearRueda();
                System.out.println("  Montando rueda " + i + ": " + r.descripcion());
            }
            System.out.println("  ✔ Vehículo ensamblado con piezas compatibles");
        }
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== Línea de ensamblaje con CocheFactory ===");
        new Ensamblaje(new CocheFactory()).ensamblar();

        System.out.println("\n=== Línea de ensamblaje con MotoFactory ===");
        new Ensamblaje(new MotoFactory()).ensamblar();

        // Para agregar una familia nueva (p. ej. Camión) se crean
        // RuedaCamion, MotorCamion y CamionFactory. Ensamblaje no se toca.
        //
        // Contra: si se agrega un producto NUEVO a la familia (p. ej. crearAsiento()),
        // hay que modificar la interfaz y TODAS las fábricas concretas.
    }
}
