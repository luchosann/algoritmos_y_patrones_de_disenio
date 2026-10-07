# Módulo 2 — Patrones estructurales y de comportamiento

**Archivos:** Maven · Patrón Adapter · Patrón Decorator · Patrón Composite · Patrón Facade · Patrón Proxy · Patrón DAO · Patrón Observer · Patrón Strategy

---

## 0. Maven (herramienta)

**Qué es:** herramienta de gestión de proyectos y automatización de compilación para Java.

**Para qué se usa:**
1. **Gestión de dependencias:** se declaran en el `pom.xml` y se descargan solas de repositorios remotos.
2. **Ciclo de vida estandarizado:** un solo comando (`mvn install`) hace todo.
3. **Estructura estándar** de proyecto.
4. **Multiproyecto / módulos.**

**`pom.xml` (Project Object Model):** define `groupId`, `artifactId`, `version`, `dependencies` (con `scope`, p. ej. `test`) y `build/plugins`.

```
proyecto/
├── src/main/java   ← código
├── src/test/java   ← tests
└── pom.xml
```

**Ciclo de vida:** `compile` → `test` → `package` (JAR/WAR) → `install` (repo local) → `deploy` (repo remoto, p. ej. Nexus).

**Comandos:** `mvn compile`, `mvn test`, `mvn package`, `mvn install`, `mvn clean`, `mvn dependency:tree`.

**Ventajas:** dependencias automáticas, ciclo estándar, integración con CI (Jenkins), portabilidad, extensible con plugins, modularidad.

**Ejemplo práctico:** proyecto Maven en IntelliJ + dependencia del driver PostgreSQL + clase `TestPostgreSQLConnection` que prueba `DriverManager.getConnection(...)`.

---

# Patrones estructurales

## 1. Adapter

**Idea:** permitir que **dos clases con interfaces incompatibles trabajen juntas**. El adaptador convierte la interfaz de una clase en la que el cliente espera.

**Participantes:**
- **Cliente:** usa la interfaz objetivo.
- **Target:** la interfaz que el cliente espera (`EnchufeEuropeo.conectar()`).
- **Adaptee:** la clase existente incompatible (`EnchufeAmericano.plugIn()`).
- **Adapter:** implementa Target y traduce las llamadas al Adaptee (`AdaptadorEnchufe`).

**Tipos:**
- **De clase:** usa **herencia**.
- **De objeto:** usa **composición**. Es el más común en Java porque es más flexible.

```java
class AdaptadorEnchufe implements EnchufeEuropeo {
    private EnchufeAmericano enchufeAmericano;
    public AdaptadorEnchufe(EnchufeAmericano e) { this.enchufeAmericano = e; }
    @Override public void conectar() { enchufeAmericano.plugIn(); }
}
```

**Usos:** integrar librerías o sistemas legacy que no se pueden modificar, compatibilizar APIs.
**Ventajas:** reutiliza código existente sin modificarlo, interoperabilidad, respeta Abierto/Cerrado.

**Adapter vs Facade vs Decorator:**
- **Adapter** *convierte* una interfaz en otra.
- **Facade** *simplifica* una interfaz.
- **Decorator** *agrega* comportamiento sin cambiar la interfaz.

### Principio Abierto/Cerrado (OCP, la "O" de SOLID)

> "Las entidades de software deben estar **abiertas para la extensión** pero **cerradas para la modificación**."

- **Mal:** `Empleado.calcularSalario(String tipo, ...)` con un `if/else` por tipo. Cada tipo nuevo obliga a modificar la clase.
- **Bien:** `abstract class Empleado { abstract double calcularSalario(); }` con subclases `EmpleadoPorHora`, `EmpleadoFijo` y `EmpleadoComision`. Se agregan tipos nuevos sin tocar lo existente.

**Beneficios:** mantenibilidad, reusabilidad, escalabilidad.

---

## 2. Decorator

