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
        // @sol Delegar y registrar el resultado o el error
        try {
            double valor = real.cotizar(moneda);
            registro.add("cotizar(" + moneda + ") = " + valor);
            return valor;
        } catch (RuntimeException e) {
            registro.add("cotizar(" + moneda + ") -> error: " + e.getMessage());
            throw e;
        }
        // @end
    }

    public List<String> getRegistro() {
        return List.copyOf(registro);
    }
}
