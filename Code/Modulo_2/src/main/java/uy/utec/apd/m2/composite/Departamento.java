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
        // TODO: Agregar a la lista y devolver this
        throw new UnsupportedOperationException("TODO");
    }

    public boolean quitar(MiembroOrganizacion miembro) {
        // TODO: Quitar de la lista
        throw new UnsupportedOperationException("TODO");
    }

    public List<MiembroOrganizacion> getMiembros() {
        return Collections.unmodifiableList(miembros);
    }

    @Override
    public double costoSalarial() {
        // TODO: Sumar recursivamente (delegando en cada miembro)
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int cantidadEmpleados() {
        // TODO: Sumar recursivamente
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Optional<Empleado> buscarEmpleado(String nombre) {
        // TODO: Buscar en cada miembro y devolver el primero que se encuentre
        throw new UnsupportedOperationException("TODO");
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
        // TODO: Primera línea con el nombre entre corchetes; luego los hijos con nivel + 1
        throw new UnsupportedOperationException("TODO");
    }
}
