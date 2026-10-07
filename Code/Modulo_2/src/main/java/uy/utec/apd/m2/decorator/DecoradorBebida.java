package uy.utec.apd.m2.decorator;

/**
 * Decorador base: ES una Bebida y TIENE una Bebida.
 * Por defecto delega todo en la bebida envuelta.
 */
public abstract class DecoradorBebida implements Bebida {

    protected final Bebida bebida;

    protected DecoradorBebida(Bebida bebida) {
        this.bebida = bebida;
    }

    @Override
    public String descripcion() {
        // TODO: Delegar en la bebida envuelta
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public double costo() {
        // TODO: Delegar en la bebida envuelta
        throw new UnsupportedOperationException("TODO");
    }
}
