package uy.utec.apd.m3.fachada;

import uy.utec.apd.m3.dao.memoria.CursoDAOMemoria;
import uy.utec.apd.m3.dao.memoria.EstudianteDAOMemoria;
import uy.utec.apd.m3.dao.memoria.InscripcionDAOMemoria;
import uy.utec.apd.m3.modelo.Curso;
import uy.utec.apd.m3.modelo.Estudiante;
import uy.utec.apd.m3.servicio.CursoService;
import uy.utec.apd.m3.servicio.EstudianteService;
import uy.utec.apd.m3.servicio.InscripcionService;

import java.util.List;

/**
 * FACADE: única puerta de entrada para la capa de presentación (consola).
 * La consola no conoce servicios ni DAOs.
 */
public class GestionAcademicaFacade {

    private final EstudianteService estudiantes;
    private final CursoService cursos;
    private final InscripcionService inscripciones;

    public GestionAcademicaFacade(EstudianteService estudiantes, CursoService cursos, InscripcionService inscripciones) {
        this.estudiantes = estudiantes;
        this.cursos = cursos;
        this.inscripciones = inscripciones;
    }

    /**
     * Arma toda la aplicación con DAOs en memoria.
     * OJO: los servicios deben COMPARTIR las mismas instancias de DAO.
     */
    public static GestionAcademicaFacade enMemoria() {
        // TODO: Crear los 3 DAOs, los 3 servicios y la fachada
        throw new UnsupportedOperationException("TODO");
    }

    public Estudiante altaEstudiante(String ci, String nombre, String email) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public List<Estudiante> listarEstudiantes() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Curso altaCurso(String codigo, String nombre, int cupo) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public List<Curso> listarCursos() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void inscribir(String ci, String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void darDeBaja(String ci, String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public List<Estudiante> inscriptosEn(String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int cuposDisponibles(String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
