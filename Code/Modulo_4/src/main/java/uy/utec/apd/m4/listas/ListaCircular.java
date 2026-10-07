package uy.utec.apd.m4.listas;

import java.util.NoSuchElementException;

/**
 * Ejercicio 2 — Lista circular simplemente enlazada (implementada a mano).
 *
 * <p>Hay un elemento "actual". El último nodo apunta al primero.
 * Conviene guardar una referencia al nodo ANTERIOR al actual: así
 * insertar antes del actual y eliminar el actual son O(1).</p>
 */
public class ListaCircular<T> {

    private static class Nodo<T> {
        T valor;
        Nodo<T> siguiente;

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    // TODO: Declarar los atributos que necesites (sugerencia: el nodo anterior al actual y el tamaño)

    /**
     * Agrega el valor justo ANTES del actual (o sea, al "final" de la vuelta).
     * Si la lista estaba vacía, el nuevo pasa a ser el actual.
     */
    public void agregar(T valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** @throws NoSuchElementException si está vacía */
    public T actual() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Mueve el actual k posiciones hacia adelante (k &gt;= 0). */
    public void avanzar(int k) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Elimina el actual y lo devuelve. El siguiente pasa a ser el actual.
     *
     * @throws NoSuchElementException si está vacía
     */
    public T eliminarActual() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int tamanio() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public boolean estaVacia() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Una vuelta completa empezando por el actual: "[a, b, c]"; "[]" si está vacía. */
    @Override
    public String toString() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    // TODO
}
