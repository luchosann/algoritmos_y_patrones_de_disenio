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
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Sin distinguir mayúsculas. */
    public Optional<Curso> buscar(String codigo) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Ordenados por código. */
    public List<Curso> listar() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
