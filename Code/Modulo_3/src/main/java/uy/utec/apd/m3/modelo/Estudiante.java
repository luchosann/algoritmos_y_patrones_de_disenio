package uy.utec.apd.m3.modelo;

import java.util.Objects;

/** Entidad. La CI se guarda normalizada: 8 dígitos sin puntos ni guion (ej. "12345672"). */
public final class Estudiante {

    private final String ci;
    private final String nombre;
    private final String email;

    public Estudiante(String ci, String nombre, String email) {
        this.ci = ci;
        this.nombre = nombre;
        this.email = email;
    }

    public String getCi() {
        return ci;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Estudiante e && ci.equals(e.ci) && nombre.equals(e.nombre) && email.equals(e.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ci, nombre, email);
    }

    @Override
    public String toString() {
        return "Estudiante{" + ci + ", " + nombre + ", " + email + "}";
    }
}
