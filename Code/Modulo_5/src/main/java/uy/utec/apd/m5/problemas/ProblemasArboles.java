package uy.utec.apd.m5.problemas;

import java.util.NoSuchElementException;

/**
 * Ejercicio 4 — Problemas clásicos sobre árboles binarios (casi todos recursivos).
 */
public final class ProblemasArboles {

    private ProblemasArboles() {
    }

    /**
     * ¿Es un BST válido? (sin duplicados). OJO: no alcanza con comparar cada nodo con
     * sus hijos directos; TODO el subárbol izquierdo debe ser menor, y el derecho mayor.
     * Pista: pasar un rango (mínimo, máximo) permitido hacia abajo. Árbol vacío → true.
     */
    public static boolean esBSTValido(Nodo raiz) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Ancestro común más bajo de a y b en un BST (se asume que ambos están).
     * Aprovechar el orden: si ambos son menores que el nodo, está a la izquierda, etc.
     */
    public static int ancestroComunMasBajo(Nodo raizBST, int a, int b) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * k-ésimo menor valor de un BST (k empieza en 1). Pista: recorrido inorden con contador.
     *
     * @throws NoSuchElementException si k &lt; 1 o k &gt; cantidad de nodos
     */
    public static int kEsimoMenor(Nodo raizBST, int k) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** ¿Existe un camino desde la RAÍZ hasta alguna HOJA cuya suma sea exactamente suma? Vacío → false. */
    public static boolean existeCaminoConSuma(Nodo raiz, int suma) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Construye un BST de altura MÍNIMA a partir de un arreglo ORDENADO:
     * la raíz es el elemento del medio y se repite con cada mitad. Arreglo vacío → null.
     * (Con cantidad par, usar como medio el índice (inicio + fin) / 2.)
     */
    public static Nodo desdeArregloOrdenado(int[] ordenado) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Devuelve un árbol NUEVO que es la imagen espejo (izquierda ↔ derecha). No modifica el original. */
    public static Nodo espejo(Nodo raiz) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: Métodos auxiliares
}
