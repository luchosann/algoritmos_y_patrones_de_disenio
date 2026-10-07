/*
 * ============================================================================
 *  MÓDULO 4 — COLAS (Queue) — FIFO  + Cola de prioridad
 * ============================================================================
 *  Teoría: Teorico/Modulo_4/Resumen_Modulo_4.md  (sección 4)
 *  Ejecutar:  java Modulo4_Colas.java
 *
 *  IDEA
 *  ----
 *  FIFO (First In, First Out): el PRIMERO que entra es el PRIMERO que sale.
 *  Como la fila del supermercado: se entra por el FINAL y se sale por el FRENTE.
 *
 *     sale ◀── [ A | B | C | D ] ◀── entra
 *             frente         final
 *
 *  OPERACIONES                   Java
 *     enqueue (encolar)          offer(e) / add(e)
 *     dequeue (desencolar)       poll()  (null si vacía) / remove() (excepción)
 *     peek    (ver el frente)    peek()
 *     isEmpty / size             isEmpty() / size()
 *
 *  VARIANTES
 *  - Cola circular (array con índices que "dan la vuelta")
 *  - Deque: se puede agregar/sacar por ambos extremos (ArrayDeque)
 *  - PriorityQueue: sale siempre el MENOR (o según un Comparator), no el
 *    primero en llegar. Internamente es un HEAP → offer/poll en O(log n).
 *
 *  USOS: tareas por orden de llegada, colas de impresión, mensajería,
 *        BFS en grafos y árboles, simulaciones.
 * ============================================================================
 */
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Modulo4_Colas {

    // ======================================================================
    //  COLA CIRCULAR CON ARRAY (implementación propia)
    // ======================================================================
    /**
     * Si sólo moviéramos el `frente` hacia adelante, el array se "gastaría".
     * Con aritmética módulo (%) los índices vuelven al principio:
     *
     *   capacidad 4:  [ _ | B | C | D ]   frente=1, final=0 (el próximo va en 0)
     */
    static class ColaCircular {
        private final int[] datos;
        private int frente = 0;     // de dónde se saca
        private int cantidad = 0;

        ColaCircular(int capacidad) { datos = new int[capacidad]; }

        boolean offer(int valor) {
            if (cantidad == datos.length) return false;            // llena
            int fin = (frente + cantidad) % datos.length;          // posición libre
            datos[fin] = valor;
            cantidad++;
            return true;
        }

        Integer poll() {
            if (cantidad == 0) return null;
            int valor = datos[frente];
            frente = (frente + 1) % datos.length;                  // avanza y "da la vuelta"
            cantidad--;
            return valor;
        }

        Integer peek() { return cantidad == 0 ? null : datos[frente]; }
        boolean isEmpty() { return cantidad == 0; }
    }

    // ======================================================================
    //  EJEMPLO: triage de un hospital con PriorityQueue
    // ======================================================================
    record Paciente(String nombre, int gravedad, int llegada) {
        @Override public String toString() { return nombre + "(g" + gravedad + ")"; }
    }

    // ======================================================================
    //  EJEMPLO: generar los binarios del 1 al n con una cola
    // ======================================================================
    /** "1" → encola "10","11" → "10" encola "100","101"... salen en orden. */
    static String binariosHasta(int n) {
        Queue<String> cola = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        cola.offer("1");
        for (int i = 0; i < n; i++) {
            String actual = cola.poll();
            sb.append(actual).append(" ");
            cola.offer(actual + "0");
            cola.offer(actual + "1");
        }
        return sb.toString().trim();
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== Cola FIFO con LinkedList ===");
        Queue<String> fila = new LinkedList<>();
        fila.offer("Ana");
        fila.offer("Beto");
        fila.offer("Caro");
        System.out.println("  fila = " + fila + "   (frente a la izquierda)");
        System.out.println("  peek() = " + fila.peek());
        System.out.println("  poll() = " + fila.poll() + " → " + fila);
        System.out.println("  size() = " + fila.size());
        fila.clear();
        System.out.println("  poll() en cola vacía = " + fila.poll() + "  (no lanza excepción)");

        System.out.println("\n=== Cola circular propia (capacidad 3) ===");
        ColaCircular cc = new ColaCircular(3);
        System.out.println("  offer 1,2,3 → " + cc.offer(1) + "," + cc.offer(2) + "," + cc.offer(3));
        System.out.println("  offer 4 (llena) → " + cc.offer(4));
        System.out.println("  poll → " + cc.poll() + ", offer 4 → " + cc.offer(4) + " (reutiliza el hueco)");
        System.out.print("  vaciando: ");
        while (!cc.isEmpty()) System.out.print(cc.poll() + " ");
        System.out.println();

        System.out.println("\n=== PriorityQueue: sale el MENOR, no el primero ===");
        Queue<Integer> pq = new PriorityQueue<>();
        for (int x : new int[]{5, 1, 8, 3, 2}) pq.offer(x);
        System.out.println("  insertados: 5 1 8 3 2");
        System.out.print("  salen:      ");
        while (!pq.isEmpty()) System.out.print(pq.poll() + " ");
        System.out.println();

        System.out.println("\n=== PriorityQueue con Comparator: triage hospitalario ===");
        // Mayor gravedad primero; a igual gravedad, quien llegó antes.
        Queue<Paciente> guardia = new PriorityQueue<>(
                Comparator.comparingInt(Paciente::gravedad).reversed()
                          .thenComparingInt(Paciente::llegada));
        guardia.offer(new Paciente("Juan", 2, 1));
        guardia.offer(new Paciente("Sofía", 5, 2));
        guardia.offer(new Paciente("Pedro", 3, 3));
        guardia.offer(new Paciente("Lucía", 5, 4));
        System.out.print("  orden de atención: ");
        while (!guardia.isEmpty()) System.out.print(guardia.poll() + " ");
        System.out.println();

        System.out.println("\n=== Deque: cola de doble extremo ===");
        Deque<Integer> dq = new ArrayDeque<>();
        dq.offerLast(2); dq.offerLast(3); dq.offerFirst(1);
        System.out.println("  " + dq + "  pollFirst=" + dq.pollFirst() + " pollLast=" + dq.pollLast());

        System.out.println("\n=== Aplicación: binarios del 1 al 10 ===");
        System.out.println("  " + binariosHasta(10));
    }
}
