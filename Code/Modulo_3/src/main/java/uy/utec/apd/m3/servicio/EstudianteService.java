package uy.utec.apd.m3.servicio;

import uy.utec.apd.m3.dao.EstudianteDAO;
import uy.utec.apd.m3.modelo.Estudiante;
import uy.utec.apd.m3.util.ValidadorCI;
import uy.utec.apd.m3.util.ValidadorEmail;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class EstudianteService {

    private final EstudianteDAO dao;

    public EstudianteService(EstudianteDAO dao) {
        this.dao = dao;
    }

    /**
     * Registra un estudiante con la CI NORMALIZADA y el nombre sin espacios sobrantes (trim).
     * Validaciones en este orden, lanzando NegocioException con el mensaje indicado:
     * <ol>
     *   <li>CI inválida → "CI invalida"</li>
     *   <li>nombre null o en blanco → "Nombre obligatorio"</li>
     *   <li>email inválido → "Email invalido"</li>
     *   <li>CI ya registrada → "Ya existe un estudiante con CI &lt;ci normalizada&gt;"</li>
     * </ol>
     */
    public Estudiante registrar(String ci, String nombre, String email) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Acepta la CI en cualquier formato. */
    public Optional<Estudiante> buscar(String ci) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Ordenados alfabéticamente por nombre. */
    public List<Estudiante> listar() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
