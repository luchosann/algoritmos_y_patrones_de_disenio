package uy.utec.apd.m2.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TiendaFacadeTest {

    private static final String TARJETA_OK = "4111111111111111";

    private CatalogoPrecios catalogo;
    private Inventario inventario;
    private PasarelaPago pagos;
    private ServicioEnvio envios;
    private TiendaFacade tienda;

    @BeforeEach
    void setUp() {
        catalogo = new CatalogoPrecios();
        inventario = new Inventario();
        pagos = new PasarelaPago();
        envios = new ServicioEnvio();
        catalogo.registrar("MOUSE", 800);
        inventario.agregarStock("MOUSE", 5);
        tienda = new TiendaFacade(catalogo, inventario, pagos, envios);
    }

    @Test
    void compraExitosa() {
        ResultadoCompra r = tienda.comprar("MOUSE", 2, TARJETA_OK, "18 de Julio 1234");
        assertTrue(r.exitosa());
        assertEquals("ENV-1", r.codigoSeguimiento());
        assertEquals(1600, r.total(), 0.001);
        assertEquals(3, inventario.stockDe("MOUSE"));
        assertEquals(1600, pagos.totalCobrado(), 0.001);
        assertEquals(1, envios.cantidadEnvios());
    }

    @Test
    void sinStockNoCobraNiEnvia() {
        ResultadoCompra r = tienda.comprar("MOUSE", 10, TARJETA_OK, "Av. Italia 1");
        assertFalse(r.exitosa());
        assertEquals("Sin stock", r.mensaje());
        assertEquals(0, pagos.cantidadCobros());
        assertEquals(0, envios.cantidadEnvios());
        assertEquals(5, inventario.stockDe("MOUSE"));
    }

    @Test
    void pagoRechazadoLiberaElStock() {
        ResultadoCompra r = tienda.comprar("MOUSE", 2, "1234", "Av. Italia 1");
        assertFalse(r.exitosa());
        assertEquals("Pago rechazado", r.mensaje());
        assertEquals(5, inventario.stockDe("MOUSE"), "El stock reservado debe liberarse");
        assertEquals(0, envios.cantidadEnvios());
    }

    @Test
    void validacionesPrevias() {
        assertEquals("Cantidad invalida", tienda.comprar("MOUSE", 0, TARJETA_OK, "x").mensaje());
        assertEquals("Direccion invalida", tienda.comprar("MOUSE", 1, TARJETA_OK, " ").mensaje());
        assertEquals("Producto inexistente", tienda.comprar("TECLADO", 1, TARJETA_OK, "x").mensaje());
        assertEquals(0, pagos.cantidadCobros());
    }

    @Test
    void variasComprasGeneranCodigosDistintos() {
        assertEquals("ENV-1", tienda.comprar("MOUSE", 1, TARJETA_OK, "a").codigoSeguimiento());
        assertEquals("ENV-2", tienda.comprar("MOUSE", 1, TARJETA_OK, "b").codigoSeguimiento());
        assertNull(tienda.comprar("MOUSE", 99, TARJETA_OK, "c").codigoSeguimiento());
    }
}
