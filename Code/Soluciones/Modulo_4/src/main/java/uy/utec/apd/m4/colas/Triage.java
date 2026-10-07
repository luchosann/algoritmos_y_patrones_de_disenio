package uy.utec.apd.m4.colas;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * Sala de emergencias con java.util.PriorityQueue.
 * <ul>
 *   <li>Prioridad 1 (más urgente) a 5 (menos urgente).</li>
 *   <li>Se atiende primero la prioridad más urgente.</li>
 *   <li>A IGUAL prioridad, por orden de llegada. ¡PriorityQueue NO garantiza esto por sí sola!
 *       (pista: guardar un número de llegada y usarlo como desempate en el Comparator).</li>
 * </ul>
 */
public class Triage {

    // @sol-blank Atributos (sugerencia: un record Paciente y una PriorityQueue con Comparator)
    private record Paciente(String nombre, int prioridad, long llegada) {
    }

    private final PriorityQueue<Paciente> espera = new PriorityQueue<>(
            (a, b) -> a.prioridad() != b.prioridad()
                    ? Integer.compare(a.prioridad(), b.prioridad())
                    : Long.compare(a.llegada(), b.llegada()));
    private long contadorLlegadas;
    // @end

    /** @throws IllegalArgumentException si la prioridad no está entre 1 y 5 */
    public void llegar(String nombre, int prioridad) {
        // @sol
        if (prioridad < 1 || prioridad > 5) {
            throw new IllegalArgumentException("Prioridad inválida: " + prioridad);
        }
        espera.add(new Paciente(nombre, prioridad, contadorLlegadas++));
        // @end
    }

    /** Nombre del paciente atendido. @throws NoSuchElementException si no hay nadie */
    public String atenderSiguiente() {
        // @sol
        Paciente p = espera.poll();
        if (p == null) {
            throw new NoSuchElementException("No hay pacientes en espera");
        }
        return p.nombre();
        // @end
    }

    public int enEspera() {
        // @sol
        return espera.size();
        // @end
    }

    /** Nombres en el orden en que serán atendidos (sin atenderlos). */
    public List<String> ordenDeAtencion() {
        // @sol
        PriorityQueue<Paciente> copia = new PriorityQueue<>(espera);
        List<String> orden = new java.util.ArrayList<>();
        while (!copia.isEmpty()) {
            orden.add(copia.poll().nombre());
        }
        return orden;
        // @end
    }
}
