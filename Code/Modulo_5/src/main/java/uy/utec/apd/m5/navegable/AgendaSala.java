package uy.utec.apd.m5.navegable;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Ejercicio 5 — Agenda de una sala usando TreeMap (árbol rojo-negro) como NavigableMap.
 *
 * <p>Clave = hora de inicio. Usar floorEntry, ceilingEntry, higherEntry, subMap...
 * para que todas las consultas sean O(log n) (no recorrer todo el mapa salvo en primerHuecoLibre).</p>
 *
 * <p>Dos reservas se solapan si una empieza antes de que termine la otra.
 * [9:00, 10:00) y [10:00, 11:00) NO se solapan.</p>
 */
public class AgendaSala {

    private final TreeMap<LocalTime, Reserva> reservas = new TreeMap<>();

    /**
     * @return false si se solapa con otra reserva
     * @throws IllegalArgumentException si fin no es posterior a inicio
     */
    public boolean reservar(LocalTime inicio, LocalTime fin, String titular) {
        // TODO: Revisar la reserva anterior (floorEntry) y la siguiente (ceilingEntry)
        throw new UnsupportedOperationException("TODO");
    }

    /** @return false si no había una reserva que empiece a esa hora */
    public boolean cancelar(LocalTime inicio) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Reserva que está ocurriendo en ese instante (inicio &lt;= t &lt; fin). */
    public Optional<Reserva> enCurso(LocalTime t) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Primera reserva que empieza a partir de t (inclusive). */
    public Optional<Reserva> proxima(LocalTime t) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Reservas que EMPIEZAN en [desde, hasta), ordenadas por inicio. */
    public List<Reserva> empiezanEntre(LocalTime desde, LocalTime hasta) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Primer horario &gt;= desde en el que entra una reserva de esa duración sin solaparse
     * y terminando a más tardar a la hora de cierre. Vacío si no hay lugar.
     */
    public Optional<LocalTime> primerHuecoLibre(LocalTime desde, Duration duracion, LocalTime cierre) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int cantidad() {
        return reservas.size();
    }
}
