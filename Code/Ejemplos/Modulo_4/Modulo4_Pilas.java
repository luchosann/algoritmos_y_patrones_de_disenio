/*
 * ============================================================================
 *  MÓDULO 4 — PILAS (Stack) — LIFO
 * ============================================================================
 *  Teoría: Teorico/Modulo_4/Resumen_Modulo_4.md  (sección 3)
 *  Ejecutar:  java Modulo4_Pilas.java
 *
 *  IDEA
 *  ----
 *  LIFO (Last In, First Out): el ÚLTIMO que entra es el PRIMERO que sale.
 *  Como una pila de platos: sólo se toca el de arriba (el TOPE).
 *
 *        push(3)            pop() → 3
 *          ↓                  ↑
 *        | 3 |  ← tope      |   |
 *        | 2 |              | 2 |  ← tope
 *        | 1 |              | 1 |
 *        └───┘              └───┘
 *
 *  OPERACIONES (todas O(1))
 *     push(e)  apila         pop()   desapila y devuelve
 *     peek()   mira el tope  empty() ¿vacía?
 *
 *  ERRORES TÍPICOS
 *     underflow → pop() sobre una pila vacía
 *     overflow  → apilar sobre una pila llena (pilas de tamaño fijo, o la
 *                 pila de llamadas con recursión infinita: StackOverflowError)
 *
 *  USOS: deshacer (Ctrl+Z), paréntesis balanceados, evaluar expresiones,
 *        simular recursión, DFS.
 *
 *  En Java: `Stack` existe (basada en Vector, sincronizada → lenta). Hoy se
 *  recomienda `Deque<T> pila = new ArrayDeque<>();`.
 * ============================================================================
 */
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class Modulo4_Pilas {

    // ======================================================================
    //  PILA IMPLEMENTADA CON UN ARRAY (tamaño fijo → puede haber overflow)
    // ======================================================================
    static class PilaArray {
        private final int[] datos;
        private int tope = -1;          // índice del elemento de arriba; -1 = vacía

        PilaArray(int capacidad) { datos = new int[capacidad]; }

        void push(int valor) {
            if (tope == datos.length - 1) throw new IllegalStateException("Stack overflow: pila llena");
            datos[++tope] = valor;
        }

        int pop() {
            if (isEmpty()) throw new IllegalStateException("Stack underflow: pila vacía");
            return datos[tope--];
        }

        int peek() {
            if (isEmpty()) throw new IllegalStateException("Pila vacía");
            return datos[tope];
        }

        boolean isEmpty() { return tope == -1; }
    }

    // ======================================================================
    //  PILA IMPLEMENTADA CON NODOS (sin límite de tamaño)
    // ======================================================================
    static class PilaEnlazada<T> {
        private static class Nodo<T> {
            final T dato; final Nodo<T> abajo;
            Nodo(T dato, Nodo<T> abajo) { this.dato = dato; this.abajo = abajo; }
        }

        private Nodo<T> tope;

        void push(T dato) { tope = new Nodo<>(dato, tope); }   // el nuevo apunta al viejo tope

        T pop() {
            if (tope == null) throw new IllegalStateException("Stack underflow: pila vacía");
            T dato = tope.dato;
            tope = tope.abajo;
            return dato;
        }

        T peek() { return tope == null ? null : tope.dato; }
        boolean isEmpty() { return tope == null; }
    }

    // ======================================================================
    //  APLICACIONES
    // ======================================================================

    /** ¿Están balanceados (), [], {} ?  Cada cierre debe coincidir con la última apertura. */
    static boolean balanceado(String expr) {
        Deque<Character> pila = new ArrayDeque<>();
        for (char c : expr.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                pila.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (pila.isEmpty()) return false;              // cierre sin apertura
                char apertura = pila.pop();
                if ((c == ')' && apertura != '(') || (c == ']' && apertura != '[') || (c == '}' && apertura != '{')) {
                    return false;
                }
            }
        }
        return pila.isEmpty();                                 // aperturas sin cerrar
    }

    /**
     * Evalúa una expresión en notación POSFIJA (polaca inversa): "3 4 + 2 *" = 14.
     * Así evalúan expresiones las calculadoras y los compiladores.
     */
    static int evaluarPosfija(String expr) {
        Deque<Integer> pila = new ArrayDeque<>();
        for (String token : expr.trim().split("\\s+")) {
            switch (token) {
                case "+", "-", "*", "/" -> {
                    int b = pila.pop(), a = pila.pop();        // ¡ojo con el orden!
                    pila.push(switch (token) {
                        case "+" -> a + b;
                        case "-" -> a - b;
                        case "*" -> a * b;
                        default  -> a / b;
                    });
                }
                default -> pila.push(Integer.parseInt(token));
            }
        }
        return pila.pop();
    }

    /** Editor con DESHACER: cada acción se apila; deshacer = pop. */
    static class Editor {
        private final StringBuilder texto = new StringBuilder();
        private final Deque<String> historial = new ArrayDeque<>();

        void escribir(String s) {
            historial.push(texto.toString());   // guardo el estado ANTERIOR
            texto.append(s);
        }

        void deshacer() {
            if (!historial.isEmpty()) {
                texto.setLength(0);
                texto.append(historial.pop());
            }
        }

        String getTexto() { return texto.toString(); }
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== java.util.Stack (ejemplo del teórico) ===");
        Stack<Integer> pila = new Stack<>();
        pila.push(1); pila.push(2); pila.push(3);
        System.out.println("  pila = " + pila + "   (el tope es el último)");
        System.out.println("  peek() = " + pila.peek());
        System.out.println("  pop()  = " + pila.pop() + " → quedan " + pila);
        System.out.println("  empty() = " + pila.empty());

        System.out.println("\n=== Deque/ArrayDeque (recomendado) ===");
        Deque<String> deque = new ArrayDeque<>();
        deque.push("a"); deque.push("b"); deque.push("c");
        System.out.print("  desapilando: ");
        while (!deque.isEmpty()) System.out.print(deque.pop() + " ");
        System.out.println(" ← orden inverso al de entrada (LIFO)");

        System.out.println("\n=== Pila propia con array: overflow y underflow ===");
        PilaArray chica = new PilaArray(2);
        chica.push(10); chica.push(20);
        try { chica.push(30); } catch (IllegalStateException e) { System.out.println("  " + e.getMessage()); }
        chica.pop(); chica.pop();
        try { chica.pop(); } catch (IllegalStateException e) { System.out.println("  " + e.getMessage()); }

        System.out.println("\n=== Pila propia enlazada ===");
        PilaEnlazada<String> pe = new PilaEnlazada<>();
        pe.push("primero"); pe.push("segundo");
        System.out.println("  peek=" + pe.peek() + ", pop=" + pe.pop() + ", pop=" + pe.pop() + ", vacía=" + pe.isEmpty());

        System.out.println("\n=== Aplicación: paréntesis balanceados ===");
        for (String e : new String[]{"{[()()]}", "([)]", "((()", "a*(b+c)"}) {
            System.out.printf("  %-10s → %s%n", e, balanceado(e) ? "balanceado" : "NO balanceado");
        }

        System.out.println("\n=== Aplicación: evaluar notación posfija ===");
        System.out.println("  \"3 4 + 2 *\"   = " + evaluarPosfija("3 4 + 2 *"));
        System.out.println("  \"5 1 2 + 4 * + 3 -\" = " + evaluarPosfija("5 1 2 + 4 * + 3 -"));

        System.out.println("\n=== Aplicación: deshacer ===");
        Editor ed = new Editor();
        ed.escribir("Hola");
        ed.escribir(" mundo");
        ed.escribir("!!!");
        System.out.println("  texto: \"" + ed.getTexto() + "\"");
        ed.deshacer();
        System.out.println("  deshacer → \"" + ed.getTexto() + "\"");
        ed.deshacer();
        System.out.println("  deshacer → \"" + ed.getTexto() + "\"");
    }
}
