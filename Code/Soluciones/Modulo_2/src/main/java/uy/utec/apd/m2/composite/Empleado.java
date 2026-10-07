package uy.utec.apd.m2.composite;

import java.util.Optional;

/** Hoja. */
public class Empleado implements MiembroOrganizacion {

    private final String nombre;
    private final String cargo;
    private final double salario;

    public Empleado(String nombre, String cargo, double salario) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }

    @Override
    public double costoSalarial() {
        // @sol Una hoja cuesta su propio salario
        return salario;
        // @end
    }

    @Override
    public int cantidadEmpleados() {
        // @sol Una hoja es un empleado
        return 1;
        // @end
    }

    @Override
    public Optional<Empleado> buscarEmpleado(String nombre) {
        // @sol Devolverse a sí mismo si coincide el nombre
        return this.nombre.equals(nombre) ? Optional.of(this) : Optional.empty();
        // @end
    }

    /** Formato: sangría + "nombre (cargo)". Ej. nivel 2: "    Ana (Vendedora)" */
    @Override
    public String mostrar(int nivel) {
        // @sol Sangría de 2 espacios por nivel
        return "  ".repeat(nivel) + nombre + " (" + cargo + ")";
        // @end
    }
}
