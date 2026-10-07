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
        // @sol
        Nodo<T> nuevo = new Nodo<>(valor);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        if (cola == null) {
            cola = nuevo;
        }
        tamanio++;
        // @end
    }

    /** O(1) gracias a la referencia a la cola. */
    public void agregarAlFinal(T valor) {
        // @sol
        Nodo<T> nuevo = new Nodo<>(valor);
        if (cola == null) {
            cabeza = nuevo;
        } else {
            cola.siguiente = nuevo;
        }
        cola = nuevo;
        tamanio++;
        // @end
    }

    /**
     * Inserta en la posición indicada (0 = inicio, tamanio() = final).
     *
     * @throws IndexOutOfBoundsException si indice &lt; 0 o indice &gt; tamanio()
     */
    public void insertar(int indice, T valor) {
        // @sol
        if (indice < 0 || indice > tamanio) {
            throw new IndexOutOfBoundsException("Índice: " + indice + ", tamaño: " + tamanio);
        }
        if (indice == 0) {
            agregarAlInicio(valor);
        } else if (indice == tamanio) {
            agregarAlFinal(valor);
        } else {
            Nodo<T> anterior = nodoEn(indice - 1);
            Nodo<T> nuevo = new Nodo<>(valor);
            nuevo.siguiente = anterior.siguiente;
            anterior.siguiente = nuevo;
            tamanio++;
        }
        // @end
    }

    /** @throws IndexOutOfBoundsException si el índice no es válido */
    public T obtener(int indice) {
        // @sol
        verificarIndice(indice);
        return nodoEn(indice).valor;
        // @end
    }

    /**
     * Elimina y devuelve el elemento de esa posición.
     *
     * @throws IndexOutOfBoundsException si el índice no es válido
     */
    public T eliminar(int indice) {
        // @sol Cuidado con actualizar cabeza y cola
        verificarIndice(indice);
        T valor;
        if (indice == 0) {
            valor = cabeza.valor;
            cabeza = cabeza.siguiente;
            if (cabeza == null) {
                cola = null;
            }
        } else {
            Nodo<T> anterior = nodoEn(indice - 1);
            valor = anterior.siguiente.valor;
            anterior.siguiente = anterior.siguiente.siguiente;
            if (anterior.siguiente == null) {
                cola = anterior;
            }
        }
        tamanio--;
        return valor;
        // @end
    }

    /** Elimina la primera aparición del valor (comparando con equals; admite null). */
    public boolean eliminarPrimero(T valor) {
        // @sol
        int indice = indiceDe(valor);
        if (indice < 0) {
            return false;
        }
        eliminar(indice);
        return true;
        // @end
    }

    /** Posición de la primera aparición, o -1. */
    public int indiceDe(T valor) {
        // @sol
        int i = 0;
        for (Nodo<T> n = cabeza; n != null; n = n.siguiente, i++) {
            if (Objects.equals(n.valor, valor)) {
                return i;
            }
        }
        return -1;
        // @end
    }

    public boolean contiene(T valor) {
        // @sol
        return indiceDe(valor) >= 0;
        // @end
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return tamanio == 0;
    }

    /** Invierte la lista IN PLACE (reenlazando nodos, sin crear nodos nuevos). O(n). */
    public void invertir() {
        // @sol Tres punteros: anterior, actual, siguiente
        Nodo<T> anterior = null;
        Nodo<T> actual = cabeza;
        cola = cabeza;
        while (actual != null) {
            Nodo<T> siguiente = actual.siguiente;
            actual.siguiente = anterior;
            anterior = actual;
            actual = siguiente;
        }
        cabeza = anterior;
        // @end
    }

    /**
     * Elemento del medio SIN usar tamanio (técnica de dos punteros: lento y rápido).
     * Con cantidad par devuelve el segundo de los dos del medio: [1,2,3,4] → 3.
     *
     * @throws NoSuchElementException si la lista está vacía
     */
    public T elementoDelMedio() {
        // @sol
        if (cabeza == null) {
            throw new NoSuchElementException("Lista vacía");
        }
        Nodo<T> lento = cabeza;
        Nodo<T> rapido = cabeza;
        while (rapido != null && rapido.siguiente != null) {
            lento = lento.siguiente;
            rapido = rapido.siguiente.siguiente;
        }
        return lento.valor;
        // @end
    }

    /** Formato: "[a, b, c]" y "[]" si está vacía. */
    @Override
    public String toString() {
        // @sol
        StringBuilder sb = new StringBuilder("[");
        for (Nodo<T> n = cabeza; n != null; n = n.siguiente) {
            sb.append(n.valor);
            if (n.siguiente != null) {
                sb.append(", ");
            }
        }
        return sb.append(']').toString();
        // @end
    }

    /** Permite usar la lista en un for-each. */
    @Override
    public Iterator<T> iterator() {
        // @sol Clase anónima (o interna) que recorra los nodos
        return new Iterator<>() {
            private Nodo<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (actual == null) {
                    throw new NoSuchElementException();
                }
                T valor = actual.valor;
                actual = actual.siguiente;
                return valor;
            }
        };
        // @end
    }

    // @sol-blank Sugerencia: métodos auxiliares privados (verificarIndice, nodoEn)
    private void verificarIndice(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice: " + indice + ", tamaño: " + tamanio);
        }
    }

    private Nodo<T> nodoEn(int indice) {
        Nodo<T> n = cabeza;
        for (int i = 0; i < indice; i++) {
            n = n.siguiente;
        }
        return n;
    }
    // @end
}
