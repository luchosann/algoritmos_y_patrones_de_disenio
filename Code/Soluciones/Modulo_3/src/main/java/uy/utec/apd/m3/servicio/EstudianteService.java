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
        // @sol
        if (!ValidadorCI.esValida(ci)) {
            throw new NegocioException("CI invalida");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new NegocioException("Nombre obligatorio");
        }
        if (!ValidadorEmail.esValido(email)) {
            throw new NegocioException("Email invalido");
        }
        String normalizada = ValidadorCI.normalizar(ci);
        if (dao.existe(normalizada)) {
            throw new NegocioException("Ya existe un estudiante con CI " + normalizada);
        }
        Estudiante estudiante = new Estudiante(normalizada, nombre.trim(), email);
        dao.guardar(estudiante);
        return estudiante;
        // @end
    }

    /** Acepta la CI en cualquier formato. */
    public Optional<Estudiante> buscar(String ci) {
        // @sol
        String normalizada = ValidadorCI.normalizar(ci);
        return normalizada == null ? Optional.empty() : dao.buscarPorId(normalizada);
        // @end
    }

    /** Ordenados alfabéticamente por nombre. */
    public List<Estudiante> listar() {
        // @sol
        return dao.listarTodos().stream()
                .sorted(Comparator.comparing(Estudiante::getNombre))
                .toList();
        // @end
    }
}
