package uy.utec.apd.m5.navegable;

import java.time.LocalTime;

/** Reserva de la sala en [inicio, fin): el fin NO está incluido. */
public record Reserva(LocalTime inicio, LocalTime fin, String titular) {
}
