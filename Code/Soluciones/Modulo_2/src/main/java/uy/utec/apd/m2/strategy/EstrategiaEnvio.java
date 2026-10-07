package uy.utec.apd.m2.strategy;

/**
 * Estrategia. Es una interfaz funcional (un solo método abstracto),
 * así que también se puede pasar una lambda: {@code p -> 42.0}.
 */
@FunctionalInterface
public interface EstrategiaEnvio {

    double costo(Paquete paquete);
}
