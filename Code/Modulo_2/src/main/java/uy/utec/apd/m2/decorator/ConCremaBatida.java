package uy.utec.apd.m2.decorator;

/** Agrega "crema batida": descripción + ", crema batida"; costo + 40. */
public class ConCremaBatida extends DecoradorBebida {

    public ConCremaBatida(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String descripcion() {
        // TODO: Extender la descripción de la bebida envuelta
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public double costo() {
        // TODO: Sumar el costo del agregado
        throw new UnsupportedOperationException("TODO");
    }
}
