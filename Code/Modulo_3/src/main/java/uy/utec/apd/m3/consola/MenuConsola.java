package uy.utec.apd.m3.consola;

import uy.utec.apd.m3.fachada.GestionAcademicaFacade;
import uy.utec.apd.m3.modelo.Curso;
import uy.utec.apd.m3.modelo.Estudiante;
import uy.utec.apd.m3.servicio.NegocioException;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * Menú de consola. Recibe el Scanner y el PrintStream por constructor para
 * poder testearlo simulando la entrada del usuario (ver MenuConsolaTest).
 *
 * <p>Reglas:</p>
 * <ul>
 *   <li>Leer SIEMPRE con nextLine() (evita el problema del buffer con nextInt()).</li>
 *   <li>Si se termina la entrada (no hay más líneas), salir como si se eligiera 0.</li>
 *   <li>Opción desconocida → "Opcion invalida".</li>
 *   <li>Número mal escrito (opción o cupo) → "Error: debe ingresar un numero".</li>
 *   <li>NegocioException → "Error: " + mensaje. El menú sigue funcionando.</li>
 * </ul>
 *
 * <p>Formato de cada opción (los prompts se imprimen con print, sin salto de línea):</p>
 * <pre>
 * 1 Alta de estudiante   pide "CI: ", "Nombre: ", "Email: "
 *                        → "Estudiante registrado: &lt;nombre&gt;"
 * 2 Listar estudiantes   una línea por estudiante: "&lt;ci&gt; - &lt;nombre&gt; &lt;&lt;email&gt;&gt;"
 *                        o "(sin estudiantes)"
 * 3 Alta de curso        pide "Codigo: ", "Nombre: ", "Cupo: "
 *                        → "Curso registrado: &lt;CODIGO&gt;"
 * 4 Listar cursos        una línea por curso: "&lt;CODIGO&gt; - &lt;nombre&gt; (disponibles: n/cupo)"
 *                        o "(sin cursos)"
 * 5 Inscribir            pide "CI: ", "Codigo: " → "Inscripcion realizada"
 * 6 Ver inscriptos       pide "Codigo: " → una línea "&lt;ci&gt; - &lt;nombre&gt;" por inscripto
 *                        o "(sin inscriptos)"
 * 0 Salir
 * </pre>
 */
public class MenuConsola {

    static final String MENU = String.join("\n",
            "=== Gestion Academica ===",
            "1. Alta de estudiante",
            "2. Listar estudiantes",
            "3. Alta de curso",
            "4. Listar cursos",
            "5. Inscribir estudiante en curso",
            "6. Ver inscriptos de un curso",
            "0. Salir");

    private final Scanner in;
    private final PrintStream out;
    private final GestionAcademicaFacade fachada;

    public MenuConsola(Scanner in, PrintStream out, GestionAcademicaFacade fachada) {
        this.in = in;
        this.out = out;
        this.fachada = fachada;
    }

    /**
     * Bucle principal: muestra MENU, luego "Opcion: " (con print, sin salto),
     * lee la opción y ejecuta. Al salir imprime "Hasta luego".
     */
    public void ejecutar() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: Implementar la lógica de cada opción (sugerencia: un método por opción)
}
