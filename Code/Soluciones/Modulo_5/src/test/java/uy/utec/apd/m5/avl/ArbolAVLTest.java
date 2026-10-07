package uy.utec.apd.m5.avl;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ArbolAVLTest {

    private ArbolAVL con(int... valores) {
        ArbolAVL a = new ArbolAVL();
        for (int v : valores) {
            a.insertar(v);
        }
        return a;
    }

    @Test
    void casoIzquierdaIzquierda() {
        assertEquals(List.of(20, 10, 30), con(30, 20, 10).preorden());
    }

    @Test
    void casoDerechaDerecha() {
        assertEquals(List.of(20, 10, 30), con(10, 20, 30).preorden());
    }

    @Test
    void casoIzquierdaDerecha() {
        assertEquals(List.of(20, 10, 30), con(30, 10, 20).preorden());
    }

    @Test
    void casoDerechaIzquierda() {
        assertEquals(List.of(20, 10, 30), con(10, 30, 20).preorden());
    }

    @Test
    void ejemploDelTeorico() {
        ArbolAVL a = con(10, 20, 30, 40, 50, 25);
        assertEquals(List.of(30, 20, 10, 25, 40, 50), a.preorden());
        assertEquals(3, a.altura());
        assertTrue(a.estaBalanceado());
    }

    @Test
    void insertarOrdenadoNoDegenera() {
        ArbolAVL a = new ArbolAVL();
        for (int i = 1; i <= 1000; i++) {
            a.insertar(i);
            assertTrue(a.estaBalanceado(), "Desbalanceado después de insertar " + i);
        }
        assertEquals(1000, a.tamanio());
        assertTrue(a.altura() <= 14, "Un AVL de 1000 nodos tiene altura <= 1.44 log2(n): fue " + a.altura());
        assertEquals(IntStream.rangeClosed(1, 1000).boxed().toList(), a.inorden());
    }

    @Test
    void duplicadosYBusqueda() {
        ArbolAVL a = con(5, 3, 8);
        assertFalse(a.insertar(3));
        assertEquals(3, a.tamanio());
        assertTrue(a.contiene(8));
        assertFalse(a.contiene(7));
    }
}
