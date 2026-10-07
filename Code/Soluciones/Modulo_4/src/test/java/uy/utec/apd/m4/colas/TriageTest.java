package uy.utec.apd.m4.colas;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TriageTest {

    @Test
    void atiendePorPrioridadYDespuesPorLlegada() {
        Triage t = new Triage();
        t.llegar("Ana", 3);
        t.llegar("Beto", 1);
        t.llegar("Caro", 3);
        t.llegar("Dani", 2);
        t.llegar("Eli", 3);
        t.llegar("Fede", 1);

        assertEquals(List.of("Beto", "Fede", "Dani", "Ana", "Caro", "Eli"), t.ordenDeAtencion());
        assertEquals(6, t.enEspera(), "ordenDeAtencion no debe atender a nadie");
        assertEquals("Beto", t.atenderSiguiente());
        assertEquals("Fede", t.atenderSiguiente());
        assertEquals(4, t.enEspera());
    }

    @Test
    void desempateConMuchosPacientesDeLaMismaPrioridad() {
        Triage t = new Triage();
        for (int i = 0; i < 50; i++) {
            t.llegar("P" + i, 4);
        }
        for (int i = 0; i < 50; i++) {
            assertEquals("P" + i, t.atenderSiguiente());
        }
    }

    @Test
    void validaciones() {
        Triage t = new Triage();
        assertThrows(IllegalArgumentException.class, () -> t.llegar("X", 0));
        assertThrows(IllegalArgumentException.class, () -> t.llegar("X", 6));
        assertThrows(NoSuchElementException.class, t::atenderSiguiente);
    }
}
