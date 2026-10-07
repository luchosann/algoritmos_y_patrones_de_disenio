package uy.utec.apd.m1.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejercicio 5 — Prototype.
 *
 * Crear un personaje "desde cero" es costoso en un juego (cargar modelos, stats...),
 * así que se clonan prototipos.
 */
public class Personaje implements Cloneable {

    private String nombre;
    private int nivel;
    private Arma arma;
    private List<String> habilidades;

    public Personaje(String nombre, int nivel, Arma arma, List<String> habilidades) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.arma = arma;
        this.habilidades = new ArrayList<>(habilidades);
    }

    /**
     * Copia SUPERFICIAL: nuevo Personaje, pero comparte la misma Arma
     * y la misma lista de habilidades que el original. Usar super.clone().
     */
    public Personaje copiaSuperficial() {
        // @sol Usar super.clone() y castear
        try {
            return (Personaje) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Personaje implementa Cloneable", e);
        }
        // @end
    }

    /**
     * Copia PROFUNDA: nuevo Personaje con su PROPIA Arma y su PROPIA lista de
     * habilidades. Modificar la copia no debe afectar al original.
     */
    public Personaje copiaProfunda() {
        // @sol Partir de la copia superficial y clonar las referencias mutables
        Personaje copia = copiaSuperficial();
        copia.arma = new Arma(arma.getNombre(), arma.getDanio());
        copia.habilidades = new ArrayList<>(habilidades);
        return copia;
        // @end
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Arma getArma() {
        return arma;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public void agregarHabilidad(String habilidad) {
        habilidades.add(habilidad);
    }
}
