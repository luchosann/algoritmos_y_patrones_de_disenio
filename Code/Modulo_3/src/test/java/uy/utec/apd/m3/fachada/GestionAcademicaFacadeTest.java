package uy.utec.apd.m3.fachada;

import org.junit.jupiter.api.Test;
import uy.utec.apd.m3.modelo.Estudiante;
import uy.utec.apd.m3.servicio.NegocioException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Test de INTEGRACIÓN: fachada + servicios + DAOs en memoria, todos reales. */
class GestionAcademicaFacadeTest {

    @Test
    void flujoCompleto() {
        GestionAcademicaFacade app = GestionAcademicaFacade.enMemoria();
        app.altaEstudiante("1.234.567-2", "Ana", "ana@utec.edu.uy");
        app.altaEstudiante("4.567.890-5", "Beto", "beto@utec.edu.uy");
        app.altaEstudiante("3.141.592-1", "Caro", "caro@utec.edu.uy");
        app.altaCurso("apd", "Algoritmos", 2);

        app.inscribir("4.567.890-5", "APD");
        app.inscribir("12345672", "apd");
        assertEquals(0, app.cuposDisponibles("APD"));
        assertEquals("Cupo lleno",
                assertThrows(NegocioException.class, () -> app.inscribir("3.141.592-1", "APD")).getMessage());

        assertEquals(List.of("Ana", "Beto"), app.inscriptosEn("APD").stream().map(Estudiante::getNombre).toList());

        app.darDeBaja("12345672", "APD");
        app.inscribir("3.141.592-1", "APD");
        assertEquals(List.of("Beto", "Caro"), app.inscriptosEn("APD").stream().map(Estudiante::getNombre).toList());
        assertEquals(3, app.listarEstudiantes().size());
        assertEquals(1, app.listarCursos().size());
    }

    @Test
    void cadaFachadaEsIndependiente() {
        GestionAcademicaFacade a = GestionAcademicaFacade.enMemoria();
        GestionAcademicaFacade b = GestionAcademicaFacade.enMemoria();
        a.altaCurso("APD", "Algoritmos", 10);
        assertTrue(b.listarCursos().isEmpty());
    }
}
