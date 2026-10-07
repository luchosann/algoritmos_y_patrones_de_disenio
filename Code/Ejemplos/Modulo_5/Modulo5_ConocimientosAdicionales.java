/*
 * ============================================================================
 *  MÓDULO 5 — CONOCIMIENTOS ADICIONALES (no evaluados)
 *             Búsqueda binaria · Sistema binario · Operaciones bit a bit
 * ============================================================================
 *  Teoría: Teorico/Modulo_5/Resumen_Modulo_5.md  (sección 3)
 *  Ejecutar:  java Modulo5_ConocimientosAdicionales.java
 *
 *  BÚSQUEDA BINARIA — O(log n), requiere un arreglo ORDENADO
 *  ----------------
 *  Mira el elemento del MEDIO: si es la clave, listo; si es menor, descarta la
 *  mitad izquierda; si es mayor, descarta la derecha. En cada paso el rango se
 *  reduce a la mitad: 1.000.000 elementos → ~20 comparaciones.
 *
 *     buscar 23 en [2 5 8 12 16 23 38 56 72 91]
 *      low=0 high=9 mid=4 → 16 < 23 → low=5
 *      low=5 high=9 mid=7 → 56 > 23 → high=6
 *      low=5 high=6 mid=5 → 23 ✔
 *
 *  SISTEMA BINARIO
 *  ---------------
 *  Cada posición es una potencia de 2:  13 = 8+4+1 = 1101₂
 *  Decimal → binario: dividir por 2 y leer los RESTOS de abajo hacia arriba.
 *
 *  OPERADORES BIT A BIT (a=5=0101, b=3=0011)
 *     &  AND   0001 = 1        |  OR   0111 = 7       ^  XOR  0110 = 6
 *     ~  NOT   -6 (complemento a 2)
 *     << izq.  5<<2 = 20 (×4)  >> der. con signo  5>>1 = 2 (÷2)
 *     >>> der. sin signo (rellena con 0 aunque sea negativo)
 * ============================================================================
 */
import java.util.Arrays;

public class Modulo5_ConocimientosAdicionales {

    // ======================================================================
    //  BÚSQUEDA BINARIA (iterativa, con traza)
    // ======================================================================
    static int busquedaBinaria(int[] a, int clave) {
        int low = 0, high = a.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;          // evita overflow de (low+high)/2
            System.out.printf("    low=%d high=%d mid=%d → a[mid]=%d%n", low, high, mid, a[mid]);
            if (a[mid] == clave)     return mid;
            else if (a[mid] < clave) low = mid + 1;   // la clave está a la DERECHA
            else                     high = mid - 1;  // la clave está a la IZQUIERDA
        }
        return -1;                                     // no está
    }

    /** Versión recursiva (divide y vencerás). */
    static int busquedaBinariaRec(int[] a, int clave, int low, int high) {
        if (low > high) return -1;
        int mid = low + (high - low) / 2;
        if (a[mid] == clave) return mid;
        return a[mid] < clave ? busquedaBinariaRec(a, clave, mid + 1, high)
                              : busquedaBinariaRec(a, clave, low, mid - 1);
    }

    // ======================================================================
    //  CONVERSIONES DECIMAL ↔ BINARIO (a mano)
    // ======================================================================
    static String decimalABinario(int n) {
        if (n == 0) return "0";
        StringBuilder restos = new StringBuilder();
        while (n > 0) {
            restos.append(n % 2);   // el resto es el próximo bit (de derecha a izquierda)
            n /= 2;
        }
        return restos.reverse().toString();   // leer de abajo hacia arriba
    }

    static int binarioADecimal(String bin) {
        int valor = 0;
        for (char c : bin.toCharArray()) {
            valor = valor * 2 + (c - '0');     // equivalente a sumar dígito × 2^posición
        }
        return valor;
    }

    static String bits4(int n) {
        return String.format("%4s", Integer.toBinaryString(n & 0xF)).replace(' ', '0');
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== Búsqueda binaria ===");
        int[] a = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        System.out.println("  arreglo: " + Arrays.toString(a));
        System.out.println("  buscar 23:");
        System.out.println("  → índice " + busquedaBinaria(a, 23));
        System.out.println("  buscar 40:");
        System.out.println("  → índice " + busquedaBinaria(a, 40) + " (no está)");
        System.out.println("  recursiva(72) → " + busquedaBinariaRec(a, 72, 0, a.length - 1));
        System.out.println("  Arrays.binarySearch(a, 56) → " + Arrays.binarySearch(a, 56) + "  (versión de la API)");

        System.out.println("\n=== Decimal ↔ binario ===");
        for (int n : new int[]{13, 19, 7, 25}) {
            System.out.printf("  %2d → %-6s (Integer.toBinaryString: %s)%n", n, decimalABinario(n), Integer.toBinaryString(n));
        }
        System.out.println("  1010 → " + binarioADecimal("1010") + "   (Integer.parseInt(\"1010\", 2) = " + Integer.parseInt("1010", 2) + ")");

        System.out.println("\n=== Operadores bit a bit con a=5, b=3 ===");
        int x = 5, y = 3;
        System.out.println("  a      = " + bits4(x) + " (" + x + ")");
        System.out.println("  b      = " + bits4(y) + " (" + y + ")");
        System.out.println("  a & b  = " + bits4(x & y) + " (" + (x & y) + ")   AND: 1 si ambos son 1");
        System.out.println("  a | b  = " + bits4(x | y) + " (" + (x | y) + ")   OR:  1 si alguno es 1");
        System.out.println("  a ^ b  = " + bits4(x ^ y) + " (" + (x ^ y) + ")   XOR: 1 si son distintos");
        System.out.println("  ~a     = " + (~x) + "          NOT: invierte todos los bits (-(a+1))");
        System.out.println("  a << 2 = " + (x << 2) + "          ×2² ");
        System.out.println("  a >> 1 = " + (x >> 1) + "           ÷2 (entero)");
        System.out.println("  -8 >> 1  = " + (-8 >> 1) + "   (conserva el signo)");
        System.out.println("  -8 >>> 1 = " + (-8 >>> 1) + "   (rellena con 0: número enorme)");

        System.out.println("\n=== Trucos útiles ===");
        int n = 42;
        System.out.println("  ¿" + n + " es par?  (n & 1) == 0 → " + ((n & 1) == 0));
        System.out.println("  ¿64 es potencia de 2?  (n & (n-1)) == 0 → " + ((64 & 63) == 0));
        System.out.println("  Bits en 1 de 13: Integer.bitCount → " + Integer.bitCount(13));
        int p = 7, q = 9;
        p ^= q; q ^= p; p ^= q;   // intercambio con XOR sin variable auxiliar
        System.out.println("  Swap con XOR de (7,9) → (" + p + "," + q + ")");
        // HashMap calcula el bucket con  hash & (capacidad-1)  en vez de  hash % capacidad
        int hash = "gato".hashCode(), capacidad = 16;
        System.out.println("  Índice HashMap de \"gato\" (cap 16): hash & 15 = " + (hash & (capacidad - 1))
                + "  == floorMod(hash,16) = " + Math.floorMod(hash, capacidad));
    }
}
