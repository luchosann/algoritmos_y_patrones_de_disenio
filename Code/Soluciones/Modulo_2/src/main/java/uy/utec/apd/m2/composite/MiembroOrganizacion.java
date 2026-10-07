package uy.utec.apd.m2.composite;

import java.util.Optional;

/** Componente del Composite: hojas (Empleado) y compuestos (Departamento) se tratan igual. */
public interface MiembroOrganizacion {

    String getNombre();

    /** Suma de salarios de todo lo que contiene (o el propio, si es hoja). */
    double costoSalarial();

    /** Cantidad de EMPLEADOS (los departamentos no cuentan). */
    int cantidadEmpleados();

    /** Busca un empleado por nombre exacto en todo el subárbol. */
    Optional<Empleado> buscarEmpleado(String nombre);

    /**
     * Representación en árbol, con 2 espacios de sangría por nivel.
     * Líneas separadas por "\n", sin salto final.
     */
    String mostrar(int nivel);
}
