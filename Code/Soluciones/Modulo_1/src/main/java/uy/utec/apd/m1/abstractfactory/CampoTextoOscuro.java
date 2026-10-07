package uy.utec.apd.m1.abstractfactory;

public class CampoTextoOscuro implements CampoTexto {

    private final String placeholder;

    public CampoTextoOscuro(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String render() {
        // @sol Devolver "[oscuro] Campo(<placeholder>)"
        return "[oscuro] Campo(" + placeholder + ")";
        // @end
    }
}
