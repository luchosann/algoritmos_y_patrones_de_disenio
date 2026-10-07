package uy.utec.apd.m4.pilas;

/**
 * Editor con deshacer / rehacer usando DOS pilas.
 * <ul>
 *   <li>Cada acción (escribir, borrar) se puede deshacer.</li>
 *   <li>Lo deshecho se puede rehacer, hasta que se haga una acción NUEVA
 *       (en ese momento se pierde el historial de rehacer).</li>
 * </ul>
 */
public class EditorTexto {

    private String texto = "";
    // @sol-blank Las dos pilas (sugerencia: guardar el texto completo antes de cada acción)
    private final Pila<String> deshacer = new Pila<>();
    private final Pila<String> rehacer = new Pila<>();
    // @end

    public void escribir(String agregado) {
        // @sol
        registrarAccion();
        texto = texto + agregado;
        // @end
    }

    /** Borra los últimos n caracteres (o todos si hay menos de n). */
    public void borrar(int n) {
        // @sol
        registrarAccion();
        texto = texto.substring(0, Math.max(0, texto.length() - n));
        // @end
    }

    /** @return false si no había nada para deshacer */
    public boolean deshacer() {
        // @sol
        if (deshacer.estaVacia()) {
            return false;
        }
        rehacer.apilar(texto);
        texto = deshacer.desapilar();
        return true;
        // @end
    }

    /** @return false si no había nada para rehacer */
    public boolean rehacer() {
        // @sol
        if (rehacer.estaVacia()) {
            return false;
        }
        deshacer.apilar(texto);
        texto = rehacer.desapilar();
        return true;
        // @end
    }

    public String getTexto() {
        return texto;
    }

    // @sol-blank
    private void registrarAccion() {
        deshacer.apilar(texto);
        while (!rehacer.estaVacia()) {
            rehacer.desapilar();
        }
    }
    // @end
}
