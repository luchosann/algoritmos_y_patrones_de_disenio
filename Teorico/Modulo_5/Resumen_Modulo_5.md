# Módulo 5 — Algoritmos avanzados

**Archivos:** 7. Arboles · 8. Recorridos de árboles · 6. Conocimientos Adicionales

> Según el README, los **conocimientos adicionales** (búsqueda binaria, bits, operaciones bit a bit) **no se evalúan**.

---

## 1. Árboles

Un **árbol** es una estructura **jerárquica** de nodos conectados por aristas.
- **Raíz:** el nodo superior.
- **Hijos:** los nodos que cuelgan de otro nodo; cada nodo puede tener cero o más.
- **Hojas:** los nodos sin hijos.

Se usan en bases de datos, sistemas de archivos y estructuras de búsqueda.

### 1.1 Árbol binario
Cada nodo tiene **como máximo dos hijos**: izquierdo y derecho.

```java
class Nodo {
    int valor;
    Nodo izquierdo, derecho;
    Nodo(int v) { valor = v; }
}
```

### 1.2 Árbol binario de búsqueda (BST)
Árbol binario ordenado. Para **cada nodo**:
- todo su **subárbol izquierdo** tiene valores **menores**;
- todo su **subárbol derecho** tiene valores **mayores**.

**Operaciones (recursivas):**

- **Insertar:** si el valor es menor se baja por la izquierda y si es mayor por la derecha, hasta encontrar un lugar vacío (`null`) donde se crea el nodo.
- **Buscar:** mismas comparaciones. Si se llega a `null` el valor no está; si coincide, sí está.
- **Eliminar:** hay tres casos:
  1. **Sin hijos:** se elimina directamente.
  2. **Un hijo:** se reemplaza el nodo por su hijo.
  3. **Dos hijos:** se reemplaza el valor por el del **sucesor in-order** (el **mínimo del subárbol derecho**) y después se elimina ese sucesor.

```java
NodoBST eliminar(NodoBST raiz, int valor) {
    if (raiz == null) return null;
    if (valor < raiz.valor) raiz.izquierdo = eliminar(raiz.izquierdo, valor);
    else if (valor > raiz.valor) raiz.derecho = eliminar(raiz.derecho, valor);
    else {
        if (raiz.izquierdo == null) return raiz.derecho;
        if (raiz.derecho == null) return raiz.izquierdo;
        raiz.valor = valorMinimo(raiz.derecho);
        raiz.derecho = eliminar(raiz.derecho, raiz.valor);
    }
    return raiz;
}
```

**Complejidad:** O(h), donde *h* es la altura del árbol. Si el árbol está balanceado es **O(log n)**; si está degenerado (por ejemplo, al insertar datos ya ordenados) es **O(n)**.

### 1.3 Árboles balanceados: AVL
Un árbol está **balanceado** si en todo nodo las alturas de los subárboles izquierdo y derecho difieren **como máximo en 1**. Así se garantiza O(log n) incluso en el peor caso.

**AVL (Adelson-Velsky y Landis):** es un BST que se re-balancea solo después de cada inserción.
- Cada nodo guarda su `altura`.
- **Factor de balance** = `altura(izq) − altura(der)`. Si es mayor que 1 o menor que −1, el nodo está desbalanceado.
- Se corrige con **rotaciones**:

| Caso | Condición | Solución |
|---|---|---|
| **Izquierda-Izquierda** | balance > 1 y valor < `izq.valor` | Rotación **derecha** |
| **Derecha-Derecha** | balance < −1 y valor > `der.valor` | Rotación **izquierda** |
| **Izquierda-Derecha** | balance > 1 y valor > `izq.valor` | Rotación izquierda sobre el hijo izquierdo y después rotación derecha |
| **Derecha-Izquierda** | balance < −1 y valor < `der.valor` | Rotación derecha sobre el hijo derecho y después rotación izquierda |

Otro tipo de árbol balanceado mencionado: **rojo-negro**.

### 1.4 Árboles en la API de Java
Basados en un BST balanceado (rojo-negro), con operaciones en **O(log n)** y elementos **siempre ordenados**:

- **`TreeSet<E>`:** un conjunto ordenado. Métodos: `add`, `remove`, `contains`, `first()`, `last()`.
- **`TreeMap<K,V>`:** un mapa ordenado por clave. Métodos: `put`, `get`, `remove`, `firstEntry()`, `lastEntry()`.
- **`NavigableSet` / `NavigableMap`** (las implementan los anteriores) agregan métodos de navegación:

