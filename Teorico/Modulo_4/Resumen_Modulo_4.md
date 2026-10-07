# Módulo 4 — Estructuras de datos avanzadas

**Archivos:** 1. Estructuras de Datos · 2. Listas Enlazadas · 3. Pilas · 4. Colas · 5. Hash · 9. Algoritmos de Ordenamiento

---

## 1. Introducción a estructuras de datos

Una **estructura de datos** es una forma de organizar y almacenar datos para accederlos y modificarlos de manera eficiente.

**Clasificación:**

```
Estructuras de datos
├── Primitivas: int, float, char, boolean
└── No primitivas
    ├── Lineales: Arrays, Listas enlazadas, Pilas, Colas
    ├── No lineales: Árboles, Grafos
    └── Otras: Tablas hash
```

| Estructura | Definición | Operaciones | Usos |
|---|---|---|---|
| **Array** | Elementos del mismo tipo en memoria **contigua** | Acceso por índice | Acceso rápido por posición |
| **Lista enlazada** | Nodos con dato y referencia al siguiente (simple, doble, circular) | Insertar, eliminar | Inserciones y eliminaciones frecuentes |
| **Pila** | **LIFO** | `push`, `pop`, `peek` | Expresiones, "deshacer" |
| **Cola** | **FIFO** (variantes: Deque, Priority Queue) | `enqueue`, `dequeue`, `peek` | Tareas del SO, simulaciones |
| **Árbol** | Jerarquía raíz → hijos (binario, BST, AVL, rojo-negro) | Insertar, eliminar, buscar | Jerarquías, sistemas de archivos, bases de datos |
| **Grafo** | Vértices y aristas (dirigido o no; matriz o lista de adyacencia) | BFS, DFS | Redes sociales, rutas |
| **Tabla hash** | Clave → valor mediante una función hash | Insertar, eliminar, buscar | Diccionarios, caches, índices |

**Criterios para elegir una estructura:** eficiencia en **tiempo**, eficiencia en **espacio**, complejidad del problema (relaciones y jerarquías) y **qué operaciones son más frecuentes**.

---

## 2. Listas enlazadas (`LinkedList`)

`java.util.LinkedList` implementa `List` como una **lista doblemente enlazada**: cada nodo tiene un valor y referencias al anterior y al siguiente.

**Operaciones:**
- Inserción: `add(e)`, `addFirst(e)`, `addLast(e)`, `add(i, e)`
- Eliminación: `removeFirst()`, `removeLast()`, `remove(i)`, `remove(obj)`
- Acceso: `get(i)`, `set(i, e)`
- Búsqueda: `contains(e)`

**Tipos:**
- **Simple:** cada nodo apunta solo al siguiente.
- **Doble:** apunta al siguiente y al anterior, así que se puede recorrer en los dos sentidos. Es la que implementa `LinkedList`.
- **Circular:** el último nodo apunta al primero.

**Cuándo usarla:** muchas inserciones y eliminaciones y **sin necesidad de acceso aleatorio**.
**Desventaja:** el acceso por índice es **secuencial**, más lento que en un array o `ArrayList`.

| Operación | `LinkedList` | `ArrayList` |
|---|---|---|
| `get(i)` | O(n) | O(1) |
| Insertar/eliminar al inicio | O(1) | O(n) |
| Insertar/eliminar al final | O(1) | O(1) amortizado |

*(Tabla de complejidades agregada como complemento; no está en el PDF.)*

---

## 3. Pilas (`Stack`)

**LIFO (Last In, First Out):** el último que entra es el primero que sale, como una pila de platos.

| Operación | Método en Java | Qué hace |
|---|---|---|
| Push | `push(e)` | Apila arriba |
| Pop | `pop()` | Saca y devuelve el de arriba |
| Peek/Top | `peek()` | Mira el de arriba sin sacarlo |
| Empty | `empty()` | ¿Está vacía? |

```java
Stack<Integer> pila = new Stack<>();
pila.push(1); pila.push(2); pila.push(3);
pila.peek(); // 3
pila.pop();  // 3 → quedan [1, 2]
```

**Usos:** historial ("deshacer"), evaluación de expresiones y compiladores, simular recursión de forma iterativa, DFS.
**Cuidado con:** *stack overflow* (desbordamiento) y *stack underflow* (hacer `pop` sobre una pila vacía).
`Stack` en Java está basada en `Vector`. Hoy se recomienda `Deque<Integer> pila = new ArrayDeque<>();` (complemento, no está en el PDF).

---

## 4. Colas (`Queue`)

