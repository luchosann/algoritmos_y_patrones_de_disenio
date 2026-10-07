package uy.utec.apd.m3.servicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uy.utec.apd.m3.dao.memoria.CursoDAOMemoria;
import uy.utec.apd.m3.modelo.Curso;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CursoServiceTest {

    private CursoService service;

    @BeforeEach
    void setUp() {
        service = new CursoService(new CursoDAOMemoria());
    }

    @Test
    void normalizaElCodigo() {
        Curso c = service.registrar("  apd ", " Algoritmos ", 30);
        assertEquals("APD", c.getCodigo());
        assertEquals("Algoritmos", c.getNombre());
        assertTrue(service.buscar("Apd").isPresent());
    }

    @Test
    void validaciones() {
        assertEquals("Codigo obligatorio",
                assertThrows(NegocioException.class, () -> service.registrar(" ", "X", 1)).getMessage());
        assertEquals("Nombre obligatorio",
                assertThrows(NegocioException.class, () -> service.registrar("X", null, 1)).getMessage());
        assertEquals("El cupo debe ser positivo",
                assertThrows(NegocioException.class, () -> service.registrar("X", "X", 0)).getMessage());
    }

    @Test
    void noPermiteCodigosRepetidos() {
        service.registrar("APD", "Algoritmos", 30);
        NegocioException e = assertThrows(NegocioException.class, () -> service.registrar("apd", "Otro", 5));
        assertEquals("Ya existe el curso APD", e.getMessage());
    }

    @Test
    void listaOrdenadoPorCodigo() {
        service.registrar("PRG", "Programacion", 10);
        service.registrar("APD", "Algoritmos", 10);
        assertEquals(List.of("APD", "PRG"), service.listar().stream().map(Curso::getCodigo).toList());
    }
}
