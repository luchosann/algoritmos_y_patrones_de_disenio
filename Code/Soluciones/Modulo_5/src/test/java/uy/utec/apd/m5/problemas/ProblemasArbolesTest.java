package uy.utec.apd.m5.problemas;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static uy.utec.apd.m5.problemas.ProblemasArboles.*;

class ProblemasArbolesTest {

    /*
     *          8
     *        /   \
     *       3     10
     *      / \      \
     *     1   6      14
     *        / \    /
     *       4   7  13
     */
    private Nodo bst() {
        return new Nodo(8,
                new Nodo(3, new Nodo(1), new Nodo(6, new Nodo(4), new Nodo(7))),
                new Nodo(10, null, new Nodo(14, new Nodo(13), null)));
    }

    private static void inorden(Nodo n, List<Integer> r) {
        if (n != null) {
            inorden(n.izq, r);
            r.add(n.valor);
            inorden(n.der, r);
        }
    }

    private static int altura(Nodo n) {
        return n == null ? 0 : 1 + Math.max(altura(n.izq), altura(n.der));
    }

    @Test
    void bstValido() {
        assertTrue(esBSTValido(bst()));
        assertTrue(esBSTValido(null));
        assertFalse(esBSTValido(new Nodo(5, new Nodo(7), null)));
    }

    @Test
    void bstInvalidoAunqueCadaNodoCumplaConSusHijos() {
        //      8
        //     /
        //    3
        //     \
        //      9   ← mayor que 3 (bien) pero está a la izquierda de 8 (mal)
        Nodo trampa = new Nodo(8, new Nodo(3, null, new Nodo(9)), null);
        assertFalse(esBSTValido(trampa));
    }

    @Test
    void valoresExtremos() {
        assertTrue(esBSTValido(new Nodo(Integer.MIN_VALUE, null, new Nodo(Integer.MAX_VALUE))));
    }

    @Test
    void ancestroComun() {
        Nodo r = bst();
        assertEquals(6, ancestroComunMasBajo(r, 4, 7));
        assertEquals(3, ancestroComunMasBajo(r, 1, 7));
        assertEquals(8, ancestroComunMasBajo(r, 4, 13));
        assertEquals(10, ancestroComunMasBajo(r, 10, 13), "Un nodo puede ser ancestro de sí mismo");
    }

    @Test
    void kEsimo() {
        Nodo r = bst();
        assertEquals(1, kEsimoMenor(r, 1));
        assertEquals(6, kEsimoMenor(r, 4));
        assertEquals(14, kEsimoMenor(r, 9));
        assertThrows(NoSuchElementException.class, () -> kEsimoMenor(r, 10));
        assertThrows(NoSuchElementException.class, () -> kEsimoMenor(r, 0));
    }

    @Test
    void caminoConSuma() {
        Nodo r = bst();
        assertTrue(existeCaminoConSuma(r, 8 + 3 + 6 + 4));
        assertTrue(existeCaminoConSuma(r, 8 + 10 + 14 + 13));
        assertFalse(existeCaminoConSuma(r, 8 + 3), "3 no es hoja");
        assertFalse(existeCaminoConSuma(r, 8 + 10), "10 no es hoja");
        assertFalse(existeCaminoConSuma(null, 0));
    }

    @Test
    void arbolBalanceadoDesdeArreglo() {
        int[] ordenado = {1, 2, 3, 4, 5, 6, 7};
        Nodo r = desdeArregloOrdenado(ordenado);
        assertEquals(4, r.valor);
        assertEquals(3, altura(r));
        List<Integer> in = new ArrayList<>();
        inorden(r, in);
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), in);
        assertTrue(esBSTValido(r));
        assertNull(desdeArregloOrdenado(new int[0]));

        int[] grande = java.util.stream.IntStream.range(0, 1000).toArray();
        assertEquals(10, altura(desdeArregloOrdenado(grande)), "1000 nodos → altura mínima 10");
    }

    @Test
    void espejoNoModificaElOriginal() {
        Nodo r = bst();
        Nodo e = espejo(r);
        List<Integer> in = new ArrayList<>();
        inorden(e, in);
        assertEquals(List.of(14, 13, 10, 8, 7, 6, 4, 3, 1), in);
        assertEquals(3, r.izq.valor, "El original queda igual");
        assertNotSame(r, e);
        assertNull(espejo(null));
    }
}
