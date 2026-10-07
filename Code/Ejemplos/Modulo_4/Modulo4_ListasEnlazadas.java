/*
 * ============================================================================
 *  MÓDULO 4 — LISTAS ENLAZADAS (simple, doble, circular) + java.util.LinkedList
 * ============================================================================
 *  Teoría: Teorico/Modulo_4/Resumen_Modulo_4.md  (secciones 1 y 2)
 *  Ejecutar:  java Modulo4_ListasEnlazadas.java
 *
 *  IDEA
 *  ----
 *  Una lista enlazada es una secuencia de NODOS. Cada nodo guarda un dato y
 *  una referencia al siguiente (y en la doble, también al anterior). Los nodos
 *  NO están contiguos en memoria (a diferencia de un array).
 *
 *   Simple:    cabeza → [10|•]→[20|•]→[30|null]
 *   Doble:     null ←[•|10|•]⇄[•|20|•]⇄[•|30|•]→ null
 *   Circular:  [1|•]→[2|•]→[3|•]─┐
 *                ▲───────────────┘
 *
 *  COMPLEJIDADES
 *  -------------
 *                       LinkedList   ArrayList
 *     get(i)              O(n)         O(1)
 *     insertar al inicio  O(1)         O(n)
 *     insertar al final   O(1)*        O(1) amortizado     (*con puntero a la cola)
 *
 *  Úsala cuando haya MUCHAS inserciones/eliminaciones y NO necesites acceso
 *  aleatorio por índice.
 *
 *  (El material usa java.util.LinkedList para la lista "simple", pero esa
 *   clase es DOBLE; y su ejemplo de lista circular no compila porque
 *   LinkedList no expone sus nodos. Por eso acá se implementan a mano.)
 * ============================================================================
 */
import java.util.Iterator;
import java.util.LinkedList;

public class Modulo4_ListasEnlazadas {

    // ======================================================================
    //  LISTA SIMPLEMENTE ENLAZADA
    // ======================================================================
    static class ListaSimple<T> {
        /** Un nodo: dato + referencia al siguiente. */
        private static class Nodo<T> {
            T dato;
            Nodo<T> siguiente;
            Nodo(T dato) { this.dato = dato; }
        }

        private Nodo<T> cabeza;
        private int tamanio;

        /** O(1): el nuevo nodo pasa a ser la cabeza. */
        void agregarAlInicio(T dato) {
            Nodo<T> nuevo = new Nodo<>(dato);
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
            tamanio++;
        }

        /** O(n): hay que recorrer hasta el último (no guardamos puntero a la cola). */
        void agregarAlFinal(T dato) {
            Nodo<T> nuevo = new Nodo<>(dato);
            if (cabeza == null) { cabeza = nuevo; tamanio++; return; }
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) actual = actual.siguiente;
            actual.siguiente = nuevo;
            tamanio++;
        }

        /** O(n): recorrido secuencial; no hay "salto" directo al índice. */
        T obtener(int indice) {
            if (indice < 0 || indice >= tamanio) throw new IndexOutOfBoundsException("Índice: " + indice);
            Nodo<T> actual = cabeza;
            for (int i = 0; i < indice; i++) actual = actual.siguiente;
            return actual.dato;
        }

        boolean contiene(T dato) {
            for (Nodo<T> n = cabeza; n != null; n = n.siguiente) {
                if (n.dato.equals(dato)) return true;
            }
            return false;
        }

        /** Elimina la primera aparición. La clave: "puentear" el nodo. */
        boolean eliminar(T dato) {
            if (cabeza == null) return false;
            if (cabeza.dato.equals(dato)) {               // caso especial: es la cabeza
                cabeza = cabeza.siguiente;
                tamanio--;
                return true;
            }
            Nodo<T> anterior = cabeza;
            while (anterior.siguiente != null && !anterior.siguiente.dato.equals(dato)) {
                anterior = anterior.siguiente;
            }
            if (anterior.siguiente == null) return false;  // no estaba
            anterior.siguiente = anterior.siguiente.siguiente;   // [A]→[X]→[B]  ⇒  [A]→[B]
            tamanio--;
            return true;
        }

        /** Clásico de entrevista: invertir la lista en O(n) sin memoria extra. */
        void invertir() {
            Nodo<T> anterior = null, actual = cabeza;
            while (actual != null) {
                Nodo<T> siguiente = actual.siguiente;
                actual.siguiente = anterior;
                anterior = actual;
                actual = siguiente;
            }
            cabeza = anterior;
        }

        int tamanio() { return tamanio; }

