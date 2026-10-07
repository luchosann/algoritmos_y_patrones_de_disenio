package uy.utec.apd.m5.navegable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AgendaSalaTest {

    private static LocalTime h(String hora) {
        return LocalTime.parse(hora);
    }

    private AgendaSala agenda;

    @BeforeEach
    void setUp() {
        agenda = new AgendaSala();
        assertTrue(agenda.reservar(h("09:00"), h("10:00"), "Ana"));
        assertTrue(agenda.reservar(h("11:00"), h("12:30"), "Beto"));
        assertTrue(agenda.reservar(h("14:00"), h("15:00"), "Caro"));
    }

    @Test
    void rechazaSolapamientos() {
        assertFalse(agenda.reservar(h("09:30"), h("10:30"), "X"), "Empieza durante la de Ana");
        assertFalse(agenda.reservar(h("08:00"), h("09:01"), "X"), "Termina durante la de Ana");
        assertFalse(agenda.reservar(h("10:30"), h("13:00"), "X"), "Contiene a la de Beto");
        assertFalse(agenda.reservar(h("11:00"), h("11:15"), "X"), "Mismo inicio");
        assertEquals(3, agenda.cantidad());
    }

    @Test
    void reservasPegadasNoSeSolapan() {
        assertTrue(agenda.reservar(h("10:00"), h("11:00"), "Dani"));
        assertTrue(agenda.reservar(h("08:00"), h("09:00"), "Eli"));
        assertEquals(5, agenda.cantidad());
    }

    @Test
    void finDebeSerPosteriorAlInicio() {
        assertThrows(IllegalArgumentException.class, () -> agenda.reservar(h("16:00"), h("16:00"), "X"));
    }

    @Test
    void enCursoYProxima() {
        assertEquals("Ana", agenda.enCurso(h("09:59")).orElseThrow().titular());
        assertTrue(agenda.enCurso(h("10:00")).isEmpty(), "El fin no está incluido");
        assertTrue(agenda.enCurso(h("08:00")).isEmpty());
        assertEquals("Beto", agenda.proxima(h("10:00")).orElseThrow().titular());
        assertEquals("Beto", agenda.proxima(h("11:00")).orElseThrow().titular());
        assertTrue(agenda.proxima(h("15:00")).isEmpty());
    }

    @Test
    void empiezanEntre() {
        List<String> titulares = agenda.empiezanEntre(h("09:00"), h("14:00")).stream().map(Reserva::titular).toList();
        assertEquals(List.of("Ana", "Beto"), titulares, "[desde, hasta): la de las 14:00 no entra");
    }

    @Test
    void cancelar() {
        assertTrue(agenda.cancelar(h("11:00")));
        assertFalse(agenda.cancelar(h("11:00")));
        assertTrue(agenda.reservar(h("10:30"), h("13:00"), "Dani"));
    }

    @Test
    void primerHuecoLibre() {
        Duration hora = Duration.ofHours(1);
        LocalTime cierre = h("18:00");
        assertEquals(Optional.of(h("10:00")), agenda.primerHuecoLibre(h("09:00"), hora, cierre));
        assertEquals(Optional.of(h("12:30")), agenda.primerHuecoLibre(h("09:00"), Duration.ofMinutes(90), cierre));
        assertEquals(Optional.of(h("15:00")), agenda.primerHuecoLibre(h("14:30"), hora, cierre));
        assertEquals(Optional.of(h("08:00")), agenda.primerHuecoLibre(h("08:00"), hora, cierre));
        assertEquals(Optional.empty(), agenda.primerHuecoLibre(h("17:30"), hora, cierre));
        assertEquals(Optional.of(h("17:00")), agenda.primerHuecoLibre(h("17:00"), hora, cierre), "Puede terminar justo al cierre");
    }
}
