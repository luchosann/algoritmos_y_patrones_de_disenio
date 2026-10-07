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
        // @sol
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            intercambiar(a, i, min);
        }
        // @end
    }

    /** Insertion sort: insertar cada elemento en la parte ya ordenada. O(n²), O(n) si ya está ordenado. */
    public static void insercion(int[] a) {
        // @sol
        for (int i = 1; i < a.length; i++) {
            int clave = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > clave) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = clave;
        }
        // @end
    }

    /** Merge sort: dividir a la mitad, ordenar cada mitad y mezclar. O(n log n). */
    public static void mergeSort(int[] a) {
        // @sol
        if (a.length > 1) {
            mergeSort(a, new int[a.length], 0, a.length - 1);
        }
        // @end
    }

    /** Quicksort (partición de Lomuto, pivote = último). O(n log n) promedio. */
    public static void quickSort(int[] a) {
        // @sol
        quickSort(a, 0, a.length - 1);
        // @end
    }

    /**
     * Heapsort SIN PriorityQueue: construir un max-heap en el propio array
     * y luego, repetidamente, llevar el máximo al final. O(n log n), O(1) de memoria extra.
     * Hijos del nodo i: 2i+1 y 2i+2.
     */
    public static void heapSort(int[] a) {
        // @sol
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            hundir(a, i, n);
        }
        for (int fin = n - 1; fin > 0; fin--) {
            intercambiar(a, 0, fin);
            hundir(a, 0, fin);
        }
        // @end
    }

    /**
     * Merge sort ESTABLE y genérico: devuelve una lista NUEVA ordenada según el comparador;
     * elementos "iguales" conservan su orden relativo original. No modifica la lista recibida.
     */
    public static <T> List<T> mergeSortEstable(List<T> lista, Comparator<? super T> comparador) {
        // @sol Para que sea estable, al mezclar, ante empate tomar primero de la mitad izquierda (<=)
        if (lista.size() <= 1) {
            return new ArrayList<>(lista);
        }
        int medio = lista.size() / 2;
        List<T> izq = mergeSortEstable(lista.subList(0, medio), comparador);
        List<T> der = mergeSortEstable(lista.subList(medio, lista.size()), comparador);
        List<T> resultado = new ArrayList<>(lista.size());
        int i = 0;
        int j = 0;
        while (i < izq.size() && j < der.size()) {
            if (comparador.compare(izq.get(i), der.get(j)) <= 0) {
                resultado.add(izq.get(i++));
            } else {
                resultado.add(der.get(j++));
            }
        }
        while (i < izq.size()) {
            resultado.add(izq.get(i++));
        }
        while (j < der.size()) {
            resultado.add(der.get(j++));
        }
        return resultado;
        // @end
    }

    // @sol-blank Métodos auxiliares (intercambiar, merge, partición, hundir...)
    private static void intercambiar(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void mergeSort(int[] a, int[] aux, int izq, int der) {
        if (izq >= der) {
            return;
        }
        int medio = (izq + der) >>> 1;
        mergeSort(a, aux, izq, medio);
        mergeSort(a, aux, medio + 1, der);
        System.arraycopy(a, izq, aux, izq, der - izq + 1);
        int i = izq;
        int j = medio + 1;
        for (int k = izq; k <= der; k++) {
            if (i > medio) {
                a[k] = aux[j++];
            } else if (j > der) {
                a[k] = aux[i++];
            } else if (aux[i] <= aux[j]) {
                a[k] = aux[i++];
            } else {
                a[k] = aux[j++];
            }
        }
    }

    private static void quickSort(int[] a, int bajo, int alto) {
        if (bajo < alto) {
            int p = particionar(a, bajo, alto);
            quickSort(a, bajo, p - 1);
            quickSort(a, p + 1, alto);
        }
    }

    private static int particionar(int[] a, int bajo, int alto) {
        int pivote = a[alto];
        int i = bajo - 1;
        for (int j = bajo; j < alto; j++) {
            if (a[j] < pivote) {
                intercambiar(a, ++i, j);
            }
        }
        intercambiar(a, i + 1, alto);
        return i + 1;
    }

    /** Hunde a[i] en el max-heap a[0..n). */
    private static void hundir(int[] a, int i, int n) {
        while (true) {
            int mayor = i;
            int izq = 2 * i + 1;
            int der = 2 * i + 2;
            if (izq < n && a[izq] > a[mayor]) {
                mayor = izq;
            }
            if (der < n && a[der] > a[mayor]) {
                mayor = der;
            }
            if (mayor == i) {
                return;
            }
            intercambiar(a, i, mayor);
            i = mayor;
        }
    }
    // @end
}
