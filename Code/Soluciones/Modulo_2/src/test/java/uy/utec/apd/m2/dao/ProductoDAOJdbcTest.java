package uy.utec.apd.m2.dao;

import org.junit.jupiter.api.AfterEach;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.UUID;

/**
 * Corre el contrato contra una base H2 EN MEMORIA en modo PostgreSQL:
 * no hace falta instalar nada. Cada test usa una base nueva.
 */
class ProductoDAOJdbcTest extends ProductoDAOContractTest {

    private Connection connection;

    @Override
    protected ProductoDAO crearDAO() throws Exception {
        String url = "jdbc:h2:mem:" + UUID.randomUUID() + ";MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE";
        connection = DriverManager.getConnection(url, "sa", "");
        try (InputStream in = getClass().getResourceAsStream("/schema.sql");
             Statement st = connection.createStatement()) {
            String script = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            for (String sentencia : script.replaceAll("--.*", "").split(";")) {
                if (!sentencia.isBlank()) {
                    st.execute(sentencia);
                }
            }
        }
        return new ProductoDAOJdbc(connection);
    }

    @AfterEach
    void cerrar() throws Exception {
        connection.close();
    }
}