        @Override public String toString() {
            StringBuilder sb = new StringBuilder("cabeza → ");
            for (Nodo<T> n = cabeza; n != null; n = n.siguiente) sb.append("[").append(n.dato).append("] → ");
            return sb.append("null").toString();
        }
    }

    // ======================================================================
    //  LISTA DOBLEMENTE ENLAZADA (así es java.util.LinkedList por dentro)
    // ======================================================================
    static class ListaDoble<T> {
        private static class Nodo<T> {
            T dato;
            Nodo<T> anterior, siguiente;
            Nodo(T dato) { this.dato = dato; }
        }

        private Nodo<T> cabeza, cola;     // punteros a ambos extremos → O(1) en los dos

        void agregarAlInicio(T dato) {
            Nodo<T> n = new Nodo<>(dato);
            if (cabeza == null) { cabeza = cola = n; return; }
            n.siguiente = cabeza;
            cabeza.anterior = n;
            cabeza = n;
        }

        void agregarAlFinal(T dato) {
            Nodo<T> n = new Nodo<>(dato);
            if (cola == null) { cabeza = cola = n; return; }
            n.anterior = cola;
            cola.siguiente = n;
            cola = n;
        }

        T eliminarUltimo() {      // O(1) gracias a `anterior` (en la simple sería O(n))
            if (cola == null) throw new IllegalStateException("Lista vacía");
            T dato = cola.dato;
            cola = cola.anterior;
            if (cola == null) cabeza = null; else cola.siguiente = null;
            return dato;
        }

        String haciaAdelante() {
            StringBuilder sb = new StringBuilder("null ⇄ ");
            for (Nodo<T> n = cabeza; n != null; n = n.siguiente) sb.append(n.dato).append(" ⇄ ");
            return sb.append("null").toString();
        }

        String haciaAtras() {          // sólo posible en la lista DOBLE
            StringBuilder sb = new StringBuilder();
            for (Nodo<T> n = cola; n != null; n = n.anterior) sb.append(n.dato).append(" ");
            return sb.toString().trim();
        }
    }

    // ======================================================================
    //  LISTA CIRCULAR (el último apunta al primero)
    // ======================================================================
    static class ListaCircular<T> {
        private static class Nodo<T> {
            T dato;
            Nodo<T> siguiente;
            Nodo(T dato) { this.dato = dato; }
        }

        private Nodo<T> ultimo;     // ultimo.siguiente es el primero
        private int tamanio;

        void agregar(T dato) {
            Nodo<T> n = new Nodo<>(dato);
            if (ultimo == null) {
                n.siguiente = n;          // un solo nodo: se apunta a sí mismo
            } else {
                n.siguiente = ultimo.siguiente;
                ultimo.siguiente = n;
            }
            ultimo = n;
            tamanio++;
        }

        /** Da `vueltas` pasos; útil para turnos rotativos (round-robin). */
        String recorrer(int pasos) {
            StringBuilder sb = new StringBuilder();
            Nodo<T> n = ultimo.siguiente;
            for (int i = 0; i < pasos; i++) {
                sb.append(n.dato).append(" ");
                n = n.siguiente;
            }
            return sb.toString().trim();
        }

        /**
         * Problema de Josefo: n personas en ronda, se elimina cada k-ésima.
         * ¿Quién sobrevive? Ejemplo natural de lista circular.
         */
        T josefo(int k) {
            Nodo<T> anterior = ultimo;
            while (tamanio > 1) {
                for (int i = 1; i < k; i++) anterior = anterior.siguiente;
                Nodo<T> eliminado = anterior.siguiente;
                System.out.print(eliminado.dato + " ");
                anterior.siguiente = eliminado.siguiente;
                if (eliminado == ultimo) ultimo = anterior;
                tamanio--;
            }
            System.out.println();
            return ultimo.dato;
        }
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== Lista SIMPLE ===");
        ListaSimple<Integer> simple = new ListaSimple<>();
        simple.agregarAlFinal(20);
        simple.agregarAlFinal(30);
        simple.agregarAlInicio(10);
        System.out.println("  " + simple + "   (tamaño " + simple.tamanio() + ")");
        System.out.println("  obtener(1) = " + simple.obtener(1) + ",  ¿contiene 30? " + simple.contiene(30));
        simple.eliminar(20);
        System.out.println("  sin el 20:  " + simple);
        simple.agregarAlFinal(40);
        simple.invertir();
        System.out.println("  invertida:  " + simple);

        System.out.println("\n=== Lista DOBLE ===");
        ListaDoble<String> doble = new ListaDoble<>();
        doble.agregarAlFinal("B");
        doble.agregarAlFinal("C");
        doble.agregarAlInicio("A");
        System.out.println("  " + doble.haciaAdelante());
        System.out.println("  hacia atrás: " + doble.haciaAtras());
        System.out.println("  eliminarUltimo() = " + doble.eliminarUltimo() + " → " + doble.haciaAdelante());

        System.out.println("\n=== Lista CIRCULAR ===");
        ListaCircular<String> turnos = new ListaCircular<>();
        for (String j : new String[]{"Ana", "Beto", "Caro"}) turnos.agregar(j);
        System.out.println("  7 turnos: " + turnos.recorrer(7));
        ListaCircular<Integer> ronda = new ListaCircular<>();
        for (int i = 1; i <= 7; i++) ronda.agregar(i);
        System.out.print("  Josefo(n=7, k=3) eliminados: ");
        System.out.println("  sobrevive: " + ronda.josefo(3));

        System.out.println("\n=== java.util.LinkedList (doblemente enlazada) ===");
        LinkedList<Integer> lista = new LinkedList<>();
        lista.add(2);              // al final
        lista.addFirst(1);         // al inicio — O(1)
        lista.addLast(4);          // al final  — O(1)
        lista.add(2, 3);           // en índice 2 — O(n) para llegar
        System.out.println("  " + lista);
        System.out.println("  get(2) = " + lista.get(2) + "  (O(n): recorre nodos)");
        lista.set(0, 100);
        System.out.println("  set(0,100) → " + lista);
        lista.removeFirst();
        lista.removeLast();
        lista.remove(Integer.valueOf(3));   // por objeto (no por índice)
        System.out.println("  tras removeFirst/removeLast/remove(3) → " + lista + ", contains(2)=" + lista.contains(2));

        // Recorrer con iterador: la forma eficiente (for-each usa Iterator por dentro).
        lista.addAll(java.util.List.of(5, 6, 7));
        Iterator<Integer> it = lista.iterator();
        while (it.hasNext()) if (it.next() % 2 == 0) it.remove();   // borrar mientras recorro
        System.out.println("  sin pares → " + lista);
    }
}
