package uy.utec.apd.m2.strategy;

/** Datos del envío. Peso y distancia no pueden ser negativos. */
public record Paquete(double pesoKg, double distanciaKm, boolean fragil) {

    public Paquete {
        if (pesoKg < 0 || distanciaKm < 0) {
            throw new IllegalArgumentException("Peso y distancia deben ser >= 0");
        }
    }
}
