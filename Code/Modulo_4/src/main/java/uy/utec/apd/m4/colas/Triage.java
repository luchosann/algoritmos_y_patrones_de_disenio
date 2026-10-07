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

    // TODO: Atributos (sugerencia: un record Paciente y una PriorityQueue con Comparator)

    /** @throws IllegalArgumentException si la prioridad no está entre 1 y 5 */
    public void llegar(String nombre, int prioridad) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Nombre del paciente atendido. @throws NoSuchElementException si no hay nadie */
    public String atenderSiguiente() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int enEspera() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Nombres en el orden en que serán atendidos (sin atenderlos). */
    public List<String> ordenDeAtencion() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
