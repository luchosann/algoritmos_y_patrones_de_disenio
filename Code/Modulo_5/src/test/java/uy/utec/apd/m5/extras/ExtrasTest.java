package uy.utec.apd.m5.extras;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static uy.utec.apd.m5.extras.Extras.*;

class ExtrasTest {

    private final int[] pares = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};

    @Test
    void busquedas() {
        assertEquals(4, busquedaBinaria(pares, 10));
        assertEquals(0, busquedaBinaria(pares, 2));
        assertEquals(9, busquedaBinaria(pares, 20));
        assertEquals(-1, busquedaBinaria(pares, 7));
        assertEquals(-1, busquedaBinaria(new int[0], 7));
        for (int i = 0; i < pares.length; i++) {
            assertEquals(i, busquedaBinariaRecursiva(pares, pares[i]));
        }
        assertEquals(-1, busquedaBinariaRecursiva(pares, 21));
    }

    @Test
    void primeraOcurrenciaConDuplicados() {
        int[] a = {1, 2, 2, 2, 2, 3, 5, 5};
        assertEquals(1, primeraOcurrencia(a, 2));
        assertEquals(6, primeraOcurrencia(a, 5));
        assertEquals(-1, primeraOcurrencia(a, 4));
        int[] iguales = new int[1000];
        assertEquals(0, primeraOcurrencia(iguales, 0));
    }

    @Test
    void conversiones() {
        assertEquals("0", aBinario(0));
        assertEquals("1101", aBinario(13));
        assertEquals("10011", aBinario(19));
        assertEquals("11001", aBinario(25));
        assertEquals(13, deBinario("1101"));
        assertEquals(10, deBinario("1010"));
        assertThrows(IllegalArgumentException.class, () -> deBinario("102"));
        assertThrows(IllegalArgumentException.class, () -> deBinario(""));
        Random r = new Random(1);
        for (int i = 0; i < 1000; i++) {
            int n = r.nextInt(Integer.MAX_VALUE);
            assertEquals(Integer.toBinaryString(n), aBinario(n));
            assertEquals(n, deBinario(aBinario(n)));
        }
    }

    @Test
    void bits() {
        assertEquals(0, contarBitsEnUno(0));
        assertEquals(2, contarBitsEnUno(5));
        assertEquals(32, contarBitsEnUno(-1));
        assertEquals(1, contarBitsEnUno(Integer.MIN_VALUE));
        assertTrue(esPotenciaDeDos(1));
        assertTrue(esPotenciaDeDos(1024));
        assertFalse(esPotenciaDeDos(0));
        assertFalse(esPotenciaDeDos(6));
        assertFalse(esPotenciaDeDos(Integer.MIN_VALUE));
    }

    @Test
    void intercambioXor() {
        int[] a = {5, 3};
        intercambiarXor(a, 0, 1);
        assertArrayEquals(new int[]{3, 5}, a);
        intercambiarXor(a, 1, 1);
        assertArrayEquals(new int[]{3, 5}, a, "Con i == j, el XOR ingenuo deja el valor en 0");
    }
}
