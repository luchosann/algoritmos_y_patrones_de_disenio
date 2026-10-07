# Módulo 2 — Patrones estructurales y de comportamiento

Paquete base: `uy.utec.apd.m2`. Resumen teórico: `Teorico/Modulo_2/Resumen_Modulo_2.md`.

| # | Patrón | Paquete | Test |
|---|---|---|---|
| 1 | Adapter | `adapter` | `AdapterTest` |
| 2 | Decorator | `decorator` | `DecoratorTest` |
| 3 | Composite | `composite` | `CompositeTest` |
| 4 | Facade | `facade` | `TiendaFacadeTest` |
| 5 | Proxy | `proxy` | `ProxyTest` |
| 6 | DAO + JDBC | `dao` | `ProductoDAOMemoriaTest`, `ProductoDAOJdbcTest`, `ProductoServiceTest` |
| 7 | Observer | `observer` | `SubastaTest` |
| 8 | Strategy | `strategy` | `EstrategiaEnvioTest` |

---

## Ejercicio 1 — Adapter (sensores de temperatura)

El sistema trabaja con la interfaz `SensorTemperatura` (`getId()`, `getCelsius()`), pero hay que integrar dos clases que **no se pueden modificar**:
- `TermometroFahrenheitLegacy`: `leerFahrenheit()`, `getCodigo()`.
- `SensorKelvinImportado`: `getKelvinValue()` (float), `serialNumber()`.

1. `AdaptadorFahrenheit` y `AdaptadorKelvin`: adapters **de objeto** (por composición). Fórmulas: C = (F − 32) × 5 / 9 y C = K − 273.15.
   El adaptador tiene que **leer el valor actual** del adaptado en cada llamada, no guardar una copia.
2. `MonitorTemperatura` (el cliente): `promedioCelsius()`, `idMasCaliente()`, `idsPorEncimaDe(umbral)`.

## Ejercicio 2 — Decorator (cafetería)

Componentes: `Espresso` (120) y `Te` (90). Implementar:
- `DecoradorBebida`: delega en la bebida envuelta.
- `ConLeche` (+30, `", leche"`), `ConCaramelo` (+25, `", caramelo"`), `ConCremaBatida` (+40, `", crema batida"`).
- `TamanioGrande`: multiplica **todo lo envuelto** por 1.5 y agrega `" (grande)"`. Con este decorador se ve que **el orden importa**.

## Ejercicio 3 — Composite (organigrama)

`Empleado` es la hoja y `Departamento` el compuesto; los dos implementan `MiembroOrganizacion`.
- `costoSalarial()` y `cantidadEmpleados()` se calculan recursivamente.
- `buscarEmpleado(nombre)` busca en todo el subárbol.
- `mostrar(nivel)` dibuja el árbol con 2 espacios por nivel. El formato exacto está en el javadoc y en el test.

## Ejercicio 4 — Facade (tienda online)

Los subsistemas `CatalogoPrecios`, `Inventario`, `PasarelaPago` y `ServicioEnvio` ya están hechos. Implementar `TiendaFacade.comprar(...)` siguiendo **en orden** las reglas del javadoc.

La regla más importante: **un fallo nunca deja efectos**. Si el pago es rechazado, hay que liberar el stock reservado.

## Ejercicio 5 — Proxy (cotizaciones)

`ServicioCotizacionRemoto` simula una API lenta y cuenta cuántas veces se la llama. Implementar tres proxies que implementan la **misma interfaz**:

1. **`CotizacionCacheProxy`**: cachea por moneda durante `ttlMillis`. El reloj se inyecta como `LongSupplier` para que el test pueda "adelantar el tiempo".
2. **`CotizacionProteccionProxy`**: solo permite las monedas habilitadas; para las demás lanza `SecurityException` **sin** llamar al real.
3. **`CotizacionLogProxy`**: registra cada llamada, tanto los éxitos como los errores.

El último test encadena los tres proxies.

## Ejercicio 6 — DAO

1. **`ProductoDAOMemoria`**: guarda en un mapa ordenado por id y **siempre guarda y devuelve copias**.
2. **`ProductoDAOJdbc`**: la misma interfaz, con JDBC y `PreparedStatement`. Envolvé cada `SQLException` en `DataAccessException`.
   Los tests corren contra **H2 en memoria** (modo PostgreSQL), así que no hace falta instalar nada.
3. **`ProductoService`**: validaciones y reglas de negocio (`registrar`, `aplicarDescuento`, `vender`, `valorInventario`).

`ProductoDAOContractTest` contiene los tests y se ejecuta **dos veces**, una por implementación. Si las dos pasan, cumplen el mismo contrato.

**6.4 (opcional, con Docker):** en esta carpeta corré `docker compose up -d` y después ejecutá `MainPostgres`. Es el mismo DAO contra un PostgreSQL real, y el `schema.sql` se carga solo. Para apagar la base: `docker compose down`.

## Ejercicio 7 — Observer (subasta)

- `Subasta` (sujeto): `suscribir` (sin duplicados), `desuscribir`, `ofertar` (con validaciones) y `cerrar`.
- `RegistroActividad` (observador): lleva un historial en texto.
- `PostorAutomatico` (observador que **reacciona**): cuando otro oferta, contraoferta sola, sin pasarse de su máximo.

Atención: hay un test en el que un observador se **desuscribe mientras está siendo notificado**. Pensá qué le pasa a un for-each sobre la lista en ese caso.

## Ejercicio 8 — Strategy (costo de envío)

- `EnvioEstandar` (100 + 10·kg + 0.5·km), `EnvioExpress` (1.8 × estándar, mínimo 300), `RetiroEnLocal` (0).
- `CalculadoraEnvio` (contexto): delega en la estrategia y agrega un 15% si el paquete es frágil. `masBarata(...)` elige la estrategia más barata de una lista.

`EstrategiaEnvio` es una `@FunctionalInterface`, así que una lambda también puede ser una estrategia.

---

## Preguntas teóricas

1. Adapter, Decorator y Proxy "envuelven" otro objeto. ¿Qué intención tiene cada uno? ¿Cuál de los tres **cambia la interfaz**?
2. ¿Por qué el Adapter de objeto (composición) suele preferirse al de clase (herencia)? ¿Se podría hacer un adapter de clase para `SensorKelvinImportado`?
3. En el Decorator, ¿cuántas subclases harían falta para cubrir todas las combinaciones de leche, caramelo y crema si se usara **herencia**?
4. En `TiendaFacade`, ¿qué pasaría si el cliente siguiera pudiendo usar `Inventario` y `PasarelaPago` directamente? ¿La fachada **prohíbe** usar los subsistemas?
5. ¿Qué problema de consistencia tiene un proxy caché? ¿Cómo lo mitiga el TTL?
6. ¿Por qué `ProductoDAOMemoria` guarda copias? ¿Qué bug aparecería en un test si no lo hiciera, y por qué ese bug **no** existe con JDBC?
7. `ProductoService` valida el id duplicado con `buscarPorId` antes de insertar. ¿Hay alguna situación en la que igualmente llegue una `DataAccessException` por clave duplicada?
8. En la subasta con `PostorAutomatico`: si el `RegistroActividad` se suscribiera **después** del postor automático, ¿en qué orden quedarían los eventos del registro? ¿Por qué?
9. ¿En qué se parecen Strategy y State? ¿Y Strategy y Factory Method?
