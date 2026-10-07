/*
 * ============================================================================
 *  MÓDULO 1 — PATRÓN FACTORY METHOD (creacional)
 * ============================================================================
 *  Teoría: Teorico/Modulo_1/Resumen_Modulo_1.md  (sección 3)
 *  Ejecutar:  java Modulo1_FactoryMethod.java
 *
 *  IDEA
 *  ----
 *  Una clase base define un "método fábrica" (crearVehiculo) para crear UN
 *  producto, pero son las SUBCLASES las que deciden qué clase concreta
 *  instanciar. El cliente nunca escribe `new Auto()` directamente.
 *
 *  ESTRUCTURA
 *  ----------
 *     Producto (interfaz)        → Vehiculo
 *     Productos concretos        → Auto, Moto, Camion
 *     Creador abstracto          → VehiculoFactory  { abstract crearVehiculo() }
 *     Creadores concretos        → AutoFactory, MotoFactory, CamionFactory
 *
 *         VehiculoFactory ───crea───▶ Vehiculo
 *              ▲                         ▲
 *        ┌─────┴─────┐             ┌─────┴─────┐
 *   AutoFactory  MotoFactory      Auto       Moto
 *
 *  ¿POR QUÉ SIRVE?
 *  ---------------
 *  - La lógica que USA el producto (entregar) vive en la clase base y no
 *    conoce las clases concretas → se agregan vehículos nuevos sin tocarla
 *    (principio Abierto/Cerrado).
 *  - La creación queda encapsulada en un único lugar.
 * ============================================================================
 */
public class Modulo1_FactoryMethod {

    // ---------------- PRODUCTO ----------------
    interface Vehiculo {
        void conducir();
    }

    // ---------------- PRODUCTOS CONCRETOS ----------------
    static class Auto implements Vehiculo {
        @Override public void conducir() { System.out.println("  🚗 Conduciendo un auto por la ruta"); }
    }

    static class Moto implements Vehiculo {
        @Override public void conducir() { System.out.println("  🏍️  Conduciendo una moto entre el tránsito"); }
    }

    static class Camion implements Vehiculo {
        @Override public void conducir() { System.out.println("  🚚 Conduciendo un camión con carga pesada"); }
    }

    // ---------------- CREADOR ABSTRACTO ----------------
    static abstract class VehiculoFactory {

        /** EL método fábrica: cada subclase decide qué Vehiculo crear. */
        protected abstract Vehiculo crearVehiculo();

        /**
         * Lógica de negocio común. Fijate que trabaja con la INTERFAZ Vehiculo:
         * no sabe (ni le importa) si es un Auto, una Moto o un Camión.
         */
        public void realizarEntrega(String destino) {
            Vehiculo v = crearVehiculo();       // ← en vez de `new Algo()`
            System.out.println(" Entrega a " + destino + " usando " + v.getClass().getSimpleName() + ":");
            v.conducir();
        }
    }

    // ---------------- CREADORES CONCRETOS ----------------
    static class AutoFactory extends VehiculoFactory {
        @Override protected Vehiculo crearVehiculo() { return new Auto(); }
    }

    static class MotoFactory extends VehiculoFactory {
        @Override protected Vehiculo crearVehiculo() { return new Moto(); }
    }

    // Agregar un vehículo nuevo = una clase producto + una clase fábrica.
    // VehiculoFactory.realizarEntrega NO se modifica.
    static class CamionFactory extends VehiculoFactory {
        @Override protected Vehiculo crearVehiculo() { return new Camion(); }
    }

    // ========================================================================
    public static void main(String[] args) {
        // Uso básico del teórico
        VehiculoFactory factory = new AutoFactory();
        Vehiculo v = factory.crearVehiculo();
        v.conducir();

        System.out.println();

        // La fábrica concreta puede elegirse en tiempo de ejecución
        // (p. ej. según configuración, distancia, tipo de paquete...).
        String[] pedidos = {"liviano", "urgente", "pesado"};
        for (String tipo : pedidos) {
            VehiculoFactory f = elegirFabrica(tipo);
            f.realizarEntrega("Montevideo (pedido " + tipo + ")");
        }
    }

    /** El ÚNICO lugar del cliente que conoce las fábricas concretas. */
    private static VehiculoFactory elegirFabrica(String tipoPedido) {
        switch (tipoPedido) {
            case "urgente": return new MotoFactory();
            case "pesado":  return new CamionFactory();
            default:        return new AutoFactory();
        }
    }
}
