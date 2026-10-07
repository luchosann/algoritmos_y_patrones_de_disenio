package uy.utec.apd.m3.dao;

import org.junit.jupiter.api.Test;
import uy.utec.apd.m3.dao.memoria.InscripcionDAOMemoria;
import uy.utec.apd.m3.modelo.Inscripcion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InscripcionDAOMemoriaTest {

    @Test
    void guardaYConsulta() {
        InscripcionDAO dao = new InscripcionDAOMemoria();
        dao.guardar(new Inscripcion("1", "APD"));
        dao.guardar(new Inscripcion("2", "APD"));
        dao.guardar(new Inscripcion("1", "BD"));

        assertTrue(dao.existe("1", "APD"));
        assertFalse(dao.existe("2", "BD"));
        assertEquals(2, dao.contarPorCurso("APD"));
        assertEquals(List.of(new Inscripcion("1", "APD"), new Inscripcion("2", "APD")), dao.listarPorCurso("APD"));
        assertEquals(List.of(new Inscripcion("1", "APD"), new Inscripcion("1", "BD")), dao.listarPorEstudiante("1"));
    }

    @Test
    void eliminaYRechazaDuplicados() {
        InscripcionDAO dao = new InscripcionDAOMemoria();
        dao.guardar(new Inscripcion("1", "APD"));
        assertThrows(IllegalStateException.class, () -> dao.guardar(new Inscripcion("1", "APD")));
        assertTrue(dao.eliminar("1", "APD"));
        assertFalse(dao.eliminar("1", "APD"));
        assertEquals(0, dao.contarPorCurso("APD"));
    }
}
