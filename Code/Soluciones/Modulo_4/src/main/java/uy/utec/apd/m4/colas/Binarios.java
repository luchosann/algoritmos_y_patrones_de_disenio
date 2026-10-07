package uy.utec.apd.m4.colas;

import java.util.ArrayList;
import java.util.List;

public final class Binarios {

    private Binarios() {
    }

    /**
     * Los números del 1 al n en binario, generados con una COLA (sin Integer.toBinaryString):
     * encolar "1"; repetir n veces: desencolar s, agregarlo al resultado, encolar s+"0" y s+"1".
     * Ej. n=5 → ["1", "10", "11", "100", "101"].
     */
    public static List<String> generar(int n) {
        // @sol
        List<String> resultado = new ArrayList<>();
        Cola<String> cola = new Cola<>();
        cola.encolar("1");
        for (int i = 0; i < n; i++) {
            String s = cola.desencolar();
            resultado.add(s);
            cola.encolar(s + "0");
            cola.encolar(s + "1");
        }
        return resultado;
        // @end
    }
}
