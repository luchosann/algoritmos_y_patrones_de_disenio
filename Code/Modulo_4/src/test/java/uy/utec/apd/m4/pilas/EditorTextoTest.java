package uy.utec.apd.m4.pilas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditorTextoTest {

    @Test
    void deshacerYRehacer() {
        EditorTexto e = new EditorTexto();
        e.escribir("Hola");
        e.escribir(" mundo");
        e.borrar(3);
        assertEquals("Hola mu", e.getTexto());

        assertTrue(e.deshacer());
        assertEquals("Hola mundo", e.getTexto());
        assertTrue(e.deshacer());
        assertEquals("Hola", e.getTexto());
        assertTrue(e.rehacer());
        assertEquals("Hola mundo", e.getTexto());
    }

    @Test
    void unaAccionNuevaBorraElHistorialDeRehacer() {
        EditorTexto e = new EditorTexto();
        e.escribir("a");
        e.escribir("b");
        e.deshacer();
        e.escribir("c");
        assertFalse(e.rehacer());
        assertEquals("ac", e.getTexto());
    }

    @Test
    void limites() {
        EditorTexto e = new EditorTexto();
        assertFalse(e.deshacer());
        assertFalse(e.rehacer());
        e.escribir("abc");
        e.borrar(10);
        assertEquals("", e.getTexto());
        e.deshacer();
        e.deshacer();
        assertFalse(e.deshacer());
        assertEquals("", e.getTexto());
    }
}
