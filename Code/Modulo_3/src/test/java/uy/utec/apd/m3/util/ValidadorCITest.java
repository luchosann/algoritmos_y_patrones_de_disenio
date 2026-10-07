package uy.utec.apd.m3.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorCITest {

    @Test
    void calculaElDigitoVerificador() {
        assertEquals(2, ValidadorCI.digitoVerificador("1234567"));
        assertEquals(5, ValidadorCI.digitoVerificador("4567890"));
        assertEquals(1, ValidadorCI.digitoVerificador("0123456"));
        assertEquals(9, ValidadorCI.digitoVerificador("2718281"));
    }

    @Test
    void digitoVerificadorRechazaEntradasInvalidas() {
        assertThrows(IllegalArgumentException.class, () -> ValidadorCI.digitoVerificador("123"));
        assertThrows(IllegalArgumentException.class, () -> ValidadorCI.digitoVerificador("12345a7"));
    }

    @Test
    void normaliza() {
        assertEquals("12345672", ValidadorCI.normalizar("1.234.567-2"));
        assertEquals("12345672", ValidadorCI.normalizar(" 1234567-2 "));
        assertEquals("01234561", ValidadorCI.normalizar("123.456-1"));
        assertNull(ValidadorCI.normalizar("12-34"));
        assertNull(ValidadorCI.normalizar("abc"));
        assertNull(ValidadorCI.normalizar(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.234.567-2", "12345672", "4.567.890-5", "3141592-1", "123.456-1"})
    void cedulasValidas(String ci) {
        assertTrue(ValidadorCI.esValida(ci), ci);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.234.567-3", "12345670", "123", "", "1.234.567-X", "123456789"})
    void cedulasInvalidas(String ci) {
        assertFalse(ValidadorCI.esValida(ci), ci);
    }

    @Test
    void nullNoEsValida() {
        assertFalse(ValidadorCI.esValida(null));
    }
}
