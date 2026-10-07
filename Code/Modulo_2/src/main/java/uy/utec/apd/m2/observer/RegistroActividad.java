package uy.utec.apd.m2.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observador concreto que lleva un historial de texto:
 * <ul>
 *   <li>"Ana oferto 150.0 por Guitarra"</li>
 *   <li>"Guitarra cerrada: gana Ana con 150.0"</li>
 *   <li>"Guitarra cerrada sin ofertas"</li>
 * </ul>
 */
public class RegistroActividad implements ObservadorSubasta {

    private final List<String> eventos = new ArrayList<>();

    @Override
    public void nuevaOferta(Subasta subasta, String postor, double monto) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void subastaCerrada(Subasta subasta, String ganador, double monto) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public List<String> getEventos() {
        return List.copyOf(eventos);
    }
}
