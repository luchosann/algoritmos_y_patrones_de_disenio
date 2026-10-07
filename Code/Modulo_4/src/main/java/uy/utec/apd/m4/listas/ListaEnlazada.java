package uy.utec.apd.m4.listas;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Ejercicio 1 — Lista simplemente enlazada IMPLEMENTADA A MANO (sin java.util.LinkedList).
 *
 * <p>Se mantiene una referencia a la cabeza y a la cola para que agregar al
 * inicio y al final sean O(1).</p>
 */
public class ListaEnlazada<T> implements Iterable<T> {

    private static class Nodo<T> {
        T valor;
        Nodo<T> siguiente;

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamanio;

    /** O(1) */
    public void agregarAlInicio(T valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** O(1) gracias a la referencia a la cola. */
    public void agregarAlFinal(T valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Inserta en la posición indicada (0 = inicio, tamanio() = final).
     *
     * @throws IndexOutOfBoundsException si indice &lt; 0 o indice &gt; tamanio()
     */
    public void insertar(int indice, T valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** @throws IndexOutOfBoundsException si el índice no es válido */
    public T obtener(int indice) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Elimina y devuelve el elemento de esa posición.
     *
     * @throws IndexOutOfBoundsException si el índice no es válido
     */
    public T eliminar(int indice) {
        // TODO: Cuidado con actualizar cabeza y cola
        throw new UnsupportedOperationException("TODO");
    }

    /** Elimina la primera aparición del valor (comparando con equals; admite null). */
    public boolean eliminarPrimero(T valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Posición de la primera aparición, o -1. */
    public int indiceDe(T valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public boolean contiene(T valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return tamanio == 0;
    }

    /** Invierte la lista IN PLACE (reenlazando nodos, sin crear nodos nuevos). O(n). */
    public void invertir() {
        // TODO: Tres punteros: anterior, actual, siguiente
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Elemento del medio SIN usar tamanio (técnica de dos punteros: lento y rápido).
     * Con cantidad par devuelve el segundo de los dos del medio: [1,2,3,4] → 3.
     *
     * @throws NoSuchElementException si la lista está vacía
     */
    public T elementoDelMedio() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Formato: "[a, b, c]" y "[]" si está vacía. */
    @Override
    public String toString() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Permite usar la lista en un for-each. */
    @Override
    public Iterator<T> iterator() {
        // TODO: Clase anónima (o interna) que recorra los nodos
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: Sugerencia: métodos auxiliares privados (verificarIndice, nodoEn)
}
