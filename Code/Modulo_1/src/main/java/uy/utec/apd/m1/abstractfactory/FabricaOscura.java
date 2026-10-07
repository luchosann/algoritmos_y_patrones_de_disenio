package uy.utec.apd.m1.abstractfactory;

/** Fábrica concreta: todos los componentes que crea son del tema Oscuro. */
public class FabricaOscura implements FabricaUI {

    @Override
    public Boton crearBoton(String etiqueta) {
        // TODO: Crear el producto concreto de esta familia
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public CampoTexto crearCampoTexto(String placeholder) {
        // TODO: Crear el producto concreto de esta familia
        throw new UnsupportedOperationException("TODO");
    }
}
