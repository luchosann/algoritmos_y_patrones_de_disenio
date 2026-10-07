package uy.utec.apd.m1.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejercicio 4 — Builder.
 *
 * Pizza INMUTABLE que solo se puede crear mediante {@link Builder}.
 * <ul>
 *   <li>El tamaño es obligatorio (se pasa a {@link #builder(Tamanio)}).</li>
 *   <li>Masa por defecto: "tradicional".</li>
 *   <li>Máximo {@value #MAX_INGREDIENTES} ingredientes.</li>
 * </ul>
 */
public final class Pizza {

    public enum Tamanio {
        CHICA(300), MEDIANA(400), GRANDE(500);

        private final double precioBase;

        Tamanio(double precioBase) {
            this.precioBase = precioBase;
        }

        public double getPrecioBase() {
            return precioBase;
        }
    }

    public static final int MAX_INGREDIENTES = 6;
    public static final double PRECIO_INGREDIENTE = 50;
    public static final double PRECIO_EXTRA_QUESO = 80;

    private final Tamanio tamanio;
    private final String masa;
    private final List<String> ingredientes;
    private final boolean extraQueso;

    private Pizza(Builder builder) {
        // TODO: Copiar los valores del builder (¡la lista debe quedar inmutable e independiente del builder!)
        throw new UnsupportedOperationException("TODO");
    }

    public static Builder builder(Tamanio tamanio) {
        return new Builder(tamanio);
    }

    public Tamanio getTamanio() {
        return tamanio;
    }

    public String getMasa() {
        return masa;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public boolean tieneExtraQueso() {
        return extraQueso;
    }

    /** Precio = base del tamaño + 50 por ingrediente + 80 si tiene extra queso. */
    public double precio() {
        // TODO: Calcular el precio según la regla del javadoc
        throw new UnsupportedOperationException("TODO");
    }

    public static final class Builder {

        private final Tamanio tamanio;
        private String masa = "tradicional";
        private final List<String> ingredientes = new ArrayList<>();
        private boolean extraQueso;

        /** @throws IllegalArgumentException si tamanio es null */
        private Builder(Tamanio tamanio) {
            // TODO: Validar y guardar el tamaño
            throw new UnsupportedOperationException("TODO");
        }

        /** @throws IllegalArgumentException si la masa es null o está en blanco */
        public Builder masa(String masa) {
            // TODO: Validar, guardar y devolver this
            throw new UnsupportedOperationException("TODO");
        }

        /** @throws IllegalArgumentException si el ingrediente es null o está en blanco */
        public Builder ingrediente(String ingrediente) {
            // TODO: Validar, agregar y devolver this
            throw new UnsupportedOperationException("TODO");
        }

        public Builder extraQueso() {
            // TODO: Activar extra queso y devolver this
            throw new UnsupportedOperationException("TODO");
        }

        /** @throws IllegalStateException si hay más de MAX_INGREDIENTES ingredientes */
        public Pizza build() {
            // TODO: Validar la cantidad de ingredientes y construir la Pizza
            throw new UnsupportedOperationException("TODO");
        }
    }
}
