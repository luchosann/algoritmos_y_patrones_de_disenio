package uy.utec.apd.m4.ordenamiento;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class OrdenamientoTest {

    private static final Map<String, Consumer<int[]>> ALGORITMOS = Map.of(
            "seleccion", Ordenamiento::seleccion,
            "insercion", Ordenamiento::insercion,
            "mergeSort", Ordenamiento::mergeSort,
            "quickSort", Ordenamiento::quickSort,
            "heapSort", Ordenamiento::heapSort);

    private static List<int[]> casos() {
        Random r = new Random(123);
        List<int[]> casos = new ArrayList<>(List.of(
                new int[]{},
                new int[]{5},
                new int[]{2, 1},
                new int[]{1, 2, 3, 4, 5},
                new int[]{5, 4, 3, 2, 1},
                new int[]{3, 1, 3, 1, 3, 1},
                new int[]{-5, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, 7},
                new int[]{7, 7, 7, 7}));
        for (int n : new int[]{10, 100, 1000}) {
            casos.add(r.ints(n, -500, 500).toArray());
        }
        return casos;
    }

    @TestFactory
    Stream<DynamicTest> cadaAlgoritmoOrdenaCorrectamente() {
        return ALGORITMOS.entrySet().stream().map(e -> DynamicTest.dynamicTest(e.getKey(), () -> {
            for (int[] caso : casos()) {
                int[] esperado = caso.clone();
                Arrays.sort(esperado);
                int[] actual = caso.clone();
                e.getValue().accept(actual);
                assertArrayEquals(esperado, actual, e.getKey() + " falló con " + Arrays.toString(caso));
            }
        }));
    }

    record Alumno(String nombre, int nota) {
    }

    @Test
    void mergeSortEstableConservaElOrdenDeLosIguales() {
        List<Alumno> alumnos = List.of(
                new Alumno("Ana", 8), new Alumno("Beto", 6), new Alumno("Caro", 8),
                new Alumno("Dani", 6), new Alumno("Eli", 10), new Alumno("Fede", 8));
        List<Alumno> ordenados = Ordenamiento.mergeSortEstable(alumnos, Comparator.comparingInt(Alumno::nota));
        assertEquals(List.of("Beto", "Dani", "Ana", "Caro", "Fede", "Eli"),
                ordenados.stream().map(Alumno::nombre).toList());
        assertEquals("Ana", alumnos.get(0).nombre(), "No debe modificar la lista original");
    }

    @Test
    void mergeSortEstableConComparadorInverso() {
        List<Integer> r = Ordenamiento.mergeSortEstable(List.of(3, 1, 2), Comparator.reverseOrder());
        assertEquals(List.of(3, 2, 1), r);
        assertEquals(List.of(), Ordenamiento.mergeSortEstable(List.<Integer>of(), Comparator.naturalOrder()));
    }
}
