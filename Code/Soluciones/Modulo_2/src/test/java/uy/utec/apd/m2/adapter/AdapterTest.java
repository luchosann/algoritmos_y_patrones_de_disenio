package uy.utec.apd.m2.adapter;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdapterTest {

    @Test
    void adaptaFahrenheitACelsius() {
        SensorTemperatura s = new AdaptadorFahrenheit(new TermometroFahrenheitLegacy("F-1", 212));
        assertEquals("F-1", s.getId());
        assertEquals(100.0, s.getCelsius(), 0.001);
        assertEquals(0.0, new AdaptadorFahrenheit(new TermometroFahrenheitLegacy("F-2", 32)).getCelsius(), 0.001);
    }

    @Test
    void elAdaptadorLeeElValorActualDelAdaptado() {
        TermometroFahrenheitLegacy legacy = new TermometroFahrenheitLegacy("F-1", 32);
        SensorTemperatura s = new AdaptadorFahrenheit(legacy);
        legacy.setFahrenheit(50);
        assertEquals(10.0, s.getCelsius(), 0.001, "No guardes una copia del valor: delegá en el adaptado");
    }

    @Test
    void adaptaKelvinACelsius() {
        SensorTemperatura s = new AdaptadorKelvin(new SensorKelvinImportado("SN-9", 300.15f));
        assertEquals("SN-9", s.getId());
        assertEquals(27.0, s.getCelsius(), 0.01);
    }

    @Test
    void elMonitorTrabajaConCualquierSensor() {
        MonitorTemperatura monitor = new MonitorTemperatura();
        monitor.agregar(new SensorCelsiusNativo("C-1", 20));
        monitor.agregar(new AdaptadorFahrenheit(new TermometroFahrenheitLegacy("F-1", 104))); // 40 C
        monitor.agregar(new AdaptadorKelvin(new SensorKelvinImportado("K-1", 273.15f)));      // 0 C

        assertEquals(20.0, monitor.promedioCelsius(), 0.01);
        assertEquals("F-1", monitor.idMasCaliente());
        assertEquals(List.of("C-1", "F-1"), monitor.idsPorEncimaDe(10));
        assertEquals(List.of(), monitor.idsPorEncimaDe(40));
    }

    @Test
    void monitorVacio() {
        MonitorTemperatura monitor = new MonitorTemperatura();
        assertThrows(IllegalStateException.class, monitor::promedioCelsius);
        assertThrows(IllegalStateException.class, monitor::idMasCaliente);
    }
}
