# Módulo 3 — Respuestas a las preguntas teóricas

**1. Unitario con mocks vs integración.**
- **`InscripcionServiceTest` (unitario):** aísla la lógica del servicio. Detecta reglas mal implementadas (orden de las validaciones, mensajes, no guardar si falla) sin depender de que los DAOs funcionen. Además, permite simular fácilmente situaciones difíciles de armar, como un curso con el cupo lleno.
- **`GestionAcademicaFacadeTest` (integración):** detecta errores de **cableado** entre las piezas. Por ejemplo, que `enMemoria()` cree dos `EstudianteDAO` distintos para dos servicios, o que el DAO real y el servicio no coincidan en el formato de la CI. Los mocks no detectan eso, porque devuelven lo que uno les dice.

**2. `verify(never())`.**
La excepción prueba que el servicio *avisó* del error, pero no que *no hizo daño*. Una implementación podría guardar la inscripción y **después** lanzar "Cupo lleno". El usuario vería el error y sin embargo quedaría inscripto. `verify(..., never())` comprueba justamente el efecto secundario que no debe ocurrir.

**3. CI normalizada.**
Así cada estudiante tiene **una única clave**. Si se guardara "1.234.567-2", entonces `existe("12345672")` daría false y se podría registrar dos veces a la misma persona, con dos formatos distintos. Normalizar al entrar y al buscar hace que la comparación sea confiable.

**4. Inyección de dependencias / inversión de dependencias.**
`MenuConsola` depende de abstracciones de entrada y salida que recibe desde afuera, en lugar de crearlas o usar las globales (la "D" de SOLID). Eso permite testear el menú con un `Scanner` sobre un `String` y un `PrintStream` sobre un `ByteArrayOutputStream`, sin intervención humana. También permitiría usarlo, por ejemplo, sobre un socket.

**5. Entidad intermedia.**
- Evita **duplicar** la información en dos lados (lista de cursos en el estudiante y de estudiantes en el curso), que tarde o temprano se desincroniza.
- Refleja cómo se guarda en una base relacional (tabla `inscripciones` con clave compuesta).
- Permite agregar datos propios de la relación, como la fecha de inscripción o la nota final.

**6. DAO genérico.**
El CRUD se escribe y se testea **una sola vez**, y cualquier entidad nueva lo obtiene gratis con una línea (`super(Entidad::getId)`). Las subclases vacías existen porque los servicios dependen de **tipos con nombre** (`EstudianteDAO`, `CursoDAO`). Eso permite agregar después consultas específicas (por ejemplo `buscarPorEmail`) y tener implementaciones JDBC distintas por entidad sin tocar los servicios.

**7. `new` adentro vs inyección.**
Si el servicio hace `new AutorDAO()`, queda **atado** a esa implementación, y para testearlo sin base de datos hay que recurrir a trucos como `mockStatic` (que el proyecto del teórico necesita). Si recibe el DAO por constructor, en el test se le pasa un mock o un DAO en memoria y listo. En producción, la fachada decide qué implementación usar.
