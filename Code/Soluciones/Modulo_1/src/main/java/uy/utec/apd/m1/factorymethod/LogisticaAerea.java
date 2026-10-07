package uy.utec.apd.m1.factorymethod;

public class LogisticaAerea extends Logistica {

    @Override
    protected Transporte crearTransporte() {
        // @sol Crear el transporte correspondiente
        return new Dron();
        // @end
    }
}
