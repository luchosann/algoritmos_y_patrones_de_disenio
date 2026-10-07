package uy.utec.apd.m1.abstractfactory;

public class CampoTextoClaro implements CampoTexto {

    private final String placeholder;

    public CampoTextoClaro(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String render() {
        // TODO: Devolver "[claro] Campo(<placeholder>)"
        throw new UnsupportedOperationException("TODO");
    }
}
