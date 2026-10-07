package uy.utec.apd.m4.pilas;

/**
 * Ejercicio 3 — Pila (LIFO) implementada con nodos enlazados.
 * Todas las operaciones son O(1): se apila y desapila por la cabeza.
 */
public class Pila<T> {

    private static class Nodo<T> {
        final T valor;
        final Nodo<T> debajo;

        Nodo(T valor, Nodo<T> debajo) {
            this.valor = valor;
            this.debajo = debajo;
        }
    }

    // TODO: Atributos: la cima y el tamaño

    /** push */
    public void apilar(T valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** pop. @throws PilaVaciaException si está vacía */
    public T desapilar() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** peek / top. @throws PilaVaciaException si está vacía */
    public T cima() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public boolean estaVacia() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int tamanio() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
