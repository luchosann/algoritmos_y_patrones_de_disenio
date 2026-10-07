package uy.utec.apd.m2.strategy;

import java.util.List;

/**
 * Contexto: usa una estrategia intercambiable en tiempo de ejecución.
 * Regla propia del contexto: si el paquete es frágil se cobra un 15% extra
 * sobre lo que calcule la estrategia.
 */
public class CalculadoraEnvio {

    public static final double RECARGO_FRAGIL = 1.15;

    private EstrategiaEnvio estrategia;

    public void setEstrategia(EstrategiaEnvio estrategia) {
        this.estrategia = estrategia;
    }

    /** @throws IllegalStateException si no se configuró una estrategia */
    public double calcular(Paquete paquete) {
        // TODO: Delegar en la estrategia y aplicar el recargo si es frágil
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Devuelve la estrategia más barata para el paquete (la primera si hay empate).
     * No modifica la estrategia configurada.
     *
     * @throws IllegalArgumentException si la lista está vacía
     */
    public static EstrategiaEnvio masBarata(Paquete paquete, List<EstrategiaEnvio> opciones) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
