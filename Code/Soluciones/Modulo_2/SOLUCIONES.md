# Módulo 2 — Respuestas a las preguntas teóricas

**1. Adapter vs Decorator vs Proxy.**
- **Adapter:** *convierte* una interfaz en otra que el cliente espera. Es el único que **cambia la interfaz** (de `leerFahrenheit()` a `getCelsius()`).
- **Decorator:** *agrega comportamiento* manteniendo la misma interfaz, y se apila.
- **Proxy:** *controla el acceso* al objeto real (caché, permisos, log, carga diferida) manteniendo la misma interfaz. Normalmente el cliente no sabe que habla con un proxy.

**2. Adapter de objeto vs de clase.**
El de objeto:
- permite adaptar **cualquier instancia**, incluso subclases del adaptado;
- no expone los métodos del adaptado;
- deja libre la única herencia que permite Java.

Un adapter de clase para `SensorKelvinImportado` sería `class AdaptadorKelvin extends SensorKelvinImportado implements SensorTemperatura`. Se puede, porque la clase no es `final`. Pero el adaptador tendría que **crear** el sensor (no podría envolver uno existente) y heredaría métodos públicos (`getKelvinValue()`) que el cliente no debería ver.

**3. Explosión de subclases.**
Con 3 agregados independientes hay 2³ = 8 combinaciones por bebida base (incluida "sin nada"). Con 2 bases son 16 clases, y eso sin repetir agregados (doble caramelo) ni contar el tamaño grande. Con Decorator son 4 clases y las combinaciones se arman en tiempo de ejecución.

**4. Facade no prohíbe.**
La fachada **no impide** usar los subsistemas: es un atajo, no una barrera. Si el cliente los usa directo, puede saltearse la coordinación (por ejemplo, cobrar sin reservar stock) y romper las invariantes. Para impedirlo habría que reducir la visibilidad de los subsistemas (paquete privado o módulos) y exponer solo la fachada.

**5. Consistencia en la caché.**
La caché puede devolver datos **viejos** (*stale*) si el valor cambió en el servidor. El TTL acota cuánto tiempo puede estar desactualizado un dato. `invalidar()` permite forzar la actualización cuando se sabe que algo cambió. Es una decisión entre rendimiento y frescura de los datos.

**6. Copias en el DAO en memoria.**
Sin copias, el DAO guardaría **la misma referencia** que tiene el cliente. Un `p.setPrecio(1)` hecho afuera cambiaría "la base de datos" sin pasar por `actualizar()`, y los tests pasarían en memoria pero fallarían con la base real. Es el test `modificarUnObjetoAfueraNoCambiaLoPersistido`. Con JDBC no pasa porque cada `SELECT` construye objetos nuevos a partir de las filas: nunca se comparten referencias.

**7. Duplicado a pesar de validar.**
Sí: por **concurrencia**. Si dos hilos o dos usuarios registran el mismo id al mismo tiempo, los dos pasan el `buscarPorId` (todavía no existe) y el segundo `INSERT` viola la clave primaria. Por eso la restricción `PRIMARY KEY` de la base es la garantía real, y la validación previa solo sirve para dar un buen mensaje en el caso común.

**8. Orden de los observadores.**
Si el postor automático está primero: Ana oferta 100 → se notifica al Robot, que **dentro de esa notificación** oferta 110 → se notifica a todos (el registro anota "Robot 110") → vuelve al bucle original, y recién ahí el registro anota "Ana 100". El historial queda `Robot 110, Ana 100`, al revés de lo que ocurrió. Las notificaciones reentrantes hacen que el orden dependa del orden de suscripción. Algunas soluciones: encolar los eventos y notificarlos al final, o no permitir cambios de estado durante una notificación.

**9. Strategy vs State vs Factory Method.**
- **Strategy y State** tienen la misma estructura: un contexto que delega en un objeto intercambiable. La diferencia es la intención. En Strategy **el cliente elige** el algoritmo; en State **el propio objeto cambia** de estado internamente según lo que pasa.
- **Strategy vs Factory Method:** Strategy usa **composición** (el objeto se inyecta y se puede cambiar en tiempo de ejecución). Factory Method usa **herencia** (la subclase decide y queda fijo al crear el objeto).
