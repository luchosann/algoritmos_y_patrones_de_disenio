package uy.utec.apd.m2.decorator;

/**
 * Tamaño grande: descripción + " (grande)"; costo * 1.5 de TODO lo que envuelve.
 * Por eso el ORDEN en que se aplican los decoradores cambia el precio final.
 */
public class TamanioGrande extends DecoradorBebida {

    public TamanioGrande(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String descripcion() {
        // @sol Extender la descripción
        return super.descripcion() + " (grande)";
        // @end
    }

    @Override
    public double costo() {
        // @sol Multiplicar el costo envuelto por 1.5
        return super.costo() * 1.5;
        // @end
    }
}
