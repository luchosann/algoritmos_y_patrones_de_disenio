package uy.utec.apd.m2.strategy;

/** 1.8 veces el costo estándar, con un mínimo de 300. (Reutilizá EnvioEstandar.) */
public class EnvioExpress implements EstrategiaEnvio {

    // @sol-blank
    private final EnvioEstandar estandar = new EnvioEstandar();
    // @end

    @Override
    public double costo(Paquete paquete) {
        // @sol
        return Math.max(300, 1.8 * estandar.costo(paquete));
        // @end
    }
}
