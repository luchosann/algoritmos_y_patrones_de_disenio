package uy.utec.apd.m1.singleton;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConfiguracionTest {

    @Test
    @Order(1)
    void variosHilosAlMismoTiempoObtienenLaMismaInstancia() throws Exception {
        int hilos = 16;
        ExecutorService pool = Executors.newFixedThreadPool(hilos);
        CountDownLatch largada = new CountDownLatch(1);
        List<Future<Configuracion>> resultados = new ArrayList<>();
        for (int i = 0; i < hilos; i++) {
            resultados.add(pool.submit(() -> {
                largada.await();
                return Configuracion.getInstancia();
            }));
        }
        largada.countDown();

        Set<Configuracion> distintas = Collections.newSetFromMap(new IdentityHashMap<>());
        for (Future<Configuracion> f : resultados) {
            distintas.add(f.get(10, TimeUnit.SECONDS));
        }
        pool.shutdown();

        assertEquals(1, distintas.size(), "Todos los hilos deben recibir el mismo objeto");
        assertEquals(1, Configuracion.getInstanciasCreadas(),
                "El constructor se ejecutó más de una vez: la implementación no es thread-safe");
    }

    @Test
    @Order(2)
    void siempreDevuelveLaMismaInstancia() {
        assertSame(Configuracion.getInstancia(), Configuracion.getInstancia());
    }

    @Test
    void todosLosConstructoresSonPrivados() {
        for (Constructor<?> c : Configuracion.class.getDeclaredConstructors()) {
            assertTrue(Modifier.isPrivate(c.getModifiers()), "El constructor debe ser privado");
        }
    }

    @Test
    void elEstadoSeComparteEntreReferencias() {
        Configuracion a = Configuracion.getInstancia();
        Configuracion b = Configuracion.getInstancia();
        a.set("tema", "oscuro");
        assertEquals("oscuro", b.get("tema"));
    }

    @Test
    void cargaValoresPorDefecto() {
        Configuracion c = Configuracion.getInstancia();
        assertEquals("Gestor UTEC", c.get("app.nombre"));
        assertEquals("sin-valor", c.get("no.existe", "sin-valor"));
    }
}
