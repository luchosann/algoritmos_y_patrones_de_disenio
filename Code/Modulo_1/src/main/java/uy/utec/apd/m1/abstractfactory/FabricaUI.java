package uy.utec.apd.m1.abstractfactory;

/**
 * Abstract Factory: crea una FAMILIA de componentes visuales que combinan entre sí.
 */
public interface FabricaUI {

    Boton crearBoton(String etiqueta);

    CampoTexto crearCampoTexto(String placeholder);

    /** Devuelve la fábrica oscura si modoOscuro es true, la clara en caso contrario. */
    static FabricaUI segunPreferencia(boolean modoOscuro) {
        // TODO: Devolver la fábrica concreta que corresponda
        throw new UnsupportedOperationException("TODO");
    }
}
