package uy.utec.apd.m2.facade;

/** Resultado de una compra hecha a través de la fachada. */
public record ResultadoCompra(boolean exitosa, String mensaje, String codigoSeguimiento, double total) {

    public static ResultadoCompra exito(String codigoSeguimiento, double total) {
        return new ResultadoCompra(true, "Compra confirmada", codigoSeguimiento, total);
    }

    public static ResultadoCompra fallo(String mensaje) {
        return new ResultadoCompra(false, mensaje, null, 0);
    }
}
