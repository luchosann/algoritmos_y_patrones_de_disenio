package uy.utec.apd.m2.observer;

/** Observador. */
public interface ObservadorSubasta {

    void nuevaOferta(Subasta subasta, String postor, double monto);

    /** ganador es null (y monto 0) si la subasta cerró sin ofertas. */
    default void subastaCerrada(Subasta subasta, String ganador, double monto) {
    }
}
