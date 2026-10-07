package uy.utec.apd.m3.servicio;

import uy.utec.apd.m3.dao.CursoDAO;
import uy.utec.apd.m3.dao.EstudianteDAO;
import uy.utec.apd.m3.dao.InscripcionDAO;
import uy.utec.apd.m3.modelo.Curso;
import uy.utec.apd.m3.modelo.Estudiante;
import uy.utec.apd.m3.modelo.Inscripcion;
import uy.utec.apd.m3.util.ValidadorCI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Coordina tres DAOs. Se testea con MOCKS (ver InscripcionServiceTest).
 * <p>La CI llega en cualquier formato (normalizarla con ValidadorCI; si no se puede,
 * tratarla como estudiante inexistente) y el código del curso en cualquier
 * combinación de mayúsculas/minúsculas.</p>
 */
public class InscripcionService {

    private final EstudianteDAO estudianteDAO;
    private final CursoDAO cursoDAO;
    private final InscripcionDAO inscripcionDAO;

    public InscripcionService(EstudianteDAO estudianteDAO, CursoDAO cursoDAO, InscripcionDAO inscripcionDAO) {
        this.estudianteDAO = estudianteDAO;
        this.cursoDAO = cursoDAO;
        this.inscripcionDAO = inscripcionDAO;
    }

    /**
     * Reglas, en este orden (NegocioException con el mensaje exacto):
     * <ol>
     *   <li>"No existe el estudiante"</li>
     *   <li>"No existe el curso"</li>
     *   <li>"El estudiante ya esta inscripto"</li>
     *   <li>"Cupo lleno"</li>
     * </ol>
     * Si todo está bien, guarda la inscripción.
     */
    public void inscribir(String ci, String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** @throws NegocioException "El estudiante no esta inscripto" si no había inscripción */
    public void darDeBaja(String ci, String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Estudiantes inscriptos en el curso, ordenados por nombre.
     *
     * @throws NegocioException "No existe el curso"
     */
    public List<Estudiante> estudiantesDe(String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Cupos libres del curso.
     *
     * @throws NegocioException "No existe el curso"
     */
    public int cuposDisponibles(String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: Sugerencia: métodos privados buscarEstudiante(ci) y buscarCurso(codigo) que lancen NegocioException
}
