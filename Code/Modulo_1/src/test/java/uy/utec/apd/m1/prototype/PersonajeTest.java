package uy.utec.apd.m1.prototype;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonajeTest {

    private Personaje guerrero() {
        return new Personaje("Guerrero", 10, new Arma("Espada", 25), List.of("Golpe", "Bloqueo"));
    }

    @Test
    void laCopiaSuperficialEsOtroObjetoPeroCompartReferencias() {
        Personaje original = guerrero();
        Personaje copia = original.copiaSuperficial();

        assertNotSame(original, copia);
        assertEquals("Guerrero", copia.getNombre());
        assertSame(original.getArma(), copia.getArma(), "En shallow copy el arma se comparte");
        assertSame(original.getHabilidades(), copia.getHabilidades());
    }

    @Test
    void enLaCopiaSuperficialModificarElArmaAfectaAlOriginal() {
        Personaje original = guerrero();
        Personaje copia = original.copiaSuperficial();

        copia.getArma().setDanio(99);
        copia.setNombre("Clon");

        assertEquals(99, original.getArma().getDanio(), "El arma es la misma");
        assertEquals("Guerrero", original.getNombre(), "Los campos propios (String) no se comparten al reasignar");
    }

    @Test
    void laCopiaProfundaEsIndependiente() {
        Personaje original = guerrero();
        Personaje copia = original.copiaProfunda();

        assertNotSame(original.getArma(), copia.getArma());
        assertNotSame(original.getHabilidades(), copia.getHabilidades());

        copia.getArma().setDanio(99);
        copia.agregarHabilidad("Furia");
        copia.setNivel(50);

        assertEquals(25, original.getArma().getDanio());
        assertEquals(List.of("Golpe", "Bloqueo"), original.getHabilidades());
        assertEquals(10, original.getNivel());
        assertEquals("Espada", copia.getArma().getNombre());
    }

    @Test
    void elRegistroEntregaCopiasNuevasEnCadaLlamada() {
        RegistroPersonajes registro = new RegistroPersonajes();
        Personaje prototipo = guerrero();
        registro.registrar("guerrero", prototipo);

        Personaje a = registro.crear("guerrero");
        Personaje b = registro.crear("guerrero");

        assertNotSame(a, b);
        assertNotSame(prototipo, a);
        a.getArma().setDanio(1);
        assertEquals(25, b.getArma().getDanio());
        assertEquals(25, prototipo.getArma().getDanio(), "Nunca se debe modificar el prototipo");
        assertEquals(java.util.Set.of("guerrero"), registro.claves());
    }

    @Test
    void elRegistroRechazaClavesDesconocidas() {
        assertThrows(IllegalArgumentException.class, () -> new RegistroPersonajes().crear("mago"));
    }
}
