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
        // TODO: Promediar las lecturas en Celsius de todos los sensores
        throw new UnsupportedOperationException("TODO");
    }

    /** Id del sensor con mayor temperatura. @throws IllegalStateException si no hay sensores */
    public String idMasCaliente() {
        // TODO: Buscar el sensor con mayor temperatura
        throw new UnsupportedOperationException("TODO");
    }

    /** Ids (en orden de alta) de los sensores con temperatura estrictamente mayor al umbral. */
    public List<String> idsPorEncimaDe(double umbralCelsius) {
        // TODO: Filtrar los sensores por encima del umbral
        throw new UnsupportedOperationException("TODO");
    }
}
