# Índice de ejemplos de código

Cada tema del teórico tiene un archivo Java **autocontenido y comentado** en [`Code/Ejemplos/`](../Code/Ejemplos/). Cada uno explica el patrón o algoritmo en el encabezado, con un diagrama ASCII, y su `main` lo muestra funcionando.

**Cómo ejecutarlos** (Java 17 o superior, sin Maven):

```bash
cd Code/Ejemplos/Modulo_1
java Modulo1_Singleton.java
```

> No confundir con `Code/Modulo_N/`: esos son los **ejercicios** con `TODO`s y tests. Los de `Code/Ejemplos/` son **ejemplos resueltos** para estudiar.

---

## Módulo 1: Patrones creacionales · [Resumen](Modulo_1/Resumen_Modulo_1.md)

| Patrón | Tipo | Archivo | Qué muestra |
|---|---|---|---|
| Singleton | Creacional | [Modulo1_Singleton.java](../Code/Ejemplos/Modulo_1/Modulo1_Singleton.java) | Variantes eager, lazy, synchronized y double-checked locking; una carrera entre 10 hilos que rompe la versión lazy; gestor de configuración |
| Factory Method | Creacional | [Modulo1_FactoryMethod.java](../Code/Ejemplos/Modulo_1/Modulo1_FactoryMethod.java) | `VehiculoFactory` → `AutoFactory`, `MotoFactory`, `CamionFactory`; elección de la fábrica en tiempo de ejecución |
| Abstract Factory | Creacional | [Modulo1_AbstractFactory.java](../Code/Ejemplos/Modulo_1/Modulo1_AbstractFactory.java) | Familias `Rueda`/`Motor` de coche y de moto; el `Ensamblaje` nunca mezcla piezas |
| Builder | Creacional | [Modulo1_Builder.java](../Code/Ejemplos/Modulo_1/Modulo1_Builder.java) | `Casa` con interfaz fluida, valores por defecto, validación en `build()` y un Director |
| Prototype | Creacional | [Modulo1_Prototype.java](../Code/Ejemplos/Modulo_1/Modulo1_Prototype.java) | Copia superficial y copia profunda (`Coche`/`Motor`); registro de prototipos |

## Módulo 2: Patrones estructurales y de comportamiento · [Resumen](Modulo_2/Resumen_Modulo_2.md)

| Patrón | Tipo | Archivo | Qué muestra |
|---|---|---|---|
| Adapter + principio Abierto/Cerrado | Estructural | [Modulo2_Adapter.java](../Code/Ejemplos/Modulo_2/Modulo2_Adapter.java) | Enchufe americano → europeo, adapter de objeto y adapter de clase; salarios con `if/else` frente a polimorfismo |
| Decorator | Estructural | [Modulo2_Decorator.java](../Code/Ejemplos/Modulo_2/Modulo2_Decorator.java) | `Ventana` con borde, barra y título; la cafetería muestra que el orden de los decoradores importa |
| Composite | Estructural | [Modulo2_Composite.java](../Code/Ejemplos/Modulo_2/Modulo2_Composite.java) | Sistema de archivos `Archivo`/`Directorio` con tamaño recursivo |
| Facade | Estructural | [Modulo2_Facade.java](../Code/Ejemplos/Modulo_2/Modulo2_Facade.java) | `HomeFacade` con luces, climatización y seguridad; esquema del `EmailFacade` |
| Proxy | Estructural | [Modulo2_Proxy.java](../Code/Ejemplos/Modulo_2/Modulo2_Proxy.java) | Proxies virtual (`ImagenProxy`), de protección, de caché y de registro, encadenados |
| DAO | Estructural / arquitectura | [Modulo2_DAO.java](../Code/Ejemplos/Modulo_2/Modulo2_DAO.java) | `UsuarioDAO` en memoria y con JDBC (`PreparedStatement`); `UsuarioService` con inyección de dependencias |
| Observer | Comportamiento | [Modulo2_Observer.java](../Code/Ejemplos/Modulo_2/Modulo2_Observer.java) | `ServicioMeteorologico` notifica a celulares, pantallas y alertas; observador con lambda |
| Strategy | Comportamiento | [Modulo2_Strategy.java](../Code/Ejemplos/Modulo_2/Modulo2_Strategy.java) | `ProcesadorPago` con tarjeta, PayPal y transferencia; estrategia como lambda; `Comparator` como Strategy |

