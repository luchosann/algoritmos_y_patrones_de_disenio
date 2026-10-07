package uy.utec.apd.m5.avl;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejercicio 3 — Árbol AVL: BST que se re-balancea con rotaciones después de cada inserción.
 *
 * <p>Cada nodo guarda su altura (hoja = 1, null = 0).
 * Factor de balance = altura(izq) - altura(der); si queda fuera de [-1, 1] se rota:</p>
 * <pre>
 *  Caso II (izq-izq):   balance &gt;  1 y valor &lt; izq.valor → rotar a la derecha
 *  Caso DD (der-der):   balance &lt; -1 y valor &gt; der.valor → rotar a la izquierda
 *  Caso ID (izq-der):   balance &gt;  1 y valor &gt; izq.valor → rotar izq a la izquierda, luego a la derecha
 *  Caso DI (der-izq):   balance &lt; -1 y valor &lt; der.valor → rotar der a la derecha, luego a la izquierda
 * </pre>
 */
public class ArbolAVL {

    static class Nodo {
        int valor;
        int altura = 1;
        Nodo izq;
        Nodo der;

        Nodo(int valor) {
            this.valor = valor;
        }
    }

    private Nodo raiz;
    private int tamanio;

    /** @return false si ya estaba */
    public boolean insertar(int valor) {
        // @sol
        int antes = tamanio;
        raiz = insertar(raiz, valor);
        return tamanio > antes;
        // @end
    }

    public boolean contiene(int valor) {
        // @sol
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

    public int altura() {
        return altura(raiz);
    }

    public int tamanio() {
        return tamanio;
    }

    /** true si TODOS los nodos tienen factor de balance entre -1 y 1 (calcular las alturas de nuevo, sin confiar en el atributo). */
    public boolean estaBalanceado() {
        // @sol
        return alturaSiBalanceado(raiz) >= 0;
        // @end
    }

    public List<Integer> preorden() {
        // @sol
        List<Integer> r = new ArrayList<>();
        preorden(raiz, r);
        return r;
        // @end
    }

    public List<Integer> inorden() {
        // @sol
        List<Integer> r = new ArrayList<>();
        inorden(raiz, r);
        return r;
        // @end
    }

    private static int altura(Nodo n) {
        return n == null ? 0 : n.altura;
    }

    // @sol-blank Implementar: insertar recursivo, rotarDerecha, rotarIzquierda, actualizarAltura, balance...
    private Nodo insertar(Nodo n, int valor) {
        if (n == null) {
            tamanio++;
            return new Nodo(valor);
        }
        if (valor < n.valor) {
            n.izq = insertar(n.izq, valor);
        } else if (valor > n.valor) {
            n.der = insertar(n.der, valor);
        } else {
            return n;
        }
        actualizarAltura(n);
        int balance = balance(n);
        if (balance > 1 && valor < n.izq.valor) {
            return rotarDerecha(n);
        }
        if (balance < -1 && valor > n.der.valor) {
            return rotarIzquierda(n);
        }
        if (balance > 1 && valor > n.izq.valor) {
            n.izq = rotarIzquierda(n.izq);
            return rotarDerecha(n);
        }
        if (balance < -1 && valor < n.der.valor) {
            n.der = rotarDerecha(n.der);
            return rotarIzquierda(n);
        }
        return n;
    }

    private static Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izq;
        y.izq = x.der;
        x.der = y;
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    private static Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.der;
        x.der = y.izq;
        y.izq = x;
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }

    private static void actualizarAltura(Nodo n) {
        n.altura = 1 + Math.max(altura(n.izq), altura(n.der));
    }

    private static int balance(Nodo n) {
        return altura(n.izq) - altura(n.der);
    }

    /** Altura real del subárbol, o -1 si algún nodo está desbalanceado. */
    private static int alturaSiBalanceado(Nodo n) {
        if (n == null) {
            return 0;
        }
        int izq = alturaSiBalanceado(n.izq);
        int der = alturaSiBalanceado(n.der);
        if (izq < 0 || der < 0 || Math.abs(izq - der) > 1) {
            return -1;
        }
        return 1 + Math.max(izq, der);
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
    // @end
}
