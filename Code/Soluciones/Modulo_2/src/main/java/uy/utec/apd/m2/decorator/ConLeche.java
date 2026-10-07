package uy.utec.apd.m2.decorator;

/** Agrega "leche": descripción + ", leche"; costo + 30. */
public class ConLeche extends DecoradorBebida {

    public ConLeche(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String descripcion() {
        // @sol Extender la descripción de la bebida envuelta
        return super.descripcion() + ", leche";
        // @end
    }

    @Override
    public double costo() {
        // @sol Sumar el costo del agregado
        return super.costo() + 30;
        // @end
    }
}
