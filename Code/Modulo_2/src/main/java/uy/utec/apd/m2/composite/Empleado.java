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
        // TODO: Una hoja cuesta su propio salario
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int cantidadEmpleados() {
        // TODO: Una hoja es un empleado
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Optional<Empleado> buscarEmpleado(String nombre) {
        // TODO: Devolverse a sí mismo si coincide el nombre
        throw new UnsupportedOperationException("TODO");
    }

    /** Formato: sangría + "nombre (cargo)". Ej. nivel 2: "    Ana (Vendedora)" */
    @Override
    public String mostrar(int nivel) {
        // TODO: Sangría de 2 espacios por nivel
        throw new UnsupportedOperationException("TODO");
    }
}
