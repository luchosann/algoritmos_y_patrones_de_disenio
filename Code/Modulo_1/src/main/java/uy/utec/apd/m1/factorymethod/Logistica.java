package uy.utec.apd.m1.factorymethod;

import java.util.Locale;

/**
 * Creador abstracto del Factory Method.
 *
 * La lógica de negocio (planificar, calcular costo) ya está implementada y
 * NO sabe qué transporte concreto usa: eso lo decide cada subclase en
 * {@link #crearTransporte()}.
 */
public abstract class Logistica {

    /** Factory method: cada subclase decide qué Transporte crear. */
    protected abstract Transporte crearTransporte();

    public String planificarEntrega(String carga) {
        return crearTransporte().entregar(carga);
    }

    public double calcularCosto(double km) {
        if (km < 0) {
            throw new IllegalArgumentException("La distancia no puede ser negativa");
        }
        return crearTransporte().costoPorKm() * km;
    }

    /**
     * Devuelve la logística adecuada según el texto recibido
     * ("terrestre", "maritima", "aerea"), sin importar mayúsculas ni espacios.
     *
     * @throws IllegalArgumentException si el tipo es null o desconocido
     */
    public static Logistica desde(String tipo) {
        // TODO: Elegir la subclase según el tipo (usar switch); lanzar IllegalArgumentException si no existe
        throw new UnsupportedOperationException("TODO");
    }
}
