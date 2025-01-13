package Cine;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class FechaEstreno {

    public static void main(String[] args) {
        viernesqueviene();

        boolean fechafinal = viernesfinal();
        if (fechafinal) {
            System.out.println("¡Es viernes a las 6 p.m.!");
        } else {
            System.out.println("No es viernes a las 6 p.m.");
        }
    }

    public static void viernesqueviene() {
        LocalDateTime ahora = LocalDateTime.now();

        LocalDateTime proximoViernes = ahora.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY))
                .withHour(17)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        // Si ya pasó el viernes a las 5 p.m., buscar el siguiente viernes
        if (ahora.isAfter(proximoViernes)) {
            proximoViernes = proximoViernes.plusWeeks(1);
        }

        // Formatear la fecha y hora para imprimirla
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy 'a las' hh:mm a");
        System.out.println("El próximo viernes a las 5 p.m. será: " + proximoViernes.format(formato));
    }

    public static boolean viernesfinal() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime viernesfinal = ahora.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY))
                .withHour(18)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        return ahora.getDayOfWeek() == DayOfWeek.FRIDAY &&
                ahora.getHour() == 18 &&
                ahora.getMinute() == 0;
    }
}



