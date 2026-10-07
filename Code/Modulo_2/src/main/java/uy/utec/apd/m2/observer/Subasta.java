package uy.utec.apd.m2.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Sujeto concreto: notifica a los observadores cada nueva oferta y el cierre.
 */
public class Subasta {

    private final String articulo;
    private final double precioBase;
    private final List<ObservadorSubasta> observadores = new ArrayList<>();
    private String lider;
    private double ofertaActual;
    private boolean abierta = true;

    public Subasta(String articulo, double precioBase) {
        this.articulo = articulo;
        this.precioBase = precioBase;
    }

    /** No agrega duplicados (el mismo observador dos veces). */
    public void suscribir(ObservadorSubasta observador) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void desuscribir(ObservadorSubasta observador) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int cantidadObservadores() {
        return observadores.size();
    }

    /**
     * Registra una oferta y notifica a TODOS los observadores (en orden de suscripción).
     *
     * @throws IllegalStateException    si la subasta está cerrada
     * @throws IllegalArgumentException si es la primera oferta y monto &lt; precioBase,
     *                                  si no supera la oferta actual,
     *                                  o si el postor ya es el líder
     */
    public void ofertar(String postor, double monto) {
        // TODO: Validar, actualizar el estado y notificar (ojo: un observador puede ofertar dentro de la notificación)
        throw new UnsupportedOperationException("TODO");
    }

    /** Cierra la subasta y notifica el resultado. Cerrar dos veces no hace nada. */
    public void cerrar() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public String getArticulo() {
        return articulo;
    }

    public boolean estaAbierta() {
        return abierta;
    }

    /** 0 si todavía no hay ofertas. */
    public double getOfertaActual() {
        return ofertaActual;
    }

    public Optional<String> getLider() {
        return Optional.ofNullable(lider);
    }
}
