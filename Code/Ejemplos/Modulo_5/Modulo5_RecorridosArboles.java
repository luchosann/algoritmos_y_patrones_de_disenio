/*
 * ============================================================================
 *  MÓDULO 5 — RECORRIDOS DE ÁRBOLES (DFS: pre/in/posorden · BFS por niveles)
 * ============================================================================
 *  Teoría: Teorico/Modulo_5/Resumen_Modulo_5.md  (sección 2)
 *  Ejecutar:  java Modulo5_RecorridosArboles.java
 *
 *  Árbol de ejemplo del teórico:
 *
 *            1
 *           / \
 *          2   3
 *         / \
 *        4   5
 *
 *  ┌───────────┬──────────────────┬────────────┬──────────────────────────────┐
 *  │ Recorrido │ Orden            │ Resultado  │ Truco                        │
 *  ├───────────┼──────────────────┼────────────┼──────────────────────────────┤
 *  │ Preorden  │ RAÍZ → Izq → Der │ 1 2 4 5 3  │ raíz PRE (antes); copiar árbol│
 *  │ Inorden   │ Izq → RAÍZ → Der │ 4 2 5 1 3  │ en un BST sale ORDENADO       │
 *  │ Posorden  │ Izq → Der → RAÍZ │ 4 5 2 3 1  │ raíz POS (después); borrar    │
 *  │ BFS       │ nivel por nivel  │ 1 2 3 4 5  │ usa una COLA                  │
 *  └───────────┴──────────────────┴────────────┴──────────────────────────────┘
 *
 *  REGLA MNEMOTÉCNICA:  DFS → PILA (o recursión)     BFS → COLA
 * ============================================================================
 */
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Modulo5_RecorridosArboles {

    static class TreeNode {
        int value;
        TreeNode left, right;
        TreeNode(int value) { this.value = value; }
    }

    // ======================================================================
    //  DFS RECURSIVOS — sólo cambia DÓNDE se "visita" la raíz
    // ======================================================================
    static void preorden(TreeNode n, List<Integer> out) {
        if (n == null) return;
        out.add(n.value);            // ← RAÍZ primero
        preorden(n.left, out);
        preorden(n.right, out);
    }

    static void inorden(TreeNode n, List<Integer> out) {
        if (n == null) return;
        inorden(n.left, out);
        out.add(n.value);            // ← RAÍZ en el medio
        inorden(n.right, out);
    }

    static void posorden(TreeNode n, List<Integer> out) {
        if (n == null) return;
        posorden(n.left, out);
        posorden(n.right, out);
        out.add(n.value);            // ← RAÍZ al final
    }

    // ======================================================================
    //  DFS ITERATIVO con PILA (= preorden)
    //  Se apila la raíz; mientras haya algo: pop, visitar, apilar DERECHO y
    //  luego IZQUIERDO (así el izquierdo sale primero: LIFO).
    // ======================================================================
    static List<Integer> dfsIterativo(TreeNode raiz) {
        List<Integer> out = new ArrayList<>();
        if (raiz == null) return out;
        Deque<TreeNode> pila = new ArrayDeque<>();
        pila.push(raiz);
        while (!pila.isEmpty()) {
            TreeNode n = pila.pop();
            out.add(n.value);
            if (n.right != null) pila.push(n.right);
            if (n.left != null)  pila.push(n.left);
        }
        return out;
    }

    // ======================================================================
    //  BFS (por niveles) con COLA
    //  Se encola la raíz; mientras haya algo: poll, visitar, encolar
    //  IZQUIERDO y DERECHO (FIFO → se respeta el orden por niveles).
    // ======================================================================
    static List<Integer> bfs(TreeNode raiz) {
        List<Integer> out = new ArrayList<>();
        if (raiz == null) return out;
        Queue<TreeNode> cola = new LinkedList<>();
        cola.offer(raiz);
        while (!cola.isEmpty()) {
            TreeNode n = cola.poll();
            out.add(n.value);
            if (n.left != null)  cola.offer(n.left);
            if (n.right != null) cola.offer(n.right);
        }
        return out;
    }

    /** Variante de BFS que separa los niveles (procesa `size()` nodos por vuelta). */
    static List<List<Integer>> porNiveles(TreeNode raiz) {
        List<List<Integer>> niveles = new ArrayList<>();
        Queue<TreeNode> cola = new LinkedList<>();
        if (raiz != null) cola.offer(raiz);
        while (!cola.isEmpty()) {
            int cantidad = cola.size();               // nodos de ESTE nivel
            List<Integer> nivel = new ArrayList<>();
            for (int i = 0; i < cantidad; i++) {
                TreeNode n = cola.poll();
                nivel.add(n.value);
                if (n.left != null)  cola.offer(n.left);
                if (n.right != null) cola.offer(n.right);
            }
            niveles.add(nivel);
        }
        return niveles;
    }

    // ======================================================================
    //  Aplicaciones típicas de los recorridos
    // ======================================================================
    /** Posorden: primero los hijos, después el nodo (necesito sus resultados). */
    static int altura(TreeNode n) {
        return n == null ? 0 : 1 + Math.max(altura(n.left), altura(n.right));
    }

    static int contarHojas(TreeNode n) {
        if (n == null) return 0;
        if (n.left == null && n.right == null) return 1;
        return contarHojas(n.left) + contarHojas(n.right);
    }

    static int suma(TreeNode n) {
        return n == null ? 0 : n.value + suma(n.left) + suma(n.right);
    }

    // ========================================================================
    public static void main(String[] args) {
        // Armamos el árbol del teórico
        TreeNode raiz = new TreeNode(1);
        raiz.left = new TreeNode(2);
        raiz.right = new TreeNode(3);
        raiz.left.left = new TreeNode(4);
        raiz.left.right = new TreeNode(5);

        List<Integer> r;
        preorden(raiz, r = new ArrayList<>());  System.out.println("Preorden  (R-I-D): " + r);
        inorden(raiz, r = new ArrayList<>());   System.out.println("Inorden   (I-R-D): " + r);
        posorden(raiz, r = new ArrayList<>());  System.out.println("Posorden  (I-D-R): " + r);
        System.out.println("DFS con pila      : " + dfsIterativo(raiz) + "   (= preorden)");
        System.out.println("BFS con cola      : " + bfs(raiz));
        System.out.println("BFS por niveles   : " + porNiveles(raiz));

        System.out.println("\nAltura = " + altura(raiz) + ", hojas = " + contarHojas(raiz) + ", suma = " + suma(raiz));

        // Inorden de un BST → ordenado
        //          8
        //        /   \
        //       3     10
        //      / \      \
        //     1   6      14
        TreeNode bst = new TreeNode(8);
        bst.left = new TreeNode(3);
        bst.right = new TreeNode(10);
        bst.left.left = new TreeNode(1);
        bst.left.right = new TreeNode(6);
        bst.right.right = new TreeNode(14);
        inorden(bst, r = new ArrayList<>());
        System.out.println("\nInorden de un BST: " + r + "   ← ¡ordenado!");
    }
}
