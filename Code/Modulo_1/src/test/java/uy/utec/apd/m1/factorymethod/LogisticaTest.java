package uy.utec.apd.m1.factorymethod;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogisticaTest {

    @Test
    void cadaLogisticaCreaSuTransporte() {
        assertInstanceOf(Camion.class, new LogisticaTerrestre().crearTransporte());
        assertInstanceOf(Barco.class, new LogisticaMaritima().crearTransporte());
        assertInstanceOf(Dron.class, new LogisticaAerea().crearTransporte());
    }

    @Test
    void planificarEntregaUsaElTransporteCorrecto() {
        assertEquals("Camion entrega 10 cajas por carretera",
                new LogisticaTerrestre().planificarEntrega("10 cajas"));
        assertEquals("Barco entrega 1 contenedor por mar",
                new LogisticaMaritima().planificarEntrega("1 contenedor"));
        assertEquals("Dron entrega un paquete por aire",
                new LogisticaAerea().planificarEntrega("un paquete"));
    }

    @Test
    void calculaElCostoSegunElTransporte() {
        assertEquals(150.0, new LogisticaTerrestre().calcularCosto(100), 0.001);
        assertEquals(80.0, new LogisticaMaritima().calcularCosto(100), 0.001);
        assertEquals(300.0, new LogisticaAerea().calcularCosto(100), 0.001);
    }

    @Test
    void distanciaNegativaEsInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new LogisticaTerrestre().calcularCosto(-1));
    }

    @Test
    void desdeEligeLaSubclaseCorrecta() {
        assertInstanceOf(LogisticaTerrestre.class, Logistica.desde("terrestre"));
        assertInstanceOf(LogisticaMaritima.class, Logistica.desde("MARITIMA"));
        assertInstanceOf(LogisticaAerea.class, Logistica.desde("  Aerea "));
    }

    @Test
    void desdeRechazaTiposInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> Logistica.desde("ferroviaria"));
        assertThrows(IllegalArgumentException.class, () -> Logistica.desde(null));
    }
}
