package uy.utec.apd.m1.abstractfactory;

public class BotonOscuro implements Boton {

    private final String etiqueta;

    public BotonOscuro(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String render() {
        // @sol Devolver "[oscuro] Boton(<etiqueta>)"
        return "[oscuro] Boton(" + etiqueta + ")";
        // @end
    }
}
