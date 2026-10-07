package uy.utec.apd.m2.adapter;

/** Sensor propio: ya implementa la interfaz objetivo, no necesita adaptador. */
public class SensorCelsiusNativo implements SensorTemperatura {

    private final String id;
    private final double celsius;

    public SensorCelsiusNativo(String id, double celsius) {
        this.id = id;
        this.celsius = celsius;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public double getCelsius() {
        return celsius;
    }
}
