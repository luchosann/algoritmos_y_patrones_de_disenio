package uy.utec.apd.m2.strategy;

/** 100 + 10 por kg + 0.5 por km. */
public class EnvioEstandar implements EstrategiaEnvio {

    @Override
    public double costo(Paquete paquete) {
        // @sol
        return 100 + 10 * paquete.pesoKg() + 0.5 * paquete.distanciaKm();
        // @end
    }
}
