/*
 * ============================================================================
 *  MÓDULO 2 — PATRÓN STRATEGY (comportamiento)
 * ============================================================================
 *  Teoría: Teorico/Modulo_2/Resumen_Modulo_2.md  (sección 8)
 *  Ejecutar:  java Modulo2_Strategy.java
 *
 *  IDEA
 *  ----
 *  Definir una FAMILIA DE ALGORITMOS, encapsular cada uno en su clase y
 *  hacerlos INTERCAMBIABLES en tiempo de ejecución. El CONTEXTO no sabe cómo
 *  se hace el trabajo: delega en la estrategia que tenga asignada.
 *
 *  COMPONENTES
 *  -----------
 *     Strategy           → MetodoPago { procesarPago(monto); }
 *     Concrete Strategies→ PagoTarjetaCredito, PagoPayPal, PagoTransferenciaBancaria
 *     Context            → ProcesadorPago { setMetodoPago(...); procesar(monto); }
 *
 *        ProcesadorPago ──tiene──▶ «MetodoPago»
 *                                  ▲    ▲    ▲
 *                           Tarjeta  PayPal  Transferencia
 *
 *  SIN el patrón:  if (tipo == "tarjeta") {...} else if (tipo == "paypal") {...} ...
 *  CON el patrón:  cada `if` es una clase; agregar un método de pago no
 *                  modifica ProcesadorPago (principio Abierto/Cerrado).
 *
 *  Strategy vs State: estructura casi igual; en Strategy el CLIENTE elige la
 *  estrategia, en State el objeto cambia solo de estado internamente.
 * ============================================================================
 */
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Modulo2_Strategy {

    // ---------------- STRATEGY ----------------
    interface MetodoPago {
        void procesarPago(double monto);
    }

    // ---------------- CONCRETE STRATEGIES ----------------
    static class PagoTarjetaCredito implements MetodoPago {
        private final String numero;
        PagoTarjetaCredito(String numero) { this.numero = numero; }

        @Override
        public void procesarPago(double monto) {
            double recargo = monto * 0.05;
            System.out.printf("  💳 Tarjeta ****%s: $%.2f (+ $%.2f de recargo)%n",
                    numero.substring(numero.length() - 4), monto, recargo);
        }
    }

    static class PagoPayPal implements MetodoPago {
        private final String email;
        PagoPayPal(String email) { this.email = email; }

        @Override
        public void procesarPago(double monto) {
            System.out.printf("  🅿️  PayPal (%s): $%.2f%n", email, monto);
        }
    }

    static class PagoTransferenciaBancaria implements MetodoPago {
        @Override
        public void procesarPago(double monto) {
            System.out.printf("  🏦 Transferencia bancaria: $%.2f (10%% de descuento → $%.2f)%n", monto, monto * 0.9);
        }
    }

    // ---------------- CONTEXT ----------------
    static class ProcesadorPago {
        private MetodoPago metodoPago;

        void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }

        void procesar(double monto) {
            if (metodoPago == null) throw new IllegalStateException("No se eligió un método de pago");
            metodoPago.procesarPago(monto);     // ← delega, sin ifs
        }
    }

    // ========================================================================
    public static void main(String[] args) {
        ProcesadorPago p = new ProcesadorPago();

        System.out.println("=== Cambiando la estrategia en tiempo de ejecución ===");
        p.setMetodoPago(new PagoTarjetaCredito("4111111111111234"));
        p.procesar(150.0);

        p.setMetodoPago(new PagoPayPal("ana@mail.com"));
        p.procesar(150.0);

        p.setMetodoPago(new PagoTransferenciaBancaria());
        p.procesar(150.0);

        System.out.println("\n=== Estrategia como lambda (interfaz funcional) ===");
        p.setMetodoPago(monto -> System.out.printf("  🪙 Cripto: %.6f BTC%n", monto / 3_000_000));
        p.procesar(150.0);

        System.out.println("\n=== Strategy en la API de Java: Comparator ===");
        // sort() es el contexto; el Comparator es la estrategia de comparación.
        List<String> nombres = new ArrayList<>(List.of("Valentina", "Ana", "Bruno", "Lu"));
        nombres.sort(Comparator.naturalOrder());
        System.out.println("  Alfabético:  " + nombres);
        nombres.sort(Comparator.comparingInt(String::length));
        System.out.println("  Por largo:   " + nombres);
        nombres.sort(Comparator.reverseOrder());
        System.out.println("  Inverso:     " + nombres);
    }
}
