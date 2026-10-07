package uy.utec.apd.m3.servicio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uy.utec.apd.m3.dao.CursoDAO;
import uy.utec.apd.m3.dao.EstudianteDAO;
import uy.utec.apd.m3.dao.InscripcionDAO;
import uy.utec.apd.m3.modelo.Curso;
import uy.utec.apd.m3.modelo.Estudiante;
import uy.utec.apd.m3.modelo.Inscripcion;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test UNITARIO con Mockito: los DAOs son mocks, así que solo se prueba
 * la lógica de InscripcionService (sin base de datos ni DAOs reales).
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InscripcionServiceTest {

    private static final String CI = "12345672";
    private static final Estudiante ANA = new Estudiante(CI, "Ana", "ana@utec.edu.uy");
    private static final Curso APD = new Curso("APD", "Algoritmos", 2);

    @Mock
    private EstudianteDAO estudianteDAO;
    @Mock
    private CursoDAO cursoDAO;
    @Mock
    private InscripcionDAO inscripcionDAO;

    @InjectMocks
    private InscripcionService service;

    private void existeAna() {
        when(estudianteDAO.buscarPorId(CI)).thenReturn(Optional.of(ANA));
        when(estudianteDAO.existe(CI)).thenReturn(true);
    }

    private void existeApd() {
        when(cursoDAO.buscarPorId("APD")).thenReturn(Optional.of(APD));
        when(cursoDAO.existe("APD")).thenReturn(true);
    }

    @Test
    void inscribeCuandoTodoEsValido() {
        existeAna();
        existeApd();
        when(inscripcionDAO.existe(CI, "APD")).thenReturn(false);
        when(inscripcionDAO.contarPorCurso("APD")).thenReturn(1);

        service.inscribir("1.234.567-2", "apd");

        verify(inscripcionDAO).guardar(new Inscripcion(CI, "APD"));
    }

    @Test
    void estudianteInexistente() {
        when(estudianteDAO.buscarPorId(anyString())).thenReturn(Optional.empty());
        existeApd();

        NegocioException e = assertThrows(NegocioException.class, () -> service.inscribir(CI, "APD"));
        assertEquals("No existe el estudiante", e.getMessage());
        verify(inscripcionDAO, never()).guardar(any());
    }

    @Test
    void ciMalFormadaEsEstudianteInexistente() {
        NegocioException e = assertThrows(NegocioException.class, () -> service.inscribir("abc", "APD"));
        assertEquals("No existe el estudiante", e.getMessage());
    }

    @Test
    void cursoInexistente() {
        existeAna();
        when(cursoDAO.buscarPorId(anyString())).thenReturn(Optional.empty());

        NegocioException e = assertThrows(NegocioException.class, () -> service.inscribir(CI, "XYZ"));
        assertEquals("No existe el curso", e.getMessage());
        verify(inscripcionDAO, never()).guardar(any());
    }

    @Test
    void yaInscripto() {
        existeAna();
        existeApd();
        when(inscripcionDAO.existe(CI, "APD")).thenReturn(true);
        when(inscripcionDAO.contarPorCurso("APD")).thenReturn(2);

        NegocioException e = assertThrows(NegocioException.class, () -> service.inscribir(CI, "APD"));
        assertEquals("El estudiante ya esta inscripto", e.getMessage(), "Se valida antes que el cupo");
        verify(inscripcionDAO, never()).guardar(any());
    }

    @Test
    void cupoLleno() {
        existeAna();
        existeApd();
        when(inscripcionDAO.existe(CI, "APD")).thenReturn(false);
        when(inscripcionDAO.contarPorCurso("APD")).thenReturn(2);

        NegocioException e = assertThrows(NegocioException.class, () -> service.inscribir(CI, "APD"));
        assertEquals("Cupo lleno", e.getMessage());
        verify(inscripcionDAO, never()).guardar(any());
    }

    @Test
    void darDeBaja() {
        when(inscripcionDAO.eliminar(CI, "APD")).thenReturn(true);
        service.darDeBaja("1.234.567-2", "apd");
        verify(inscripcionDAO).eliminar(CI, "APD");
    }

    @Test
    void darDeBajaSinInscripcion() {
        when(inscripcionDAO.eliminar(anyString(), anyString())).thenReturn(false);
        NegocioException e = assertThrows(NegocioException.class, () -> service.darDeBaja(CI, "APD"));
        assertEquals("El estudiante no esta inscripto", e.getMessage());
    }

    @Test
    void estudiantesDeUnCursoOrdenadosPorNombre() {
        existeApd();
        Estudiante zoe = new Estudiante("45678905", "Zoe", "zoe@utec.edu.uy");
        when(inscripcionDAO.listarPorCurso("APD"))
                .thenReturn(List.of(new Inscripcion("45678905", "APD"), new Inscripcion(CI, "APD")));
        when(estudianteDAO.buscarPorId("45678905")).thenReturn(Optional.of(zoe));
        when(estudianteDAO.buscarPorId(CI)).thenReturn(Optional.of(ANA));

        assertEquals(List.of(ANA, zoe), service.estudiantesDe("apd"));
    }

    @Test
    void cuposDisponibles() {
        existeApd();
        when(inscripcionDAO.contarPorCurso("APD")).thenReturn(1);
        assertEquals(1, service.cuposDisponibles("APD"));
        when(cursoDAO.buscarPorId("NADA")).thenReturn(Optional.empty());
        assertThrows(NegocioException.class, () -> service.cuposDisponibles("nada"));
    }
}
