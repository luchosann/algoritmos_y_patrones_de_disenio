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
        // @sol
        String ciNormalizada = buscarEstudiante(ci).getCi();
        Curso curso = buscarCurso(codigoCurso);
        if (inscripcionDAO.existe(ciNormalizada, curso.getCodigo())) {
            throw new NegocioException("El estudiante ya esta inscripto");
        }
        if (inscripcionDAO.contarPorCurso(curso.getCodigo()) >= curso.getCupoMaximo()) {
            throw new NegocioException("Cupo lleno");
        }
        inscripcionDAO.guardar(new Inscripcion(ciNormalizada, curso.getCodigo()));
        // @end
    }

    /** @throws NegocioException "El estudiante no esta inscripto" si no había inscripción */
    public void darDeBaja(String ci, String codigoCurso) {
        // @sol
        String ciNormalizada = ValidadorCI.normalizar(ci);
        String codigo = codigoCurso.trim().toUpperCase(Locale.ROOT);
        if (ciNormalizada == null || !inscripcionDAO.eliminar(ciNormalizada, codigo)) {
            throw new NegocioException("El estudiante no esta inscripto");
        }
        // @end
    }

    /**
     * Estudiantes inscriptos en el curso, ordenados por nombre.
     *
     * @throws NegocioException "No existe el curso"
     */
    public List<Estudiante> estudiantesDe(String codigoCurso) {
        // @sol
        Curso curso = buscarCurso(codigoCurso);
        List<Estudiante> resultado = new ArrayList<>();
        for (Inscripcion i : inscripcionDAO.listarPorCurso(curso.getCodigo())) {
            estudianteDAO.buscarPorId(i.ci()).ifPresent(resultado::add);
        }
        resultado.sort(Comparator.comparing(Estudiante::getNombre));
        return resultado;
        // @end
    }

    /**
     * Cupos libres del curso.
     *
     * @throws NegocioException "No existe el curso"
     */
    public int cuposDisponibles(String codigoCurso) {
        // @sol
        Curso curso = buscarCurso(codigoCurso);
        return curso.getCupoMaximo() - inscripcionDAO.contarPorCurso(curso.getCodigo());
        // @end
    }

    // @sol-blank Sugerencia: métodos privados buscarEstudiante(ci) y buscarCurso(codigo) que lancen NegocioException
    private Estudiante buscarEstudiante(String ci) {
        String normalizada = ValidadorCI.normalizar(ci);
        if (normalizada == null) {
            throw new NegocioException("No existe el estudiante");
        }
        return estudianteDAO.buscarPorId(normalizada)
                .orElseThrow(() -> new NegocioException("No existe el estudiante"));
    }

    private Curso buscarCurso(String codigo) {
        if (codigo == null) {
            throw new NegocioException("No existe el curso");
        }
        return cursoDAO.buscarPorId(codigo.trim().toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new NegocioException("No existe el curso"));
    }
    // @end
}
