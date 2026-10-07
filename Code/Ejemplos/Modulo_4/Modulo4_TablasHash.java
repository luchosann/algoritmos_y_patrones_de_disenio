/*
 * ============================================================================
 *  MÓDULO 4 — TABLAS HASH (HashMap) y manejo de colisiones
 * ============================================================================
 *  Teoría: Teorico/Modulo_4/Resumen_Modulo_4.md  (sección 5)
 *  Ejecutar:  java Modulo4_TablasHash.java
 *
 *  IDEA
 *  ----
 *  Asociar CLAVES → VALORES. Una FUNCIÓN HASH transforma la clave en un
 *  índice de un array de "buckets", así se llega al dato casi directo:
 *  O(1) PROMEDIO para buscar, insertar y eliminar.
 *
 *     "gato" ──hashCode()──▶ 3198433 ──% 8──▶ índice 1 ──▶ bucket[1]
 *
 *  COLISIONES: dos claves distintas caen en el mismo índice.
 *  1) ENCADENAMIENTO (chaining): cada bucket es una LISTA de entradas.
 *        [0] → null
 *        [1] → (gato,cat) → (sol,sun)      ← colisión resuelta con la lista
 *        [2] → (perro,dog)
 *  2) DIRECCIONAMIENTO ABIERTO: si el lugar está ocupado se busca otro:
 *        lineal: i+1, i+2, ...   cuadrático: i+1², i+2², ...   doble hashing.
 *
 *  FACTOR DE CARGA = elementos / buckets. Si crece mucho, hay más colisiones
 *  → se REDIMENSIONA (rehash). HashMap lo hace al superar 0.75.
 *
 *  ⚠ Si usás tus propios objetos como clave, sobrescribí equals() Y hashCode().
 * ============================================================================
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class Modulo4_TablasHash {

    // ======================================================================
    //  1) TABLA HASH CON ENCADENAMIENTO (como en el PDF)
    // ======================================================================
    static class HashTableWithChaining<K, V> {
        private static class Entry<K, V> {
            final K key;
            V value;
            Entry(K key, V value) { this.key = key; this.value = value; }
            @Override public String toString() { return "(" + key + "," + value + ")"; }
        }

        private LinkedList<Entry<K, V>>[] table;
        private int size;

        @SuppressWarnings("unchecked")
        HashTableWithChaining(int capacidad) {
            table = new LinkedList[capacidad];
        }

        /** Función hash → índice. abs porque hashCode() puede ser negativo. */
        private int indice(K key) {
            return Math.abs(key.hashCode() % table.length);
        }

        void put(K key, V value) {
            int i = indice(key);
            if (table[i] == null) table[i] = new LinkedList<>();
            for (Entry<K, V> e : table[i]) {
                if (e.key.equals(key)) { e.value = value; return; }   // clave existente → actualizo
            }
            table[i].add(new Entry<>(key, value));                      // nueva (posible colisión)
            size++;
            if ((double) size / table.length > 0.75) rehash();
        }

        V get(K key) {
            LinkedList<Entry<K, V>> bucket = table[indice(key)];
            if (bucket != null) {
                for (Entry<K, V> e : bucket) if (e.key.equals(key)) return e.value;
            }
            return null;
        }

        boolean remove(K key) {
            LinkedList<Entry<K, V>> bucket = table[indice(key)];
            if (bucket != null && bucket.removeIf(e -> e.key.equals(key))) { size--; return true; }
            return false;
        }

        /** Duplica la capacidad y reubica TODAS las entradas (su índice cambia). */
        @SuppressWarnings("unchecked")
        private void rehash() {
            LinkedList<Entry<K, V>>[] vieja = table;
            table = new LinkedList[vieja.length * 2];
            size = 0;
            for (LinkedList<Entry<K, V>> bucket : vieja) {
                if (bucket != null) for (Entry<K, V> e : bucket) put(e.key, e.value);
            }
            System.out.println("    (rehash: capacidad " + vieja.length + " → " + table.length + ")");
        }

        void imprimir() {
            for (int i = 0; i < table.length; i++) {
                System.out.println("    [" + i + "] → " + (table[i] == null || table[i].isEmpty() ? "∅" : table[i]));
            }
        }
    }

    // ======================================================================
    //  2) TABLA HASH CON DIRECCIONAMIENTO ABIERTO (sondeo LINEAL)
    // ======================================================================
    static class HashTableLinearProbing {
        private final String[] claves;
        private final Integer[] valores;

        HashTableLinearProbing(int capacidad) {
            claves = new String[capacidad];
            valores = new Integer[capacidad];
        }

        private int hash(String k) { return Math.abs(k.hashCode() % claves.length); }

        void put(String k, int v) {
            int i = hash(k);
            int intentos = 0;
            while (claves[i] != null && !claves[i].equals(k)) {   // ocupado por otra clave
                i = (i + 1) % claves.length;                       // → probar el siguiente
                if (++intentos == claves.length) throw new IllegalStateException("Tabla llena");
            }
            if (intentos > 0) System.out.println("    colisión con '" + k + "': se ubicó " + intentos + " lugar(es) más adelante");
            claves[i] = k;
            valores[i] = v;
        }

        Integer get(String k) {
            int i = hash(k);
            for (int n = 0; n < claves.length && claves[i] != null; n++) {
                if (claves[i].equals(k)) return valores[i];
                i = (i + 1) % claves.length;
            }
            return null;
        }
    }

    // ======================================================================
    //  equals + hashCode en una clave propia
    // ======================================================================
    static final class Coordenada {
        final int x, y;
        Coordenada(int x, int y) { this.x = x; this.y = y; }

        @Override public boolean equals(Object o) {
            return o instanceof Coordenada c && c.x == x && c.y == y;
        }
        @Override public int hashCode() { return Objects.hash(x, y); }   // si equals es true, hashCode DEBE coincidir
    }

    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== HashMap: diccionario español → inglés ===");
        Map<String, String> dic = new HashMap<>();
        dic.put("gato", "cat");
        dic.put("perro", "dog");
        dic.put("sol", "sun");
        System.out.println("  get(\"gato\") = " + dic.get("gato"));
        System.out.println("  containsKey(\"luna\") = " + dic.containsKey("luna"));
        System.out.println("  getOrDefault(\"luna\", \"?\") = " + dic.getOrDefault("luna", "?"));
        dic.put("gato", "kitty");                    // misma clave → se reemplaza el valor
        dic.remove("perro");
        System.out.println("  " + dic + "  size=" + dic.size() + "  (el orden NO está garantizado)");

        System.out.println("\n=== Uso clásico: contar frecuencias en O(n) ===");
        String texto = "el perro y el gato y el loro";
        Map<String, Integer> frecuencia = new HashMap<>();
        for (String palabra : texto.split(" ")) frecuencia.merge(palabra, 1, Integer::sum);
        System.out.println("  " + frecuencia);

        System.out.println("\n=== Tabla propia con ENCADENAMIENTO (capacidad 4) ===");
        HashTableWithChaining<String, String> t = new HashTableWithChaining<>(4);
        t.put("gato", "cat");
        t.put("perro", "dog");
        t.put("sol", "sun");
        t.imprimir();
        t.put("luna", "moon");                       // supera 0.75 → rehash
        t.imprimir();
        System.out.println("  get(\"sol\") = " + t.get("sol") + ", remove(\"sol\") = " + t.remove("sol") + ", get(\"sol\") = " + t.get("sol"));

        System.out.println("\n=== Tabla propia con SONDEO LINEAL (capacidad 5) ===");
        HashTableLinearProbing lp = new HashTableLinearProbing(5);
        for (String k : new String[]{"a", "f", "k", "b"}) lp.put(k, k.charAt(0) - 'a');  // "a","f","k" colisionan con % 5
        System.out.println("  get(\"k\") = " + lp.get("k") + ", get(\"z\") = " + lp.get("z"));

        System.out.println("\n=== Claves propias: equals + hashCode ===");
        Map<Coordenada, String> mapa = new HashMap<>();
        mapa.put(new Coordenada(1, 2), "tesoro");
        System.out.println("  get(new Coordenada(1,2)) = " + mapa.get(new Coordenada(1, 2))
                + "   (sin hashCode() propio daría null)");
    }
}
