package Cine;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.FileWriter;

public class Sala {
    private final int FILAS = 4;
    private final int COLUMNAS = 4;
    private String[][] asientos;
    private Peliculas pelicula;
    private User user;
    private FechaEstreno fechaestreno;
    private int libres;
    private int ocupados;

    public Sala(Peliculas pelicula) {
        this.asientos = new String[FILAS][COLUMNAS];
        this.libres = FILAS * COLUMNAS;
        this.ocupados = 0;
        this.pelicula = pelicula;
        this.user = user;
        this.fechaestreno = fechaestreno;

        for (int fila = 0; fila < FILAS; fila++) {
            char letra = (char) ('A' + fila);
            for (int columna = 0; columna < COLUMNAS; columna++) {
                asientos[fila][columna] = letra + String.valueOf(columna + 1);
            }
        }

        cargarAsientosDesdeArchivo();
    }

    private void cargarAsientosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("asientos_ocupados.txt"))) {
            String asiento;
            while ((asiento = reader.readLine()) != null) {
                marcarAsientoOcupado(asiento.trim()); // Eliminar espacios antes y después
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de asientos ocupados.");
        }
    }

    private void liberarAsientos() {
        // Verificar si es viernes a las 6 p.m. para liberar los asientos
        if (fechaestreno.viernesfinal()) {
            try {
                // Borrar el archivo que contiene los asientos ocupados
                Files.deleteIfExists(Paths.get("asientos_ocupados.txt"));
                System.out.println("Se han liberado todos los asientos.");

                // Reiniciar la matriz de asientos a su estado original
                for (int fila = 0; fila < FILAS; fila++) {
                    char letra = (char) ('A' + fila);
                    for (int columna = 0; columna < COLUMNAS; columna++) {
                        asientos[fila][columna] = letra + String.valueOf(columna + 1);
                    }
                }
                libres = FILAS * COLUMNAS;  // Restaurar asientos libres
                ocupados = 0;  // Restablecer asientos ocupados
            } catch (IOException e) {
                System.out.println("Error al liberar los asientos.");
            }
        } else {
            // Si no es viernes a las 6 p.m., no se realiza ninguna acción
            System.out.println("No es viernes a las 6 p.m. No se liberan los asientos.");
        }
    }


    private void marcarAsientoOcupado(String asiento) {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (asientos[fila][columna].equals(asiento)) {
                    asientos[fila][columna] = "  X  ";
                    ocupados++;
                    libres--;
                    break;
                }
            }
        }
    }

    public void comprarBoletos(User user) {
        this.user = user;
        Scanner scanner = new Scanner(System.in);

        System.out.print("¿Cuántos boletos deseas comprar? ");
        int cantidadBoletos = scanner.nextInt();

        if (cantidadBoletos > libres) {
            System.out.println("No hay suficientes asientos disponibles. Intenta nuevamente.");
            return;
        }

        for (int i = 0; i < cantidadBoletos; i++) {
            mostrarEstadoAsientos();
            System.out.print("Selecciona el asiento " + (i + 1) + " (ejemplo: A1): ");
            String asiento = scanner.next().toUpperCase();

            boolean encontrado = false;
            for (int fila = 0; fila < FILAS; fila++) {
                for (int columna = 0; columna < COLUMNAS; columna++) {
                    if (asientos[fila][columna].equals(asiento)) {
                        asientos[fila][columna] = "  X  ";
                        ocupados++;
                        libres--;
                        encontrado = true;
                        System.out.println("Asiento " + asiento + " marcado como ocupado.");
                        guardarAsientosEnArchivo(asiento);
                        guardarReservas(asiento);
                        break;
                    }
                }
                if (encontrado) break;
            }

            if (!encontrado) {
                System.out.println("Asiento no válido o ya ocupado. Intenta nuevamente.");
                i--;
            }
        }
        System.out.println("Compra realizada con éxito.");
    }

    public void mostrarEstadoAsientos() {
        System.out.println("\nInformación de la película:");
        System.out.println("Nombre: " + pelicula.getNombre());
        System.out.println("Formato: " + pelicula.getFormato());
        System.out.println("Género: " + pelicula.getGenero());
        System.out.println("Duración: " + pelicula.getDuracion() + " minutos");
        System.out.println("Día: " + pelicula.getDia());

        System.out.println("\nEstado de los asientos ( X = ocupado, [A1] = disponible):");
        System.out.println(" \n");

        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (!asientos[fila][columna].equals("  X  ")) {
                    System.out.print(" [" + asientos[fila][columna] + "]  ");
                } else {
                    System.out.print("  X  \t");
                }
            }
            System.out.println("\n [   MESA   ]  [   MESA   ]");
            System.out.println();
        }
        System.out.println("__________________________");
        System.out.println("         Pantalla          ");
        mostrarDisponibilidad();
    }

    public void mostrarDisponibilidad() {
        System.out.println("\nResumen de disponibilidad:");
        System.out.println("Asientos disponibles: " + libres);
        System.out.println("Asientos ocupados: " + ocupados);
    }

    private void guardarAsientosEnArchivo(String asiento) {
        try (FileWriter writer = new FileWriter("asientos_ocupados.txt", true)) {
            writer.write(asiento + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar los asientos en el archivo.");
        }
    }
    private void guardarReservas(String asiento) {
        try (FileWriter writer = new FileWriter("asientosreservas.txt", true)) {
            writer.write("Asiento: " + asiento + " | Apellido: " + user.getApellido() +
                    " | Nombre: " + user.getNombre() + " | Cédula: " + user.getCedula() + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar los asientos en el archivo.");
        }
    }

    public static void main(String[] args) {
        Peliculas pelicula = new Peliculas("Inception", "2D", "Ciencia Ficción", 148, "09/01/2025");
        User user1 = new User("Vasquez", "Luis", "100021202");
        Sala sala = new Sala(pelicula);
        sala.mostrarEstadoAsientos();
        sala.comprarBoletos(user1);
        sala.mostrarEstadoAsientos();
    }
}
