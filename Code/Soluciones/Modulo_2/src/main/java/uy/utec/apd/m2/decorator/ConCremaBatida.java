package uy.utec.apd.m2.decorator;

/** Agrega "crema batida": descripción + ", crema batida"; costo + 40. */
public class ConCremaBatida extends DecoradorBebida {

    public ConCremaBatida(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String descripcion() {
        // @sol Extender la descripción de la bebida envuelta
        return super.descripcion() + ", crema batida";
        // @end
    }

    @Override
    public double costo() {
        // @sol Sumar el costo del agregado
        return super.costo() + 40;
        // @end
    }
}
