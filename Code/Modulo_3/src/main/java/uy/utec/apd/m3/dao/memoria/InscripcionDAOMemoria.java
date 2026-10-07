package uy.utec.apd.m3.dao.memoria;

import uy.utec.apd.m3.dao.InscripcionDAO;
import uy.utec.apd.m3.modelo.Inscripcion;

import java.util.ArrayList;
import java.util.List;

public class InscripcionDAOMemoria implements InscripcionDAO {

    private final List<Inscripcion> inscripciones = new ArrayList<>();

    @Override
    public void guardar(Inscripcion inscripcion) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean eliminar(String ci, String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean existe(String ci, String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<Inscripcion> listarPorCurso(String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<Inscripcion> listarPorEstudiante(String ci) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int contarPorCurso(String codigoCurso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
