package uy.utec.apd.m4.hash;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProblemasHashTest {

    @Test
    void frecuencias() {
        assertEquals(Map.of("el", 2, "gato", 2, "y", 1, "perro", 1),
                ProblemasHash.frecuenciaPalabras("El gato y el perro. ¡GATO!"));
        assertEquals(Map.of("año", 1, "número", 1), ProblemasHash.frecuenciaPalabras("Año, número"));
        assertTrue(ProblemasHash.frecuenciaPalabras("").isEmpty());
    }

    @Test
    void dosSuma() {
        assertArrayEquals(new int[]{0, 1}, ProblemasHash.dosSuma(new int[]{2, 7, 11, 15}, 9));
        assertArrayEquals(new int[]{1, 2}, ProblemasHash.dosSuma(new int[]{3, 2, 4}, 6));
        assertArrayEquals(new int[]{0, 1}, ProblemasHash.dosSuma(new int[]{3, 3}, 6));
        assertArrayEquals(new int[0], ProblemasHash.dosSuma(new int[]{1, 2, 3}, 100));
    }

    @Test
    void dosSumaEsLineal() {
        int n = 200_000;
        int[] grande = new int[n];
        for (int i = 0; i < n; i++) {
            grande[i] = i * 2;
        }
        long inicio = System.nanoTime();
        assertArrayEquals(new int[]{n - 2, n - 1}, ProblemasHash.dosSuma(grande, (n - 2) * 2 + (n - 1) * 2));
        assertTrue(System.nanoTime() - inicio < 2_000_000_000L, "Con doble for (O(n²)) esto tarda demasiado");
    }

    @Test
    void primerNoRepetido() {
        assertEquals(Optional.of('p'), ProblemasHash.primerNoRepetido("programacion"));
        assertEquals(Optional.of('c'), ProblemasHash.primerNoRepetido("aabbcdd"));
        assertEquals(Optional.empty(), ProblemasHash.primerNoRepetido("aabb"));
        assertEquals(Optional.empty(), ProblemasHash.primerNoRepetido(""));
    }

    @Test
    void anagramas() {
        assertTrue(ProblemasHash.sonAnagramas("Roma", "amor"));
        assertTrue(ProblemasHash.sonAnagramas("La tele", "El tela"));
        assertFalse(ProblemasHash.sonAnagramas("hola", "holaa"));
        assertFalse(ProblemasHash.sonAnagramas("abc", "abd"));
    }
}
