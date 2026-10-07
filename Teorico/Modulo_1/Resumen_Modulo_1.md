# Módulo 1 — Patrones de diseño creacionales

**Archivos:** Patrones de diseño · Patrón Singleton · Singleton – Conexión a la BD · Patrón Factory Method · Patrón Abstract Factory · Patrón Builder · Patrón Prototype

---

## 1. ¿Qué son los patrones de diseño?

Son **soluciones generales y reutilizables** para problemas comunes de diseño de software. No son código listo para copiar, sino una **plantilla** que se adapta a cada situación.

**¿Para qué sirven?**
- Reutilizar soluciones ya probadas.
- Dar un **vocabulario común** al equipo ("usemos un Singleton").
- Mejorar la mantenibilidad y reducir errores.
- Promover buenas prácticas → software más robusto y flexible.

**Clasificación (según su propósito):**

| Categoría | Foco | Ejemplos |
|---|---|---|
| **Creacionales** | Cómo se crean los objetos (abstraer la instanciación) | Singleton, Factory Method, Abstract Factory, Builder, Prototype |
| **Estructurales** | Cómo se componen clases y objetos | Adapter, Composite, Proxy, Decorator, Facade, Bridge, Flyweight |
| **De comportamiento** | Cómo interactúan y se reparten responsabilidades los objetos | Strategy, Observer, Command, State, Chain of Responsibility, Mediator, Visitor, Iterator, Memento, Template Method, Interpreter |

---

## 2. Singleton

**Idea:** garantizar que una clase tenga **una única instancia** y ofrecer un **punto de acceso global** a ella.

**Piezas clave:**
- Atributo `private static` que guarda la instancia.
- **Constructor privado** (nadie puede hacer `new`).
- Método `public static obtenerInstancia()` que la crea si no existe y la devuelve.

**Variantes y concurrencia:**

| Variante | Cómo funciona | Pros | Contras |
|---|---|---|---|
| **Eager** (temprana) | `private static final Singleton inst = new Singleton();` | Simple, thread-safe sin sincronizar | Se crea aunque nunca se use |
| **Lazy** (perezosa) | Se crea en el primer `obtenerInstancia()` | Ahorra recursos | **No es thread-safe**: dos hilos pueden crear dos instancias |
| **Synchronized** | Método `static synchronized` | Thread-safe | Costo de sincronizar en *cada* llamada |
| **Double-Checked Locking** | `volatile` + `if null` → `synchronized` → `if null` | Thread-safe y eficiente (sincroniza solo la 1ª vez) | Más complejo; requiere `volatile` |

```java
public class Singleton {
    private static volatile Singleton instanciaUnica;
    private Singleton() {}
    public static Singleton obtenerInstancia() {
        if (instanciaUnica == null) {
            synchronized (Singleton.class) {
                if (instanciaUnica == null) instanciaUnica = new Singleton();
            }
        }
        return instanciaUnica;
    }
}
```

**Usos típicos:** conexión a base de datos, manejador de configuración (`config.properties` cargado una vez), logger único.

### Práctica: Singleton para conexión a PostgreSQL
1. **Base de datos:** PostgreSQL local o vía **Docker** con `docker-compose.yml` (imagen `postgres:16.2`, puerto `5432`, variables `POSTGRES_USER/PASSWORD/DB`, volumen persistente).
2. **Proyecto:** IntelliJ → New Project → Maven → archetype `maven-archetype-quickstart`.
3. **`pom.xml`:** dependencia `org.postgresql:postgresql:42.6.0` y Java 17.
4. **Clase `ConexionBDSingleton`:** guarda un `Connection` creado con `DriverManager.getConnection(url, usuario, contraseña)` en el constructor privado; expone `getConexion()`.
5. Levantar el contenedor con `docker-compose up` y ejecutar `Main`.

**Anexo Docker:** plataforma de **contenedores** (aislamiento, portabilidad, más livianos que VMs porque comparten el kernel).
Comandos: `docker --version`, `docker run -d -p 80:80 nginx`, `docker ps`, `docker stop`, `docker rm`, `docker images`, `docker rmi`.

Repo del ejemplo: https://git.utec.edu.uy/pa-2024/pa-2024-s2-singleton

---

## 3. Factory Method

