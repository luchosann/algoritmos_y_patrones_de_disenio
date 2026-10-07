package uy.utec.apd.m1.abstractfactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Cliente del Abstract Factory: arma un formulario sin conocer las clases concretas.
 * Solo depende de {@link FabricaUI}, {@link Boton} y {@link CampoTexto}.
 */
public class Formulario {

    private final FabricaUI fabrica;
    private final List<String> componentes = new ArrayList<>();

    public Formulario(FabricaUI fabrica) {
        this.fabrica = fabrica;
    }

    /** Agrega un campo de texto creado con la fábrica. Devuelve this para encadenar. */
    public Formulario agregarCampo(String placeholder) {
        // TODO: Crear el campo con la fábrica y guardar su render
        throw new UnsupportedOperationException("TODO");
    }

    /** Agrega un botón creado con la fábrica. Devuelve this para encadenar. */
    public Formulario agregarBoton(String etiqueta) {
        // TODO: Crear el botón con la fábrica y guardar su render
        throw new UnsupportedOperationException("TODO");
    }

    public int cantidadComponentes() {
        return componentes.size();
    }

    /** Todos los componentes renderizados, uno por línea (separados por "\n"). */
    public String render() {
        // TODO: Unir los componentes con "\n"
        throw new UnsupportedOperationException("TODO");
    }
}
