package uy.utec.apd.m1.factorymethod;

public class Barco implements Transporte {

    @Override
    public String entregar(String carga) {
        // @sol Devolver "Barco entrega <carga> por mar"
        return "Barco entrega " + carga + " por mar";
        // @end
    }

    @Override
    public double costoPorKm() {
        // @sol Devolver 0.8
        return 0.8;
        // @end
    }
}
