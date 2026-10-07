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
        // @sol Construir la pizza con el builder
        return Pizza.builder(tamanio)
                .ingrediente("tomate")
                .ingrediente("mozzarella")
                .ingrediente("albahaca")
                .build();
        // @end
    }

    /** Ingredientes: mozzarella, roquefort, parmesano, provolone (en ese orden) + extra queso. */
    public static Pizza cuatroQuesos(Pizza.Tamanio tamanio) {
        // @sol Construir la pizza con el builder
        return Pizza.builder(tamanio)
                .ingrediente("mozzarella")
                .ingrediente("roquefort")
                .ingrediente("parmesano")
                .ingrediente("provolone")
                .extraQueso()
                .build();
        // @end
    }
}
