package uy.utec.apd.m2.adapter;

/**
 * Librería de terceros (importada). NO SE PUEDE MODIFICAR.
 * Mide en Kelvin y devuelve float.
 */
public class SensorKelvinImportado {

    private final String serial;
    private final float kelvin;

    public SensorKelvinImportado(String serial, float kelvin) {
        this.serial = serial;
        this.kelvin = kelvin;
    }

    public String serialNumber() {
        return serial;
    }

    public float getKelvinValue() {
        return kelvin;
    }
}
