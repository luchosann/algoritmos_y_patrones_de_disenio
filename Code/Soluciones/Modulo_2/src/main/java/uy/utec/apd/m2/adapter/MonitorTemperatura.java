package uy.utec.apd.m2.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * CLIENTE: solo conoce SensorTemperatura. No sabe (ni le importa) si detrás
 * hay un sensor nativo, uno Fahrenheit o uno Kelvin.
 */
public class MonitorTemperatura {

    private final List<SensorTemperatura> sensores = new ArrayList<>();

    public void agregar(SensorTemperatura sensor) {
        sensores.add(sensor);
    }

    /** @throws IllegalStateException si no hay sensores */
    public double promedioCelsius() {
        // @sol Promediar las lecturas en Celsius de todos los sensores
        if (sensores.isEmpty()) {
            throw new IllegalStateException("No hay sensores registrados");
        }
        double suma = 0;
        for (SensorTemperatura s : sensores) {
            suma += s.getCelsius();
        }
        return suma / sensores.size();
        // @end
    }

    /** Id del sensor con mayor temperatura. @throws IllegalStateException si no hay sensores */
    public String idMasCaliente() {
        // @sol Buscar el sensor con mayor temperatura
        if (sensores.isEmpty()) {
            throw new IllegalStateException("No hay sensores registrados");
        }
        SensorTemperatura max = sensores.get(0);
        for (SensorTemperatura s : sensores) {
            if (s.getCelsius() > max.getCelsius()) {
                max = s;
            }
        }
        return max.getId();
        // @end
    }

    /** Ids (en orden de alta) de los sensores con temperatura estrictamente mayor al umbral. */
    public List<String> idsPorEncimaDe(double umbralCelsius) {
        // @sol Filtrar los sensores por encima del umbral
        List<String> ids = new ArrayList<>();
        for (SensorTemperatura s : sensores) {
            if (s.getCelsius() > umbralCelsius) {
                ids.add(s.getId());
            }
        }
        return ids;
        // @end
    }
}
