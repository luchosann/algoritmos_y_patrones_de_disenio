package uy.utec.apd.m1.prototype;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Registro de prototipos: guarda personajes "plantilla" y entrega COPIAS PROFUNDAS.
 */
public class RegistroPersonajes {

    private final Map<String, Personaje> prototipos = new HashMap<>();

    public void registrar(String clave, Personaje prototipo) {
        // @sol Guardar el prototipo bajo la clave
        prototipos.put(clave, prototipo);
        // @end
    }

    /**
     * Devuelve una copia profunda del prototipo registrado.
     *
     * @throws IllegalArgumentException si no hay prototipo con esa clave
     */
    public Personaje crear(String clave) {
        // @sol Buscar el prototipo y devolver una copia profunda
        Personaje prototipo = prototipos.get(clave);
        if (prototipo == null) {
            throw new IllegalArgumentException("No existe el prototipo: " + clave);
        }
        return prototipo.copiaProfunda();
        // @end
    }

    public Set<String> claves() {
        return Set.copyOf(prototipos.keySet());
    }
}
