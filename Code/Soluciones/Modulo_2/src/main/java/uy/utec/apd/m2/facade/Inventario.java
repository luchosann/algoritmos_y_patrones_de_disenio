package uy.utec.apd.m2.facade;

import java.util.HashMap;
import java.util.Map;

/** Subsistema (ya implementado, no modificar). */
public class Inventario {

    private final Map<String, Integer> stock = new HashMap<>();

    public void agregarStock(String productoId, int cantidad) {
        stock.merge(productoId, cantidad, Integer::sum);
    }

    public int stockDe(String productoId) {
        return stock.getOrDefault(productoId, 0);
    }

    public boolean hayStock(String productoId, int cantidad) {
        return stockDe(productoId) >= cantidad;
    }

    public void reservar(String productoId, int cantidad) {
        if (!hayStock(productoId, cantidad)) {
            throw new IllegalStateException("Stock insuficiente de " + productoId);
        }
        stock.put(productoId, stockDe(productoId) - cantidad);
    }

    public void liberar(String productoId, int cantidad) {
        agregarStock(productoId, cantidad);
    }
}
