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
        // @sol
        int antes = tamanio;
        raiz = insertar(raiz, valor);
        return tamanio > antes;
        // @end
    }

    public boolean contiene(int valor) {
        // @sol Iterativo: bajar a la izquierda o derecha según el valor
        Nodo n = raiz;
        while (n != null) {
            if (valor == n.valor) {
                return true;
            }
            n = valor < n.valor ? n.izq : n.der;
        }
        return false;
        // @end
    }

    /**
     * Elimina el valor. Tres casos: hoja, un hijo, dos hijos
     * (reemplazar por el sucesor in-order = mínimo del subárbol derecho).
     *
     * @return false si el valor no estaba
     */
    public boolean eliminar(int valor) {
        // @sol
        int antes = tamanio;
        raiz = eliminar(raiz, valor);
        return tamanio < antes;
        // @end
    }

    /** @throws NoSuchElementException si está vacío */
    public int minimo() {
        // @sol
        if (raiz == null) {
            throw new NoSuchElementException("Árbol vacío");
        }
        return minimo(raiz);
        // @end
    }

    /** @throws NoSuchElementException si está vacío */
    public int maximo() {
        // @sol
        if (raiz == null) {
            throw new NoSuchElementException("Árbol vacío");
        }
        Nodo n = raiz;
        while (n.der != null) {
            n = n.der;
        }
        return n.valor;
        // @end
    }

    public int altura() {
        // @sol
        return altura(raiz);
        // @end
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public int cantidadHojas() {
        // @sol
        return hojas(raiz);
        // @end
    }

    /** Raíz → Izq → Der */
    public List<Integer> preorden() {
        // @sol
        List<Integer> r = new ArrayList<>();
        preorden(raiz, r);
        return r;
        // @end
    }

    /** Izq → Raíz → Der (en un BST sale ORDENADO) */
    public List<Integer> inorden() {
        // @sol
        List<Integer> r = new ArrayList<>();
        inorden(raiz, r);
        return r;
        // @end
    }

    /** Izq → Der → Raíz */
    public List<Integer> posorden() {
        // @sol
        List<Integer> r = new ArrayList<>();
        posorden(raiz, r);
        return r;
        // @end
    }

    /** BFS: nivel por nivel, de izquierda a derecha. Usar una cola. */
    public List<Integer> porNiveles() {
        // @sol
        List<Integer> r = new ArrayList<>();
        for (List<Integer> nivel : niveles()) {
            r.addAll(nivel);
        }
        return r;
        // @end
    }

    /** Igual que porNiveles pero agrupado: [[raíz], [nivel 1...], [nivel 2...]]. */
    public List<List<Integer>> niveles() {
        // @sol Pista: en cada vuelta del while, procesar exactamente queue.size() nodos
        List<List<Integer>> r = new ArrayList<>();
        if (raiz == null) {
            return r;
        }
        Queue<Nodo> cola = new ArrayDeque<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            int cantidad = cola.size();
            List<Integer> nivel = new ArrayList<>();
            for (int i = 0; i < cantidad; i++) {
                Nodo n = cola.poll();
                nivel.add(n.valor);
                if (n.izq != null) {
                    cola.add(n.izq);
                }
                if (n.der != null) {
                    cola.add(n.der);
                }
            }
            r.add(nivel);
        }
        return r;
        // @end
    }

    /** Preorden SIN recursión, con una pila explícita (DFS). */
    public List<Integer> preordenIterativo() {
        // @sol Apilar primero el hijo derecho y después el izquierdo
        List<Integer> r = new ArrayList<>();
        if (raiz == null) {
            return r;
        }
        Deque<Nodo> pila = new ArrayDeque<>();
        pila.push(raiz);
        while (!pila.isEmpty()) {
            Nodo n = pila.pop();
            r.add(n.valor);
            if (n.der != null) {
                pila.push(n.der);
            }
            if (n.izq != null) {
                pila.push(n.izq);
            }
        }
        return r;
        // @end
    }

    // @sol-blank Métodos recursivos auxiliares que reciben un Nodo
    private Nodo insertar(Nodo n, int valor) {
        if (n == null) {
            tamanio++;
            return new Nodo(valor);
        }
        if (valor < n.valor) {
            n.izq = insertar(n.izq, valor);
        } else if (valor > n.valor) {
            n.der = insertar(n.der, valor);
        }
        return n;
    }

    private Nodo eliminar(Nodo n, int valor) {
        if (n == null) {
            return null;
        }
        if (valor < n.valor) {
            n.izq = eliminar(n.izq, valor);
        } else if (valor > n.valor) {
            n.der = eliminar(n.der, valor);
        } else {
            if (n.izq == null) {
                tamanio--;
                return n.der;
            }
            if (n.der == null) {
                tamanio--;
                return n.izq;
            }
            n.valor = minimo(n.der);
            n.der = eliminar(n.der, n.valor);
        }
        return n;
    }

    private static int minimo(Nodo n) {
        while (n.izq != null) {
            n = n.izq;
        }
        return n.valor;
    }

    private static int altura(Nodo n) {
        return n == null ? 0 : 1 + Math.max(altura(n.izq), altura(n.der));
    }

    private static int hojas(Nodo n) {
        if (n == null) {
            return 0;
        }
        if (n.izq == null && n.der == null) {
            return 1;
        }
        return hojas(n.izq) + hojas(n.der);
    }

    private static void preorden(Nodo n, List<Integer> r) {
        if (n != null) {
            r.add(n.valor);
            preorden(n.izq, r);
            preorden(n.der, r);
        }
    }

    private static void inorden(Nodo n, List<Integer> r) {
        if (n != null) {
            inorden(n.izq, r);
            r.add(n.valor);
            inorden(n.der, r);
        }
    }

    private static void posorden(Nodo n, List<Integer> r) {
        if (n != null) {
            posorden(n.izq, r);
            posorden(n.der, r);
            r.add(n.valor);
        }
    }
    // @end
}
