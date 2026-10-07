package uy.utec.apd.m3.dao.memoria;

import uy.utec.apd.m3.dao.InscripcionDAO;
import uy.utec.apd.m3.modelo.Inscripcion;

import java.util.ArrayList;
import java.util.List;

public class InscripcionDAOMemoria implements InscripcionDAO {

    private final List<Inscripcion> inscripciones = new ArrayList<>();

    @Override
    public void guardar(Inscripcion inscripcion) {
        // @sol
        if (inscripciones.contains(inscripcion)) {
            throw new IllegalStateException("Inscripción duplicada: " + inscripcion);
        }
        inscripciones.add(inscripcion);
        // @end
    }

    @Override
    public boolean eliminar(String ci, String codigoCurso) {
        // @sol
        return inscripciones.remove(new Inscripcion(ci, codigoCurso));
        // @end
    }

    @Override
    public boolean existe(String ci, String codigoCurso) {
        // @sol
        return inscripciones.contains(new Inscripcion(ci, codigoCurso));
        // @end
    }

    @Override
    public List<Inscripcion> listarPorCurso(String codigoCurso) {
        // @sol
        List<Inscripcion> resultado = new ArrayList<>();
        for (Inscripcion i : inscripciones) {
            if (i.codigoCurso().equals(codigoCurso)) {
                resultado.add(i);
            }
        }
        return resultado;
        // @end
    }

    @Override
    public List<Inscripcion> listarPorEstudiante(String ci) {
        // @sol
        List<Inscripcion> resultado = new ArrayList<>();
        for (Inscripcion i : inscripciones) {
            if (i.ci().equals(ci)) {
                resultado.add(i);
            }
        }
        return resultado;
        // @end
    }

    @Override
    public int contarPorCurso(String codigoCurso) {
        // @sol
        return listarPorCurso(codigoCurso).size();
        // @end
    }
}
