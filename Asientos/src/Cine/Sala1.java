package Cine;

import java.util.Scanner;

public class Sala1 {
    private final int FILAS = 7;
    private final int COLUMNAS = 7;
    private String[][] asientos;
    private int libres;
    private int ocupados;
    private String numeracionsala;
    private Peliculas pelicula; // Asociación con Peliculas

    public Sala1(Peliculas pelicula) {
        this.asientos = new String[FILAS][COLUMNAS];
        this.libres = FILAS * COLUMNAS;
        this.ocupados = 0;
        this.pelicula = pelicula;
        this.numeracionsala = numeracionsala;
        for (int fila = 0; fila < FILAS; fila++) {
            char letra = (char) ('A' + fila);
            for (int columna = 0; columna < COLUMNAS; columna++) {
                asientos[fila][columna] = letra + String.valueOf(columna + 1);
            }
        }
    }

    public String getNumeracionsala() {
        return numeracionsala = "Sala1";
    }

    public void registrarAsientoOcupado() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa el asiento que deseas marcar como ocupado (ejemplo: A1): ");
        String asiento = scanner.next().toUpperCase();

        boolean encontrado = false;
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (asientos[fila][columna].equals(asiento)) {
                    asientos[fila][columna] = " X";
                    ocupados++;
                    libres--;
                    encontrado = true;
                    System.out.println("Asiento " + asiento + " marcado como ocupado.");
                    break;
                }
            }
            if (encontrado) break;
        }

        if (!encontrado) {
            System.out.println("Asiento no válido o ya ocupado. Intenta nuevamente.");
        }
    }

    public void mostrarEstadoAsientos() {
        System.out.println("\nInformación de la película:");
        System.out.println("Nombre: " + pelicula.getNombre());
        System.out.println("Formato: "+pelicula.getFormato());
        System.out.println("Género: " + pelicula.getGenero());
        System.out.println("Duración: " + pelicula.getDuracion() + " minutos");
        System.out.println("Dia: "+pelicula.getDia());
        System.out.println(getNumeracionsala());

        System.out.println("\nEstado de los asientos ( X = ocupado, [A1] = disponible):");

        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (!asientos[fila][columna].equals("X")) {
                    System.out.print("[" + asientos[fila][columna] + "]\t");
                } else {
                    System.out.print("X\t");
                }
            }
            System.out.println();
        }
        System.out.println("__________________________________________________________");
        System.out.println("                      Pantalla                            ");
        mostrarDisponibilidad();
    }

    public void mostrarDisponibilidad() {
        System.out.println("\nResumen de disponibilidad:");
        System.out.println("Asientos disponibles: " + libres);
        System.out.println("Asientos ocupados: " + ocupados);
    }

    public static void main(String[] args) {
        Peliculas pelicula = new Peliculas("Inception", "2D","Ciencia Ficción", 148,"0");
        Sala1 sala = new Sala1(pelicula);
        sala.mostrarEstadoAsientos();
        sala.registrarAsientoOcupado();
        sala.mostrarEstadoAsientos();
    }
}
