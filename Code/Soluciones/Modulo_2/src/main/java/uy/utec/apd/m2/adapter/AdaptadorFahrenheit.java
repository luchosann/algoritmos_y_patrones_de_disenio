package uy.utec.apd.m2.adapter;

/**
 * Adapter de OBJETO (composición): hace que un TermometroFahrenheitLegacy
 * pueda usarse como SensorTemperatura.
 * <p>Fórmula: C = (F - 32) * 5 / 9. El id es el código del termómetro.</p>
 */
public class AdaptadorFahrenheit implements SensorTemperatura {

    // @sol-blank Guardar una referencia al objeto adaptado
    private final TermometroFahrenheitLegacy termometro;
    // @end

    public AdaptadorFahrenheit(TermometroFahrenheitLegacy termometro) {
        // @sol Guardar el adaptado
        this.termometro = termometro;
        // @end
    }

    @Override
    public String getId() {
        // @sol Delegar en el adaptado
        return termometro.getCodigo();
        // @end
    }

    @Override
    public double getCelsius() {
        // @sol Convertir la lectura del adaptado a Celsius
        return (termometro.leerFahrenheit() - 32) * 5 / 9;
        // @end
    }
}
