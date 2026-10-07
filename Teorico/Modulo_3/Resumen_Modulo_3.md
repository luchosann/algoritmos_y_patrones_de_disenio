# Módulo 3 — Aplicación completa CRUD

**Archivos:** Proyecto · Tests Unitarios

Este módulo integra lo visto en los módulos 1 y 2 (Singleton, DAO, Facade, Maven, JDBC) en una aplicación de consola, y agrega pruebas unitarias con JUnit y Mockito.

---

## 1. Introducción al proyecto CRUD

**Objetivo:** una aplicación **CRUD** (Create, Read, Update, Delete) de consola para gestionar **libros y autores** y la relación entre ellos.

**Requerimientos funcionales:**
- CRUD de libros y de autores.
- Vincular libros con autores.
- Menú interactivo en consola.

**Requerimientos no funcionales:**
- Fácil de usar y de mantener.
- Modular, con buenas prácticas y **patrones de diseño**.
- Portable a cualquier entorno con Java.

---

## 2. Diseño de la aplicación

**Paquetes** (`uy.utec.pa`):

| Paquete | Responsabilidad | Clases |
|---|---|---|
| `modelo` | Entidades | `Autor`, `Libro` |
| `dao` | Persistencia (JDBC) | `AutorDAO`, `LibroDAO` |
| `servicio` | Lógica de negocio, usa los DAOs | `AutorService`, `LibroService` |
| `consola` | Menús e interacción con el usuario | `Main` |
| `util` | Utilidades y conexión a la BD | `DatabaseConnection` |

**Modelo de datos:**
- `Libro`: `id`, `titulo`, `anioPublicacion`, `Autor autor`.
- `Autor`: `id`, `nombre`, `apellido`, `List<Libro> libros`.
- Relación **uno a muchos**: un autor tiene muchos libros y cada libro tiene un solo autor (FK `autor_id` en la tabla `libros`).

**Flujo en capas:**
```
Main (consola) → Service → DAO → DatabaseConnection (Singleton) → PostgreSQL
```

---

## 3. Patrones creacionales: Singleton

`DatabaseConnection` es un **Singleton** (lazy) que crea una sola `Connection` JDBC a PostgreSQL y la expone con `getInstance().getConnection()`. Los DAOs la piden en cada operación.

## 4. Patrones estructurales: Facade y DAO

- **Facade:** según el enunciado, debe unificar servicios y DAOs en una interfaz sencilla para las operaciones CRUD. El código de ejemplo no lo implementa: `Main` llama directo a los servicios, así que queda como tarea.
- **DAO:** `AutorDAO` y `LibroDAO` encapsulan el SQL con `PreparedStatement` (`INSERT`, `UPDATE`, `DELETE`) y `Statement` para los `SELECT`. `LibroDAO.obtenerLibros()` usa un `JOIN` con `autores` para reconstruir el `Autor` de cada libro.

## 5. Interacción desde consola

- Menú con un `do { ... } while (opcion != 0)` y un `switch` con 8 opciones (agregar, listar, actualizar y eliminar autores y libros) más la opción 0 para salir.
- La entrada se lee con `Scanner`. Después de `nextInt()` hay que llamar a `scanner.nextLine()` para limpiar el buffer.
- **Validación de entrada** (pedida en el enunciado): comprobar los datos antes de procesarlos, por ejemplo que el ID exista antes de actualizar o eliminar.

---

## 6. Pruebas y validación

### 6.1 Tests unitarios: conceptos

Una **prueba unitaria** verifica **la unidad más pequeña** del código (un método) **de forma aislada**.

**Ventajas:**
- Mejoran la calidad y detectan errores temprano.
- Permiten **refactorizar** con seguridad.
- Funcionan como **documentación viva**.
- Son la base de **TDD** (primero el test, después el código).

**Herramientas Java:**

| Herramienta | Uso |
|---|---|
| **JUnit 5** | Framework de tests |
| **Mockito** | *Mocks*: objetos falsos que simulan dependencias |
| **AssertJ** | Aserciones más expresivas |
| **Hamcrest** | *Matchers* para aserciones complejas |

