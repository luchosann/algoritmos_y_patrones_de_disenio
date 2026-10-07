package uy.utec.apd.m4.listas;

import java.util.ArrayList;
import java.util.List;

/**
 * Problema de Josefo: n personas (numeradas 1..n) en ronda. Empezando por la 1,
 * se cuenta k personas y se elimina la k-ésima; se sigue contando desde la
 * siguiente. Ej. n=7, k=3 → se eliminan 3, 6, 2, 7, 5, 1 y sobrevive la 4.
 *
 * <p>Resolverlo usando {@link ListaCircular}.</p>
 */
public final class Josefo {

    private Josefo() {
    }

    /** Orden en que se eliminan las n personas (el último es el sobreviviente). */
    public static List<Integer> ordenDeEliminacion(int n, int k) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public static int sobreviviente(int n, int k) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
