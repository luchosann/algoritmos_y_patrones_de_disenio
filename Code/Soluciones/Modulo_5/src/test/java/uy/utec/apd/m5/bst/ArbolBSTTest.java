package uy.utec.apd.m5.bst;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class ArbolBSTTest {

    /*
     *            50
     *          /    \
     *        30      70
     *       /  \    /  \
     *     20   40  60   80
     *                     \
     *                      90
     */
    private ArbolBST arbol;

    @BeforeEach
    void setUp() {
        arbol = new ArbolBST();
        for (int v : new int[]{50, 30, 70, 20, 40, 60, 80, 90}) {
            arbol.insertar(v);
        }
    }

    @Test
    void insertarYBuscar() {
        assertEquals(8, arbol.tamanio());
        assertTrue(arbol.contiene(60));
        assertFalse(arbol.contiene(65));
        assertFalse(arbol.insertar(40), "No se insertan duplicados");
        assertEquals(8, arbol.tamanio());
    }

    @Test
    void minimoMaximoAlturaHojas() {
        assertEquals(20, arbol.minimo());
        assertEquals(90, arbol.maximo());
        assertEquals(4, arbol.altura());
        assertEquals(4, arbol.cantidadHojas());   // 20, 40, 60, 90
    }

    @Test
    void arbolVacio() {
        ArbolBST vacio = new ArbolBST();
        assertTrue(vacio.estaVacio());
        assertEquals(0, vacio.altura());
        assertEquals(0, vacio.cantidadHojas());
        assertEquals(List.of(), vacio.inorden());
        assertEquals(List.of(), vacio.niveles());
        assertThrows(NoSuchElementException.class, vacio::minimo);
        assertThrows(NoSuchElementException.class, vacio::maximo);
    }

    @Test
    void recorridos() {
        assertEquals(List.of(50, 30, 20, 40, 70, 60, 80, 90), arbol.preorden());
        assertEquals(List.of(20, 30, 40, 50, 60, 70, 80, 90), arbol.inorden());
        assertEquals(List.of(20, 40, 30, 60, 90, 80, 70, 50), arbol.posorden());
        assertEquals(List.of(50, 30, 70, 20, 40, 60, 80, 90), arbol.porNiveles());
        assertEquals(List.of(List.of(50), List.of(30, 70), List.of(20, 40, 60, 80), List.of(90)), arbol.niveles());
        assertEquals(arbol.preorden(), arbol.preordenIterativo());
    }

    @Test
    void eliminarHoja() {
        assertTrue(arbol.eliminar(20));
        assertEquals(List.of(30, 40, 50, 60, 70, 80, 90), arbol.inorden());
    }

    @Test
    void eliminarNodoConUnHijo() {
        assertTrue(arbol.eliminar(80));
        assertEquals(List.of(50, 30, 20, 40, 70, 60, 90), arbol.preorden(), "90 sube al lugar de 80");
    }

    @Test
    void eliminarNodoConDosHijosUsaElSucesor() {
        assertTrue(arbol.eliminar(30));
        assertEquals(List.of(50, 40, 20, 70, 60, 80, 90), arbol.preorden(), "30 se reemplaza por 40 (mínimo de su derecha)");
        assertTrue(arbol.eliminar(50));
        assertEquals(60, arbol.preorden().get(0), "La raíz se reemplaza por 60");
        assertEquals(6, arbol.tamanio());
    }

    @Test
    void eliminarInexistente() {
        assertFalse(arbol.eliminar(55));
        assertEquals(8, arbol.tamanio());
    }

    @Test
    void operacionesAleatoriasContraTreeSet() {
        Random r = new Random(99);
        ArbolBST mio = new ArbolBST();
        TreeSet<Integer> referencia = new TreeSet<>();
        for (int i = 0; i < 5_000; i++) {
            int v = r.nextInt(500);
            if (r.nextBoolean()) {
                assertEquals(referencia.add(v), mio.insertar(v));
            } else {
                assertEquals(referencia.remove(v), mio.eliminar(v));
            }
        }
        assertEquals(new ArrayList<>(referencia), mio.inorden());
        assertEquals(referencia.size(), mio.tamanio());
    }
}
