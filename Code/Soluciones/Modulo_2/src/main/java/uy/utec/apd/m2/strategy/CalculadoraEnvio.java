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
        // @sol Delegar en la estrategia y aplicar el recargo si es frágil
        if (estrategia == null) {
            throw new IllegalStateException("No hay estrategia configurada");
        }
        double costo = estrategia.costo(paquete);
        return paquete.fragil() ? costo * RECARGO_FRAGIL : costo;
        // @end
    }

    /**
     * Devuelve la estrategia más barata para el paquete (la primera si hay empate).
     * No modifica la estrategia configurada.
     *
     * @throws IllegalArgumentException si la lista está vacía
     */
    public static EstrategiaEnvio masBarata(Paquete paquete, List<EstrategiaEnvio> opciones) {
        // @sol
        if (opciones.isEmpty()) {
            throw new IllegalArgumentException("No hay opciones");
        }
        EstrategiaEnvio mejor = opciones.get(0);
        for (EstrategiaEnvio e : opciones) {
            if (e.costo(paquete) < mejor.costo(paquete)) {
                mejor = e;
            }
        }
        return mejor;
        // @end
    }
}
