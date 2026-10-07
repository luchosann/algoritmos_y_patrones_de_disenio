package uy.utec.apd.m2.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/** Compuesto: contiene empleados y/o otros departamentos. */
public class Departamento implements MiembroOrganizacion {

    private final String nombre;
    private final List<MiembroOrganizacion> miembros = new ArrayList<>();

    public Departamento(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    /** Agrega un miembro y devuelve this para encadenar. */
    public Departamento agregar(MiembroOrganizacion miembro) {
        // @sol Agregar a la lista y devolver this
        miembros.add(miembro);
        return this;
        // @end
    }

    public boolean quitar(MiembroOrganizacion miembro) {
        // @sol Quitar de la lista
        return miembros.remove(miembro);
        // @end
    }

    public List<MiembroOrganizacion> getMiembros() {
        return Collections.unmodifiableList(miembros);
    }

    @Override
    public double costoSalarial() {
        // @sol Sumar recursivamente (delegando en cada miembro)
        double total = 0;
        for (MiembroOrganizacion m : miembros) {
            total += m.costoSalarial();
        }
        return total;
        // @end
    }

    @Override
    public int cantidadEmpleados() {
        // @sol Sumar recursivamente
        int total = 0;
        for (MiembroOrganizacion m : miembros) {
            total += m.cantidadEmpleados();
        }
        return total;
        // @end
    }

    @Override
    public Optional<Empleado> buscarEmpleado(String nombre) {
        // @sol Buscar en cada miembro y devolver el primero que se encuentre
        for (MiembroOrganizacion m : miembros) {
            Optional<Empleado> encontrado = m.buscarEmpleado(nombre);
            if (encontrado.isPresent()) {
                return encontrado;
            }
        }
        return Optional.empty();
        // @end
    }

    /**
     * Formato: sangría + "[nombre]" y debajo cada miembro con nivel + 1.
     * <pre>
     * [Empresa]
     *   [Ventas]
     *     Ana (Vendedora)
     *   Luis (CEO)
     * </pre>
     */
    @Override
    public String mostrar(int nivel) {
        // @sol Primera línea con el nombre entre corchetes; luego los hijos con nivel + 1
        StringBuilder sb = new StringBuilder("  ".repeat(nivel)).append('[').append(nombre).append(']');
        for (MiembroOrganizacion m : miembros) {
            sb.append('\n').append(m.mostrar(nivel + 1));
        }
        return sb.toString();
        // @end
    }
}
