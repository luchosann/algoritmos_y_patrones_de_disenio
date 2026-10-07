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

    // TODO: Escribir los tests acá (usá @Test, assertEquals, assertThrows...)
}
