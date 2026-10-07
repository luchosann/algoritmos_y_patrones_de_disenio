package uy.utec.apd.m4.listas;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ListaEnlazadaTest {

    private ListaEnlazada<String> abc() {
        ListaEnlazada<String> l = new ListaEnlazada<>();
        l.agregarAlFinal("a");
        l.agregarAlFinal("b");
        l.agregarAlFinal("c");
        return l;
    }

    @Test
    void listaVacia() {
        ListaEnlazada<String> l = new ListaEnlazada<>();
        assertTrue(l.estaVacia());
        assertEquals(0, l.tamanio());
        assertEquals("[]", l.toString());
        assertThrows(IndexOutOfBoundsException.class, () -> l.obtener(0));
    }

    @Test
    void agregarAlInicioYAlFinal() {
        ListaEnlazada<Integer> l = new ListaEnlazada<>();
        l.agregarAlFinal(2);
        l.agregarAlInicio(1);
        l.agregarAlFinal(3);
        assertEquals("[1, 2, 3]", l.toString());
        assertEquals(3, l.tamanio());
        assertEquals(3, l.obtener(2));
    }

    @Test
    void insertarEnCualquierPosicion() {
        ListaEnlazada<String> l = abc();
        l.insertar(0, "inicio");
        l.insertar(2, "medio");
        l.insertar(l.tamanio(), "fin");
        assertEquals("[inicio, a, medio, b, c, fin]", l.toString());
        assertThrows(IndexOutOfBoundsException.class, () -> l.insertar(99, "x"));
        assertThrows(IndexOutOfBoundsException.class, () -> l.insertar(-1, "x"));
    }

    @Test
    void eliminarPorIndice() {
        ListaEnlazada<String> l = abc();
        assertEquals("b", l.eliminar(1));
        assertEquals("[a, c]", l.toString());
        assertEquals("c", l.eliminar(1));
        assertEquals("a", l.eliminar(0));
        assertTrue(l.estaVacia());
        assertThrows(IndexOutOfBoundsException.class, () -> l.eliminar(0));
    }

    @Test
    void despuesDeEliminarElUltimoSePuedeSeguirAgregandoAlFinal() {
        ListaEnlazada<String> l = abc();
        l.eliminar(2);
        l.agregarAlFinal("z");
        assertEquals("[a, b, z]", l.toString(), "La referencia a la cola debe actualizarse al eliminar el último");
        l.eliminar(0);
        l.eliminar(0);
        l.eliminar(0);
        l.agregarAlFinal("solo");
        assertEquals("[solo]", l.toString());
    }

    @Test
    void buscarYEliminarPorValor() {
        ListaEnlazada<String> l = abc();
        l.agregarAlFinal("b");
        l.agregarAlFinal(null);
        assertEquals(1, l.indiceDe("b"));
        assertEquals(4, l.indiceDe(null));
        assertEquals(-1, l.indiceDe("z"));
        assertTrue(l.contiene("c"));
        assertTrue(l.eliminarPrimero("b"));
        assertEquals("[a, c, b, null]", l.toString());
        assertFalse(l.eliminarPrimero("z"));
    }

    @Test
    void invertir() {
        ListaEnlazada<String> l = abc();
        l.invertir();
        assertEquals("[c, b, a]", l.toString());
        l.agregarAlFinal("z");
        assertEquals("[c, b, a, z]", l.toString(), "Después de invertir, la cola también cambia");

        ListaEnlazada<String> vacia = new ListaEnlazada<>();
        vacia.invertir();
        assertEquals("[]", vacia.toString());
    }

    @Test
    void elementoDelMedio() {
        ListaEnlazada<Integer> l = new ListaEnlazada<>();
        assertThrows(NoSuchElementException.class, l::elementoDelMedio);
        l.agregarAlFinal(1);
        assertEquals(1, l.elementoDelMedio());
        l.agregarAlFinal(2);
        l.agregarAlFinal(3);
        assertEquals(2, l.elementoDelMedio());
        l.agregarAlFinal(4);
        assertEquals(3, l.elementoDelMedio());
    }

    @Test
    void seRecorreConForEach() {
        List<String> vistos = new ArrayList<>();
        for (String s : abc()) {
            vistos.add(s);
        }
        assertEquals(List.of("a", "b", "c"), vistos);
    }
}
