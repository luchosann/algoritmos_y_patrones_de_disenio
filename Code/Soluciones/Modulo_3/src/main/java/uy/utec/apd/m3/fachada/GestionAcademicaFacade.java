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
        // @sol Crear los 3 DAOs, los 3 servicios y la fachada
        EstudianteDAOMemoria estudianteDAO = new EstudianteDAOMemoria();
        CursoDAOMemoria cursoDAO = new CursoDAOMemoria();
        InscripcionDAOMemoria inscripcionDAO = new InscripcionDAOMemoria();
        return new GestionAcademicaFacade(
                new EstudianteService(estudianteDAO),
                new CursoService(cursoDAO),
                new InscripcionService(estudianteDAO, cursoDAO, inscripcionDAO));
        // @end
    }

    public Estudiante altaEstudiante(String ci, String nombre, String email) {
        // @sol
        return estudiantes.registrar(ci, nombre, email);
        // @end
    }

    public List<Estudiante> listarEstudiantes() {
        // @sol
        return estudiantes.listar();
        // @end
    }

    public Curso altaCurso(String codigo, String nombre, int cupo) {
        // @sol
        return cursos.registrar(codigo, nombre, cupo);
        // @end
    }

    public List<Curso> listarCursos() {
        // @sol
        return cursos.listar();
        // @end
    }

    public void inscribir(String ci, String codigoCurso) {
        // @sol
        inscripciones.inscribir(ci, codigoCurso);
        // @end
    }

    public void darDeBaja(String ci, String codigoCurso) {
        // @sol
        inscripciones.darDeBaja(ci, codigoCurso);
        // @end
    }

    public List<Estudiante> inscriptosEn(String codigoCurso) {
        // @sol
        return inscripciones.estudiantesDe(codigoCurso);
        // @end
    }

    public int cuposDisponibles(String codigoCurso) {
        // @sol
        return inscripciones.cuposDisponibles(codigoCurso);
        // @end
    }
}
