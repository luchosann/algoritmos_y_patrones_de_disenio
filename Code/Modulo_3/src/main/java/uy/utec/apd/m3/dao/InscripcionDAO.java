package uy.utec.apd.m3.dao;

import uy.utec.apd.m3.modelo.Inscripcion;

import java.util.List;

public interface InscripcionDAO {

    /** @throws IllegalStateException si ya existe esa inscripción */
    void guardar(Inscripcion inscripcion);

    boolean eliminar(String ci, String codigoCurso);

    boolean existe(String ci, String codigoCurso);

    /** En orden de inscripción. */
    List<Inscripcion> listarPorCurso(String codigoCurso);

    /** En orden de inscripción. */
    List<Inscripcion> listarPorEstudiante(String ci);

    int contarPorCurso(String codigoCurso);
}
