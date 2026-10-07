package uy.utec.apd.m1.abstractfactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FabricaUITest {

    @Test
    void laFabricaClaraCreaComponentesClaros() {
        FabricaUI fabrica = new FabricaClara();
        assertEquals("[claro] Boton(Aceptar)", fabrica.crearBoton("Aceptar").render());
        assertEquals("[claro] Campo(Email)", fabrica.crearCampoTexto("Email").render());
    }

    @Test
    void laFabricaOscuraCreaComponentesOscuros() {
        FabricaUI fabrica = new FabricaOscura();
        assertInstanceOf(BotonOscuro.class, fabrica.crearBoton("x"));
        assertInstanceOf(CampoTextoOscuro.class, fabrica.crearCampoTexto("x"));
        assertEquals("[oscuro] Boton(Cancelar)", fabrica.crearBoton("Cancelar").render());
    }

    @Test
    void segunPreferenciaDevuelveLaFabricaAdecuada() {
        assertInstanceOf(FabricaOscura.class, FabricaUI.segunPreferencia(true));
        assertInstanceOf(FabricaClara.class, FabricaUI.segunPreferencia(false));
    }

    @Test
    void elFormularioNoMezclaFamilias() {
        String render = new Formulario(new FabricaOscura())
                .agregarCampo("Usuario")
                .agregarCampo("Contraseña")
                .agregarBoton("Ingresar")
                .render();

        String[] lineas = render.split("\n");
        assertEquals(3, lineas.length);
        for (String linea : lineas) {
            assertTrue(linea.startsWith("[oscuro]"), "Componente de otra familia: " + linea);
        }
        assertEquals("[oscuro] Campo(Usuario)\n[oscuro] Campo(Contraseña)\n[oscuro] Boton(Ingresar)", render);
    }

    @Test
    void cambiarLaFabricaCambiaTodoElFormulario() {
        Formulario claro = new Formulario(FabricaUI.segunPreferencia(false)).agregarBoton("OK");
        assertEquals("[claro] Boton(OK)", claro.render());
        assertEquals(1, claro.cantidadComponentes());
    }

    @Test
    void formularioVacioRenderizaVacio() {
        assertEquals("", new Formulario(new FabricaClara()).render());
    }
}
