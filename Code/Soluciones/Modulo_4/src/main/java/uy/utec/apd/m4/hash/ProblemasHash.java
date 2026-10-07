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
        // @sol
        Map<String, Integer> frecuencias = new HashMap<>();
        for (String palabra : texto.toLowerCase().split("[^\\p{L}]+")) {
            if (!palabra.isEmpty()) {
                frecuencias.merge(palabra, 1, Integer::sum);
            }
        }
        return frecuencias;
        // @end
    }

    /**
     * Índices {i, j} (i &lt; j) de dos números que suman el objetivo, en UNA pasada (O(n)).
     * Si hay varias soluciones, la que tenga el menor j. Si no hay, un array vacío.
     * Ej.: [2, 7, 11, 15], 9 → {0, 1}.
     */
    public static int[] dosSuma(int[] numeros, int objetivo) {
        // @sol Guardar en un mapa: valor visto → índice
        Map<Integer, Integer> vistos = new HashMap<>();
        for (int j = 0; j < numeros.length; j++) {
            Integer i = vistos.get(objetivo - numeros[j]);
            if (i != null) {
                return new int[]{i, j};
            }
            vistos.putIfAbsent(numeros[j], j);
        }
        return new int[0];
        // @end
    }

    /** Primer carácter que aparece UNA sola vez. Ej.: "programacion" → 'p'; "aabb" → vacío. */
    public static Optional<Character> primerNoRepetido(String texto) {
        // @sol Dos pasadas: contar y luego buscar
        Map<Character, Integer> cuenta = new HashMap<>();
        for (char c : texto.toCharArray()) {
            cuenta.merge(c, 1, Integer::sum);
        }
        for (char c : texto.toCharArray()) {
            if (cuenta.get(c) == 1) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
        // @end
    }

    /** Anagramas ignorando espacios y mayúsculas. Ej.: "Roma", "amor" → true. */
    public static boolean sonAnagramas(String a, String b) {
        // @sol Contar letras de a (+1) y de b (-1): todo debe quedar en 0
        Map<Character, Integer> cuenta = new HashMap<>();
        for (char c : a.toLowerCase().replace(" ", "").toCharArray()) {
            cuenta.merge(c, 1, Integer::sum);
        }
        for (char c : b.toLowerCase().replace(" ", "").toCharArray()) {
            cuenta.merge(c, -1, Integer::sum);
        }
        for (int valor : cuenta.values()) {
            if (valor != 0) {
                return false;
            }
        }
        return true;
        // @end
    }
}
