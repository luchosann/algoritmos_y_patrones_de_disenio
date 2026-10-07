# Módulo 3 — Aplicación completa CRUD: Gestión Académica

Una aplicación de consola para gestionar **estudiantes**, **cursos** e **inscripciones**. Aplica lo del curso en un solo proyecto: capas, DAO, Facade, validaciones, menú de consola y tests con JUnit y Mockito.

Paquete base: `uy.utec.apd.m3`. Resumen teórico: `Teorico/Modulo_3/Resumen_Modulo_3.md`.

```
consola/   MenuConsola, Main        ← solo habla con la fachada
fachada/   GestionAcademicaFacade    ← Facade
servicio/  *Service, NegocioException ← reglas de negocio
dao/       interfaces + memoria/     ← acceso a datos
modelo/    Estudiante, Curso, Inscripcion
util/      ValidadorCI, ValidadorEmail
```

Hacé los ejercicios **en orden**: cada capa usa la anterior.

---

## Ejercicio 1 — Validar cédulas uruguayas (`util/ValidadorCI`)

1. `digitoVerificador("1234567")` → 2. Algoritmo: multiplicar los dígitos por 2, 9, 8, 7, 6, 3, 4, sumar, y el verificador es `(10 − suma % 10) % 10`.
2. `normalizar("1.234.567-2")` → `"12345672"`. Las cédulas de 6 + 1 dígitos se completan con un `0` adelante.
3. `esValida(ci)`.

Test: `ValidadorCITest`.

## Ejercicio 2 — DAOs en memoria (`dao/memoria`)

1. `CrudDAOMemoria<T, ID>`: un DAO **genérico** que se escribe una vez y sirve para cualquier entidad. Recibe una `Function<T, ID>` que obtiene el id (por ejemplo `Estudiante::getCi`). Debe mantener el orden de inserción.
2. `InscripcionDAOMemoria`.

Tests: `CrudDAOMemoriaTest`, `InscripcionDAOMemoriaTest`.

## Ejercicio 3 — Servicios (`servicio`)

Los mensajes de las `NegocioException` están en los javadoc y deben ser **exactos**, porque la consola los muestra.

1. `CursoService`. Test: `CursoServiceTest`.
2. `EstudianteService`: **todavía sin tests**, los escribís vos en el ejercicio 5.
3. `InscripcionService`: coordina tres DAOs. Su test, `InscripcionServiceTest`, usa **Mockito**: los DAOs son *mocks*, así que se prueba solo la lógica del servicio. Leé el test para ver cómo se usan `when(...).thenReturn(...)` y `verify(...)`.

## Ejercicio 4 — Fachada (`fachada/GestionAcademicaFacade`)

1. `enMemoria()`: arma toda la aplicación. Ojo: los servicios tienen que **compartir** las mismas instancias de DAO.
2. Los métodos de la fachada delegan en los servicios.

Test: `GestionAcademicaFacadeTest` (de **integración**: todo real, sin mocks).

## Ejercicio 5 — Escribir tus propios tests (`EstudianteServiceTest`)

El archivo existe pero está vacío. Escribí los tests que se listan en el comentario de la clase. Después comparalos con los de `Soluciones/Modulo_3`.

Para comprobar que tus tests sirven, **rompé a propósito** `EstudianteService` (por ejemplo, borrá la validación del email) y verificá que algún test falle.

## Ejercicio 6 — Menú de consola (`consola/MenuConsola`)

El formato exacto de cada opción está en el javadoc de la clase. El menú recibe `Scanner` y `PrintStream` por constructor, y así `MenuConsolaTest` puede **simular lo que teclea el usuario**.

- Leé siempre con `nextLine()`: mezclar `nextInt()` y `nextLine()` es el bug clásico del buffer.
- Una opción o un cupo que no son números → `"Error: debe ingresar un numero"`.
- Cualquier `NegocioException` → `"Error: " + mensaje`, y el menú sigue funcionando.
- Si se termina la entrada, se sale de forma ordenada.

Cuando pasen los tests, ejecutá `Main` y probala a mano.

## Ejercicio 7 (opcional) — Persistencia en PostgreSQL

1. `docker compose up -d` en esta carpeta levanta PostgreSQL con las tablas de `src/main/resources/schema.sql`.
2. Implementá `EstudianteDAOJdbc`, `CursoDAOJdbc` e `InscripcionDAOJdbc`, que implementan **las mismas interfaces** que los DAOs en memoria. Usá como modelo `ProductoDAOJdbc` del módulo 2.
3. Implementá un Singleton `ConexionBD` para la conexión, como en el proyecto del teórico.
4. Agregá `GestionAcademicaFacade.conPostgres()` y usala en `Main`. **Ni los servicios ni la consola deberían cambiar**: esa es la gracia del DAO.

Este ejercicio no tiene tests automáticos ni solución incluida. Para verificar, usá la aplicación y consultá la base con `docker exec -it apd_m3_postgres psql -U usuario academia`.

---

## Preguntas teóricas

1. ¿Qué diferencia hay entre `InscripcionServiceTest` (con mocks) y `GestionAcademicaFacadeTest` (sin mocks)? ¿Qué tipo de error detecta cada uno que el otro no?
2. `InscripcionServiceTest` usa `verify(inscripcionDAO, never()).guardar(any())`. ¿Por qué no alcanza con verificar que se lance la excepción?
3. ¿Por qué la CI se guarda normalizada? ¿Qué pasaría con `existe("1.234.567-2")` si se guardara tal como la escribe el usuario?
4. `MenuConsola` recibe `Scanner` y `PrintStream` en lugar de usar `System.in` y `System.out` directamente. ¿Qué principio de diseño es ese y qué permite?
5. La relación entre Estudiante y Curso es muchos a muchos. ¿Por qué se modela con una tercera entidad `Inscripcion` en lugar de una lista de cursos dentro de `Estudiante`?
6. `CrudDAOMemoria` es genérico. ¿Qué ventaja tiene frente a escribir `EstudianteDAOMemoria` y `CursoDAOMemoria` por separado? ¿Por qué igual existen esas dos clases (vacías)?
7. En el proyecto del teórico, `AutorService` hace `new AutorDAO()` dentro de su constructor. Acá los servicios **reciben** los DAOs. ¿Qué ventaja tiene para los tests?
