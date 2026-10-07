package uy.utec.apd.m4.hash;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Problemas que se resuelven en O(n) gracias a un HashMap (acá SÍ se usa java.util.HashMap).
 */
public final class ProblemasHash {

    private ProblemasHash() {
    }

    /**
     * Cuántas veces aparece cada palabra. Se pasa a minúsculas y se separa por
     * cualquier cosa que no sea letra ({@code "[^\\p{L}]+"}). Texto vacío → mapa vacío.
     */
    public static Map<String, Integer> frecuenciaPalabras(String texto) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Índices {i, j} (i &lt; j) de dos números que suman el objetivo, en UNA pasada (O(n)).
     * Si hay varias soluciones, la que tenga el menor j. Si no hay, un array vacío.
     * Ej.: [2, 7, 11, 15], 9 → {0, 1}.
     */
    public static int[] dosSuma(int[] numeros, int objetivo) {
        // TODO: Guardar en un mapa: valor visto → índice
        throw new UnsupportedOperationException("TODO");
    }

    /** Primer carácter que aparece UNA sola vez. Ej.: "programacion" → 'p'; "aabb" → vacío. */
    public static Optional<Character> primerNoRepetido(String texto) {
        // TODO: Dos pasadas: contar y luego buscar
        throw new UnsupportedOperationException("TODO");
    }

    /** Anagramas ignorando espacios y mayúsculas. Ej.: "Roma", "amor" → true. */
    public static boolean sonAnagramas(String a, String b) {
        // TODO: Contar letras de a (+1) y de b (-1): todo debe quedar en 0
        throw new UnsupportedOperationException("TODO");
    }
}
