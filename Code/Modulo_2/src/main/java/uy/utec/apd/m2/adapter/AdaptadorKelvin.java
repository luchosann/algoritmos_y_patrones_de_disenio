package uy.utec.apd.m2.adapter;

/**
 * Adapter para la librería importada.
 * <p>Fórmula: C = K - 273.15. El id es el número de serie.</p>
 */
public class AdaptadorKelvin implements SensorTemperatura {

    // TODO: Guardar una referencia al objeto adaptado

    public AdaptadorKelvin(SensorKelvinImportado sensor) {
        // TODO: Guardar el adaptado
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public String getId() {
        // TODO: Delegar en el adaptado
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public double getCelsius() {
        // TODO: Convertir la lectura del adaptado a Celsius
        throw new UnsupportedOperationException("TODO");
    }
}
