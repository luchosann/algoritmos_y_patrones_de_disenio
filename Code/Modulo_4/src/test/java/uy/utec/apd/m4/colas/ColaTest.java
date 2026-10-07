package uy.utec.apd.m4.colas;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ColaTest {

    @Test
    void fifo() {
        Cola<String> c = new Cola<>();
        c.encolar("a");
        c.encolar("b");
        c.encolar("c");
        assertEquals("a", c.frente());
        assertEquals("a", c.desencolar());
        assertEquals("b", c.desencolar());
        assertEquals(1, c.tamanio());
    }

    @Test
    void vacia() {
        Cola<String> c = new Cola<>();
        assertTrue(c.estaVacia());
        assertThrows(NoSuchElementException.class, c::desencolar);
        assertThrows(NoSuchElementException.class, c::frente);
    }

    @Test
    void creceCuandoSeLlena() {
        Cola<Integer> c = new Cola<>();
        for (int i = 0; i < 5; i++) {
            c.encolar(i);
        }
        assertEquals(8, c.capacidad());
        for (int i = 0; i < 5; i++) {
            assertEquals(i, c.desencolar());
        }
    }

    @Test
    void daLaVueltaAlArraySinPerderElOrden() {
        Cola<Integer> c = new Cola<>();
        c.encolar(1);
        c.encolar(2);
        c.encolar(3);
        c.desencolar();
        c.desencolar();
        c.encolar(4);
        c.encolar(5);
        c.encolar(6);   // el inicio está en la posición 2: los datos "dan la vuelta"
        assertEquals(4, c.capacidad(), "Hay lugar: no debería crecer todavía");
        c.encolar(7);   // ahora sí crece, con los datos dando la vuelta
        for (int esperado : List.of(3, 4, 5, 6, 7)) {
            assertEquals(esperado, c.desencolar());
        }
    }

    @Test
    void secuenciaAleatoriaContraArrayDeque() {
        Random r = new Random(42);
        Cola<Integer> mia = new Cola<>();
        Queue<Integer> referencia = new ArrayDeque<>();
        for (int i = 0; i < 10_000; i++) {
            if (r.nextInt(3) > 0 || referencia.isEmpty()) {
                mia.encolar(i);
                referencia.add(i);
            } else {
                assertEquals(referencia.poll(), mia.desencolar());
            }
            assertEquals(referencia.size(), mia.tamanio());
        }
    }

    @Test
    void binarios() {
        assertEquals(List.of("1", "10", "11", "100", "101"), Binarios.generar(5));
        assertEquals(List.of(), Binarios.generar(0));
        List<String> mil = Binarios.generar(1000);
        assertEquals(Integer.toBinaryString(1000), mil.get(999));
    }
}
