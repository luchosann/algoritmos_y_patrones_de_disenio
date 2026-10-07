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
        // @sol
        return esBST(raiz, Long.MIN_VALUE, Long.MAX_VALUE);
        // @end
    }

    /**
     * Ancestro común más bajo de a y b en un BST (se asume que ambos están).
     * Aprovechar el orden: si ambos son menores que el nodo, está a la izquierda, etc.
     */
    public static int ancestroComunMasBajo(Nodo raizBST, int a, int b) {
        // @sol
        Nodo n = raizBST;
        while (n != null) {
            if (a < n.valor && b < n.valor) {
                n = n.izq;
            } else if (a > n.valor && b > n.valor) {
                n = n.der;
            } else {
                return n.valor;
            }
        }
        throw new NoSuchElementException("Valores no encontrados");
        // @end
    }

    /**
     * k-ésimo menor valor de un BST (k empieza en 1). Pista: recorrido inorden con contador.
     *
     * @throws NoSuchElementException si k &lt; 1 o k &gt; cantidad de nodos
     */
    public static int kEsimoMenor(Nodo raizBST, int k) {
        // @sol
        int[] estado = {k, 0};   // {restantes, resultado}
        if (k < 1 || !kEsimo(raizBST, estado)) {
            throw new NoSuchElementException("k fuera de rango: " + k);
        }
        return estado[1];
        // @end
    }

    /** ¿Existe un camino desde la RAÍZ hasta alguna HOJA cuya suma sea exactamente suma? Vacío → false. */
    public static boolean existeCaminoConSuma(Nodo raiz, int suma) {
        // @sol
        if (raiz == null) {
            return false;
        }
        int resto = suma - raiz.valor;
        if (raiz.izq == null && raiz.der == null) {
            return resto == 0;
        }
        return existeCaminoConSuma(raiz.izq, resto) || existeCaminoConSuma(raiz.der, resto);
        // @end
    }

    /**
     * Construye un BST de altura MÍNIMA a partir de un arreglo ORDENADO:
     * la raíz es el elemento del medio y se repite con cada mitad. Arreglo vacío → null.
     * (Con cantidad par, usar como medio el índice (inicio + fin) / 2.)
     */
    public static Nodo desdeArregloOrdenado(int[] ordenado) {
        // @sol
        return construir(ordenado, 0, ordenado.length - 1);
        // @end
    }

    /** Devuelve un árbol NUEVO que es la imagen espejo (izquierda ↔ derecha). No modifica el original. */
    public static Nodo espejo(Nodo raiz) {
        // @sol
        if (raiz == null) {
            return null;
        }
        return new Nodo(raiz.valor, espejo(raiz.der), espejo(raiz.izq));
        // @end
    }

    // @sol-blank Métodos auxiliares
    private static boolean esBST(Nodo n, long min, long max) {
        if (n == null) {
            return true;
        }
        if (n.valor <= min || n.valor >= max) {
            return false;
        }
        return esBST(n.izq, min, n.valor) && esBST(n.der, n.valor, max);
    }

    /** Inorden que descuenta estado[0]; al llegar a 0 guarda el valor en estado[1]. */
    private static boolean kEsimo(Nodo n, int[] estado) {
        if (n == null) {
            return false;
        }
        if (kEsimo(n.izq, estado)) {
            return true;
        }
        if (--estado[0] == 0) {
            estado[1] = n.valor;
            return true;
        }
        return kEsimo(n.der, estado);
    }

    private static Nodo construir(int[] a, int inicio, int fin) {
        if (inicio > fin) {
            return null;
        }
        int medio = (inicio + fin) / 2;
        return new Nodo(a[medio], construir(a, inicio, medio - 1), construir(a, medio + 1, fin));
    }
    // @end
}
