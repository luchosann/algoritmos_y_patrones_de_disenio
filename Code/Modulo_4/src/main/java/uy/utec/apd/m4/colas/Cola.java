package uy.utec.apd.m4.colas;

import java.util.NoSuchElementException;

/**
 * Ejercicio 4 — Cola (FIFO) sobre un ARRAY CIRCULAR que crece cuando se llena.
 *
 * <p>Se usan dos índices: {@code inicio} (el frente) y el tamaño. La posición
 * donde se encola es {@code (inicio + tamanio) % datos.length}. Al llenarse,
 * se duplica la capacidad copiando los elementos EN ORDEN al principio del nuevo array.</p>
 */
public class Cola<T> {

    public static final int CAPACIDAD_INICIAL = 4;

    private Object[] datos = new Object[CAPACIDAD_INICIAL];
    private int inicio;
    private int tamanio;

    /** enqueue. O(1) amortizado. */
    public void encolar(T valor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** dequeue. @throws NoSuchElementException si está vacía */
    @SuppressWarnings("unchecked")
    public T desencolar() {
        // TODO: No olvides poner null en la posición liberada
        throw new UnsupportedOperationException("TODO");
    }

    /** peek. @throws NoSuchElementException si está vacía */
    @SuppressWarnings("unchecked")
    public T frente() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return tamanio == 0;
    }

    /** Largo actual del array interno (para testear el crecimiento). */
    public int capacidad() {
        return datos.length;
    }

    // TODO
}
