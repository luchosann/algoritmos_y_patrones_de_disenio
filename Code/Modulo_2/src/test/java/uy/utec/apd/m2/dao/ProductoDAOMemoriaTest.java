package uy.utec.apd.m2.dao;

class ProductoDAOMemoriaTest extends ProductoDAOContractTest {

    @Override
    protected ProductoDAO crearDAO() {
        return new ProductoDAOMemoria();
    }
}