## Módulo 3: Aplicación CRUD completa · [Resumen](Modulo_3/Resumen_Modulo_3.md)

| Tema | Patrones / conceptos | Archivo | Qué muestra |
|---|---|---|---|
| Aplicación CRUD de consola | Singleton + DAO + Service + Facade | [Modulo3_AplicacionCRUD.java](../Code/Ejemplos/Modulo_3/Modulo3_AplicacionCRUD.java) | Biblioteca de autores y libros con arquitectura en capas, menú con `Scanner` y validación de entrada. Corre una demo automática; con `-i` es interactivo |
| Tests unitarios | JUnit, mocks, patrón AAA | [Modulo3_TestsUnitarios.java](../Code/Ejemplos/Modulo_3/Modulo3_TestsUnitarios.java) | `assertEquals`, `assertThrows` y un mock hechos a mano, con su equivalente en JUnit/Mockito al lado; nota sobre `mockStatic` |

## Módulo 4: Estructuras de datos · [Resumen](Modulo_4/Resumen_Modulo_4.md)

| Estructura / algoritmo | Principio | Archivo | Qué muestra |
|---|---|---|---|
| Listas enlazadas | Nodos enlazados | [Modulo4_ListasEnlazadas.java](../Code/Ejemplos/Modulo_4/Modulo4_ListasEnlazadas.java) | Lista simple (con inversión), doble y circular (problema de Josefo) implementadas a mano; API de `LinkedList` |
| Pilas | LIFO | [Modulo4_Pilas.java](../Code/Ejemplos/Modulo_4/Modulo4_Pilas.java) | Pila con array (overflow y underflow) y con nodos; `Stack` y `ArrayDeque`; paréntesis balanceados, notación posfija, deshacer |
| Colas | FIFO / prioridad | [Modulo4_Colas.java](../Code/Ejemplos/Modulo_4/Modulo4_Colas.java) | Cola circular propia, `LinkedList`, `PriorityQueue` (triage con `Comparator`), `Deque`, binarios con una cola |
| Tablas hash | Clave → valor | [Modulo4_TablasHash.java](../Code/Ejemplos/Modulo_4/Modulo4_TablasHash.java) | `HashMap`, encadenamiento con rehash, sondeo lineal, `equals`/`hashCode` en claves propias |
| Algoritmos de ordenamiento | Selection, Insertion, Heap, Quick, Merge | [Modulo4_AlgoritmosOrdenamiento.java](../Code/Ejemplos/Modulo_4/Modulo4_AlgoritmosOrdenamiento.java) | Traza paso a paso de cada algoritmo y comparación de tiempos con 20.000 elementos |

## Módulo 5: Algoritmos avanzados · [Resumen](Modulo_5/Resumen_Modulo_5.md)

| Tema | Archivo | Qué muestra |
|---|---|---|
| Árboles: binario, BST, AVL, `TreeSet`/`TreeMap` | [Modulo5_Arboles.java](../Code/Ejemplos/Modulo_5/Modulo5_Arboles.java) | Insertar, buscar y eliminar en un BST (los 3 casos); BST degenerado frente a AVL; las 4 rotaciones; `floor`, `ceiling`, `lower` y `higher` |
| Recorridos de árboles | [Modulo5_RecorridosArboles.java](../Code/Ejemplos/Modulo_5/Modulo5_RecorridosArboles.java) | Preorden, inorden y posorden; DFS iterativo con pila; BFS con cola y por niveles |
| Conocimientos adicionales *(no evaluados)* | [Modulo5_ConocimientosAdicionales.java](../Code/Ejemplos/Modulo_5/Modulo5_ConocimientosAdicionales.java) | Búsqueda binaria con traza, conversión entre decimal y binario, operadores bit a bit y trucos |

---

Ver también el índice general del teórico: [Resumen_Teorico.md](Resumen_Teorico.md).
