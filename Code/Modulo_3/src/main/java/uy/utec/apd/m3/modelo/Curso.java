package uy.utec.apd.m3.modelo;

import java.util.Objects;

/** Entidad. El código se guarda en mayúsculas (ej. "APD"). */
public final class Curso {

    private final String codigo;
    private final String nombre;
    private final int cupoMaximo;

    public Curso(String codigo, String nombre, int cupoMaximo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cupoMaximo = cupoMaximo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Curso c && codigo.equals(c.codigo) && nombre.equals(c.nombre) && cupoMaximo == c.cupoMaximo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nombre, cupoMaximo);
    }

    @Override
    public String toString() {
        return "Curso{" + codigo + ", " + nombre + ", cupo=" + cupoMaximo + "}";
    }
}
