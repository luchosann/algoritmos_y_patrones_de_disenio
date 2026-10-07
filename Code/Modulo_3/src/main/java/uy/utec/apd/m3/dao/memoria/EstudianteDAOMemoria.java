package uy.utec.apd.m3.dao.memoria;

import uy.utec.apd.m3.dao.EstudianteDAO;
import uy.utec.apd.m3.modelo.Estudiante;

public class EstudianteDAOMemoria extends CrudDAOMemoria<Estudiante, String> implements EstudianteDAO {

    public EstudianteDAOMemoria() {
        super(Estudiante::getCi);
    }
}
