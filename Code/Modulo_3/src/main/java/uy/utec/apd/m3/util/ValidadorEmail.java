package uy.utec.apd.m3.util;

import java.util.regex.Pattern;

/** Validación simple de email (ya implementada). */
public final class ValidadorEmail {

    private static final Pattern PATRON = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");

    private ValidadorEmail() {
    }

    public static boolean esValido(String email) {
        return email != null && PATRON.matcher(email).matches();
    }
}
