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
    // TODO: Las dos pilas (sugerencia: guardar el texto completo antes de cada acción)

    public void escribir(String agregado) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Borra los últimos n caracteres (o todos si hay menos de n). */
    public void borrar(int n) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** @return false si no había nada para deshacer */
    public boolean deshacer() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** @return false si no había nada para rehacer */
    public boolean rehacer() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public String getTexto() {
        return texto;
    }

    // TODO
}
