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
        // @sol
        Pila<Character> pila = new Pila<>();
        for (char c : texto.toCharArray()) {
            switch (c) {
                case '(', '[', '{' -> pila.apilar(c);
                case ')', ']', '}' -> {
                    if (pila.estaVacia() || pila.desapilar() != apertura(c)) {
                        return false;
                    }
                }
                default -> {
                }
            }
        }
        return pila.estaVacia();
        // @end
    }

    /**
     * Evalúa una expresión en notación POSTFIJA con enteros y + - * / (división entera).
     * Ej.: "3 4 + 2 *" → 14; "5 1 2 + 4 * + 3 -" → 14.
     *
     * @throws IllegalArgumentException si la expresión está mal formada
     *                                  (faltan operandos, sobran operandos o hay un token inválido)
     */
    public static int evaluarPostfija(String expresion) {
        // @sol Recorrer tokens: número → apilar; operador → desapilar b, luego a, apilar a op b
        Pila<Integer> pila = new Pila<>();
        for (String token : expresion.trim().split(" ")) {
            if (esOperador(token)) {
                if (pila.tamanio() < 2) {
                    throw new IllegalArgumentException("Faltan operandos para " + token);
                }
                int b = pila.desapilar();
                int a = pila.desapilar();
                pila.apilar(aplicar(token, a, b));
            } else {
                try {
                    pila.apilar(Integer.parseInt(token));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Token inválido: " + token);
                }
            }
        }
        if (pila.tamanio() != 1) {
            throw new IllegalArgumentException("Expresión mal formada: " + expresion);
        }
        return pila.desapilar();
        // @end
    }

    /**
     * DESAFÍO — Convierte infija a postfija (algoritmo shunting-yard de Dijkstra).
     * Soporta + - * / con la precedencia usual, asociatividad izquierda y paréntesis.
     * Ej.: "3 + 4 * 2" → "3 4 2 * +"; "( 3 + 4 ) * 2" → "3 4 + 2 *"; "10 - 4 - 3" → "10 4 - 3 -".
     */
    public static String infijaAPostfija(String expresion) {
        // @sol
        StringBuilder salida = new StringBuilder();
        Pila<String> operadores = new Pila<>();
        for (String token : expresion.trim().split(" ")) {
            if (esOperador(token)) {
                while (!operadores.estaVacia() && esOperador(operadores.cima())
                        && precedencia(operadores.cima()) >= precedencia(token)) {
                    salida.append(operadores.desapilar()).append(' ');
                }
                operadores.apilar(token);
            } else if (token.equals("(")) {
                operadores.apilar(token);
            } else if (token.equals(")")) {
                while (!operadores.estaVacia() && !operadores.cima().equals("(")) {
                    salida.append(operadores.desapilar()).append(' ');
                }
                if (operadores.estaVacia()) {
                    throw new IllegalArgumentException("Paréntesis desbalanceados");
                }
                operadores.desapilar();
            } else {
                salida.append(token).append(' ');
            }
        }
        while (!operadores.estaVacia()) {
            String op = operadores.desapilar();
            if (op.equals("(")) {
                throw new IllegalArgumentException("Paréntesis desbalanceados");
            }
            salida.append(op).append(' ');
        }
        return salida.toString().trim();
        // @end
    }

    /** Evalúa una expresión infija combinando los dos métodos anteriores. */
    public static int evaluar(String infija) {
        // @sol
        return evaluarPostfija(infijaAPostfija(infija));
        // @end
    }

    // @sol-blank Métodos auxiliares sugeridos: apertura(char), esOperador(String), precedencia(String), aplicar(...)
    private static char apertura(char cierre) {
        return switch (cierre) {
            case ')' -> '(';
            case ']' -> '[';
            default -> '{';
        };
    }

    private static boolean esOperador(String token) {
        return token.length() == 1 && "+-*/".contains(token);
    }

    private static int precedencia(String operador) {
        return operador.equals("+") || operador.equals("-") ? 1 : 2;
    }

    private static int aplicar(String operador, int a, int b) {
        return switch (operador) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            default -> a / b;
        };
    }
    // @end
}
