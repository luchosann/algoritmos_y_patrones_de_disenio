package uy.utec.apd.m2.adapter;

/**
 * Clase LEGACY de un proveedor viejo. NO SE PUEDE MODIFICAR.
 * Mide en grados Fahrenheit y tiene una interfaz distinta a la que usa nuestro sistema.
 */
public class TermometroFahrenheitLegacy {

    private final String codigo;
    private double fahrenheit;

    public TermometroFahrenheitLegacy(String codigo, double fahrenheit) {
        this.codigo = codigo;
        this.fahrenheit = fahrenheit;
    }

    public String getCodigo() {
        return codigo;
    }

    public double leerFahrenheit() {
        return fahrenheit;
    }

    public void setFahrenheit(double fahrenheit) {
        this.fahrenheit = fahrenheit;
    }
}
