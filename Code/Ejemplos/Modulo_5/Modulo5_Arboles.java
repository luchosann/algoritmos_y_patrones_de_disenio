/*
 * ============================================================================
 *  MÓDULO 5 — ÁRBOLES: binario, BST, AVL y TreeSet/TreeMap
 * ============================================================================
 *  Teoría: Teorico/Modulo_5/Resumen_Modulo_5.md  (sección 1)
 *  Ejecutar:  java Modulo5_Arboles.java
 *
 *  VOCABULARIO
 *  -----------
 *  raíz (nodo superior) · hijos · hojas (sin hijos) · altura (niveles)
 *  Árbol BINARIO: cada nodo tiene como máximo 2 hijos (izquierdo, derecho).
 *
 *  BST (Árbol Binario de Búsqueda)
 *  -------------------------------
 *  Para CADA nodo:  todo el subárbol IZQUIERDO < nodo < todo el subárbol DERECHO
 *
 *            50
 *          /    \
 *        30      70
 *       /  \    /  \
 *     20   40  60   80
 *
 *  Buscar/insertar/eliminar cuestan O(h):
 *    - balanceado  → h ≈ log n  → O(log n)
 *    - degenerado  → h = n      → O(n)   (insertar 1,2,3,4,5 en orden = una "lista")
 *
 *  AVL: BST que se re-balancea solo. En cada nodo
 *       factor de balance = altura(izq) − altura(der)  ∈ {-1, 0, 1}
 *  Si se sale de ese rango → ROTACIONES (II, DD, ID, DI).
 *
 *  En Java: TreeSet / TreeMap (árbol rojo-negro, otro BST balanceado).
 * ============================================================================
 */
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class Modulo5_Arboles {

    // ======================================================================
    //  BST — Árbol Binario de Búsqueda
    // ======================================================================
    static class ArbolBST {
        static class Nodo {
            int valor;
            Nodo izquierdo, derecho;
            Nodo(int v) { valor = v; }
        }

        Nodo raiz;

        // ---------- INSERTAR ----------
        void insertar(int valor) { raiz = insertar(raiz, valor); }

        /** Baja comparando hasta encontrar un lugar vacío (null) y ahí crea el nodo. */
        private Nodo insertar(Nodo n, int valor) {
            if (n == null) return new Nodo(valor);
            if (valor < n.valor)      n.izquierdo = insertar(n.izquierdo, valor);
            else if (valor > n.valor) n.derecho   = insertar(n.derecho, valor);
            // si es igual, no se insertan duplicados
            return n;
        }

        // ---------- BUSCAR ----------
        boolean buscar(int valor) {
            Nodo n = raiz;
            while (n != null) {                     // versión iterativa: misma lógica
                if (valor == n.valor) return true;
                n = valor < n.valor ? n.izquierdo : n.derecho;
            }
            return false;
        }

        // ---------- ELIMINAR (los 3 casos) ----------
        void eliminar(int valor) { raiz = eliminar(raiz, valor); }

        private Nodo eliminar(Nodo n, int valor) {
            if (n == null) return null;
            if (valor < n.valor)      n.izquierdo = eliminar(n.izquierdo, valor);
            else if (valor > n.valor) n.derecho   = eliminar(n.derecho, valor);
            else {
                // Caso 1 (hoja) y Caso 2 (un hijo): se reemplaza por el hijo (o null)
                if (n.izquierdo == null) return n.derecho;
                if (n.derecho == null)   return n.izquierdo;
                // Caso 3 (dos hijos): copio el SUCESOR in-order (mínimo del subárbol
                // derecho) y después elimino ese sucesor del subárbol derecho.
                n.valor = valorMinimo(n.derecho);
                n.derecho = eliminar(n.derecho, n.valor);
            }
            return n;
        }

        private int valorMinimo(Nodo n) {
            while (n.izquierdo != null) n = n.izquierdo;   // el mínimo está siempre a la izquierda
            return n.valor;
        }

        int altura() { return altura(raiz); }
        private int altura(Nodo n) { return n == null ? 0 : 1 + Math.max(altura(n.izquierdo), altura(n.derecho)); }

        /** Inorden de un BST = valores ORDENADOS. */
        List<Integer> enOrden() { List<Integer> r = new ArrayList<>(); enOrden(raiz, r); return r; }
        private void enOrden(Nodo n, List<Integer> r) {
            if (n == null) return;
            enOrden(n.izquierdo, r); r.add(n.valor); enOrden(n.derecho, r);
        }

        void dibujar() { dibujar(raiz, "", true); }
        private void dibujar(Nodo n, String prefijo, boolean esUltimo) {
            if (n == null) return;
            System.out.println("    " + prefijo + (esUltimo ? "└── " : "├── ") + n.valor);
            String p = prefijo + (esUltimo ? "    " : "│   ");
            if (n.izquierdo != null || n.derecho != null) {
                if (n.derecho != null) dibujar(n.derecho, p, n.izquierdo == null);   // derecho arriba
                if (n.izquierdo != null) dibujar(n.izquierdo, p, true);
            }
        }
    }

    // ======================================================================
    //  AVL — BST auto-balanceado
    // ======================================================================
    static class ArbolAVL {
        static class Nodo {
            int valor, altura = 1;
            Nodo izquierdo, derecho;
            Nodo(int v) { valor = v; }
        }

        Nodo raiz;

        private int altura(Nodo n) { return n == null ? 0 : n.altura; }
        private int balance(Nodo n) { return n == null ? 0 : altura(n.izquierdo) - altura(n.derecho); }
        private void actualizarAltura(Nodo n) { n.altura = 1 + Math.max(altura(n.izquierdo), altura(n.derecho)); }

        /*
         * Rotación DERECHA sobre y:          Rotación IZQUIERDA sobre x:
         *         y                x                 x                    y
         *        / \              / \               / \                  / \
         *       x   C    ──▶     A   y             A   y      ──▶       x   C
         *      / \                  / \               / \              / \
         *     A   B                B   C             B   C            A   B
         */
        private Nodo rotarDerecha(Nodo y) {
            Nodo x = y.izquierdo, b = x.derecho;
            x.derecho = y;
            y.izquierdo = b;
            actualizarAltura(y);
            actualizarAltura(x);
            return x;
        }

        private Nodo rotarIzquierda(Nodo x) {
            Nodo y = x.derecho, b = y.izquierdo;
            y.izquierdo = x;
            x.derecho = b;
            actualizarAltura(x);
            actualizarAltura(y);
            return y;
        }

        void insertar(int valor) { raiz = insertar(raiz, valor); }

        private Nodo insertar(Nodo n, int valor) {
            // 1) Inserción normal de BST
            if (n == null) return new Nodo(valor);
            if (valor < n.valor)      n.izquierdo = insertar(n.izquierdo, valor);
            else if (valor > n.valor) n.derecho   = insertar(n.derecho, valor);
            else return n;

            // 2) Actualizar altura y calcular balance
            actualizarAltura(n);
            int b = balance(n);

            // 3) Los 4 casos de desbalance
            if (b > 1 && valor < n.izquierdo.valor) {                 // Izquierda-Izquierda
                System.out.println("    rotación DERECHA en " + n.valor + " (caso II)");
                return rotarDerecha(n);
            }
            if (b < -1 && valor > n.derecho.valor) {                  // Derecha-Derecha
                System.out.println("    rotación IZQUIERDA en " + n.valor + " (caso DD)");
                return rotarIzquierda(n);
            }
            if (b > 1 && valor > n.izquierdo.valor) {                 // Izquierda-Derecha
                System.out.println("    rotación IZQ-DER en " + n.valor + " (caso ID)");
                n.izquierdo = rotarIzquierda(n.izquierdo);
                return rotarDerecha(n);
            }
            if (b < -1 && valor < n.derecho.valor) {                  // Derecha-Izquierda
                System.out.println("    rotación DER-IZQ en " + n.valor + " (caso DI)");
                n.derecho = rotarDerecha(n.derecho);
                return rotarIzquierda(n);
            }
            return n;
        }

        int altura() { return altura(raiz); }
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== BST: insertar 50 30 70 20 40 60 80 ===");
        ArbolBST bst = new ArbolBST();
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80}) bst.insertar(v);
        bst.dibujar();
        System.out.println("  inorden (ordenado): " + bst.enOrden());
        System.out.println("  buscar(60)=" + bst.buscar(60) + "  buscar(65)=" + bst.buscar(65));

        System.out.println("\n  eliminar 20 (caso 1: hoja)");
        bst.eliminar(20);
        System.out.println("  eliminar 30 (caso 2: un hijo) ");
        bst.eliminar(30);
        System.out.println("  eliminar 50 (caso 3: dos hijos → sube el sucesor 60)");
        bst.eliminar(50);
        bst.dibujar();

        System.out.println("\n=== BST DEGENERADO vs AVL: insertar 1..7 en orden ===");
        ArbolBST degenerado = new ArbolBST();
        ArbolAVL avl = new ArbolAVL();
        for (int v = 1; v <= 7; v++) {
            degenerado.insertar(v);
            System.out.println("  AVL inserta " + v);
            avl.insertar(v);
        }
        System.out.println("  Altura BST = " + degenerado.altura() + "  (es una lista: O(n))");
        degenerado.dibujar();
        System.out.println("  Altura AVL = " + avl.altura() + "  (balanceado: O(log n))");

        System.out.println("\n=== AVL: los casos dobles ===");
        System.out.println("  insertar 30, 10, 20  (20 entra a la DERECHA del hijo IZQUIERDO)");
        ArbolAVL id = new ArbolAVL();
        for (int v : new int[]{30, 10, 20}) id.insertar(v);
        System.out.println("  raíz final = " + id.raiz.valor);
        System.out.println("  insertar 10, 30, 20  (20 entra a la IZQUIERDA del hijo DERECHO)");
        ArbolAVL di = new ArbolAVL();
        for (int v : new int[]{10, 30, 20}) di.insertar(v);
        System.out.println("  raíz final = " + di.raiz.valor);

        System.out.println("\n=== TreeSet (conjunto ordenado, rojo-negro) ===");
        TreeSet<Integer> set = new TreeSet<>(List.of(40, 10, 30, 20, 50));
        System.out.println("  " + set + "  first=" + set.first() + " last=" + set.last());
        System.out.println("  lower(30)="   + set.lower(30)   + "   (mayor < 30)");
        System.out.println("  floor(35)="   + set.floor(35)   + "   (mayor ≤ 35)");
        System.out.println("  ceiling(35)=" + set.ceiling(35) + "   (menor ≥ 35)");
        System.out.println("  higher(30)="  + set.higher(30)  + "   (menor > 30)");
        System.out.println("  headSet(30)=" + set.headSet(30) + "  tailSet(30)=" + set.tailSet(30));

        System.out.println("\n=== TreeMap (mapa ordenado por clave) ===");
        TreeMap<String, Integer> notas = new TreeMap<>();
        notas.put("Martina", 9); notas.put("Ana", 7); notas.put("Lucas", 10); notas.put("Bruno", 6);
        System.out.println("  " + notas + "   (ordenado alfabéticamente)");
        System.out.println("  firstEntry=" + notas.firstEntry() + "  lastEntry=" + notas.lastEntry());
        System.out.println("  floorEntry(\"C\")=" + notas.floorEntry("C") + "  ceilingEntry(\"C\")=" + notas.ceilingEntry("C"));

        // Uso típico: rango de horarios
        TreeMap<Integer, String> agenda = new TreeMap<>();
        agenda.put(9, "Algoritmos"); agenda.put(11, "Base de datos"); agenda.put(14, "Inglés");
        int ahora = 12;
        System.out.println("  Son las " + ahora + "h: clase actual/anterior = " + agenda.floorEntry(ahora).getValue()
                + ", próxima = " + agenda.higherEntry(ahora).getValue());
    }
}
