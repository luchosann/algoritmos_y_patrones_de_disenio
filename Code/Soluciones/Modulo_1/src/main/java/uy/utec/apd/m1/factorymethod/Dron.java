package uy.utec.apd.m1.factorymethod;

public class Dron implements Transporte {

    @Override
    public String entregar(String carga) {
        // @sol Devolver "Dron entrega <carga> por aire"
        return "Dron entrega " + carga + " por aire";
        // @end
    }

    @Override
    public double costoPorKm() {
        // @sol Devolver 3.0
        return 3.0;
        // @end
    }
}
