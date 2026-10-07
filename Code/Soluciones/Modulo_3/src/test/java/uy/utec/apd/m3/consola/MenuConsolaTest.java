package uy.utec.apd.m3.consola;

import org.junit.jupiter.api.Test;
import uy.utec.apd.m3.fachada.GestionAcademicaFacade;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/** Simula lo que teclea el usuario y verifica lo que se imprime. */
class MenuConsolaTest {

    private String ejecutar(GestionAcademicaFacade fachada, String... lineas) {
        String entrada = String.join("\n", lineas) + "\n";
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        new MenuConsola(new Scanner(entrada), new PrintStream(salida, true, StandardCharsets.UTF_8), fachada).ejecutar();
        return salida.toString(StandardCharsets.UTF_8);
    }

    private String ejecutar(String... lineas) {
        return ejecutar(GestionAcademicaFacade.enMemoria(), lineas);
    }

    @Test
    void muestraElMenuYSale() {
        String out = ejecutar("0");
        assertTrue(out.contains(MenuConsola.MENU));
        assertTrue(out.contains("Opcion: "));
        assertTrue(out.strip().endsWith("Hasta luego"));
    }

    @Test
    void altaYListadoDeEstudiantes() {
        String out = ejecutar("2", "1", "1.234.567-2", "Ana", "ana@utec.edu.uy", "2", "0");
        assertTrue(out.contains("(sin estudiantes)"));
        assertTrue(out.contains("Estudiante registrado: Ana"));
        assertTrue(out.contains("12345672 - Ana <ana@utec.edu.uy>"));
    }

    @Test
    void altaYListadoDeCursos() {
        String out = ejecutar("4", "3", "apd", "Algoritmos", "30", "4", "0");
        assertTrue(out.contains("(sin cursos)"));
        assertTrue(out.contains("Curso registrado: APD"));
        assertTrue(out.contains("APD - Algoritmos (disponibles: 30/30)"));
    }

    @Test
    void inscribirYVerInscriptos() {
        GestionAcademicaFacade f = GestionAcademicaFacade.enMemoria();
        f.altaEstudiante("1.234.567-2", "Ana", "ana@utec.edu.uy");
        f.altaCurso("APD", "Algoritmos", 5);
        String out = ejecutar(f, "6", "APD", "5", "12345672", "apd", "6", "APD", "0");
        assertTrue(out.contains("(sin inscriptos)"));
        assertTrue(out.contains("Inscripcion realizada"));
        assertTrue(out.contains("12345672 - Ana"));
    }

    @Test
    void losErroresDeNegocioSeMuestranYElMenuSigue() {
        String out = ejecutar("1", "123", "Ana", "ana@utec.edu.uy", "5", "12345672", "APD", "0");
        assertTrue(out.contains("Error: CI invalida"));
        assertTrue(out.contains("Error: No existe el estudiante"));
        assertTrue(out.contains("Hasta luego"));
    }

    @Test
    void entradasInvalidas() {
        String out = ejecutar("9", "hola", "3", "APD", "Algoritmos", "treinta", "0");
        assertTrue(out.contains("Opcion invalida"));
        assertEquals(2, out.split("Error: debe ingresar un numero", -1).length - 1,
                "Tanto la opción 'hola' como el cupo 'treinta' deben dar este error");
    }

    @Test
    void siSeTerminaLaEntradaSale() {
        assertTrue(ejecutar("2").contains("Hasta luego"));
        assertTrue(ejecutar("1", "1.234.567-2").contains("Hasta luego"), "Fin de entrada a mitad de un alta");
    }
}
