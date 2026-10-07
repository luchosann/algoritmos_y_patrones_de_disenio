package uy.utec.apd.m3.servicio;

import uy.utec.apd.m3.dao.CursoDAO;
import uy.utec.apd.m3.modelo.Curso;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class CursoService {

    private final CursoDAO dao;

    public CursoService(CursoDAO dao) {
        this.dao = dao;
    }

    /**
     * Registra un curso con el código en MAYÚSCULAS y sin espacios sobrantes.
     * <ol>
     *   <li>código null o en blanco → "Codigo obligatorio"</li>
     *   <li>nombre null o en blanco → "Nombre obligatorio"</li>
     *   <li>cupo &lt;= 0 → "El cupo debe ser positivo"</li>
     *   <li>código repetido → "Ya existe el curso &lt;CODIGO&gt;"</li>
     * </ol>
     */
    public Curso registrar(String codigo, String nombre, int cupoMaximo) {
        // @sol
        if (codigo == null || codigo.isBlank()) {
            throw new NegocioException("Codigo obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new NegocioException("Nombre obligatorio");
        }
        if (cupoMaximo <= 0) {
            throw new NegocioException("El cupo debe ser positivo");
        }
        String normalizado = codigo.trim().toUpperCase(Locale.ROOT);
        if (dao.existe(normalizado)) {
            throw new NegocioException("Ya existe el curso " + normalizado);
        }
        Curso curso = new Curso(normalizado, nombre.trim(), cupoMaximo);
        dao.guardar(curso);
        return curso;
        // @end
    }

    /** Sin distinguir mayúsculas. */
    public Optional<Curso> buscar(String codigo) {
        // @sol
        return codigo == null ? Optional.empty() : dao.buscarPorId(codigo.trim().toUpperCase(Locale.ROOT));
        // @end
    }

    /** Ordenados por código. */
    public List<Curso> listar() {
        // @sol
        return dao.listarTodos().stream()
                .sorted(Comparator.comparing(Curso::getCodigo))
                .toList();
        // @end
    }
}