**Dependencias Maven:** `org.junit.jupiter:junit-jupiter-api` y `junit-jupiter-engine` (scope `test`) y, para mocks, `org.mockito:mockito-core`.

### 6.2 Ejemplo básico: `Calculadora`

```java
class CalculadoraTest {
    private final Calculadora calc = new Calculadora();

    @Test void testSumar() { assertEquals(5, calc.sumar(2, 3)); }

    @Test void testDividirPorCero() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> calc.dividir(6, 0));
        assertEquals("No se puede dividir por cero", e.getMessage());
    }
}
```

- `assertEquals(esperado, actual)`: compara dos valores.
- `assertThrows(Clase.class, lambda)`: verifica que se lance la excepción.
- `assertTrue(...)`: verifica que una condición sea verdadera.
- En IntelliJ se ejecuta con clic derecho sobre la clase → *Run*.

### 6.3 Tests de los DAOs con Mockito

**Regla clave:** un test unitario **no toca la base de datos real ni servicios de terceros**. Se *mockean* `Connection`, `PreparedStatement`, `Statement` y `ResultSet`, y así el test es rápido, predecible y prueba solo la lógica del DAO.

**Anotaciones y métodos:**
- `@Mock`: crea un objeto simulado.
- `@InjectMocks`: crea la clase bajo prueba con los mocks inyectados.
- `@BeforeEach` / `@AfterEach`: preparación y limpieza antes y después de cada test.
- `MockitoAnnotations.openMocks(this)`: inicializa los mocks.
- `mockStatic(DatabaseConnection.class)`: mockea el método estático `getInstance()` del Singleton para que devuelva una conexión falsa.
- `when(x.metodo()).thenReturn(valor)`: define qué devuelve un mock. Con varios valores devuelve uno por llamada, p. ej. `resultSet.next()` → `true, true, false`.
- `verify(mock).metodo(args)`: comprueba que se llamó al método con esos argumentos.

**Qué verifica cada test:**
- `agregar`, `actualizar` y `eliminar`: que se hayan llamado los `setInt` y `setString` correctos en el orden de los `?`, y después `executeUpdate()`.
- `obtener`: se simula un `ResultSet` con filas falsas y se comprueba la lista resultante con `assertEquals`.

### 6.4 Tests de validación de negocio

Se agrega una validación en `AutorDAO.guardar()`: si el nombre es `null` o está vacío, lanza `IllegalArgumentException`. Se testean tres casos: nombre vacío, nombre `null` (los dos esperan la excepción) y nombre válido (espera `true`). En este caso **no se usan mocks** porque se prueba la lógica real.

**Tests de integración:** validan que los componentes funcionen **combinados**. Después viene la validación funcional completa de la aplicación.

Repo: https://git.utec.edu.uy/pda-2024/m5/proyecto

---

## Errores detectados en el material

- **`mockStatic` sin cerrar:** en `setUp()` se llama `mockStatic(DatabaseConnection.class)` pero nunca se cierra el `MockedStatic`. Con eso, desde el segundo test Mockito falla con *"static mocking is already registered in the current thread"*; `clearAllCaches()` no alcanza. Solución: guardar el `MockedStatic` en un campo y llamar a `.close()` en `@AfterEach`, o usar un `try (MockedStatic<...> m = mockStatic(...))`.
- **Imports faltantes:** `AutorDAO` y `LibroDAO` usan `DatabaseConnection` sin importar `uy.utec.pa.util.DatabaseConnection`, y `LibroDAO` usa `Autor` sin importarlo.
- **`Main.agregarLibro()` / `actualizarLibro()`:** crean un `Autor` falso (`"NombreAutor"`) en lugar de buscarlo por ID. El comentario lo admite; falta un `obtenerAutorPorId`.
- **Ejemplo de validación:** usa `AutorDao` (distinto de `AutorDAO`) y `new Autor(1, "")` con 2 argumentos, pero el constructor de `Autor` pide 3.
- **Facade:** el enunciado lo exige, pero el código de ejemplo no lo implementa.
