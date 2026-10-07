package uy.utec.apd.m1.abstractfactory;

/** Fábrica concreta: todos los componentes que crea son del tema Claro. */
public class FabricaClara implements FabricaUI {

    @Override
    public Boton crearBoton(String etiqueta) {
        // @sol Crear el producto concreto de esta familia
        return new BotonClaro(etiqueta);
        // @end
    }

    @Override
    public CampoTexto crearCampoTexto(String placeholder) {
        // @sol Crear el producto concreto de esta familia
        return new CampoTextoClaro(placeholder);
        // @end
    }
}
