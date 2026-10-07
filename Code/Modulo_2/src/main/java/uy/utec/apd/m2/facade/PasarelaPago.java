package uy.utec.apd.m2.facade;

import java.util.ArrayList;
import java.util.List;

/** Subsistema (ya implementado, no modificar). Simula un procesador de tarjetas. */
public class PasarelaPago {

    private final List<Double> cobros = new ArrayList<>();

    /** Aprueba si la tarjeta tiene exactamente 16 dígitos y el monto es positivo. */
    public boolean cobrar(String tarjeta, double monto) {
        boolean aprobado = tarjeta != null && tarjeta.matches("\\d{16}") && monto > 0;
        if (aprobado) {
            cobros.add(monto);
        }
        return aprobado;
    }

    public int cantidadCobros() {
        return cobros.size();
    }

    public double totalCobrado() {
        return cobros.stream().mapToDouble(Double::doubleValue).sum();
    }
}
