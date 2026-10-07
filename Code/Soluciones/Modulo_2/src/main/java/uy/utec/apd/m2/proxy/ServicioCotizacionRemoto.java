package uy.utec.apd.m2.proxy;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Objeto REAL (ya implementado, no modificar). Simula una API externa lenta y
 * con costo por llamada, por eso cuenta cuántas veces se la invocó.
 */
public class ServicioCotizacionRemoto implements ServicioCotizacion {

    private final Map<String, Double> cotizaciones = new HashMap<>(Map.of(
            "USD", 40.0,
            "EUR", 43.5,
            "BRL", 7.8));
    private int llamadas;

    @Override
    public double cotizar(String moneda) {
        llamadas++;
        Double valor = cotizaciones.get(moneda.toUpperCase(Locale.ROOT));
        if (valor == null) {
            throw new IllegalArgumentException("Moneda desconocida: " + moneda);
        }
        return valor;
    }

    /** Simula que la cotización cambió en el servidor. */
    public void actualizar(String moneda, double valor) {
        cotizaciones.put(moneda.toUpperCase(Locale.ROOT), valor);
    }

    public int getLlamadas() {
        return llamadas;
    }
}
