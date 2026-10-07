package uy.utec.apd.m2.decorator;

/** Componente concreto. */
public class Espresso implements Bebida {

    @Override
    public String descripcion() {
        return "Espresso";
    }

    @Override
    public double costo() {
        return 120;
    }
}
