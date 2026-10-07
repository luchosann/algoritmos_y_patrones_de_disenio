package uy.utec.apd.m1.factorymethod;

/** Producto del Factory Method. */
public interface Transporte {

    /** Describe la entrega, p. ej. "Camion entrega 10 cajas por carretera". */
    String entregar(String carga);

    /** Costo por kilómetro recorrido. */
    double costoPorKm();
}
