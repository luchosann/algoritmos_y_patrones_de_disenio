package uy.utec.apd.m4.hash;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TablaHashTest {

    /** Clave con hashCode constante: TODAS colisionan en el mismo bucket. */
    record ClaveMala(String id) {
        @Override
        public int hashCode() {
            return 42;
        }
    }

    @Test
    void operacionesBasicas() {
        TablaHash<String, Integer> t = new TablaHash<>();
        assertNull(t.poner("uno", 1));
        t.poner("dos", 2);
        assertEquals(2, t.obtener("dos"));
        assertNull(t.obtener("tres"));
        assertEquals(2, t.tamanio());
        assertTrue(t.contieneClave("uno"));
        assertFalse(t.contieneClave("tres"));
    }

    @Test
    void ponerReemplazaYDevuelveElAnterior() {
        TablaHash<String, Integer> t = new TablaHash<>();
        t.poner("a", 1);
        assertEquals(1, t.poner("a", 100));
        assertEquals(100, t.obtener("a"));
        assertEquals(1, t.tamanio());
    }

    @Test
    void eliminar() {
        TablaHash<String, Integer> t = new TablaHash<>();
        t.poner("a", 1);
        assertEquals(1, t.eliminar("a"));
        assertNull(t.eliminar("a"));
        assertEquals(0, t.tamanio());
    }

    @Test
    void valoresNullSonValidos() {
        TablaHash<String, String> t = new TablaHash<>();
        t.poner("a", null);
        assertTrue(t.contieneClave("a"));
        assertNull(t.obtener("a"));
        assertThrows(IllegalArgumentException.class, () -> t.poner(null, "x"));
    }

    @Test
    void manejaColisiones() {
        TablaHash<ClaveMala, Integer> t = new TablaHash<>();
        for (int i = 0; i < 20; i++) {
            t.poner(new ClaveMala("k" + i), i);
        }
        for (int i = 0; i < 20; i++) {
            assertEquals(i, t.obtener(new ClaveMala("k" + i)));
        }
        assertEquals(10, t.eliminar(new ClaveMala("k10")));
        assertEquals(0, t.eliminar(new ClaveMala("k0")));
        assertEquals(19, t.eliminar(new ClaveMala("k19")));
        assertEquals(17, t.tamanio());
        assertEquals(5, t.obtener(new ClaveMala("k5")));
    }

    @Test
    void hashCodesNegativos() {
        TablaHash<Integer, String> t = new TablaHash<>();
        t.poner(Integer.MIN_VALUE, "min");
        t.poner(-7, "menos siete");
        assertEquals("min", t.obtener(Integer.MIN_VALUE));
        assertEquals("menos siete", t.obtener(-7));
    }

    @Test
    void redimensionaSegunElFactorDeCarga() {
        TablaHash<Integer, Integer> t = new TablaHash<>();
        assertEquals(8, t.capacidad());
        for (int i = 0; i < 6; i++) {
            t.poner(i, i);
        }
        assertEquals(8, t.capacidad(), "6/8 = 0.75: todavía no supera el máximo");
        t.poner(6, 6);
        assertEquals(16, t.capacidad(), "7/8 > 0.75: debe duplicar");
        for (int i = 0; i < 7; i++) {
            assertEquals(i, t.obtener(i), "Después del rehash todo debe seguir encontrándose");
        }
    }

    @Test
    void secuenciaAleatoriaContraHashMap() {
        Random r = new Random(7);
        TablaHash<Integer, Integer> mia = new TablaHash<>();
        Map<Integer, Integer> referencia = new HashMap<>();
        for (int i = 0; i < 20_000; i++) {
            int clave = r.nextInt(2_000) - 1_000;
            switch (r.nextInt(3)) {
                case 0 -> assertEquals(referencia.put(clave, i), mia.poner(clave, i));
                case 1 -> assertEquals(referencia.remove(clave), mia.eliminar(clave));
                default -> assertEquals(referencia.get(clave), mia.obtener(clave));
            }
        }
        assertEquals(referencia.size(), mia.tamanio());
        Set<Integer> claves = new HashSet<>(mia.claves());
        assertEquals(referencia.keySet(), claves);
        assertEquals(mia.tamanio(), mia.claves().size());
    }
}
