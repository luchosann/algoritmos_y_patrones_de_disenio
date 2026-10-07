package uy.utec.apd.m2.decorator;

/** Agrega "caramelo": descripción + ", caramelo"; costo + 25. */
public class ConCaramelo extends DecoradorBebida {

    public ConCaramelo(Bebida bebida) {
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
