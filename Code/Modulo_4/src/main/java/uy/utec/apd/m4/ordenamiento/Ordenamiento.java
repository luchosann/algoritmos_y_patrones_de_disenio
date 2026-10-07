package uy.utec.apd.m4.ordenamiento;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Ejercicio 6 — Algoritmos de ordenamiento sobre int[] (ordenan IN PLACE, ascendente).
 * Prohibido usar Arrays.sort, Collections.sort o PriorityQueue.
 */
public final class Ordenamiento {

    private Ordenamiento() {
    }

    /** Selection sort: buscar el mínimo del resto y ponerlo en la posición i. O(n²). */
    public static void seleccion(int[] a) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Insertion sort: insertar cada elemento en la parte ya ordenada. O(n²), O(n) si ya está ordenado. */
    public static void insercion(int[] a) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Merge sort: dividir a la mitad, ordenar cada mitad y mezclar. O(n log n). */
    public static void mergeSort(int[] a) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Quicksort (partición de Lomuto, pivote = último). O(n log n) promedio. */
    public static void quickSort(int[] a) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Heapsort SIN PriorityQueue: construir un max-heap en el propio array
     * y luego, repetidamente, llevar el máximo al final. O(n log n), O(1) de memoria extra.
     * Hijos del nodo i: 2i+1 y 2i+2.
     */
    public static void heapSort(int[] a) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Merge sort ESTABLE y genérico: devuelve una lista NUEVA ordenada según el comparador;
     * elementos "iguales" conservan su orden relativo original. No modifica la lista recibida.
     */
    public static <T> List<T> mergeSortEstable(List<T> lista, Comparator<? super T> comparador) {
        // TODO: Para que sea estable, al mezclar, ante empate tomar primero de la mitad izquierda (<=)
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: Métodos auxiliares (intercambiar, merge, partición, hundir...)
}
