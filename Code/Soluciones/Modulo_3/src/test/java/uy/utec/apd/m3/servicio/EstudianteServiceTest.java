package uy.utec.apd.m3.servicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uy.utec.apd.m3.dao.EstudianteDAO;
import uy.utec.apd.m3.dao.memoria.EstudianteDAOMemoria;
import uy.utec.apd.m3.modelo.Estudiante;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ejercicio 5: ESCRIBIR TUS PROPIOS TESTS para EstudianteService.
 *
 * Casos a cubrir (como mínimo):
 *  - registrar un estudiante válido guarda la CI normalizada y el nombre sin espacios sobrantes
 *  - cada una de las 4 validaciones lanza NegocioException con el mensaje correcto
 *  - buscar acepta la CI con puntos y guion
 *  - buscar con una CI mal formada devuelve Optional.empty()
 *  - listar devuelve los estudiantes ordenados por nombre
 */
class EstudianteServiceTest {

    private EstudianteDAO dao;
    private EstudianteService service;

    @BeforeEach
    void setUp() {
        dao = new EstudianteDAOMemoria();
        service = new EstudianteService(dao);
    }

    // @sol-blank Escribir los tests acá (usá @Test, assertEquals, assertThrows...)
    @Test
    void registraConCiNormalizada() {
        Estudiante e = service.registrar("1.234.567-2", "  Ana Perez ", "ana@utec.edu.uy");
        assertEquals("12345672", e.getCi());
        assertEquals("Ana Perez", e.getNombre());
        assertEquals(e, dao.buscarPorId("12345672").orElseThrow());
    }

    @Test
    void validaCi() {
        NegocioException ex = assertThrows(NegocioException.class,
                () -> service.registrar("1.234.567-3", "Ana", "ana@utec.edu.uy"));
        assertEquals("CI invalida", ex.getMessage());
    }

    @Test
    void validaNombre() {
        NegocioException ex = assertThrows(NegocioException.class,
                () -> service.registrar("1.234.567-2", " ", "ana@utec.edu.uy"));
        assertEquals("Nombre obligatorio", ex.getMessage());
    }

    @Test
    void validaEmail() {
        NegocioException ex = assertThrows(NegocioException.class,
                () -> service.registrar("1.234.567-2", "Ana", "ana@"));
        assertEquals("Email invalido", ex.getMessage());
    }

    @Test
    void noPermiteCiDuplicada() {
        service.registrar("1.234.567-2", "Ana", "ana@utec.edu.uy");
        NegocioException ex = assertThrows(NegocioException.class,
                () -> service.registrar("12345672", "Otra", "otra@utec.edu.uy"));
        assertEquals("Ya existe un estudiante con CI 12345672", ex.getMessage());
        assertEquals(1, dao.listarTodos().size());
    }

    @Test
    void buscarAceptaCualquierFormato() {
        service.registrar("12345672", "Ana", "ana@utec.edu.uy");
        assertTrue(service.buscar("1.234.567-2").isPresent());
        assertTrue(service.buscar("no-es-ci").isEmpty());
    }

    @Test
    void listaOrdenadoPorNombre() {
        service.registrar("4.567.890-5", "Zoe", "zoe@utec.edu.uy");
        service.registrar("1.234.567-2", "Ana", "ana@utec.edu.uy");
        service.registrar("3.141.592-1", "Mario", "mario@utec.edu.uy");
        assertEquals(List.of("Ana", "Mario", "Zoe"),
                service.listar().stream().map(Estudiante::getNombre).toList());
    }
    // @end
}