**Idea:** definir una interfaz para crear un objeto en una clase base, pero **dejar que las subclases decidan qué clase concreta instanciar**. Se reemplaza el `new` directo por un *método fábrica*.

**Cuándo usarlo:**
- Delegar la creación a subclases.
- Encapsular la lógica de creación en un solo lugar.
- Varias clases comparten interfaz pero difieren en implementación.

**Estructura:**
- **Producto** (interfaz): `Vehiculo`
- **Productos concretos:** `Auto`, `Moto`
- **Creador abstracto:** `VehiculoFactory` con `abstract Vehiculo crearVehiculo()`
- **Creadores concretos:** `AutoFactory`, `MotoFactory`

```java
VehiculoFactory factory = new AutoFactory();
Vehiculo v = factory.crearVehiculo();
v.conducir();
```

**Otros ejemplos vistos:** conexiones BD (`MySQLConexionFactory` / `PostgreSQLConexionFactory`), notificaciones (Email / SMS), exportadores de archivos (PDF / CSV / XML).

**Consideraciones:** el método fábrica puede estar en la misma clase o en una separada; cuidar thread-safety si aplica.

---

## 4. Abstract Factory

**Idea:** crear **familias de objetos relacionados** que deben funcionar juntos, sin especificar sus clases concretas. Garantiza la **compatibilidad** entre productos de una misma familia.

**Estructura:**
- **Abstract Factory:** `VehiculoFactory { Rueda crearRueda(); Motor crearMotor(); }`
- **Concrete Factory:** `CocheFactory`, `MotoFactory`
- **Producto abstracto:** `Rueda`, `Motor`
- **Producto concreto:** `RuedaCoche`, `MotorCoche`, `RuedaMoto`, `MotorMoto`

El cliente (`Ensamblaje`) recibe una fábrica y nunca mezcla una rueda de moto con un motor de coche. Para cambiar de familia se cambia solo la fábrica.

**Factory Method vs. Abstract Factory:**

| | Factory Method | Abstract Factory |
|---|---|---|
| Crea | **Un** tipo de producto | Una **familia** de productos |
| Mecanismo | Un método (herencia/subclases) | Interfaz con varios métodos fábrica |
| Relación entre productos | Ninguna explícita | Diseñados para trabajar juntos |
| Complejidad | Menor | Mayor, pero más potente |

---

## 5. Builder

**Idea:** construir **objetos complejos paso a paso**, separando la construcción de su representación. Evita constructores con muchos parámetros.

**Participantes:**
- **Director** (opcional): orquesta los pasos.
- **Builder** (interfaz): define los pasos (`setPuertas`, `setVentanas`, …, `build()`).
- **Concrete Builder:** implementa los pasos y arma el producto.
- **Producto:** el objeto final (`Casa`).

```java
Casa casa = new CasaConcretaBuilder()
    .setPuertas(4).setVentanas(10)
    .setGaraje(true).setPiscina(true)
    .build();
```

Cada setter devuelve `this` → **interfaz fluida** (encadenamiento).

**Ventajas:** control sobre la construcción, código limpio, reutilización, flexibilidad para distintas representaciones.

---

## 6. Prototype

**Idea:** crear objetos **clonando un prototipo existente** en vez de construirlos desde cero. Útil cuando crear el objeto es costoso.

**Participantes:** interfaz *Prototype* (`clone()`), *Concrete Prototype* (implementa `Cloneable`), *Cliente* (pide clones).

**Copia superficial vs. profunda** (concepto clave):

| | Shallow copy | Deep copy |
|---|---|---|
| Qué copia | El objeto; las referencias internas se **comparten** | El objeto **y** todos sus objetos referenciados (recursivo) |
| Efecto | Cambiar el `Motor` del clon cambia el del original | Clon y original son independientes |
| En Java | `return super.clone();` | `super.clone()` + clonar manualmente cada referencia |

```java
@Override
protected Object clone() throws CloneNotSupportedException {
    Coche c = (Coche) super.clone();
    c.motor = new Motor(this.motor.getTipo()); // deep copy del motor
    return c;
}
```

**Ventajas:** mejor rendimiento, flexibilidad, el cliente no necesita saber cómo se construye el objeto.

---

## Errores detectados en el material

- **Prototype:** el ejemplo usa `cocheClon.getMotor()`, pero la clase `Coche` no define ese getter.
