package uy.utec.apd.m4.listas;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ListaCircularTest {

    @Test
    void agregarYAvanzar() {
        ListaCircular<String> l = new ListaCircular<>();
        l.agregar("a");
        l.agregar("b");
        l.agregar("c");
        assertEquals("a", l.actual());
        assertEquals("[a, b, c]", l.toString());
        l.avanzar(1);
        assertEquals("b", l.actual());
        assertEquals("[b, c, a]", l.toString());
        l.avanzar(5);
        assertEquals("a", l.actual(), "Da la vuelta: es circular");
    }

    @Test
    void agregarInsertaAntesDelActual() {
        ListaCircular<Integer> l = new ListaCircular<>();
        l.agregar(1);
        l.agregar(2);
        l.avanzar(1);
        l.agregar(3);
        assertEquals("[2, 1, 3]", l.toString(), "El 3 queda entre el 1 y el 2 (antes del actual)");
    }

    @Test
    void eliminarActual() {
        ListaCircular<String> l = new ListaCircular<>();
        l.agregar("a");
        l.agregar("b");
        l.agregar("c");
        l.avanzar(2);
        assertEquals("c", l.eliminarActual());
        assertEquals("a", l.actual(), "Después del último viene el primero");
        assertEquals(2, l.tamanio());
        l.eliminarActual();
        l.eliminarActual();
        assertTrue(l.estaVacia());
        assertEquals("[]", l.toString());
        assertThrows(NoSuchElementException.class, l::actual);
        assertThrows(NoSuchElementException.class, l::eliminarActual);
        l.agregar("z");
        assertEquals("z", l.actual());
    }

    @Test
    void josefo() {
        assertEquals(List.of(3, 6, 2, 7, 5, 1, 4), Josefo.ordenDeEliminacion(7, 3));
        assertEquals(4, Josefo.sobreviviente(7, 3));
        assertEquals(1, Josefo.sobreviviente(1, 10));
        assertEquals(5, Josefo.sobreviviente(5, 1));
        assertEquals(73, Josefo.sobreviviente(100, 2));
        assertThrows(IllegalArgumentException.class, () -> Josefo.sobreviviente(0, 3));
    }
}
