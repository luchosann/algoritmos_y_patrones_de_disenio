package uy.utec.apd.m2.decorator;

/** Agrega "leche": descripción + ", leche"; costo + 30. */
public class ConLeche extends DecoradorBebida {

    public ConLeche(Bebida bebida) {
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
