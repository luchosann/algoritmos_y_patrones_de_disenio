package uy.utec.apd.m2.decorator;

/** Agrega "caramelo": descripción + ", caramelo"; costo + 25. */
public class ConCaramelo extends DecoradorBebida {

    public ConCaramelo(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String descripcion() {
        // @sol Extender la descripción de la bebida envuelta
        return super.descripcion() + ", caramelo";
        // @end
    }

    @Override
    public double costo() {
        // @sol Sumar el costo del agregado
        return super.costo() + 25;
        // @end
    }
}
