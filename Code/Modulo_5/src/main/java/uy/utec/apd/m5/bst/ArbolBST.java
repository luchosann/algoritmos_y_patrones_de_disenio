package uy.utec.apd.m5.bst;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Ejercicios 1 y 2 — Árbol binario de búsqueda de enteros (sin duplicados) y sus recorridos.
 *
 * <p>Convención de altura: árbol vacío = 0, un solo nodo = 1.</p>
 */
public class ArbolBST {

    static class Nodo {
        int valor;
        Nodo izq;
        Nodo der;

        Nodo(int valor) {
            this.valor = valor;
        }
    }

    private Nodo raiz;
    private int tamanio;

    /** @return false si el valor ya estaba (no se insertan duplicados) */
    public boolean insertar(int valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public boolean contiene(int valor) {
        // TODO: Iterativo: bajar a la izquierda o derecha según el valor
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Elimina el valor. Tres casos: hoja, un hijo, dos hijos
     * (reemplazar por el sucesor in-order = mínimo del subárbol derecho).
     *
     * @return false si el valor no estaba
     */
    public boolean eliminar(int valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** @throws NoSuchElementException si está vacío */
    public int minimo() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** @throws NoSuchElementException si está vacío */
    public int maximo() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int altura() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public int cantidadHojas() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Raíz → Izq → Der */
    public List<Integer> preorden() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Izq → Raíz → Der (en un BST sale ORDENADO) */
    public List<Integer> inorden() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Izq → Der → Raíz */
    public List<Integer> posorden() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** BFS: nivel por nivel, de izquierda a derecha. Usar una cola. */
    public List<Integer> porNiveles() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Igual que porNiveles pero agrupado: [[raíz], [nivel 1...], [nivel 2...]]. */
    public List<List<Integer>> niveles() {
        // TODO: Pista: en cada vuelta del while, procesar exactamente queue.size() nodos
        throw new UnsupportedOperationException("TODO");
    }

    /** Preorden SIN recursión, con una pila explícita (DFS). */
    public List<Integer> preordenIterativo() {
        // TODO: Apilar primero el hijo derecho y después el izquierdo
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: Métodos recursivos auxiliares que reciben un Nodo
}
