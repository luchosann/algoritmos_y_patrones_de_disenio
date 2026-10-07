package uy.utec.apd.m2.proxy;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class ProxyTest {

    @Test
    void elProxyCacheEvitaLlamadasRepetidas() {
        ServicioCotizacionRemoto real = new ServicioCotizacionRemoto();
        ServicioCotizacion proxy = new CotizacionCacheProxy(real, 60_000, () -> 0L);

        assertEquals(40.0, proxy.cotizar("USD"));
        assertEquals(40.0, proxy.cotizar("USD"));
        assertEquals(40.0, proxy.cotizar("usd"));
        assertEquals(1, real.getLlamadas(), "Solo la primera consulta debe llegar al servicio real");

        proxy.cotizar("EUR");
        assertEquals(2, real.getLlamadas(), "Cada moneda tiene su propia entrada en la caché");
    }

    @Test
    void laCacheVence() {
        ServicioCotizacionRemoto real = new ServicioCotizacionRemoto();
        AtomicLong ahora = new AtomicLong(1_000);
        CotizacionCacheProxy proxy = new CotizacionCacheProxy(real, 500, ahora::get);

        proxy.cotizar("USD");
        real.actualizar("USD", 41.0);
        ahora.set(1_499);
        assertEquals(40.0, proxy.cotizar("USD"), "Todavía no venció: valor viejo");
        ahora.set(1_500);
        assertEquals(41.0, proxy.cotizar("USD"), "Venció: debe consultar de nuevo");
        assertEquals(2, real.getLlamadas());
    }

    @Test
    void invalidarVaciaLaCache() {
        ServicioCotizacionRemoto real = new ServicioCotizacionRemoto();
        CotizacionCacheProxy proxy = new CotizacionCacheProxy(real, 60_000, () -> 0L);
        proxy.cotizar("BRL");
        proxy.invalidar();
        proxy.cotizar("BRL");
        assertEquals(2, real.getLlamadas());
    }

    @Test
    void elProxyDeProteccionBloqueaSinLlamarAlReal() {
        ServicioCotizacionRemoto real = new ServicioCotizacionRemoto();
        ServicioCotizacion proxy = new CotizacionProteccionProxy(real, Set.of("usd"));

        assertEquals(40.0, proxy.cotizar("USD"));
        assertThrows(SecurityException.class, () -> proxy.cotizar("EUR"));
        assertEquals(1, real.getLlamadas());
    }

    @Test
    void elProxyDeLogRegistraExitosYErrores() {
        CotizacionLogProxy proxy = new CotizacionLogProxy(new ServicioCotizacionRemoto());
        proxy.cotizar("USD");
        assertThrows(IllegalArgumentException.class, () -> proxy.cotizar("XYZ"));
        assertEquals(List.of(
                "cotizar(USD) = 40.0",
                "cotizar(XYZ) -> error: Moneda desconocida: XYZ"), proxy.getRegistro());
    }

    @Test
    void losProxiesSeCombinan() {
        ServicioCotizacionRemoto real = new ServicioCotizacionRemoto();
        CotizacionLogProxy log = new CotizacionLogProxy(real);
        ServicioCotizacion cliente = new CotizacionProteccionProxy(
                new CotizacionCacheProxy(log, 60_000, () -> 0L), Set.of("USD", "EUR"));

        cliente.cotizar("USD");
        cliente.cotizar("USD");
        assertThrows(SecurityException.class, () -> cliente.cotizar("BRL"));
        assertEquals(1, real.getLlamadas());
        assertEquals(1, log.getRegistro().size(), "El log solo ve lo que la caché deja pasar");
    }
}
