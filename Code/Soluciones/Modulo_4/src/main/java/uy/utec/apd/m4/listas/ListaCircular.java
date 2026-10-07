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

    // @sol-blank Declarar los atributos que necesites (sugerencia: el nodo anterior al actual y el tamaño)
    private Nodo<T> anterior;
    private int tamanio;
    // @end

    /**
     * Agrega el valor justo ANTES del actual (o sea, al "final" de la vuelta).
     * Si la lista estaba vacía, el nuevo pasa a ser el actual.
     */
    public void agregar(T valor) {
        // @sol
        Nodo<T> nuevo = new Nodo<>(valor);
        if (anterior == null) {
            nuevo.siguiente = nuevo;
        } else {
            nuevo.siguiente = anterior.siguiente;
            anterior.siguiente = nuevo;
        }
        anterior = nuevo;
        tamanio++;
        // @end
    }

    /** @throws NoSuchElementException si está vacía */
    public T actual() {
        // @sol
        verificarNoVacia();
        return anterior.siguiente.valor;
        // @end
    }

    /** Mueve el actual k posiciones hacia adelante (k &gt;= 0). */
    public void avanzar(int k) {
        // @sol
        if (k < 0) {
            throw new IllegalArgumentException("k debe ser >= 0");
        }
        if (tamanio == 0) {
            return;
        }
        for (int i = 0; i < k % tamanio; i++) {
            anterior = anterior.siguiente;
        }
        // @end
    }

    /**
     * Elimina el actual y lo devuelve. El siguiente pasa a ser el actual.
     *
     * @throws NoSuchElementException si está vacía
     */
    public T eliminarActual() {
        // @sol
        verificarNoVacia();
        Nodo<T> eliminado = anterior.siguiente;
        if (eliminado == anterior) {
            anterior = null;
        } else {
            anterior.siguiente = eliminado.siguiente;
        }
        tamanio--;
        return eliminado.valor;
        // @end
    }

    public int tamanio() {
        // @sol
        return tamanio;
        // @end
    }

    public boolean estaVacia() {
        // @sol
        return tamanio == 0;
        // @end
    }

    /** Una vuelta completa empezando por el actual: "[a, b, c]"; "[]" si está vacía. */
    @Override
    public String toString() {
        // @sol
        StringBuilder sb = new StringBuilder("[");
        if (anterior != null) {
            Nodo<T> n = anterior.siguiente;
            for (int i = 0; i < tamanio; i++, n = n.siguiente) {
                sb.append(i == 0 ? "" : ", ").append(n.valor);
            }
        }
        return sb.append(']').toString();
        // @end
    }

    // @sol-blank
    private void verificarNoVacia() {
        if (anterior == null) {
            throw new NoSuchElementException("Lista vacía");
        }
    }
    // @end
}
