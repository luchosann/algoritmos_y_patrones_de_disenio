package uy.utec.apd.m3.consola;

import uy.utec.apd.m3.fachada.GestionAcademicaFacade;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new MenuConsola(new Scanner(System.in), System.out, GestionAcademicaFacade.enMemoria()).ejecutar();
    }
}