| Método | Devuelve |
|---|---|
| `lower(x)` | El mayor elemento **< x** |
| `floor(x)` | El mayor elemento **≤ x** |
| `ceiling(x)` | El menor elemento **≥ x** |
| `higher(x)` | El menor elemento **> x** |

En los mapas existen las versiones `lowerEntry`, `floorEntry`, `ceilingEntry` y `higherEntry`.

---

## 2. Recorridos de árboles

Árbol de ejemplo:
```
        1
       / \
      2   3
     / \
    4   5
```

### Recorridos en profundidad (DFS)

| Recorrido | Orden | Resultado del ejemplo | Truco |
|---|---|---|---|
| **Preorden** | **Raíz** → Izq → Der | `1 2 4 5 3` | La raíz va *pre* (antes) |
| **Inorden** | Izq → **Raíz** → Der | `4 2 5 1 3` | En un **BST** devuelve los valores **ordenados** |
| **Posorden** | Izq → Der → **Raíz** | `4 5 2 3 1` | La raíz va *pos* (después); sirve para borrar un árbol |

```java
void inorder(TreeNode n) {
    if (n == null) return;
    inorder(n.left);
    System.out.print(n.value + " ");
    inorder(n.right);
}
```

**DFS iterativo con una pila:** se apila la raíz. Mientras la pila no esté vacía, se hace `pop`, se visita el nodo y se apilan el hijo **derecho y después el izquierdo**, para que el izquierdo salga primero. El resultado es igual al preorden: `1 2 4 5 3`.

### Recorrido en anchura (BFS / por niveles)
Visita **nivel por nivel** usando una **cola**: se encola la raíz y, mientras la cola no esté vacía, se hace `poll`, se visita el nodo y se encolan sus hijos izquierdo y derecho.
Resultado del ejemplo: `1 2 3 4 5`.

> **Regla mnemotécnica:** **DFS usa una pila** (o recursión) y **BFS usa una cola**.

---

## 3. Conocimientos adicionales (no evaluados)

### 3.1 Búsqueda binaria
Busca un elemento en un arreglo **ordenado** partiendo el rango a la mitad en cada paso. Complejidad **O(log n)**.

```java
int low = 0, high = a.length - 1;
while (low <= high) {
    int mid = low + (high - low) / 2; // evita overflow
    if (a[mid] == clave) return mid;
    else if (a[mid] < clave) low = mid + 1;
    else high = mid - 1;
}
return -1;
```

### 3.2 Bits y sistema binario
- **Bit:** la unidad mínima de información, que vale 0 o 1.
- En binario, cada posición es una potencia de 2: 13 = 8 + 4 + 1 = `1101`.
- **Decimal → binario:** se divide sucesivamente por 2 y se leen los **restos de abajo hacia arriba**. Por ejemplo, 19 da `10011`, 7 da `111` y 25 da `11001`.
- **Binario → decimal:** se suma cada dígito multiplicado por su potencia de 2. Por ejemplo, `1010` = 8 + 2 = 10.

### 3.3 Operaciones bit a bit (Java)
Con `a = 5` (`0101`) y `b = 3` (`0011`):

| Operador | Nombre | Ejemplo | Resultado |
|---|---|---|---|
| `&` | AND | `5 & 3` | `0001` = 1 |
| `\|` | OR | `5 \| 3` | `0111` = 7 |
| `^` | XOR | `5 ^ 3` | `0110` = 6 |
| `~` | NOT | `~5` | −6 (complemento a 2) |
| `<<` | Desplazamiento a la izquierda | `5 << 2` | 20 (equivale a × 2²) |
| `>>` | Desplazamiento a la derecha con signo | `5 >> 1` | 2 (equivale a ÷ 2) |
| `>>>` | Desplazamiento a la derecha sin signo | `5 >>> 1` | 2 |

**Relación con las estructuras de datos:** las operaciones de bits se usan para calcular funciones hash (por ejemplo, `HashMap` usa `hash & (n-1)` como índice) y para optimizaciones de bajo nivel.

---

## Errores detectados en el material

- **Recorridos, ejemplo completo:** usa `Queue`, `LinkedList` y `Stack` sin sus `import java.util.*`.
- **Búsqueda binaria:** en el PDF los pasos 5 y 6 están cortados. Deberían decir `high = mid - 1` y `low = mid + 1`.
