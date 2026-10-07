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

    // @sol-blank Atributos: la cima y el tamaño
    private Nodo<T> cima;
    private int tamanio;
    // @end

    /** push */
    public void apilar(T valor) {
        // @sol
        cima = new Nodo<>(valor, cima);
        tamanio++;
        // @end
    }

    /** pop. @throws PilaVaciaException si está vacía */
    public T desapilar() {
        // @sol
        if (cima == null) {
            throw new PilaVaciaException();
        }
        T valor = cima.valor;
        cima = cima.debajo;
        tamanio--;
        return valor;
        // @end
    }

    /** peek / top. @throws PilaVaciaException si está vacía */
    public T cima() {
        // @sol
        if (cima == null) {
            throw new PilaVaciaException();
        }
        return cima.valor;
        // @end
    }

    public boolean estaVacia() {
        // @sol
        return cima == null;
        // @end
    }

    public int tamanio() {
        // @sol
        return tamanio;
        // @end
    }
}
