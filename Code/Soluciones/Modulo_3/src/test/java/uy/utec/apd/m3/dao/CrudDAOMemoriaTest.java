package uy.utec.apd.m3.dao;

import org.junit.jupiter.api.Test;
import uy.utec.apd.m3.dao.memoria.CrudDAOMemoria;
import uy.utec.apd.m3.dao.memoria.CursoDAOMemoria;
import uy.utec.apd.m3.modelo.Curso;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrudDAOMemoriaTest {

    @Test
    void funcionaConCualquierTipo() {
        record Ciudad(String nombre, int habitantes) {
        }
        CrudDAO<Ciudad, String> dao = new CrudDAOMemoria<>(Ciudad::nombre);
        dao.guardar(new Ciudad("Salto", 110_000));
        dao.guardar(new Ciudad("Paysandu", 115_000));

        assertEquals(110_000, dao.buscarPorId("Salto").orElseThrow().habitantes());
        assertTrue(dao.existe("Paysandu"));
        assertFalse(dao.existe("Rivera"));
    }

    @Test
    void crudCompleto() {
        CursoDAO dao = new CursoDAOMemoria();
        dao.guardar(new Curso("APD", "Algoritmos", 30));
        dao.guardar(new Curso("BD", "Bases de datos", 20));

        assertEquals(List.of("APD", "BD"), dao.listarTodos().stream().map(Curso::getCodigo).toList());
        assertTrue(dao.actualizar(new Curso("BD", "Bases de datos II", 25)));
        assertEquals(25, dao.buscarPorId("BD").orElseThrow().getCupoMaximo());
        assertFalse(dao.actualizar(new Curso("X", "X", 1)));
        assertTrue(dao.eliminar("APD"));
        assertFalse(dao.eliminar("APD"));
        assertEquals(1, dao.listarTodos().size());
    }

    @Test
    void mantieneElOrdenDeInsercion() {
        CursoDAO dao = new CursoDAOMemoria();
        for (String c : List.of("Z", "A", "M")) {
            dao.guardar(new Curso(c, c, 1));
        }
        assertEquals(List.of("Z", "A", "M"), dao.listarTodos().stream().map(Curso::getCodigo).toList());
    }

    @Test
    void noPermiteDuplicados() {
        CursoDAO dao = new CursoDAOMemoria();
        dao.guardar(new Curso("APD", "Algoritmos", 30));
        assertThrows(IllegalStateException.class, () -> dao.guardar(new Curso("APD", "Otro", 1)));
    }

    @Test
    void listarDevuelveUnaCopia() {
        CursoDAO dao = new CursoDAOMemoria();
        dao.guardar(new Curso("APD", "Algoritmos", 30));
        dao.listarTodos().clear();
        assertEquals(1, dao.listarTodos().size());
    }
}
