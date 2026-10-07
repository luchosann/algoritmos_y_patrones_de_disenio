package uy.utec.apd.m2.composite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositeTest {

    private Departamento empresa;
    private Departamento ventas;
    private Empleado ana;

    @BeforeEach
    void armarOrganigrama() {
        ana = new Empleado("Ana", "Vendedora", 1000);
        ventas = new Departamento("Ventas")
                .agregar(ana)
                .agregar(new Empleado("Beto", "Vendedor", 1200));
        Departamento it = new Departamento("IT")
                .agregar(new Empleado("Caro", "Dev", 2000))
                .agregar(new Departamento("Soporte").agregar(new Empleado("Dani", "Tecnico", 900)));
        empresa = new Departamento("Empresa")
                .agregar(new Empleado("Luis", "CEO", 5000))
                .agregar(ventas)
                .agregar(it);
    }

    @Test
    void unaHojaSeComportaComoUnMiembroMas() {
        assertEquals(1000, ana.costoSalarial(), 0.001);
        assertEquals(1, ana.cantidadEmpleados());
    }

    @Test
    void elCompuestoSumaRecursivamente() {
        assertEquals(2200, ventas.costoSalarial(), 0.001);
        assertEquals(10100, empresa.costoSalarial(), 0.001);
        assertEquals(5, empresa.cantidadEmpleados());
    }

    @Test
    void departamentoVacio() {
        Departamento vacio = new Departamento("Nuevo");
        assertEquals(0, vacio.costoSalarial(), 0.001);
        assertEquals(0, vacio.cantidadEmpleados());
    }

    @Test
    void buscaEnProfundidad() {
        assertEquals("Tecnico", empresa.buscarEmpleado("Dani").orElseThrow().getCargo());
        assertSame(ana, empresa.buscarEmpleado("Ana").orElseThrow());
        assertTrue(empresa.buscarEmpleado("Zoe").isEmpty());
    }

    @Test
    void quitarActualizaLosTotales() {
        assertTrue(ventas.quitar(ana));
        assertFalse(ventas.quitar(ana));
        assertEquals(4, empresa.cantidadEmpleados());
    }

    @Test
    void losMiembrosNoSePuedenModificarDesdeAfuera() {
        assertThrows(UnsupportedOperationException.class,
                () -> ventas.getMiembros().add(new Empleado("X", "Y", 1)));
    }

    @Test
    void muestraElArbolConSangria() {
        String esperado = String.join("\n",
                "[Empresa]",
                "  Luis (CEO)",
                "  [Ventas]",
                "    Ana (Vendedora)",
                "    Beto (Vendedor)",
                "  [IT]",
                "    Caro (Dev)",
                "    [Soporte]",
                "      Dani (Tecnico)");
        assertEquals(esperado, empresa.mostrar(0));
        assertEquals("  Ana (Vendedora)", ana.mostrar(1));
    }
}
