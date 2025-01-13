package Cine;

import java.time.LocalTime;

public class Peliculas {
    private String nombre;
    private String genero;
    private int duracion;
    private String formato;
    private String dia;


    public Peliculas(String nombre, String formato, String genero, int duracion, String dia) {
        this.nombre = nombre;
        this.formato = formato;
        this.genero = genero;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getFormato() {
        return formato;
    }

    public String getDia() {
        return dia;
    }

    public LocalTime calculardisponible(String horaInicio) {
        try {
            LocalTime inicio = LocalTime.parse(horaInicio);
            return inicio.plusMinutes(duracion+20);
        } catch (Exception e) {
            System.out.println("Error: Formato de hora inválido. Use HH:MM ");
            return null;
        }
    }
    public static void main(String[] args) {
        Peliculas pelicula = new Peliculas("Inception", "Ciencia Ficción", " ",120," ");
        String horaInicio = "20:15";
        LocalTime horaFinal = pelicula.calculardisponible(horaInicio);
        if (horaFinal != null) {
            System.out.println("La película '" + pelicula.getNombre() + "' terminará a las: " + horaFinal);
        }
    }
}
