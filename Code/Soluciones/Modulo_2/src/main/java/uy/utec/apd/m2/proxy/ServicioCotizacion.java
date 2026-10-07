package uy.utec.apd.m2.proxy;

/** Interfaz común del objeto real y de los proxies. */
public interface ServicioCotizacion {

    /** Pesos uruguayos por 1 unidad de la moneda (p. ej. "USD"). */
    double cotizar(String moneda);
}
