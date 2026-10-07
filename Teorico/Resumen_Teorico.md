# Resumen del Teórico — Algoritmos y Patrones de Diseño

Índice general. El detalle de cada tema está en el resumen de su módulo.

| Módulo | Tema | Resumen | PDFs |
|---|---|---|---|
| 1 | Patrones de diseño creacionales | [Resumen_Modulo_1.md](Modulo_1/Resumen_Modulo_1.md) | Patrones de diseño, Singleton, Singleton – Conexión a la BD, Factory Method, Abstract Factory, Builder, Prototype |
| 2 | Patrones estructurales y de comportamiento | [Resumen_Modulo_2.md](Modulo_2/Resumen_Modulo_2.md) | Maven, Adapter, Decorator, Composite, Facade, Proxy, DAO, Observer, Strategy |
| 3 | Aplicación completa CRUD | [Resumen_Modulo_3.md](Modulo_3/Resumen_Modulo_3.md) | Proyecto, Tests Unitarios |
| 4 | Estructuras de datos avanzadas | [Resumen_Modulo_4.md](Modulo_4/Resumen_Modulo_4.md) | Estructuras de Datos, Listas Enlazadas, Pilas, Colas, Hash, Algoritmos de Ordenamiento |
| 5 | Algoritmos avanzados | [Resumen_Modulo_5.md](Modulo_5/Resumen_Modulo_5.md) | Árboles, Recorridos de árboles, Conocimientos Adicionales (no evaluado) |

---

## Repaso rápido: patrones

| Patrón | Tipo | Problema que resuelve | Palabra clave |
|---|---|---|---|
| Singleton | Creacional | Una sola instancia global | *única instancia* |
| Factory Method | Creacional | Las subclases deciden qué objeto crear | *un producto, subclases* |
| Abstract Factory | Creacional | Crear familias de objetos compatibles | *familias* |
| Builder | Creacional | Construir objetos complejos paso a paso | *paso a paso, interfaz fluida* |
| Prototype | Creacional | Crear objetos clonando uno existente | *clone, shallow/deep* |
| Adapter | Estructural | Hacer compatibles interfaces distintas | *convertir interfaz* |
| Decorator | Estructural | Agregar funcionalidad dinámicamente | *envolver, apilar* |
| Composite | Estructural | Tratar igual hojas y compuestos de un árbol | *parte-todo* |
| Facade | Estructural | Simplificar un subsistema complejo | *interfaz simple* |
| Proxy | Estructural | Controlar el acceso a un objeto | *sustituto, lazy* |
| DAO | Estructural / arquitectura | Separar el acceso a datos del negocio | *CRUD, JDBC* |
| Observer | Comportamiento | Notificar cambios a muchos interesados | *suscripción* |
| Strategy | Comportamiento | Intercambiar algoritmos en tiempo de ejecución | *algoritmos intercambiables* |

## Repaso rápido: estructuras de datos

| Estructura | Principio | Clase Java | Buscar | Insertar/Eliminar |
|---|---|---|---|---|
| Lista enlazada | Secuencia de nodos | `LinkedList` | O(n) | O(1) en los extremos |
| Pila | LIFO | `Stack` / `ArrayDeque` | — | O(1) en el tope |
| Cola | FIFO | `LinkedList`, `PriorityQueue` | — | O(1); O(log n) en la de prioridad |
| Tabla hash | Clave → valor | `HashMap` | O(1) promedio | O(1) promedio |
| BST balanceado | Izquierda < raíz < derecha | `TreeSet`, `TreeMap` | O(log n) | O(log n) |

**Recorridos:** preorden (R-I-D), inorden (I-R-D, que en un BST sale ordenado), posorden (I-D-R), BFS con cola, DFS con pila.
