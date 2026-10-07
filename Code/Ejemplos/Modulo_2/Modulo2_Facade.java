/*
 * ============================================================================
 *  MÓDULO 2 — PATRÓN FACADE (estructural)
 * ============================================================================
 *  Teoría: Teorico/Modulo_2/Resumen_Modulo_2.md  (sección 4)
 *  Ejecutar:  java Modulo2_Facade.java
 *
 *  IDEA
 *  ----
 *  Ofrecer una interfaz SIMPLIFICADA y unificada a un SUBSISTEMA complejo,
 *  ocultando sus detalles. El cliente llama a un método de alto nivel y la
 *  fachada coordina a todas las piezas internas.
 *
 *          Cliente
 *             │  llegarACasa()
 *             ▼
 *        ┌─────────────┐
 *        │ HomeFacade  │
 *        └─────────────┘
 *        │      │       │
 *        ▼      ▼       ▼
 *     Luces  Clima  Seguridad   ← subsistema (sigue existiendo y se puede usar directo)
 *
 *  ⚠ La fachada NO reemplaza al subsistema: lo hace más fácil de usar.
 *
 *  ADAPTER vs FACADE vs DECORATOR
 *  - Adapter   CONVIERTE una interfaz en otra.
 *  - Facade    SIMPLIFICA una interfaz (normalmente de varios objetos).
 *  - Decorator AGREGA comportamiento sin cambiar la interfaz.
 * ============================================================================
 */
public class Modulo2_Facade {

    // ---------------- SUBSISTEMA (clases complejas) ----------------
    static class Luces {
        void encender(String ambiente) { System.out.println("    💡 Luces encendidas en " + ambiente); }
        void apagarTodas()             { System.out.println("    💡 Todas las luces apagadas"); }
        void atenuar(int porcentaje)   { System.out.println("    💡 Luces al " + porcentaje + "%"); }
    }

    static class Climatizacion {
        void encender()                { System.out.println("    ❄️  Aire acondicionado encendido"); }
        void fijarTemperatura(int g)   { System.out.println("    ❄️  Temperatura fijada en " + g + "°C"); }
        void modoAhorro()              { System.out.println("    ❄️  Climatización en modo ahorro"); }
    }

    static class Seguridad {
        void desactivarAlarma(String pin) { System.out.println("    🔒 Alarma desactivada (PIN " + "*".repeat(pin.length()) + ")"); }
        void activarAlarma()              { System.out.println("    🔒 Alarma activada"); }
        void cerrarPuertas()              { System.out.println("    🔒 Puertas cerradas con llave"); }
        void abrirPuertaPrincipal()       { System.out.println("    🔒 Puerta principal abierta"); }
    }

    // ---------------- FACADE ----------------
    static class HomeFacade {
        private final Luces luces;
        private final Climatizacion clima;
        private final Seguridad seguridad;

        HomeFacade(Luces luces, Climatizacion clima, Seguridad seguridad) {
            this.luces = luces;
            this.clima = clima;
            this.seguridad = seguridad;
        }

        /** Un solo método reemplaza 5 llamadas en el orden correcto. */
        void llegarACasa(String pin) {
            System.out.println("  🏠 llegarACasa()");
            seguridad.desactivarAlarma(pin);
            seguridad.abrirPuertaPrincipal();
            luces.encender("living");
            clima.encender();
            clima.fijarTemperatura(22);
        }

        void salirDeCasa() {
            System.out.println("  🚪 salirDeCasa()");
            luces.apagarTodas();
            clima.modoAhorro();
            seguridad.cerrarPuertas();
            seguridad.activarAlarma();
        }

        void modoPelicula() {
            System.out.println("  🎬 modoPelicula()");
            luces.atenuar(10);
            clima.fijarTemperatura(21);
        }
    }

    /*
     * Ejemplo del PDF con JavaMail (requiere la dependencia javax.mail):
     *
     *   public class EmailFacade {
     *       public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
     *           Properties props = ...;            // host SMTP, puerto, TLS
     *           Session session = Session.getInstance(props, new Authenticator() {...});
     *           Message msg = new MimeMessage(session);
     *           msg.setRecipients(...); msg.setSubject(asunto); msg.setText(cuerpo);
     *           Transport.send(msg);
     *       }
     *   }
     *
     * El cliente sólo llama: new EmailFacade().enviarCorreo("a@b.com", "Hola", "...");
     */

    // ========================================================================
    public static void main(String[] args) {
        HomeFacade casa = new HomeFacade(new Luces(), new Climatizacion(), new Seguridad());

        casa.llegarACasa("1234");
        System.out.println();
        casa.modoPelicula();
        System.out.println();
        casa.salirDeCasa();

        System.out.println("\n  Sin fachada, el cliente tendría que conocer las 3 clases,");
        System.out.println("  sus métodos y el ORDEN correcto de las llamadas.");
    }
}
