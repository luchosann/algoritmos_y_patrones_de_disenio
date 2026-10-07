package uy.utec.apd.m2.facade;

import java.util.ArrayList;
import java.util.List;

/** Subsistema (ya implementado, no modificar). */
public class ServicioEnvio {

    private final List<String> envios = new ArrayList<>();

    /** Programa un envío y devuelve el código de seguimiento: "ENV-1", "ENV-2", ... */
    public String programarEnvio(String direccion, String productoId, int cantidad) {
        String codigo = "ENV-" + (envios.size() + 1);
        envios.add(codigo + " -> " + direccion + " (" + cantidad + " x " + productoId + ")");
        return codigo;
    }

    public int cantidadEnvios() {
        return envios.size();
    }
}
