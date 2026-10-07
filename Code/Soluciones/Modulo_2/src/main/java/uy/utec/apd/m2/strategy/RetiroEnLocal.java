package uy.utec.apd.m2.strategy;

/** Sin costo. */
public class RetiroEnLocal implements EstrategiaEnvio {

    @Override
    public double costo(Paquete paquete) {
        // @sol
        return 0;
        // @end
    }
}
