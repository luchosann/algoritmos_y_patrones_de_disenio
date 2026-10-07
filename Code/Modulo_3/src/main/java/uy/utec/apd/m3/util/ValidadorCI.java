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
        // TODO: Aplicar el algoritmo descrito en el javadoc de la clase
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Quita puntos, guiones y espacios y completa con ceros a la izquierda hasta 8 dígitos.
     * Devuelve null si el resultado no son 7 u 8 dígitos (antes de completar).
     * Ej.: "1.234.567-2" → "12345672"; "123.456-3" → "01234563"; "abc" → null.
     */
    public static String normalizar(String ci) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** true si la CI tiene formato válido y su dígito verificador es correcto. */
    public static boolean esValida(String ci) {
        // TODO: Normalizar y comparar el último dígito con el verificador calculado
        throw new UnsupportedOperationException("TODO");
    }
}
