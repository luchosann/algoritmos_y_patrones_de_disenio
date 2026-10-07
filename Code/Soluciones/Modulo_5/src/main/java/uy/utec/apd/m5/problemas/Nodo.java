package uy.utec.apd.m5.problemas;

/** Nodo de árbol binario (ya implementado) para los problemas. */
public class Nodo {

    public int valor;
    public Nodo izq;
    public Nodo der;

    public Nodo(int valor) {
        this(valor, null, null);
    }

    public Nodo(int valor, Nodo izq, Nodo der) {
        this.valor = valor;
        this.izq = izq;
        this.der = der;
    }
}