**Idea:** **agregar funcionalidades a un objeto concreto de forma dinámica**, envolviéndolo, sin modificar su clase ni usar herencia para cada combinación.

**Estructura:**
- **Component:** `Ventana { dibujar(); }`
- **Concrete Component:** `VentanaSimple`
- **Decorator (abstracto):** implementa `Ventana` y **contiene** una `Ventana` a la que delega
- **Concrete Decorators:** `BordeDecorador`, `BarraDesplazamientoDecorador` (llaman a `super.dibujar()` y agregan lo suyo)

```java
Ventana v = new BarraDesplazamientoDecorador(new BordeDecorador(new VentanaSimple()));
v.dibujar();
```

**Cuándo:** agregar responsabilidades a objetos individuales de forma flexible y reversible, cuando la herencia generaría una explosión de subclases.

**Ventajas:** flexibilidad, combinaciones reutilizables, una responsabilidad por decorador.
**Desventajas:** más complejidad con muchas capas; **el orden de los decoradores importa**.

---

## 3. Composite

**Idea:** componer objetos en **estructuras de árbol** (jerarquías parte-todo) y tratar **hojas y compuestos de forma uniforme** mediante una interfaz común.

**Estructura:**
- **Component:** interfaz común (`FileSystemComponent.showDetails()`)
- **Leaf:** objeto individual (`File`)
- **Composite:** contiene una lista de Components y delega en ellos (`Directory` con `addComponent` / `removeComponent`)

**Usos:** sistemas de archivos, componentes de GUI, documentos XML/HTML.
**Ventajas:** simplicidad (trato uniforme), flexibilidad para agregar nuevos tipos, mejor organización de jerarquías.

---

## 4. Facade

**Idea:** ofrecer una **interfaz simplificada y unificada** a un subsistema complejo, ocultando sus detalles internos.

**Ejemplo hogar inteligente:** `HomeFacade` coordina `Luces`, `Climatizacion` y `Seguridad` con métodos de alto nivel `llegarACasa()` y `salirDeCasa()`.

**Ejemplo práctico, envío de email con JavaMail** (dependencias `javax.mail-api` y `com.sun.mail:javax.mail` 1.6.2): la configuración SMTP (`Properties`), la `Session` con `Authenticator`, el `MimeMessage` y `Transport.send()` se encapsulan en `EmailFacade.enviarCorreo(destinatario, asunto, cuerpo)`.

**Usos:** bibliotecas complejas, grandes sistemas empresariales, integración con sistemas legacy.
**Ventajas:** simplificación, desacoplamiento cliente–subsistema, mantenimiento centralizado.
**Desventajas:** la fachada puede crecer demasiado; limita el acceso a funcionalidades específicas.

---

## 5. Proxy

**Idea:** un **sustituto/intermediario** que implementa la misma interfaz que el objeto real y **controla el acceso** a él, pudiendo hacer cosas antes o después.

| Tipo | Propósito |
|---|---|
| **Protección** | Control de permisos/acceso |
| **Remoto** | Representa un objeto en otro espacio de memoria/servidor |
| **Virtual** | Retrasa la creación de un objeto costoso (*lazy loading*) |
| **Caché** | Guarda resultados de operaciones costosas |
| **Registro** | Audita/loguea las operaciones |

**Ejemplo (Proxy Virtual):** `ImagenProxy` implementa `Imagen` y solo crea la `ImagenReal` (que carga desde disco) la primera vez que se llama a `mostrar()`.

**Ventajas:** control centralizado, optimización (virtual/caché), desacoplamiento.
**Desventajas:** complejidad añadida, posible sobrecarga.

> **Proxy vs Decorator:** ambos envuelven un objeto con la misma interfaz. El Proxy **controla el acceso** (y suele gestionar el ciclo de vida del objeto real); el Decorator **agrega comportamiento** y se apila.

---

## 6. DAO (Data Access Object)

