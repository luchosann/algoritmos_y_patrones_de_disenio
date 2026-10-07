package uy.utec.apd.m2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Ejercicio opcional 6.4: probar ProductoDAOJdbc contra PostgreSQL real.
 * Primero: {@code docker compose up -d} en la carpeta del módulo.
 */
public class MainPostgres {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tienda";
        try (Connection conn = DriverManager.getConnection(url, "usuario", "contrasenia")) {
            ProductoService service = new ProductoService(new ProductoDAOJdbc(conn));
            ProductoDAO dao = new ProductoDAOJdbc(conn);
            dao.eliminar(1);
            service.registrar(new Producto(1, "Teclado mecanico", 2500, 10));
            service.aplicarDescuento(1, 10);
            System.out.println("Productos en PostgreSQL: " + dao.listarTodos());
            System.out.println("Valor del inventario: " + service.valorInventario());
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a PostgreSQL (¿levantaste el docker?): " + e.getMessage());
        }
    }
}
