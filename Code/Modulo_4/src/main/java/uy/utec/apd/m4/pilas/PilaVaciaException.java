package uy.utec.apd.m4.pilas;

/** Stack underflow: se intentó desapilar o mirar la cima de una pila vacía. */
public class PilaVaciaException extends RuntimeException {

    public PilaVaciaException() {
        super("La pila está vacía");
    }
}
