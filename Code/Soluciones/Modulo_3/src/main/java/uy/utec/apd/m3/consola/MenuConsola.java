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
        // @sol
        boolean seguir = true;
        while (seguir) {
            out.println(MENU);
            String opcion = leer("Opcion: ");
            if (opcion == null) {
                break;
            }
            try {
                seguir = procesar(opcion.trim());
            } catch (NegocioException e) {
                out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                out.println("Error: debe ingresar un numero");
            } catch (FinDeEntrada e) {
                seguir = false;
            }
        }
        out.println("Hasta luego");
        // @end
    }

    // @sol-blank Implementar la lógica de cada opción (sugerencia: un método por opción)
    /** @return false si hay que salir */
    private boolean procesar(String opcion) {
        switch (Integer.parseInt(opcion)) {
            case 1 -> altaEstudiante();
            case 2 -> listarEstudiantes();
            case 3 -> altaCurso();
            case 4 -> listarCursos();
            case 5 -> inscribir();
            case 6 -> verInscriptos();
            case 0 -> {
                return false;
            }
            default -> out.println("Opcion invalida");
        }
        return true;
    }

    /** Pide "CI: ", "Nombre: ", "Email: " → "Estudiante registrado: &lt;nombre&gt;" */
    private void altaEstudiante() {
        String ci = leerObligatorio("CI: ");
        String nombre = leerObligatorio("Nombre: ");
        String email = leerObligatorio("Email: ");
        Estudiante e = fachada.altaEstudiante(ci, nombre, email);
        out.println("Estudiante registrado: " + e.getNombre());
    }

    /** Una línea por estudiante: "&lt;ci&gt; - &lt;nombre&gt; &lt;&lt;email&gt;&gt;", o "(sin estudiantes)". */
    private void listarEstudiantes() {
        List<Estudiante> lista = fachada.listarEstudiantes();
        if (lista.isEmpty()) {
            out.println("(sin estudiantes)");
        }
        for (Estudiante e : lista) {
            out.println(e.getCi() + " - " + e.getNombre() + " <" + e.getEmail() + ">");
        }
    }

    /** Pide "Codigo: ", "Nombre: ", "Cupo: " → "Curso registrado: &lt;CODIGO&gt;" */
    private void altaCurso() {
        String codigo = leerObligatorio("Codigo: ");
        String nombre = leerObligatorio("Nombre: ");
        int cupo = Integer.parseInt(leerObligatorio("Cupo: ").trim());
        Curso c = fachada.altaCurso(codigo, nombre, cupo);
        out.println("Curso registrado: " + c.getCodigo());
    }

    /** Una línea por curso: "&lt;CODIGO&gt; - &lt;nombre&gt; (disponibles: n/cupo)", o "(sin cursos)". */
    private void listarCursos() {
        List<Curso> lista = fachada.listarCursos();
        if (lista.isEmpty()) {
            out.println("(sin cursos)");
        }
        for (Curso c : lista) {
            out.println(c.getCodigo() + " - " + c.getNombre()
                    + " (disponibles: " + fachada.cuposDisponibles(c.getCodigo()) + "/" + c.getCupoMaximo() + ")");
        }
    }

    /** Pide "CI: ", "Codigo: " → "Inscripcion realizada" */
    private void inscribir() {
        String ci = leerObligatorio("CI: ");
        String codigo = leerObligatorio("Codigo: ");
        fachada.inscribir(ci, codigo);
        out.println("Inscripcion realizada");
    }

    /** Pide "Codigo: " → una línea "&lt;ci&gt; - &lt;nombre&gt;" por inscripto, o "(sin inscriptos)". */
    private void verInscriptos() {
        String codigo = leerObligatorio("Codigo: ");
        List<Estudiante> lista = fachada.inscriptosEn(codigo);
        if (lista.isEmpty()) {
            out.println("(sin inscriptos)");
        }
        for (Estudiante e : lista) {
            out.println(e.getCi() + " - " + e.getNombre());
        }
    }

    /** Muestra el prompt y lee una línea; null si no hay más entrada. */
    private String leer(String prompt) {
        out.print(prompt);
        return in.hasNextLine() ? in.nextLine() : null;
    }

    private String leerObligatorio(String prompt) {
        String linea = leer(prompt);
        if (linea == null) {
            throw new FinDeEntrada();
        }
        return linea;
    }

    /** Se terminó la entrada en medio de una operación. */
    private static class FinDeEntrada extends RuntimeException {
    }
    // @end
}
