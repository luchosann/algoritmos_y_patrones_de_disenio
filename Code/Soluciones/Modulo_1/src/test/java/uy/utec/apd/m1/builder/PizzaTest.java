package uy.utec.apd.m1.builder;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PizzaTest {

    @Test
    void valoresPorDefecto() {
        Pizza p = Pizza.builder(Pizza.Tamanio.CHICA).build();
        assertEquals(Pizza.Tamanio.CHICA, p.getTamanio());
        assertEquals("tradicional", p.getMasa());
        assertTrue(p.getIngredientes().isEmpty());
        assertFalse(p.tieneExtraQueso());
        assertEquals(300, p.precio(), 0.001);
    }

    @Test
    void construccionEncadenada() {
        Pizza p = Pizza.builder(Pizza.Tamanio.GRANDE)
                .masa("integral")
                .ingrediente("jamon")
                .ingrediente("morron")
                .extraQueso()
                .build();
        assertEquals("integral", p.getMasa());
        assertEquals(List.of("jamon", "morron"), p.getIngredientes());
        assertTrue(p.tieneExtraQueso());
        assertEquals(500 + 2 * 50 + 80, p.precio(), 0.001);
    }

    @Test
    void elTamanioEsObligatorio() {
        assertThrows(IllegalArgumentException.class, () -> Pizza.builder(null));
    }

    @Test
    void validaIngredientesYMasa() {
        Pizza.Builder b = Pizza.builder(Pizza.Tamanio.MEDIANA);
        assertThrows(IllegalArgumentException.class, () -> b.ingrediente(" "));
        assertThrows(IllegalArgumentException.class, () -> b.ingrediente(null));
        assertThrows(IllegalArgumentException.class, () -> b.masa(""));
    }

    @Test
    void noPermiteMasDeSeisIngredientes() {
        Pizza.Builder b = Pizza.builder(Pizza.Tamanio.MEDIANA);
        for (int i = 0; i < 7; i++) {
            b.ingrediente("ing" + i);
        }
        assertThrows(IllegalStateException.class, b::build);
    }

    @Test
    void laPizzaEsInmutable() {
        Pizza p = Pizza.builder(Pizza.Tamanio.CHICA).ingrediente("tomate").build();
        assertThrows(UnsupportedOperationException.class, () -> p.getIngredientes().add("anana"));
    }

    @Test
    void seguirUsandoElBuilderNoModificaPizzasYaConstruidas() {
        Pizza.Builder b = Pizza.builder(Pizza.Tamanio.CHICA).ingrediente("tomate");
        Pizza primera = b.build();
        b.ingrediente("aceitunas");
        Pizza segunda = b.build();
        assertEquals(List.of("tomate"), primera.getIngredientes());
        assertEquals(List.of("tomate", "aceitunas"), segunda.getIngredientes());
    }

    @Test
    void elDirectorArmaLasRecetas() {
        Pizza margarita = MenuPizzeria.margarita(Pizza.Tamanio.MEDIANA);
        assertEquals(List.of("tomate", "mozzarella", "albahaca"), margarita.getIngredientes());
        assertFalse(margarita.tieneExtraQueso());
        assertEquals(550, margarita.precio(), 0.001);

        Pizza cuatroQuesos = MenuPizzeria.cuatroQuesos(Pizza.Tamanio.GRANDE);
        assertEquals(List.of("mozzarella", "roquefort", "parmesano", "provolone"), cuatroQuesos.getIngredientes());
        assertTrue(cuatroQuesos.tieneExtraQueso());
        assertEquals(780, cuatroQuesos.precio(), 0.001);
    }
}
