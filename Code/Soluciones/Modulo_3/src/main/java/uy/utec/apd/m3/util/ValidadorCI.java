package uy.utec.apd.m3.util;

/**
 * Validación de cédulas de identidad uruguayas.
 *
 * <p>Formato aceptado: con o sin puntos y guion ("1.234.567-2", "1234567-2", "12345672").
 * Las cédulas viejas de 6 dígitos + verificador ("123.456-3") se completan con un 0 adelante.</p>
 *
 * <p>Dígito verificador: se multiplican los 7 dígitos por 2, 9, 8, 7, 6, 3, 4,
 * se suman, y el verificador es (10 - suma % 10) % 10.</p>
 */
public final class ValidadorCI {

    private static final int[] PESOS = {2, 9, 8, 7, 6, 3, 4};

    private ValidadorCI() {
    }

    /**
     * Calcula el dígito verificador de un número de 7 dígitos.
     *
     * @throws IllegalArgumentException si no son exactamente 7 dígitos
     */
    public static int digitoVerificador(String sieteDigitos) {
        // @sol Aplicar el algoritmo descrito en el javadoc de la clase
        if (sieteDigitos == null || !sieteDigitos.matches("\\d{7}")) {
            throw new IllegalArgumentException("Se esperaban 7 dígitos: " + sieteDigitos);
        }
        int suma = 0;
        for (int i = 0; i < 7; i++) {
            suma += (sieteDigitos.charAt(i) - '0') * PESOS[i];
        }
        return (10 - suma % 10) % 10;
        // @end
    }

    /**
     * Quita puntos, guiones y espacios y completa con ceros a la izquierda hasta 8 dígitos.
     * Devuelve null si el resultado no son 7 u 8 dígitos (antes de completar).
     * Ej.: "1.234.567-2" → "12345672"; "123.456-3" → "01234563"; "abc" → null.
     */
    public static String normalizar(String ci) {
        // @sol
        if (ci == null) {
            return null;
        }
        String limpia = ci.replaceAll("[.\\-\\s]", "");
        if (!limpia.matches("\\d{7,8}")) {
            return null;
        }
        return limpia.length() == 7 ? "0" + limpia : limpia;
        // @end
    }

    /** true si la CI tiene formato válido y su dígito verificador es correcto. */
    public static boolean esValida(String ci) {
        // @sol Normalizar y comparar el último dígito con el verificador calculado
        String normalizada = normalizar(ci);
        if (normalizada == null) {
            return false;
        }
        int esperado = digitoVerificador(normalizada.substring(0, 7));
        return normalizada.charAt(7) - '0' == esperado;
        // @end
    }
}
