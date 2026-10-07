package uy.utec.apd.m1.builder;

/**
 * Director (opcional en el patrón Builder): conoce las "recetas" y
 * usa el Builder para construir pizzas predefinidas.
 */
public final class MenuPizzeria {

    private MenuPizzeria() {
    }

    /** Ingredientes: tomate, mozzarella, albahaca (en ese orden). */
    public static Pizza margarita(Pizza.Tamanio tamanio) {
        // TODO: Construir la pizza con el builder
        throw new UnsupportedOperationException("TODO");
    }

    /** Ingredientes: mozzarella, roquefort, parmesano, provolone (en ese orden) + extra queso. */
    public static Pizza cuatroQuesos(Pizza.Tamanio tamanio) {
        // TODO: Construir la pizza con el builder
        throw new UnsupportedOperationException("TODO");
    }
}
