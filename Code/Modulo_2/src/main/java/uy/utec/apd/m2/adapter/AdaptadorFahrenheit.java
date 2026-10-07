package uy.utec.apd.m2.adapter;

/**
 * Adapter de OBJETO (composición): hace que un TermometroFahrenheitLegacy
 * pueda usarse como SensorTemperatura.
 * <p>Fórmula: C = (F - 32) * 5 / 9. El id es el código del termómetro.</p>
 */
public class AdaptadorFahrenheit implements SensorTemperatura {

    // TODO: Guardar una referencia al objeto adaptado

    public AdaptadorFahrenheit(TermometroFahrenheitLegacy termometro) {
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
