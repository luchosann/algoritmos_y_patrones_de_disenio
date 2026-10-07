/*
 * ============================================================================
 *  MÓDULO 4 — ALGORITMOS DE ORDENAMIENTO
 * ============================================================================
 *  Teoría: Teorico/Modulo_4/Resumen_Modulo_4.md  (sección 6)
 *  Ejecutar:  java Modulo4_AlgoritmosOrdenamiento.java
 *
 *  Se implementan los 5 algoritmos del teórico sobre List<Integer>, y se
 *  imprime PASO A PASO cómo trabajan con un ejemplo chico.
 *
 *  ┌────────────┬────────────┬────────────┬────────────┬──────────┬────────┐
 *  │ Algoritmo  │ Mejor      │ Promedio   │ Peor       │ Memoria  │ Estable│
 *  ├────────────┼────────────┼────────────┼────────────┼──────────┼────────┤
 *  │ Selection  │ O(n²)      │ O(n²)      │ O(n²)      │ O(1)     │ No     │
 *  │ Insertion  │ O(n)       │ O(n²)      │ O(n²)      │ O(1)     │ Sí     │
 *  │ Heapsort   │ O(n log n) │ O(n log n) │ O(n log n) │ O(1)*    │ No     │
 *  │ Quicksort  │ O(n log n) │ O(n log n) │ O(n²)      │ O(log n) │ No     │
 *  │ Mergesort  │ O(n log n) │ O(n log n) │ O(n log n) │ O(n)     │ Sí     │
 *  └────────────┴────────────┴────────────┴────────────┴──────────┴────────┘
 *   * La versión con PriorityQueue (la del PDF) usa O(n) extra.
 *   ESTABLE = mantiene el orden relativo de elementos iguales.
 * ============================================================================
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Modulo4_AlgoritmosOrdenamiento {

    static boolean traza = true;   // imprime los pasos (se apaga para la prueba de tiempos)

    static void log(String s) { System.out.println(s); }

    // ======================================================================
    //  SELECTION SORT
    //  En cada pasada busca el MÍNIMO del resto y lo pone en la posición i.
    //  [5 3 8 1] → min=1 → [1 | 3 8 5] → min=3 → [1 3 | 8 5] → [1 3 5 | 8]
    // ======================================================================
    static void selectionSort(List<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j) < list.get(min)) min = j;
            }
            Collections.swap(list, i, min);
            if (traza) log("    pasada " + (i + 1) + ": mínimo=" + list.get(i) + " → " + list);
        }
    }

    // ======================================================================
    //  INSERTION SORT
    //  Como ordenar cartas en la mano: toma cada elemento (key) y lo INSERTA
    //  en su lugar dentro de la parte ya ordenada, corriendo a la derecha a
    //  los mayores. Muy rápido si la lista ya está casi ordenada (O(n)).
    // ======================================================================
    static void insertionSort(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            int key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j) > key) {
                list.set(j + 1, list.get(j));   // corro el mayor a la derecha
                j--;
            }
            list.set(j + 1, key);              // inserto key en el hueco
            if (traza) log("    insertar " + key + " → " + list);
        }
    }

    // ======================================================================
    //  HEAPSORT (versión del PDF con PriorityQueue)
    //  PriorityQueue es un min-heap: cargo todo y saco con poll() → sale ordenado.
    // ======================================================================
    static void heapSortConPriorityQueue(List<Integer> list) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(list);
        for (int i = 0; i < list.size(); i++) list.set(i, heap.poll());
    }

    // ======================================================================
    //  HEAPSORT "in-place" (versión clásica, O(1) de memoria extra)
    //  1) Convertir la lista en un MAX-heap: padre ≥ hijos
    //     (hijos de i: 2i+1 y 2i+2).
    //  2) Repetir: el máximo está en [0] → lo cambio con el último y "hundo"
    //     la nueva raíz para restaurar el heap en el resto.
    // ======================================================================
    static void heapSort(List<Integer> list) {
        int n = list.size();
        for (int i = n / 2 - 1; i >= 0; i--) hundir(list, n, i);    // construir el heap
        if (traza) log("    max-heap inicial: " + list);
        for (int fin = n - 1; fin > 0; fin--) {
            Collections.swap(list, 0, fin);                          // máximo al final
            hundir(list, fin, 0);
            if (traza) log("    saco " + list.get(fin) + " → " + list.subList(0, fin) + " | " + list.subList(fin, n));
        }
    }

    private static void hundir(List<Integer> a, int n, int i) {
        int mayor = i, izq = 2 * i + 1, der = 2 * i + 2;
        if (izq < n && a.get(izq) > a.get(mayor)) mayor = izq;
        if (der < n && a.get(der) > a.get(mayor)) mayor = der;
        if (mayor != i) {
            Collections.swap(a, i, mayor);
            hundir(a, n, mayor);
        }
    }

    // ======================================================================
    //  QUICKSORT (divide y vencerás)
    //  Elige un PIVOTE (el último), PARTICIONA: menores a la izquierda,
    //  mayores a la derecha; el pivote queda en su lugar definitivo.
    //  Después ordena recursivamente cada lado.
    //  Peor caso O(n²): lista ya ordenada con pivote = último.
    // ======================================================================
    static void quickSort(List<Integer> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            if (traza) log("    pivote " + list.get(pi) + " en su lugar → " + list);
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    private static int partition(List<Integer> list, int low, int high) {
        int pivot = list.get(high);
        int i = low - 1;                          // frontera de "menores que el pivote"
        for (int j = low; j < high; j++) {
            if (list.get(j) < pivot) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);      // pivote justo después de los menores
        return i + 1;
    }

    // ======================================================================
    //  MERGESORT (divide y vencerás)
    //  DIVIDE a la mitad hasta tener listas de 1 elemento (ya ordenadas),
    //  y las MEZCLA (merge) comparando el primero de cada una.
    //       [5 3 8 1]
    //      [5 3]  [8 1]
    //     [5][3]  [8][1]
    //      [3 5]  [1 8]
    //       [1 3 5 8]
    // ======================================================================
    static List<Integer> mergeSort(List<Integer> list) {
        if (list.size() <= 1) return list;
        int mid = list.size() / 2;
        List<Integer> izq = mergeSort(new ArrayList<>(list.subList(0, mid)));
        List<Integer> der = mergeSort(new ArrayList<>(list.subList(mid, list.size())));
        List<Integer> res = merge(izq, der);
        if (traza) log("    merge " + izq + " + " + der + " → " + res);
        return res;
    }

    private static List<Integer> merge(List<Integer> a, List<Integer> b) {
        List<Integer> res = new ArrayList<>(a.size() + b.size());
        int i = 0, j = 0;
        while (i < a.size() && j < b.size()) {
            // `<=` (y no `<`) es lo que hace que mergesort sea ESTABLE
            if (a.get(i) <= b.get(j)) res.add(a.get(i++));
            else res.add(b.get(j++));
        }
        while (i < a.size()) res.add(a.get(i++));
        while (j < b.size()) res.add(b.get(j++));
        return res;
    }

    // ========================================================================
    public static void main(String[] args) {
        List<Integer> original = List.of(29, 10, 14, 37, 13, 5);
        System.out.println("Lista original: " + original);

        List<Integer> l;

        System.out.println("\n=== Selection Sort ===");
        selectionSort(l = new ArrayList<>(original));

        System.out.println("\n=== Insertion Sort ===");
        insertionSort(l = new ArrayList<>(original));

        System.out.println("\n=== Heapsort con PriorityQueue (versión del PDF) ===");
        heapSortConPriorityQueue(l = new ArrayList<>(original));
        System.out.println("    resultado: " + l);

        System.out.println("\n=== Heapsort in-place ===");
        heapSort(l = new ArrayList<>(original));

        System.out.println("\n=== Quicksort ===");
        quickSort(l = new ArrayList<>(original), 0, original.size() - 1);

        System.out.println("\n=== Mergesort ===");
        System.out.println("    resultado: " + mergeSort(new ArrayList<>(original)));

        // ---------------- Comparación de tiempos ----------------
        traza = false;
        int n = 20_000;
        Random rnd = new Random(42);
        List<Integer> grande = new ArrayList<>();
        for (int i = 0; i < n; i++) grande.add(rnd.nextInt(1_000_000));

        System.out.println("\n=== Tiempos con " + n + " números aleatorios ===");
        medir("Selection  O(n²)     ", () -> selectionSort(new ArrayList<>(grande)));
        medir("Insertion  O(n²)     ", () -> insertionSort(new ArrayList<>(grande)));
        medir("Heapsort   O(n log n)", () -> heapSort(new ArrayList<>(grande)));
        medir("Quicksort  O(n log n)", () -> { List<Integer> c = new ArrayList<>(grande); quickSort(c, 0, c.size() - 1); });
        medir("Mergesort  O(n log n)", () -> mergeSort(new ArrayList<>(grande)));
        medir("Collections.sort     ", () -> Collections.sort(new ArrayList<>(grande)));   // TimSort (merge+insertion)
    }

    static void medir(String nombre, Runnable r) {
        long t0 = System.nanoTime();
        r.run();
        System.out.printf("  %s → %6d ms%n", nombre, (System.nanoTime() - t0) / 1_000_000);
    }
}