**Idea:** **separar la lógica de acceso a datos de la lógica de negocio.** El DAO encapsula todas las interacciones con la fuente de datos.

**Ventajas:**
1. Separación de responsabilidades.
2. Cambiar la fuente de datos (MySQL → PostgreSQL, memoria → BD) sin tocar el negocio.
3. Pruebas más sencillas (se puede testear o mockear aisladamente).

**Estructura (entidad `Usuario`):**
- **Entidad:** `Usuario` (id, nombre, email, password)
- **Interfaz DAO:** `UsuarioDAO` con el CRUD: `agregarUsuario`, `obtenerUsuarioPorId`, `obtenerTodosLosUsuarios`, `actualizarUsuario`, `eliminarUsuario`
- **Implementación:** `UsuarioDAOImpl`, primero en memoria (`ArrayList`) y luego con **JDBC**
- **Servicio:** `UsuarioService` recibe un `UsuarioDAO` por constructor (inyección de dependencias)

**JDBC y `PreparedStatement`:**
- Consultas precompiladas con **placeholders `?`** → `setString(1, …)`, `setInt(2, …)`.
- `executeUpdate()` para INSERT/UPDATE/DELETE (devuelve las filas afectadas); `executeQuery()` para SELECT (devuelve un `ResultSet` que se recorre con `next()`).
- **Ventajas:** protege contra **inyección SQL**, mejor rendimiento en consultas repetidas, legibilidad.
- Usar **try-with-resources** para cerrar los recursos.

```java
try (Connection c = DriverManager.getConnection(url, user, pass)) {
    UsuarioService service = new UsuarioService(new UsuarioDAOImpl(c));
    service.agregarUsuario(new Usuario(0, "Juan Pérez", "juan@example.com", "1234"));
}
```

---

# Patrones de comportamiento

## 7. Observer

**Idea:** relación **uno-a-muchos**: cuando el **sujeto** cambia de estado, **notifica automáticamente** a todos sus **observadores** suscritos.

**Componentes:**
- **Subject:** `agregarObservador`, `eliminarObservador`, `notificarObservadores`
- **Observer:** `actualizar(estado)`
- **Concrete Subject:** `ServicioMeteorologico` (al cambiar `estadoClima` llama a `notificarObservadores()`)
- **Concrete Observer:** `DispositivoMovil`

**Usos:** eventos en GUIs, notificaciones y mensajería, publicación/suscripción.
**Ventajas:** desacoplamiento, alta y baja de observadores en tiempo de ejecución, reutilización.
**Desventajas:** complejidad; notificar a muchos observadores puede ser costoso.

---

## 8. Strategy

**Idea:** definir una **familia de algoritmos**, encapsular cada uno y hacerlos **intercambiables en tiempo de ejecución**. El contexto delega en la estrategia elegida.

**Componentes:**
- **Strategy:** `MetodoPago { procesarPago(monto); }`
- **Concrete Strategies:** `PagoTarjetaCredito`, `PagoPayPal`, `PagoTransferenciaBancaria`
- **Context:** `ProcesadorPago` con `setMetodoPago(...)` y `procesar(monto)`

```java
ProcesadorPago p = new ProcesadorPago();
p.setMetodoPago(new PagoPayPal());
p.procesar(150.0);
```

**Otros usos:** compresión (ZIP/RAR/GZIP), impuestos por región, algoritmos de búsqueda y ordenamiento.
**Ventajas:** flexibilidad, cumple **Abierto/Cerrado**, desacopla el comportamiento del algoritmo.
**Desventajas:** más clases; es difícil de mantener si hay muchas estrategias.

---

## Errores detectados en el material

- **DAO (versión en memoria):** el `Main` crea `new Usuario(1, "Juan Perez", "juan@example.com")` con 3 argumentos, pero el constructor pide 4 (falta `password`).
- **Composite:** la clase se llama `File`, que choca con `java.io.File` si se importa ese paquete; conviene renombrarla (p. ej. `Archivo`).
