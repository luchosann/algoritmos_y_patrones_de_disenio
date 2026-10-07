package uy.utec.apd.m4.pilas;

/**
 * Aplicaciones clásicas de pilas. Usar {@link Pila} (la tuya, no java.util.Stack).
 * Los tokens de las expresiones vienen separados por UN espacio.
 */
public final class Expresiones {

    private Expresiones() {
    }

    /**
     * true si los paréntesis (), corchetes [] y llaves {} están balanceados y bien anidados.
     * Los demás caracteres se ignoran. Ej.: "{[()()]}" → true, "([)]" → false, "((" → false.
     */
    public static boolean estaBalanceado(String texto) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Evalúa una expresión en notación POSTFIJA con enteros y + - * / (división entera).
     * Ej.: "3 4 + 2 *" → 14; "5 1 2 + 4 * + 3 -" → 14.
     *
     * @throws IllegalArgumentException si la expresión está mal formada
     *                                  (faltan operandos, sobran operandos o hay un token inválido)
     */
    public static int evaluarPostfija(String expresion) {
        // TODO: Recorrer tokens: número → apilar; operador → desapilar b, luego a, apilar a op b
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * DESAFÍO — Convierte infija a postfija (algoritmo shunting-yard de Dijkstra).
     * Soporta + - * / con la precedencia usual, asociatividad izquierda y paréntesis.
     * Ej.: "3 + 4 * 2" → "3 4 2 * +"; "( 3 + 4 ) * 2" → "3 4 + 2 *"; "10 - 4 - 3" → "10 4 - 3 -".
     */
    public static String infijaAPostfija(String expresion) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Evalúa una expresión infija combinando los dos métodos anteriores. */
    public static int evaluar(String infija) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    // TODO: Métodos auxiliares sugeridos: apertura(char), esOperador(String), precedencia(String), aplicar(...)
}
