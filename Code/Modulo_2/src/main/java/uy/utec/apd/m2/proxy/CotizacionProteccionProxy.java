package uy.utec.apd.m2.proxy;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Proxy de PROTECCIÓN: solo deja consultar las monedas permitidas
 * (sin distinguir mayúsculas). Si no está permitida lanza SecurityException
 * SIN llamar al servicio real.
 */
public class CotizacionProteccionProxy implements ServicioCotizacion {

    private final ServicioCotizacion real;
    private final Set<String> permitidas;

    public CotizacionProteccionProxy(ServicioCotizacion real, Set<String> permitidas) {
        this.real = real;
        this.permitidas = permitidas.stream()
                .map(m -> m.toUpperCase(Locale.ROOT))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public double cotizar(String moneda) {
        // TODO: Verificar el permiso antes de delegar
        throw new UnsupportedOperationException("TODO");
    }
}
