package uy.utec.apd.m2.decorator;

/** Componente concreto. */
public class Te implements Bebida {

    @Override
    public String descripcion() {
        return "Te";
    }

    @Override
    public double costo() {
        return 90;
    }
}
