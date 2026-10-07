# Módulo 1 — Patrones creacionales

Paquete base: `uy.utec.apd.m1`. Resumen teórico: `Teorico/Modulo_1/Resumen_Modulo_1.md`.

| # | Patrón | Paquete | Test |
|---|---|---|---|
| 1 | Singleton | `singleton` | `ConfiguracionTest` |
| 2 | Factory Method | `factorymethod` | `LogisticaTest` |
| 3 | Abstract Factory | `abstractfactory` | `FabricaUITest` |
| 4 | Builder | `builder` | `PizzaTest` |
| 5 | Prototype | `prototype` | `PersonajeTest` |

---

## Ejercicio 1 — Singleton thread-safe (`Configuracion`)

Una configuración global que solo puede existir **una vez**, aunque 16 hilos la pidan al mismo tiempo.

- Declarar el atributo estático que guarda la instancia.
- Implementar `getInstancia()` con **lazy initialization** y **double-checked locking**.

El constructor tarda 50 ms a propósito (simula leer un archivo). Con eso, una implementación sin sincronizar crea varias instancias y el test `variosHilosAlMismoTiempoObtienenLaMismaInstancia` falla.

**Probá esto:** implementalo primero de la forma ingenua (`if (instancia == null) instancia = new ...`) y corré el test. ¿Cuántas instancias se crean? Después arreglalo.

---

## Ejercicio 2 — Factory Method (`Logistica`)

Una empresa de logística entrega por tierra, mar o aire. `Logistica` ya tiene la lógica de negocio (`planificarEntrega`, `calcularCosto`), pero **no sabe qué transporte usa**: eso lo decide cada subclase en el *factory method* `crearTransporte()`.

| Transporte | `entregar("X")` | Costo por km |
|---|---|---|
| `Camion` | `"Camion entrega X por carretera"` | 1.5 |
| `Barco` | `"Barco entrega X por mar"` | 0.8 |
| `Dron` | `"Dron entrega X por aire"` | 3.0 |

1. Implementar los tres transportes.
2. Implementar `crearTransporte()` en `LogisticaTerrestre`, `LogisticaMaritima` y `LogisticaAerea`.
3. Implementar `Logistica.desde(String tipo)`: `"terrestre"`, `"maritima"` o `"aerea"`, sin distinguir mayúsculas e ignorando espacios. Un tipo desconocido o `null` lanza `IllegalArgumentException`.

---

## Ejercicio 3 — Abstract Factory (`FabricaUI`)

Una interfaz gráfica con dos **familias** de componentes: tema claro y tema oscuro. Nunca se deben mezclar componentes de familias distintas.

| Componente | Render |
|---|---|
| `BotonClaro("OK")` | `[claro] Boton(OK)` |
| `CampoTextoClaro("Email")` | `[claro] Campo(Email)` |
| `BotonOscuro` / `CampoTextoOscuro` | igual, pero con `[oscuro]` |

1. Implementar los 4 productos concretos.
2. Implementar `FabricaClara` y `FabricaOscura`.
3. Implementar `FabricaUI.segunPreferencia(boolean modoOscuro)`.
4. Implementar el cliente `Formulario`, que solo conoce la interfaz `FabricaUI`. `agregarCampo` y `agregarBoton` devuelven `this`; `render()` une los componentes con `"\n"`.

---

## Ejercicio 4 — Builder (`Pizza`)

Una `Pizza` **inmutable** que solo se construye con su `Builder`:

```java
Pizza p = Pizza.builder(Pizza.Tamanio.GRANDE)
        .masa("integral")
        .ingrediente("jamon")
        .extraQueso()
        .build();
```

- El tamaño es **obligatorio**: `builder(null)` lanza `IllegalArgumentException`.
- La masa por defecto es `"tradicional"`.
- Una masa o un ingrediente `null` o en blanco lanza `IllegalArgumentException`.
- Con más de 6 ingredientes, `build()` lanza `IllegalStateException`.
- **Precio** = base del tamaño (300 / 400 / 500) + 50 por ingrediente + 80 si lleva extra queso.
- `getIngredientes()` no se puede modificar desde afuera.
- Seguir usando el builder después de `build()` **no** debe cambiar las pizzas ya construidas.

Después implementá el **Director** `MenuPizzeria` con las recetas `margarita` y `cuatroQuesos` (ingredientes en el javadoc).

---

## Ejercicio 5 — Prototype (`Personaje`)

En un juego, crear personajes desde cero es caro, así que se **clonan**. `Personaje` tiene un `Arma` (mutable) y una lista de habilidades.

1. `copiaSuperficial()`: con `super.clone()`. El arma y la lista se **comparten** con el original.
2. `copiaProfunda()`: el clon tiene su **propia** arma y su **propia** lista.
3. `RegistroPersonajes`: guarda prototipos por clave y `crear(clave)` devuelve **copias profundas**. Una clave desconocida lanza `IllegalArgumentException`.

---

## Preguntas teóricas

1. ¿Por qué hace falta `volatile` en el double-checked locking? ¿Qué podría pasar sin esa palabra?
2. La inicialización *eager* (`private static final Configuracion INSTANCIA = new Configuracion();`) también es thread-safe. ¿Por qué? ¿Cuándo conviene más que la *lazy*?
3. El Singleton es muy criticado porque "dificulta el testing". ¿Por qué? (Pista: pensá en cómo reemplazarías `Configuracion` por una falsa en un test.)
4. ¿Qué diferencia hay entre el Factory Method de `Logistica` y el método estático `Logistica.desde(...)`? ¿El segundo es un Factory Method "de libro"?
5. Si mañana hay que agregar un tema `AltoContraste`, ¿qué clases hay que crear y cuáles modificar? ¿Y si hay que agregar un nuevo componente `Checkbox` a todas las familias? ¿Cuál de los dos cambios es más costoso en Abstract Factory?
6. ¿Por qué `Pizza` usa `List.copyOf` en el constructor en lugar de guardar directamente la lista del builder?
7. En `copiaSuperficial()`, después de `copia.setNombre("Clon")`, el nombre del original **no** cambia, pero el arma sí. ¿Por qué hay diferencia si las dos cosas son referencias?
