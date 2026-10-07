package uy.utec.apd.m4.hash;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejercicio 5 — Tabla hash con ENCADENAMIENTO, implementada a mano.
 *
 * <ul>
 *   <li>Array de "buckets"; cada bucket es una cadena de {@link Entrada} enlazadas.</li>
 *   <li>Índice: {@code (clave.hashCode() & 0x7fffffff) % capacidad}
 *       (el &amp; quita el signo; ojo que Math.abs(Integer.MIN_VALUE) es negativo).</li>
 *   <li>Si después de insertar tamanio / capacidad &gt; 0.75, se DUPLICA la capacidad
 *       y se re-insertan todas las entradas (rehash).</li>
 *   <li>No admite claves null.</li>
 * </ul>
 */
public class TablaHash<K, V> {

    public static final int CAPACIDAD_INICIAL = 8;
    public static final double FACTOR_CARGA_MAXIMO = 0.75;

    private static class Entrada<K, V> {
        final K clave;
        V valor;
        Entrada<K, V> siguiente;

        Entrada(K clave, V valor, Entrada<K, V> siguiente) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = siguiente;
        }
    }

    private Entrada<K, V>[] buckets = crearBuckets(CAPACIDAD_INICIAL);
    private int tamanio;

    /**
     * Asocia el valor a la clave. Si la clave ya existía, reemplaza el valor.
     *
     * @return el valor anterior, o null si la clave era nueva
     */
    public V poner(K clave, V valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** @return el valor, o null si no está */
    public V obtener(K clave) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** @return el valor eliminado, o null si no estaba */
    public V eliminar(K clave) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public boolean contieneClave(K clave) {
        // TODO: Ojo: la clave puede existir con valor null
        throw new UnsupportedOperationException("TODO");
    }

    public int tamanio() {
        return tamanio;
    }

    public int capacidad() {
        return buckets.length;
    }

    /** Todas las claves, en cualquier orden. */
    public List<K> claves() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: Métodos auxiliares sugeridos: indice(clave, capacidad), redimensionar(), verificarClave(clave)

    @SuppressWarnings("unchecked")
    private static <K, V> Entrada<K, V>[] crearBuckets(int capacidad) {
        return (Entrada<K, V>[]) new Entrada[capacidad];
    }
}
