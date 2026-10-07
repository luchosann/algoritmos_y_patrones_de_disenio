package uy.utec.apd.m3.dao.memoria;

import uy.utec.apd.m3.dao.CursoDAO;
import uy.utec.apd.m3.modelo.Curso;

public class CursoDAOMemoria extends CrudDAOMemoria<Curso, String> implements CursoDAO {

    public CursoDAOMemoria() {
        super(Curso::getCodigo);
    }
}
