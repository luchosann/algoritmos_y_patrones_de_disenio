package uy.utec.apd.m1.factorymethod;

public class LogisticaMaritima extends Logistica {

    @Override
    protected Transporte crearTransporte() {
        // @sol Crear el transporte correspondiente
        return new Barco();
        // @end
    }
}
