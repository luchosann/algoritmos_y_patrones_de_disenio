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
        // @sol Revisar la reserva anterior (floorEntry) y la siguiente (ceilingEntry)
        if (!fin.isAfter(inicio)) {
            throw new IllegalArgumentException("El fin debe ser posterior al inicio");
        }
        Map.Entry<LocalTime, Reserva> anterior = reservas.floorEntry(inicio);
        if (anterior != null && anterior.getValue().fin().isAfter(inicio)) {
            return false;
        }
        Map.Entry<LocalTime, Reserva> siguiente = reservas.ceilingEntry(inicio);
        if (siguiente != null && siguiente.getKey().isBefore(fin)) {
            return false;
        }
        reservas.put(inicio, new Reserva(inicio, fin, titular));
        return true;
        // @end
    }

    /** @return false si no había una reserva que empiece a esa hora */
    public boolean cancelar(LocalTime inicio) {
        // @sol
        return reservas.remove(inicio) != null;
        // @end
    }

    /** Reserva que está ocurriendo en ese instante (inicio &lt;= t &lt; fin). */
    public Optional<Reserva> enCurso(LocalTime t) {
        // @sol
        Map.Entry<LocalTime, Reserva> e = reservas.floorEntry(t);
        return e != null && e.getValue().fin().isAfter(t) ? Optional.of(e.getValue()) : Optional.empty();
        // @end
    }

    /** Primera reserva que empieza a partir de t (inclusive). */
    public Optional<Reserva> proxima(LocalTime t) {
        // @sol
        return Optional.ofNullable(reservas.ceilingEntry(t)).map(Map.Entry::getValue);
        // @end
    }

    /** Reservas que EMPIEZAN en [desde, hasta), ordenadas por inicio. */
    public List<Reserva> empiezanEntre(LocalTime desde, LocalTime hasta) {
        // @sol
        return new ArrayList<>(reservas.subMap(desde, true, hasta, false).values());
        // @end
    }

    /**
     * Primer horario &gt;= desde en el que entra una reserva de esa duración sin solaparse
     * y terminando a más tardar a la hora de cierre. Vacío si no hay lugar.
     */
    public Optional<LocalTime> primerHuecoLibre(LocalTime desde, Duration duracion, LocalTime cierre) {
        // @sol
        LocalTime candidato = desde;
        Optional<Reserva> actual = enCurso(candidato);
        if (actual.isPresent()) {
            candidato = actual.get().fin();
        }
        for (Reserva r : reservas.tailMap(candidato, true).values()) {
            if (!candidato.plus(duracion).isAfter(r.inicio())) {
                break;
            }
            candidato = r.fin();
        }
        LocalTime fin = candidato.plus(duracion);
        boolean pasaMedianoche = fin.isBefore(candidato);
        return !pasaMedianoche && !fin.isAfter(cierre) ? Optional.of(candidato) : Optional.empty();
        // @end
    }

    public int cantidad() {
        return reservas.size();
    }
}
