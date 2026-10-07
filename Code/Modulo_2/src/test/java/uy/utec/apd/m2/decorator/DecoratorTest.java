package uy.utec.apd.m2.decorator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorTest {

    @Test
    void unDecoradorAgregaDescripcionYCosto() {
        Bebida b = new ConLeche(new Espresso());
        assertEquals("Espresso, leche", b.descripcion());
        assertEquals(150, b.costo(), 0.001);
    }

    @Test
    void losDecoradoresSeApilan() {
        Bebida b = new ConCremaBatida(new ConCaramelo(new ConLeche(new Te())));
        assertEquals("Te, leche, caramelo, crema batida", b.descripcion());
        assertEquals(90 + 30 + 25 + 40, b.costo(), 0.001);
    }

    @Test
    void sePuedeAplicarElMismoDecoradorDosVeces() {
        Bebida dobleCaramelo = new ConCaramelo(new ConCaramelo(new Espresso()));
        assertEquals("Espresso, caramelo, caramelo", dobleCaramelo.descripcion());
        assertEquals(170, dobleCaramelo.costo(), 0.001);
    }

    @Test
    void elOrdenDeLosDecoradoresImporta() {
        Bebida grandeConLeche = new TamanioGrande(new ConLeche(new Espresso()));   // (120 + 30) * 1.5
        Bebida conLecheGrande = new ConLeche(new TamanioGrande(new Espresso()));   // 120 * 1.5 + 30
        assertEquals(225, grandeConLeche.costo(), 0.001);
        assertEquals(210, conLecheGrande.costo(), 0.001);
        assertEquals("Espresso, leche (grande)", grandeConLeche.descripcion());
    }

    @Test
    void elDecoradorEsUnaBebida() {
        Bebida b = new ConLeche(new Espresso());
        assertInstanceOf(DecoradorBebida.class, b);
    }
}
