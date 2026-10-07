package uy.utec.apd.m1.factorymethod;

public class Camion implements Transporte {

    @Override
    public String entregar(String carga) {
        // @sol Devolver "Camion entrega <carga> por carretera"
        return "Camion entrega " + carga + " por carretera";
        // @end
    }

    @Override
    public double costoPorKm() {
        // @sol Devolver 1.5
        return 1.5;
        // @end
    }
}
