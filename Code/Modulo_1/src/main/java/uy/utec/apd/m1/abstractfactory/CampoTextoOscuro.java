package uy.utec.apd.m1.abstractfactory;

public class CampoTextoOscuro implements CampoTexto {

    private final String placeholder;

    public CampoTextoOscuro(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String render() {
        // TODO: Devolver "[oscuro] Campo(<placeholder>)"
        throw new UnsupportedOperationException("TODO");
    }
}
