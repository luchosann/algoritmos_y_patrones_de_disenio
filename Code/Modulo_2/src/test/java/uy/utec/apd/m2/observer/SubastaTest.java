package uy.utec.apd.m2.observer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubastaTest {

    /** Observador de prueba que solo cuenta notificaciones. */
    static class Contador implements ObservadorSubasta {
        int ofertas;
        int cierres;

        @Override
        public void nuevaOferta(Subasta s, String postor, double monto) {
            ofertas++;
        }

        @Override
        public void subastaCerrada(Subasta s, String ganador, double monto) {
            cierres++;
        }
    }

    @Test
    void notificaATodosLosSuscriptos() {
        Subasta s = new Subasta("Guitarra", 100);
        Contador a = new Contador();
        Contador b = new Contador();
        s.suscribir(a);
        s.suscribir(b);
        s.suscribir(a);
        assertEquals(2, s.cantidadObservadores(), "No debe haber duplicados");

        s.ofertar("Ana", 100);
        s.ofertar("Beto", 120);
        assertEquals(2, a.ofertas);
        assertEquals(2, b.ofertas);
        assertEquals("Beto", s.getLider().orElseThrow());
        assertEquals(120, s.getOfertaActual(), 0.001);
    }

    @Test
    void unDesuscriptoNoRecibeMasNotificaciones() {
        Subasta s = new Subasta("Guitarra", 100);
        Contador a = new Contador();
        s.suscribir(a);
        s.ofertar("Ana", 100);
        s.desuscribir(a);
        s.ofertar("Beto", 150);
        assertEquals(1, a.ofertas);
    }

    @Test
    void validaLasOfertas() {
        Subasta s = new Subasta("Guitarra", 100);
        assertThrows(IllegalArgumentException.class, () -> s.ofertar("Ana", 99));
        s.ofertar("Ana", 100);
        assertThrows(IllegalArgumentException.class, () -> s.ofertar("Beto", 100));
        assertThrows(IllegalArgumentException.class, () -> s.ofertar("Ana", 200), "Ana ya es líder");
    }

    @Test
    void registroDeActividadYCierre() {
        Subasta s = new Subasta("Guitarra", 100);
        RegistroActividad registro = new RegistroActividad();
        Contador contador = new Contador();
        s.suscribir(registro);
        s.suscribir(contador);

        s.ofertar("Ana", 100);
        s.ofertar("Beto", 150);
        s.cerrar();
        s.cerrar();

        assertEquals(List.of(
                "Ana oferto 100.0 por Guitarra",
                "Beto oferto 150.0 por Guitarra",
                "Guitarra cerrada: gana Beto con 150.0"), registro.getEventos());
        assertEquals(1, contador.cierres, "Cerrar dos veces notifica una sola vez");
        assertFalse(s.estaAbierta());
        assertThrows(IllegalStateException.class, () -> s.ofertar("Caro", 500));
    }

    @Test
    void cierreSinOfertas() {
        Subasta s = new Subasta("Cuadro", 1000);
        RegistroActividad registro = new RegistroActividad();
        s.suscribir(registro);
        s.cerrar();
        assertEquals(List.of("Cuadro cerrada sin ofertas"), registro.getEventos());
        assertTrue(s.getLider().isEmpty());
    }

    @Test
    void elPostorAutomaticoContraoferta() {
        Subasta s = new Subasta("Bici", 100);
        RegistroActividad registro = new RegistroActividad();
        s.suscribir(registro);
        s.suscribir(new PostorAutomatico("Robot", 150, 10));

        s.ofertar("Ana", 100);   // Robot -> 110
        s.ofertar("Ana", 140);   // Robot -> 150
        s.ofertar("Ana", 160);   // Robot no puede (170 > 150)
        s.cerrar();

        assertEquals("Ana", s.getLider().orElseThrow());
        assertEquals(List.of(
                "Ana oferto 100.0 por Bici",
                "Robot oferto 110.0 por Bici",
                "Ana oferto 140.0 por Bici",
                "Robot oferto 150.0 por Bici",
                "Ana oferto 160.0 por Bici",
                "Bici cerrada: gana Ana con 160.0"), registro.getEventos());
    }

    @Test
    void unObservadorPuedeDesuscribirseDuranteLaNotificacion() {
        Subasta s = new Subasta("Libro", 10);
        List<String> recibidos = new ArrayList<>();
        ObservadorSubasta unaSolaVez = new ObservadorSubasta() {
            @Override
            public void nuevaOferta(Subasta subasta, String postor, double monto) {
                recibidos.add(postor);
                subasta.desuscribir(this);
            }
        };
        Contador contador = new Contador();
        s.suscribir(unaSolaVez);
        s.suscribir(contador);
        assertDoesNotThrow(() -> s.ofertar("Ana", 10));
        s.ofertar("Beto", 20);
        assertEquals(List.of("Ana"), recibidos);
        assertEquals(2, contador.ofertas,
                "Si se itera la lista original mientras se modifica, se saltea observadores: iterar una copia");
    }
}
