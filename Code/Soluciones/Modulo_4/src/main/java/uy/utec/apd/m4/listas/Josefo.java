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
        // @sol
        if (n <= 0 || k <= 0) {
            throw new IllegalArgumentException("n y k deben ser positivos");
        }
        ListaCircular<Integer> ronda = new ListaCircular<>();
        for (int i = 1; i <= n; i++) {
            ronda.agregar(i);
        }
        List<Integer> orden = new ArrayList<>();
        while (!ronda.estaVacia()) {
            ronda.avanzar(k - 1);
            orden.add(ronda.eliminarActual());
        }
        return orden;
        // @end
    }

    public static int sobreviviente(int n, int k) {
        // @sol
        List<Integer> orden = ordenDeEliminacion(n, k);
        return orden.get(orden.size() - 1);
        // @end
    }
}
