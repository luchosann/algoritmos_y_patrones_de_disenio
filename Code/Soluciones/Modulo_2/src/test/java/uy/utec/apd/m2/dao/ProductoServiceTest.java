package uy.utec.apd.m2.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceTest {

    private ProductoDAO dao;
    private ProductoService service;

    @BeforeEach
    void setUp() {
        dao = new ProductoDAOMemoria();
        service = new ProductoService(dao);
    }

    @Test
    void registraProductosValidos() {
        service.registrar(new Producto(1, "Mouse", 800, 5));
        assertTrue(dao.buscarPorId(1).isPresent());
    }

    @Test
    void rechazaProductosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> service.registrar(new Producto(1, " ", 800, 5)));
        assertThrows(IllegalArgumentException.class, () -> service.registrar(new Producto(1, null, 800, 5)));
        assertThrows(IllegalArgumentException.class, () -> service.registrar(new Producto(1, "Mouse", 0, 5)));
        assertThrows(IllegalArgumentException.class, () -> service.registrar(new Producto(1, "Mouse", 800, -1)));
        assertTrue(dao.listarTodos().isEmpty());
    }

    @Test
    void rechazaIdDuplicadoConIllegalArgument() {
        service.registrar(new Producto(1, "Mouse", 800, 5));
        assertThrows(IllegalArgumentException.class, () -> service.registrar(new Producto(1, "Otro", 10, 1)));
    }

    @Test
    void aplicaDescuentoYRedondea() {
        service.registrar(new Producto(1, "Mouse", 999.99, 5));
        Producto actualizado = service.aplicarDescuento(1, 15);
        assertEquals(849.99, actualizado.getPrecio(), 0.0001);
        assertEquals(849.99, dao.buscarPorId(1).orElseThrow().getPrecio(), 0.0001, "Debe persistirse");
    }

    @Test
    void descuentoInvalido() {
        service.registrar(new Producto(1, "Mouse", 100, 5));
        assertThrows(IllegalArgumentException.class, () -> service.aplicarDescuento(1, 0));
        assertThrows(IllegalArgumentException.class, () -> service.aplicarDescuento(1, 101));
        assertThrows(NoSuchElementException.class, () -> service.aplicarDescuento(7, 10));
    }

    @Test
    void venderDescuentaStock() {
        service.registrar(new Producto(1, "Mouse", 100, 5));
        service.vender(1, 3);
        assertEquals(2, dao.buscarPorId(1).orElseThrow().getStock());
        assertThrows(IllegalStateException.class, () -> service.vender(1, 3));
        assertThrows(IllegalArgumentException.class, () -> service.vender(1, 0));
        assertThrows(NoSuchElementException.class, () -> service.vender(9, 1));
    }

    @Test
    void valorInventario() {
        assertEquals(0, service.valorInventario(), 0.001);
        service.registrar(new Producto(1, "Mouse", 100, 5));
        service.registrar(new Producto(2, "Teclado", 250, 2));
        assertEquals(1000, service.valorInventario(), 0.001);
    }
}
