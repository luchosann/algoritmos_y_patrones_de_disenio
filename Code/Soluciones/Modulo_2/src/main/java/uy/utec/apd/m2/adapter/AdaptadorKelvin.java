package uy.utec.apd.m2.adapter;

/**
 * Adapter para la librería importada.
 * <p>Fórmula: C = K - 273.15. El id es el número de serie.</p>
 */
public class AdaptadorKelvin implements SensorTemperatura {

    // @sol-blank Guardar una referencia al objeto adaptado
    private final SensorKelvinImportado sensor;
    // @end

    public AdaptadorKelvin(SensorKelvinImportado sensor) {
        // @sol Guardar el adaptado
        this.sensor = sensor;
        // @end
    }

    @Override
    public String getId() {
        // @sol Delegar en el adaptado
        return sensor.serialNumber();
        // @end
    }

    @Override
    public double getCelsius() {
        // @sol Convertir la lectura del adaptado a Celsius
        return sensor.getKelvinValue() - 273.15;
        // @end
    }
}
