package uy.utec.apd.m2.dao;

/** Error de la capa de datos (envuelve SQLException u otros errores de persistencia). */
public class DataAccessException extends RuntimeException {

    public DataAccessException(String mensaje) {
        super(mensaje);
    }

    public DataAccessException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