**FIFO (First In, First Out):** el primero que entra es el primero que sale, como una fila de personas. Se agrega por el final y se saca por el frente.

| Operación | Método en Java |
|---|---|
| Enqueue | `offer(e)` / `add(e)` |
| Dequeue | `poll()` (devuelve `null` si está vacía) |
| Peek | `peek()` |
| IsEmpty / Size | `isEmpty()` / `size()` |

**Implementaciones:**
- `Queue<Integer> cola = new LinkedList<>();`: cola FIFO clásica, de uso general.
- `Queue<Integer> pq = new PriorityQueue<>();`: **cola de prioridad**. Saca siempre el **menor** elemento (orden natural), no el primero que entró. Internamente es un *heap*.

**Usos:** gestión de tareas por orden de llegada, colas de mensajes, control de flujo, **BFS** en grafos y árboles.

---

## 5. Tablas hash (`HashMap`)

Asocian **claves → valores**. Una **función hash** transforma la clave en un índice dentro de un array de *buckets*.

- **Complejidad promedio O(1)** para buscar, insertar y eliminar.
- En Java el hash de un objeto sale de `hashCode()`.

**Operaciones (`Map<K,V> m = new HashMap<>()`):** `put(k, v)`, `get(k)`, `remove(k)`, `containsKey(k)`, `size()`.

**Colisiones:** ocurren cuando dos claves distintas van al mismo índice.

1. **Encadenamiento (chaining):** cada bucket es una lista enlazada de entradas `(clave, valor)`. En el PDF, `HashTableWithChaining<K,V>` usa `LinkedList<Entry<K,V>>[]` y el índice se calcula con `Math.abs(key.hashCode() % table.length)`.
2. **Direccionamiento abierto:** si el lugar está ocupado, se busca otro libre:
   - **Lineal:** i+1, i+2, …
   - **Cuadrático:** i+1², i+2², …
   - **Doble hashing:** el salto lo define una segunda función hash.

**Usos:** diccionarios (p. ej. traducción `"gato" → "cat"`), caches, índices, tablas de símbolos en compiladores.

---

## 6. Algoritmos de ordenamiento

El PDF los implementa sobre `List<Integer>` / `ArrayList`.

### Selection Sort
En cada pasada busca el **mínimo** del resto y lo intercambia con la posición `i`.
```java
for (int i = 0; i < n - 1; i++) {
    int min = i;
    for (int j = i + 1; j < n; j++) if (list.get(j) < list.get(min)) min = j;
    swap(list, i, min);
}
```

### Insertion Sort
Toma cada elemento (`key`) y lo **inserta** en su lugar dentro de la parte ya ordenada, corriendo a la derecha los mayores. Es como ordenar cartas en la mano.

### Heapsort
Usa un **heap binario**. En el PDF se hace con `PriorityQueue`: se cargan todos los elementos y se sacan con `poll()` hasta vaciarla, y salen en orden ascendente.

### Quicksort (divide y vencerás)
Elige un **pivote** (en el PDF, el último elemento) y **particiona**: los menores quedan a la izquierda y los mayores a la derecha. Después ordena recursivamente cada lado.
```java
if (low < high) {
    int pi = partition(list, low, high);
    quickSort(list, low, pi - 1);
    quickSort(list, pi + 1, high);
}
```

### Mergesort (divide y vencerás)
**Divide** la lista a la mitad, ordena recursivamente cada mitad y las **mezcla** (*merge*) comparando elemento a elemento.

### Comparación
*(Complemento; el PDF no incluye complejidades.)*

| Algoritmo | Mejor | Promedio | Peor | Memoria extra | Estable |
|---|---|---|---|---|---|
| Selection | O(n²) | O(n²) | O(n²) | O(1) | No |
| Insertion | O(n) | O(n²) | O(n²) | O(1) | Sí |
| Heapsort | O(n log n) | O(n log n) | O(n log n) | O(1)* | No |
| Quicksort | O(n log n) | O(n log n) | O(n²) | O(log n) | No |
| Mergesort | O(n log n) | O(n log n) | O(n log n) | O(n) | Sí |

\* La versión del PDF con `PriorityQueue` usa O(n) extra.

---

## Errores detectados en el material

- **Listas "simples" con `LinkedList`:** el ejemplo de lista simple usa `java.util.LinkedList`, que en realidad es **doble**. Los ejemplos de lista simple y doble son idénticos.
- **Lista circular:** `lista.getLast().next = lista.getFirst();` **no compila**, porque `getLast()` devuelve un `Integer`, no un nodo. `LinkedList` no expone sus nodos; una lista circular hay que implementarla a mano con una clase `Nodo`.
