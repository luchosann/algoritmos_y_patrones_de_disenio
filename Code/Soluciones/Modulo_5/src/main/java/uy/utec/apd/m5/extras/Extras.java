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
        // @sol
        int bajo = 0;
        int alto = a.length - 1;
        while (bajo <= alto) {
            int medio = bajo + (alto - bajo) / 2;
            if (a[medio] == clave) {
                return medio;
            } else if (a[medio] < clave) {
                bajo = medio + 1;
            } else {
                alto = medio - 1;
            }
        }
        return -1;
        // @end
    }

    /** Igual, pero recursiva. */
    public static int busquedaBinariaRecursiva(int[] a, int clave) {
        // @sol
        return busquedaRec(a, clave, 0, a.length - 1);
        // @end
    }

    /**
     * Con DUPLICADOS: índice de la PRIMERA aparición, o -1. Debe seguir siendo O(log n):
     * al encontrarla, no cortar; seguir buscando a la izquierda.
     */
    public static int primeraOcurrencia(int[] a, int clave) {
        // @sol
        int bajo = 0;
        int alto = a.length - 1;
        int resultado = -1;
        while (bajo <= alto) {
            int medio = bajo + (alto - bajo) / 2;
            if (a[medio] == clave) {
                resultado = medio;
                alto = medio - 1;
            } else if (a[medio] < clave) {
                bajo = medio + 1;
            } else {
                alto = medio - 1;
            }
        }
        return resultado;
        // @end
    }

    /** Decimal (n &gt;= 0) a binario por divisiones sucesivas. 0 → "0". */
    public static String aBinario(int n) {
        // @sol
        if (n < 0) {
            throw new IllegalArgumentException("n debe ser >= 0");
        }
        if (n == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            sb.append(n % 2);
            n /= 2;
        }
        return sb.reverse().toString();
        // @end
    }

    /** Binario a decimal. @throws IllegalArgumentException si hay caracteres que no son 0/1 o está vacío. */
    public static int deBinario(String binario) {
        // @sol Horner: resultado = resultado * 2 + dígito
        if (binario == null || binario.isEmpty()) {
            throw new IllegalArgumentException("Binario vacío");
        }
        int resultado = 0;
        for (char c : binario.toCharArray()) {
            if (c != '0' && c != '1') {
                throw new IllegalArgumentException("Carácter inválido: " + c);
            }
            resultado = resultado * 2 + (c - '0');
        }
        return resultado;
        // @end
    }

    /** Cantidad de bits en 1 (también para negativos, en complemento a 2). */
    public static int contarBitsEnUno(int n) {
        // @sol Truco: n &amp; (n - 1) apaga el bit 1 más bajo
        int cuenta = 0;
        while (n != 0) {
            n &= n - 1;
            cuenta++;
        }
        return cuenta;
        // @end
    }

    /** true si n es 1, 2, 4, 8, ... (en una sola expresión con operadores de bits). */
    public static boolean esPotenciaDeDos(int n) {
        // @sol
        return n > 0 && (n & (n - 1)) == 0;
        // @end
    }

    /** Intercambia a[i] y a[j] usando XOR (sin variable temporal). ¡Cuidado con i == j! */
    public static void intercambiarXor(int[] a, int i, int j) {
        // @sol
        if (i == j) {
            return;
        }
        a[i] ^= a[j];
        a[j] ^= a[i];
        a[i] ^= a[j];
        // @end
    }

    // @sol-blank
    private static int busquedaRec(int[] a, int clave, int bajo, int alto) {
        if (bajo > alto) {
            return -1;
        }
        int medio = bajo + (alto - bajo) / 2;
        if (a[medio] == clave) {
            return medio;
        }
        return a[medio] < clave ? busquedaRec(a, clave, medio + 1, alto) : busquedaRec(a, clave, bajo, medio - 1);
    }
    // @end
}
