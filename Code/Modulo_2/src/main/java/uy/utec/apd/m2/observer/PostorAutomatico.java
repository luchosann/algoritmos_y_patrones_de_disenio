package uy.utec.apd.m2.observer;

/**
 * Observador que REACCIONA: cada vez que OTRO postor oferta, contraoferta
 * automáticamente (monto + incremento) siempre que no supere su máximo.
 */
public class PostorAutomatico implements ObservadorSubasta {

    private final String nombre;
    private final double maximo;
    private final double incremento;

    public PostorAutomatico(String nombre, double maximo, double incremento) {
        this.nombre = nombre;
        this.maximo = maximo;
        this.incremento = incremento;
    }

    @Override
    public void nuevaOferta(Subasta subasta, String postor, double monto) {
        // TODO: Ignorar las ofertas propias; contraofertar si no se pasa del máximo
        throw new UnsupportedOperationException("TODO");
    }

    public String getNombre() {
        return nombre;
    }
}
