package uy.utec.apd.m2.strategy;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EstrategiaEnvioTest {

    private final Paquete paquete = new Paquete(2, 100, false);   // estándar: 100 + 20 + 50 = 170

    @Test
    void estrategiasConcretas() {
        assertEquals(170, new EnvioEstandar().costo(paquete), 0.001);
        assertEquals(306, new EnvioExpress().costo(paquete), 0.001);
        assertEquals(0, new RetiroEnLocal().costo(paquete), 0.001);
    }

    @Test
    void expressTieneMinimo() {
        assertEquals(300, new EnvioExpress().costo(new Paquete(0, 0, false)), 0.001);
    }

    @Test
    void elContextoCambiaDeEstrategiaEnEjecucion() {
        CalculadoraEnvio calc = new CalculadoraEnvio();
        calc.setEstrategia(new EnvioEstandar());
        assertEquals(170, calc.calcular(paquete), 0.001);
        calc.setEstrategia(new RetiroEnLocal());
        assertEquals(0, calc.calcular(paquete), 0.001);
    }

    @Test
    void recargoPorFragil() {
        CalculadoraEnvio calc = new CalculadoraEnvio();
        calc.setEstrategia(new EnvioEstandar());
        assertEquals(170 * 1.15, calc.calcular(new Paquete(2, 100, true)), 0.001);
    }

    @Test
    void sinEstrategiaFalla() {
        assertThrows(IllegalStateException.class, () -> new CalculadoraEnvio().calcular(paquete));
    }

    @Test
    void aceptaLambdasComoEstrategia() {
        CalculadoraEnvio calc = new CalculadoraEnvio();
        calc.setEstrategia(p -> 50 + p.pesoKg());
        assertEquals(52, calc.calcular(paquete), 0.001);
    }

    @Test
    void eligeLaMasBarata() {
        EstrategiaEnvio estandar = new EnvioEstandar();
        EstrategiaEnvio express = new EnvioExpress();
        EstrategiaEnvio moto = p -> 150;
        assertSame(moto, CalculadoraEnvio.masBarata(paquete, List.of(express, estandar, moto)));
        assertThrows(IllegalArgumentException.class, () -> CalculadoraEnvio.masBarata(paquete, List.of()));
    }

    @Test
    void paqueteInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Paquete(-1, 10, false));
    }
}
