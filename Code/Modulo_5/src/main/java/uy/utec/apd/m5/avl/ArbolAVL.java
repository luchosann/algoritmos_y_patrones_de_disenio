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
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public boolean contiene(int valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int altura() {
        return altura(raiz);
    }

    public int tamanio() {
        return tamanio;
    }

    /** true si TODOS los nodos tienen factor de balance entre -1 y 1 (calcular las alturas de nuevo, sin confiar en el atributo). */
    public boolean estaBalanceado() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public List<Integer> preorden() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public List<Integer> inorden() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    private static int altura(Nodo n) {
        return n == null ? 0 : n.altura;
    }

    // TODO: Implementar: insertar recursivo, rotarDerecha, rotarIzquierda, actualizarAltura, balance...
}
