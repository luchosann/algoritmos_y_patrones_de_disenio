package uy.utec.apd.m5.extras;

/**
 * Ejercicio 6 (conocimientos adicionales, NO evaluado): búsqueda binaria y bits.
 * Prohibido usar Arrays.binarySearch, Integer.toBinaryString, Integer.parseInt(s, 2) e Integer.bitCount.
 */
public final class Extras {

    private Extras() {
    }

    /** Índice de la clave en el arreglo ORDENADO, o -1. Iterativa, O(log n). */
    public static int busquedaBinaria(int[] a, int clave) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Igual, pero recursiva. */
    public static int busquedaBinariaRecursiva(int[] a, int clave) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Con DUPLICADOS: índice de la PRIMERA aparición, o -1. Debe seguir siendo O(log n):
     * al encontrarla, no cortar; seguir buscando a la izquierda.
     */
    public static int primeraOcurrencia(int[] a, int clave) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Decimal (n &gt;= 0) a binario por divisiones sucesivas. 0 → "0". */
    public static String aBinario(int n) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Binario a decimal. @throws IllegalArgumentException si hay caracteres que no son 0/1 o está vacío. */
    public static int deBinario(String binario) {
        // TODO: Horner: resultado = resultado * 2 + dígito
        throw new UnsupportedOperationException("TODO");
    }

    /** Cantidad de bits en 1 (también para negativos, en complemento a 2). */
    public static int contarBitsEnUno(int n) {
        // TODO: Truco: n &amp; (n - 1) apaga el bit 1 más bajo
        throw new UnsupportedOperationException("TODO");
    }

    /** true si n es 1, 2, 4, 8, ... (en una sola expresión con operadores de bits). */
    public static boolean esPotenciaDeDos(int n) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Intercambia a[i] y a[j] usando XOR (sin variable temporal). ¡Cuidado con i == j! */
    public static void intercambiarXor(int[] a, int i, int j) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    // TODO
}
