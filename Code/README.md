# Ejercicios de práctica — Algoritmos y Patrones de Diseño

Un proyecto Maven por módulo, con **enunciados**, **código base con `TODO`s** y **tests JUnit ya escritos** para que puedas autocorregirte.

```
Code/
├── Modulo_1/   Patrones creacionales         (30 tests)
├── Modulo_2/   Estructurales y comportamiento (64 tests)
├── Modulo_3/   Aplicación CRUD completa       (45 tests + los que escribas vos)
├── Modulo_4/   Estructuras de datos           (52 tests)
├── Modulo_5/   Árboles + extras               (36 tests)
└── Soluciones/ Mismos proyectos, resueltos, + respuestas teóricas (SOLUCIONES.md)
```

## Cómo trabajar

1. Abrí la carpeta del módulo (`Code/Modulo_N`) en IntelliJ como proyecto Maven (*File → Open* → elegí el `pom.xml` → *Open as Project*).
2. Leé `EJERCICIOS.md`.
3. Buscá los `TODO` (en IntelliJ: *View → Tool Windows → TODO*). Cada método a completar tiene un `throw new UnsupportedOperationException("TODO")` que tenés que reemplazar.
4. Corré los tests del ejercicio en el que estás:
   - IntelliJ: clic derecho en la clase de test → *Run*.
   - Terminal: `mvn test -Dtest=NombreDelTest` (por ejemplo `mvn test -Dtest=PizzaTest`).
5. Cuando todo esté en verde, compará con `Soluciones/Modulo_N` y respondé las preguntas teóricas del final de `EJERCICIOS.md`.

Al principio **todos los tests fallan**: es lo esperado. Algunos pocos pueden pasar "de casualidad" antes de que escribas código (por ejemplo, el que verifica que un constructor sea privado). No te fíes de esos hasta haber implementado el ejercicio.

## Reglas generales

- **No modifiques los tests**: si un test te parece mal, primero revisá el enunciado y el javadoc.
- **No modifiques las clases marcadas como "ya implementada" o "NO SE PUEDE MODIFICAR"** (simulan código legado o de terceros).
- Podés agregar métodos y atributos privados cuando quieras.
- Los javadoc de cada método son parte del enunciado: ahí están los formatos exactos, los mensajes de error y los casos borde.

## Requisitos

- Java 17 o superior
- Maven (o el que trae IntelliJ)
- Docker, **solo** para los ejercicios opcionales con PostgreSQL (módulos 2 y 3)

La primera vez que corras `mvn test`, Maven descarga las dependencias (JUnit, Mockito, H2) y necesita internet.
