package uy.utec.apd.m2.dao;

import java.util.NoSuchElementException;

/**
 * Lógica de negocio. Depende de la INTERFAZ ProductoDAO (se inyecta por constructor),
 * así funciona igual con cualquier implementación.
 */
public class ProductoService {

    private final ProductoDAO dao;

    public ProductoService(ProductoDAO dao) {
        this.dao = dao;
    }

    /**
     * @throws IllegalArgumentException si el nombre es null/en blanco, precio &lt;= 0,
     *                                  stock &lt; 0 o ya existe un producto con ese id
     */
    public void registrar(Producto producto) {
        // TODO: Validar y luego delegar en el DAO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Baja el precio un porcentaje (0 &lt; porcentaje &lt;= 100) y lo redondea a 2 decimales.
     *
     * @return el producto actualizado
     * @throws IllegalArgumentException si el porcentaje no es válido
     * @throws NoSuchElementException   si el producto no existe
     */
    public Producto aplicarDescuento(int id, double porcentaje) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Descuenta stock por una venta.
     *
     * @throws IllegalArgumentException si cantidad &lt;= 0
     * @throws NoSuchElementException   si el producto no existe
     * @throws IllegalStateException    si no hay stock suficiente
     */
    public void vender(int id, int cantidad) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Suma de precio * stock de todos los productos. */
    public double valorInventario() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
