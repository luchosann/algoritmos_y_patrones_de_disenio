# Módulo 1 — Respuestas a las preguntas teóricas

**1. ¿Por qué `volatile` en DCL?**
`instancia = new Configuracion()` no es atómico: se reserva memoria, se ejecuta el constructor y se asigna la referencia. Sin `volatile`, la JVM y la CPU pueden **reordenar** esos pasos y publicar la referencia antes de que el constructor termine. Otro hilo pasaría el primer `if (instancia == null)` (ya no es null), no entraría al `synchronized` y usaría un objeto **a medio construir**. `volatile` prohíbe ese reordenamiento y garantiza la visibilidad entre hilos.

**2. Eager thread-safe.**
La JVM inicializa los atributos `static` durante la **carga de la clase**, y ese proceso está sincronizado por la propia JVM: solo un hilo inicializa la clase. Conviene cuando crear la instancia es barato, o cuando siempre se va a usar (no hay nada que ganar con la carga perezosa). Una alternativa lazy y sin `synchronized` es el *holder idiom*: una clase interna estática `Holder` con `static final Configuracion INSTANCIA`, que se carga recién la primera vez que se usa.

**3. Singleton y testing.**
El código cliente llama a `Configuracion.getInstancia()`, que es un método **estático**: no se puede inyectar otra implementación ni sustituirla por un mock sin herramientas especiales (como el `mockStatic` del proyecto del curso). Además, el estado global **se comparte entre tests** (fijate que `ConfiguracionTest` necesita `@Order` porque la instancia sobrevive de un test a otro). La alternativa es crear una sola instancia en el `main` y **pasarla por constructor** (inyección de dependencias).

**4. Factory Method vs `desde(...)`.**
El Factory Method de libro es `crearTransporte()`: un método **abstracto** redefinido por **subclases**, usado por la lógica de la clase base. `desde(String)` es una *Simple Factory* o *static factory method*: un método estático con un `switch` que elige qué subclase instanciar. No usa herencia para decidir y, para agregar un tipo, hay que modificarlo (no respeta Abierto/Cerrado). En la práctica se combinan mucho.

**5. Extender Abstract Factory.**
- Nueva familia `AltoContraste`: se **crean** `BotonAltoContraste`, `CampoTextoAltoContraste` y `FabricaAltoContraste`, sin modificar nada existente (salvo `segunPreferencia`, si se quiere exponer).
- Nuevo producto `Checkbox`: hay que **modificar** la interfaz `FabricaUI` (agregar `crearCheckbox`) y **todas** las fábricas concretas.

Agregar productos es el cambio caro: es la debilidad conocida del patrón.

**6. `List.copyOf`.**
Por dos motivos:
- La lista del builder sigue viva y se puede modificar después de `build()`. Si la pizza guardara la misma referencia, agregar un ingrediente al builder cambiaría una pizza ya construida y rompería la inmutabilidad.
- `List.copyOf` devuelve una lista **inmodificable**, así que `getIngredientes().add(...)` lanza `UnsupportedOperationException` sin tener que envolverla en cada getter.

**7. Shallow copy: nombre vs arma.**
Después del `clone()`, los dos objetos apuntan al mismo `String` y a la misma `Arma`. Pero:
- `setNombre("Clon")` **reasigna** el campo del clon para que apunte a otro `String`. El original sigue apuntando al viejo. (Además, `String` es inmutable: no hay forma de "modificarlo" por dentro.)
- `getArma().setDanio(99)` **modifica el objeto** compartido; no reasigna ninguna referencia. Como los dos apuntan al mismo `Arma`, ambos ven el cambio.

Moraleja: la copia superficial es segura para campos primitivos e inmutables, y peligrosa para objetos mutables.
