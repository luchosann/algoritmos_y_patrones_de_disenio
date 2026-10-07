package uy.utec.apd.m2.proxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Proxy de REGISTRO: anota cada consulta y después delega.
 * <ul>
 *   <li>Éxito: "cotizar(USD) = 40.0"</li>
 *   <li>Error: "cotizar(XYZ) -> error: &lt;mensaje de la excepción&gt;" y relanza la excepción</li>
 * </ul>
 * La moneda se registra tal como llegó (sin normalizar).
 */
public class CotizacionLogProxy implements ServicioCotizacion {

    private final ServicioCotizacion real;
    private final List<String> registro = new ArrayList<>();

    public CotizacionLogProxy(ServicioCotizacion real) {
        this.real = real;
    }

    @Override
    public double cotizar(String moneda) {
        // TODO: Delegar y registrar el resultado o el error
        throw new UnsupportedOperationException("TODO");
    }

    public List<String> getRegistro() {
        return List.copyOf(registro);
    }
}
