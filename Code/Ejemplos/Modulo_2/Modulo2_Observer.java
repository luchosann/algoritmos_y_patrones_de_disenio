/*
 * ============================================================================
 *  MÓDULO 2 — PATRÓN OBSERVER (comportamiento)
 * ============================================================================
 *  Teoría: Teorico/Modulo_2/Resumen_Modulo_2.md  (sección 7)
 *  Ejecutar:  java Modulo2_Observer.java
 *
 *  IDEA
 *  ----
 *  Relación UNO-A-MUCHOS: cuando el SUJETO cambia de estado, NOTIFICA
 *  automáticamente a todos los OBSERVADORES suscritos. Es la base de los
 *  eventos en interfaces gráficas y del modelo publicación/suscripción.
 *
 *  COMPONENTES
 *  -----------
 *     Subject           → Sujeto { agregarObservador, eliminarObservador, notificarObservadores }
 *     Observer          → Observador { actualizar(estado) }
 *     Concrete Subject  → ServicioMeteorologico (cambia el clima y avisa)
 *     Concrete Observer → DispositivoMovil, PantallaAeropuerto, AlertaTormenta
 *
 *                        setEstadoClima("Lluvia")
 *                                 │
 *                    ServicioMeteorologico
 *                 notificarObservadores()
 *          ┌──────────────┼───────────────┐
 *          ▼              ▼               ▼
 *   DispositivoMovil  Pantalla      AlertaTormenta
 *     actualizar()    actualizar()   actualizar()
 *
 *  El sujeto sólo conoce la INTERFAZ Observador → bajo acoplamiento.
 *  Se pueden suscribir y desuscribir observadores en tiempo de ejecución.
 * ============================================================================
 */
import java.util.ArrayList;
import java.util.List;

public class Modulo2_Observer {

    // ---------------- OBSERVER ----------------
    interface Observador {
        void actualizar(String estadoClima, double temperatura);
    }

    // ---------------- SUBJECT ----------------
    interface Sujeto {
        void agregarObservador(Observador o);
        void eliminarObservador(Observador o);
        void notificarObservadores();
    }

    // ---------------- CONCRETE SUBJECT ----------------
    static class ServicioMeteorologico implements Sujeto {
        private final List<Observador> observadores = new ArrayList<>();
        private String estadoClima = "Despejado";
        private double temperatura = 20;

        @Override public void agregarObservador(Observador o)  { observadores.add(o); }
        @Override public void eliminarObservador(Observador o) { observadores.remove(o); }

        @Override
        public void notificarObservadores() {
            for (Observador o : observadores) {
                o.actualizar(estadoClima, temperatura);   // "push": se envía el estado
            }
        }

        /** Al cambiar el estado, notifica AUTOMÁTICAMENTE. */
        void setMedicion(String estadoClima, double temperatura) {
            System.out.println("\n☁️  Nueva medición: " + estadoClima + ", " + temperatura + "°C");
            this.estadoClima = estadoClima;
            this.temperatura = temperatura;
            notificarObservadores();
        }
    }

    // ---------------- CONCRETE OBSERVERS ----------------
    static class DispositivoMovil implements Observador {
        private final String duenio;

        DispositivoMovil(String duenio) { this.duenio = duenio; }

        @Override
        public void actualizar(String estadoClima, double temperatura) {
            System.out.println("  📱 Celular de " + duenio + ": ahora " + estadoClima + " (" + temperatura + "°C)");
        }
    }

    static class PantallaAeropuerto implements Observador {
        @Override
        public void actualizar(String estadoClima, double temperatura) {
            System.out.println("  🛫 Pantalla aeropuerto: CLIMA " + estadoClima.toUpperCase());
        }
    }

    /** Un observador puede reaccionar sólo a lo que le interesa. */
    static class AlertaTormenta implements Observador {
        @Override
        public void actualizar(String estadoClima, double temperatura) {
            if (estadoClima.equalsIgnoreCase("Tormenta")) {
                System.out.println("  🚨 ALERTA: tormenta en camino. ¡Asegure objetos sueltos!");
            }
        }
    }

    // ========================================================================
    public static void main(String[] args) {
        ServicioMeteorologico servicio = new ServicioMeteorologico();

        DispositivoMovil celularAna = new DispositivoMovil("Ana");
        DispositivoMovil celularLuis = new DispositivoMovil("Luis");
        servicio.agregarObservador(celularAna);
        servicio.agregarObservador(celularLuis);
        servicio.agregarObservador(new PantallaAeropuerto());
        servicio.agregarObservador(new AlertaTormenta());

        servicio.setMedicion("Lluvia", 15.5);

        System.out.println("\n(Luis se desuscribe)");
        servicio.eliminarObservador(celularLuis);

        servicio.setMedicion("Tormenta", 12.0);

        // También se puede suscribir con una lambda: Observador es una interfaz
        // con un solo método (interfaz funcional).
        servicio.agregarObservador((estado, temp) ->
                System.out.println("  λ Observador lambda registró: " + estado));
        servicio.setMedicion("Despejado", 24.0);
    }
}
