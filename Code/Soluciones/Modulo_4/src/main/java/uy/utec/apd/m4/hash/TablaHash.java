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
        // @sol
        verificarClave(clave);
        int i = indice(clave, buckets.length);
        for (Entrada<K, V> e = buckets[i]; e != null; e = e.siguiente) {
            if (e.clave.equals(clave)) {
                V anterior = e.valor;
                e.valor = valor;
                return anterior;
            }
        }
        buckets[i] = new Entrada<>(clave, valor, buckets[i]);
        tamanio++;
        if ((double) tamanio / buckets.length > FACTOR_CARGA_MAXIMO) {
            redimensionar();
        }
        return null;
        // @end
    }

    /** @return el valor, o null si no está */
    public V obtener(K clave) {
        // @sol
        verificarClave(clave);
        for (Entrada<K, V> e = buckets[indice(clave, buckets.length)]; e != null; e = e.siguiente) {
            if (e.clave.equals(clave)) {
                return e.valor;
            }
        }
        return null;
        // @end
    }

    /** @return el valor eliminado, o null si no estaba */
    public V eliminar(K clave) {
        // @sol
        verificarClave(clave);
        int i = indice(clave, buckets.length);
        Entrada<K, V> anterior = null;
        for (Entrada<K, V> e = buckets[i]; e != null; anterior = e, e = e.siguiente) {
            if (e.clave.equals(clave)) {
                if (anterior == null) {
                    buckets[i] = e.siguiente;
                } else {
                    anterior.siguiente = e.siguiente;
                }
                tamanio--;
                return e.valor;
            }
        }
        return null;
        // @end
    }

    public boolean contieneClave(K clave) {
        // @sol Ojo: la clave puede existir con valor null
        verificarClave(clave);
        for (Entrada<K, V> e = buckets[indice(clave, buckets.length)]; e != null; e = e.siguiente) {
            if (e.clave.equals(clave)) {
                return true;
            }
        }
        return false;
        // @end
    }

    public int tamanio() {
        return tamanio;
    }

    public int capacidad() {
        return buckets.length;
    }

    /** Todas las claves, en cualquier orden. */
    public List<K> claves() {
        // @sol
        List<K> claves = new ArrayList<>();
        for (Entrada<K, V> bucket : buckets) {
            for (Entrada<K, V> e = bucket; e != null; e = e.siguiente) {
                claves.add(e.clave);
            }
        }
        return claves;
        // @end
    }

    // @sol-blank Métodos auxiliares sugeridos: indice(clave, capacidad), redimensionar(), verificarClave(clave)
    private static int indice(Object clave, int capacidad) {
        return (clave.hashCode() & 0x7fffffff) % capacidad;
    }

    private void redimensionar() {
        Entrada<K, V>[] nuevos = crearBuckets(buckets.length * 2);
        for (Entrada<K, V> bucket : buckets) {
            for (Entrada<K, V> e = bucket; e != null; e = e.siguiente) {
                int i = indice(e.clave, nuevos.length);
                nuevos[i] = new Entrada<>(e.clave, e.valor, nuevos[i]);
            }
        }
        buckets = nuevos;
    }

    private static void verificarClave(Object clave) {
        if (clave == null) {
            throw new IllegalArgumentException("La clave no puede ser null");
        }
    }
    // @end

    @SuppressWarnings("unchecked")
    private static <K, V> Entrada<K, V>[] crearBuckets(int capacidad) {
        return (Entrada<K, V>[]) new Entrada[capacidad];
    }
}
