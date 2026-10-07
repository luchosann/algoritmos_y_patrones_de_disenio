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
        // @sol Validar y luego delegar en el DAO
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (dao.buscarPorId(producto.getId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un producto con id " + producto.getId());
        }
        dao.insertar(producto);
        // @end
    }

    /**
     * Baja el precio un porcentaje (0 &lt; porcentaje &lt;= 100) y lo redondea a 2 decimales.
     *
     * @return el producto actualizado
     * @throws IllegalArgumentException si el porcentaje no es válido
     * @throws NoSuchElementException   si el producto no existe
     */
    public Producto aplicarDescuento(int id, double porcentaje) {
        // @sol
        if (porcentaje <= 0 || porcentaje > 100) {
            throw new IllegalArgumentException("Porcentaje inválido: " + porcentaje);
        }
        Producto p = dao.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("No existe el producto " + id));
        double nuevoPrecio = Math.round(p.getPrecio() * (1 - porcentaje / 100) * 100) / 100.0;
        p.setPrecio(nuevoPrecio);
        dao.actualizar(p);
        return p;
        // @end
    }

    /**
     * Descuenta stock por una venta.
     *
     * @throws IllegalArgumentException si cantidad &lt;= 0
     * @throws NoSuchElementException   si el producto no existe
     * @throws IllegalStateException    si no hay stock suficiente
     */
    public void vender(int id, int cantidad) {
        // @sol
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }
        Producto p = dao.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("No existe el producto " + id));
        if (p.getStock() < cantidad) {
            throw new IllegalStateException("Stock insuficiente");
        }
        p.setStock(p.getStock() - cantidad);
        dao.actualizar(p);
        // @end
    }

    /** Suma de precio * stock de todos los productos. */
    public double valorInventario() {
        // @sol
        double total = 0;
        for (Producto p : dao.listarTodos()) {
            total += p.getPrecio() * p.getStock();
        }
        return total;
        // @end
    }
}
