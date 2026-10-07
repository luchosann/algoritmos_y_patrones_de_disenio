package uy.utec.apd.m4.pilas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PilaTest {

    @Test
    void lifo() {
        Pila<Integer> p = new Pila<>();
        assertTrue(p.estaVacia());
        p.apilar(1);
        p.apilar(2);
        p.apilar(3);
        assertEquals(3, p.tamanio());
        assertEquals(3, p.cima());
        assertEquals(3, p.desapilar());
        assertEquals(2, p.desapilar());
        assertEquals(1, p.tamanio());
    }

    @Test
    void underflow() {
        Pila<String> p = new Pila<>();
        assertThrows(PilaVaciaException.class, p::desapilar);
        assertThrows(PilaVaciaException.class, p::cima);
    }

    @Test
    void balanceo() {
        assertTrue(Expresiones.estaBalanceado("{[()()]}"));
        assertTrue(Expresiones.estaBalanceado("int f(int[] a) { return a[0]; }"));
        assertTrue(Expresiones.estaBalanceado(""));
        assertFalse(Expresiones.estaBalanceado("([)]"));
        assertFalse(Expresiones.estaBalanceado("(("));
        assertFalse(Expresiones.estaBalanceado("())"));
        assertFalse(Expresiones.estaBalanceado("}"));
    }

    @Test
    void postfija() {
        assertEquals(14, Expresiones.evaluarPostfija("3 4 + 2 *"));
        assertEquals(14, Expresiones.evaluarPostfija("5 1 2 + 4 * + 3 -"));
        assertEquals(3, Expresiones.evaluarPostfija("10 3 /"));
        assertEquals(-7, Expresiones.evaluarPostfija("3 10 -"), "El orden de los operandos importa");
        assertEquals(42, Expresiones.evaluarPostfija("42"));
    }

    @Test
    void postfijaMalFormada() {
        assertThrows(IllegalArgumentException.class, () -> Expresiones.evaluarPostfija("3 +"));
        assertThrows(IllegalArgumentException.class, () -> Expresiones.evaluarPostfija("3 4"));
        assertThrows(IllegalArgumentException.class, () -> Expresiones.evaluarPostfija("3 x +"));
    }

    @Test
    void infijaAPostfija() {
        assertEquals("3 4 2 * +", Expresiones.infijaAPostfija("3 + 4 * 2"));
        assertEquals("3 4 + 2 *", Expresiones.infijaAPostfija("( 3 + 4 ) * 2"));
        assertEquals("10 4 - 3 -", Expresiones.infijaAPostfija("10 - 4 - 3"));
        assertEquals("2 3 4 * 5 - *", Expresiones.infijaAPostfija("2 * ( 3 * 4 - 5 )"));
    }

    @Test
    void evaluarInfija() {
        assertEquals(11, Expresiones.evaluar("3 + 4 * 2"));
        assertEquals(3, Expresiones.evaluar("10 - 4 - 3"));
        assertEquals(14, Expresiones.evaluar("2 * ( 3 * 4 - 5 )"));
    }
}
