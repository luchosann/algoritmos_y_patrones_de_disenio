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
        // @sol Copiar los valores del builder (¡la lista debe quedar inmutable e independiente del builder!)
        this.tamanio = builder.tamanio;
        this.masa = builder.masa;
        this.ingredientes = List.copyOf(builder.ingredientes);
        this.extraQueso = builder.extraQueso;
        // @end
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
        // @sol Calcular el precio según la regla del javadoc
        double total = tamanio.getPrecioBase() + ingredientes.size() * PRECIO_INGREDIENTE;
        if (extraQueso) {
            total += PRECIO_EXTRA_QUESO;
        }
        return total;
        // @end
    }

    public static final class Builder {

        private final Tamanio tamanio;
        private String masa = "tradicional";
        private final List<String> ingredientes = new ArrayList<>();
        private boolean extraQueso;

        /** @throws IllegalArgumentException si tamanio es null */
        private Builder(Tamanio tamanio) {
            // @sol Validar y guardar el tamaño
            if (tamanio == null) {
                throw new IllegalArgumentException("El tamaño es obligatorio");
            }
            this.tamanio = tamanio;
            // @end
        }

        /** @throws IllegalArgumentException si la masa es null o está en blanco */
        public Builder masa(String masa) {
            // @sol Validar, guardar y devolver this
            if (masa == null || masa.isBlank()) {
                throw new IllegalArgumentException("La masa no puede estar vacía");
            }
            this.masa = masa;
            return this;
            // @end
        }

        /** @throws IllegalArgumentException si el ingrediente es null o está en blanco */
        public Builder ingrediente(String ingrediente) {
            // @sol Validar, agregar y devolver this
            if (ingrediente == null || ingrediente.isBlank()) {
                throw new IllegalArgumentException("El ingrediente no puede estar vacío");
            }
            ingredientes.add(ingrediente);
            return this;
            // @end
        }

        public Builder extraQueso() {
            // @sol Activar extra queso y devolver this
            this.extraQueso = true;
            return this;
            // @end
        }

        /** @throws IllegalStateException si hay más de MAX_INGREDIENTES ingredientes */
        public Pizza build() {
            // @sol Validar la cantidad de ingredientes y construir la Pizza
            if (ingredientes.size() > MAX_INGREDIENTES) {
                throw new IllegalStateException("Máximo " + MAX_INGREDIENTES + " ingredientes");
            }
            return new Pizza(this);
            // @end
        }
    }
}
