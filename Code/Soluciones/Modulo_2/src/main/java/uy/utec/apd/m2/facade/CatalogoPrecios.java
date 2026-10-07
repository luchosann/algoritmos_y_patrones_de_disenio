package uy.utec.apd.m2.facade;

import java.util.HashMap;
import java.util.Map;

/** Subsistema (ya implementado, no modificar). */
public class CatalogoPrecios {

    private final Map<String, Double> precios = new HashMap<>();

    public void registrar(String productoId, double precio) {
        precios.put(productoId, precio);
    }

    public boolean existe(String productoId) {
        return precios.containsKey(productoId);
    }

    public double precioDe(String productoId) {
        Double precio = precios.get(productoId);
        if (precio == null) {
            throw new IllegalArgumentException("Producto inexistente: " + productoId);
        }
        return precio;
    }
}
