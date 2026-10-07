package uy.utec.apd.m3.servicio;

/** Violación de una regla de negocio. El mensaje se muestra al usuario. */
public class NegocioException extends RuntimeException {

    public NegocioException(String mensaje) {
        super(mensaje);
    }
}
