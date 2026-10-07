package uy.utec.apd.m2.facade;

/**
 * FACHADA: el cliente hace una sola llamada, {@link #comprar}, y la fachada
 * coordina catálogo, inventario, pagos y envíos.
 */
public class TiendaFacade {

    private final CatalogoPrecios catalogo;
    private final Inventario inventario;
    private final PasarelaPago pagos;
    private final ServicioEnvio envios;

    public TiendaFacade(CatalogoPrecios catalogo, Inventario inventario, PasarelaPago pagos, ServicioEnvio envios) {
        this.catalogo = catalogo;
        this.inventario = inventario;
        this.pagos = pagos;
        this.envios = envios;
    }

    /**
     * Reglas, EN ESTE ORDEN:
     * <ol>
     *   <li>cantidad &lt;= 0 → fallo "Cantidad invalida"</li>
     *   <li>dirección null o en blanco → fallo "Direccion invalida"</li>
     *   <li>producto que no está en el catálogo → fallo "Producto inexistente"</li>
     *   <li>sin stock suficiente → fallo "Sin stock"</li>
     *   <li>reservar el stock y cobrar precio * cantidad</li>
     *   <li>si el pago es rechazado → LIBERAR el stock y fallo "Pago rechazado"</li>
     *   <li>programar el envío → éxito con el código de seguimiento y el total</li>
     * </ol>
     * Un fallo nunca debe dejar cobros, envíos ni stock reservado.
     */
    public ResultadoCompra comprar(String productoId, int cantidad, String tarjeta, String direccion) {
        // TODO: Coordinar los subsistemas siguiendo las reglas del javadoc
        throw new UnsupportedOperationException("TODO");
    }
}
