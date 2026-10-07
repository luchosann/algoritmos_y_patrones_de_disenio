package uy.utec.apd.m2.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de CONTRATO: los mismos tests se ejecutan contra cada implementación
 * del DAO (ver las subclases). Si el DAO está bien encapsulado, el
 * comportamiento observable es idéntico.
 */
abstract class ProductoDAOContractTest {

    protected ProductoDAO dao;

    protected abstract ProductoDAO crearDAO() throws Exception;

    @BeforeEach
    void setUp() throws Exception {
        dao = crearDAO();
    }

    @Test
    void insertarYBuscarPorId() {
        dao.insertar(new Producto(1, "Mouse", 800, 5));
        assertEquals(new Producto(1, "Mouse", 800, 5), dao.buscarPorId(1).orElseThrow());
        assertTrue(dao.buscarPorId(99).isEmpty());
    }

    @Test
    void insertarDuplicadoFalla() {
        dao.insertar(new Producto(1, "Mouse", 800, 5));
        assertThrows(DataAccessException.class, () -> dao.insertar(new Producto(1, "Otro", 1, 1)));
    }

    @Test
    void listarTodosOrdenadoPorId() {
        dao.insertar(new Producto(3, "C", 1, 1));
        dao.insertar(new Producto(1, "A", 1, 1));
        dao.insertar(new Producto(2, "B", 1, 1));
        assertEquals(List.of(1, 2, 3), dao.listarTodos().stream().map(Producto::getId).toList());
    }

    @Test
    void actualizar() {
        dao.insertar(new Producto(1, "Mouse", 800, 5));
        assertTrue(dao.actualizar(new Producto(1, "Mouse inalambrico", 950, 3)));
        assertEquals(new Producto(1, "Mouse inalambrico", 950, 3), dao.buscarPorId(1).orElseThrow());
        assertFalse(dao.actualizar(new Producto(42, "X", 1, 1)));
    }

    @Test
    void eliminar() {
        dao.insertar(new Producto(1, "Mouse", 800, 5));
        assertTrue(dao.eliminar(1));
        assertFalse(dao.eliminar(1));
        assertTrue(dao.listarTodos().isEmpty());
    }

    @Test
    void buscarPorNombreSinDistinguirMayusculas() {
        dao.insertar(new Producto(1, "Teclado mecanico", 2500, 1));
        dao.insertar(new Producto(2, "Mouse", 800, 1));
        dao.insertar(new Producto(3, "TECLADO numerico", 700, 1));
        assertEquals(List.of(1, 3), dao.buscarPorNombre("teclado").stream().map(Producto::getId).toList());
        assertTrue(dao.buscarPorNombre("monitor").isEmpty());
    }

    @Test
    void modificarUnObjetoAfueraNoCambiaLoPersistido() {
        Producto p = new Producto(1, "Mouse", 800, 5);
        dao.insertar(p);
        p.setPrecio(1);
        dao.buscarPorId(1).orElseThrow().setStock(0);
        assertEquals(new Producto(1, "Mouse", 800, 5), dao.buscarPorId(1).orElseThrow());
    }
}
