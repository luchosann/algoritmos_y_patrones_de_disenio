package uy.utec.apd.m1.abstractfactory;

public class BotonClaro implements Boton {

    private final String etiqueta;

    public BotonClaro(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String render() {
        // @sol Devolver "[claro] Boton(<etiqueta>)"
        return "[claro] Boton(" + etiqueta + ")";
        // @end
    }
}
