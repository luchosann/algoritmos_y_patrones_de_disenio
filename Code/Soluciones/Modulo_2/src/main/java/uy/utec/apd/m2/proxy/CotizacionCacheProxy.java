package uy.utec.apd.m2.proxy;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.LongSupplier;

/**
 * Proxy CACHÉ: guarda cada cotización durante {@code ttlMillis} milisegundos.
 * Mientras no venza, responde sin llamar al servicio real.
 * <p>La moneda se normaliza a mayúsculas ("usd" y "USD" son la misma entrada).</p>
 * <p>El reloj se inyecta para poder testear el vencimiento sin esperar.</p>
 */
public class CotizacionCacheProxy implements ServicioCotizacion {

    private final ServicioCotizacion real;
    private final long ttlMillis;
    private final LongSupplier reloj;
    // @sol-blank Estructura para guardar valor + momento en que se obtuvo, por moneda
    private final Map<String, Entrada> cache = new HashMap<>();

    private record Entrada(double valor, long obtenidoEn) {
    }
    // @end

    public CotizacionCacheProxy(ServicioCotizacion real, long ttlMillis, LongSupplier reloj) {
        this.real = real;
        this.ttlMillis = ttlMillis;
        this.reloj = reloj;
    }

    public CotizacionCacheProxy(ServicioCotizacion real, long ttlMillis) {
        this(real, ttlMillis, System::currentTimeMillis);
    }

    @Override
    public double cotizar(String moneda) {
        // @sol Devolver de la caché si no venció; si no, pedir al real y guardar
        String clave = moneda.toUpperCase(Locale.ROOT);
        long ahora = reloj.getAsLong();
        Entrada entrada = cache.get(clave);
        if (entrada != null && ahora - entrada.obtenidoEn() < ttlMillis) {
            return entrada.valor();
        }
        double valor = real.cotizar(clave);
        cache.put(clave, new Entrada(valor, ahora));
        return valor;
        // @end
    }

    /** Vacía la caché: la próxima consulta de cada moneda va al servicio real. */
    public void invalidar() {
        // @sol Vaciar la caché
        cache.clear();
        // @end
    }
}
