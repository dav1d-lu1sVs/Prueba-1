package Cine;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class FechaEstreno {

        public static String viernesqueviene() {
            LocalDateTime hoy = LocalDateTime.now();
            LocalDateTime proximoViernes = hoy.plusDays(1);
            while (proximoViernes.getDayOfWeek() != DayOfWeek.FRIDAY) {
                proximoViernes = proximoViernes.plusDays(1);
            }
            proximoViernes = proximoViernes.withHour(17).withMinute(0).withSecond(0);

            DateTimeFormatter formato = DateTimeFormatter.ofPattern(" dd 'de' MMMM 'de' yyyy 'a las' hh:mm a");
            return "|♣ La siguiente funcion sera el viernes " + proximoViernes.format(formato)+"|";
        }

    public static boolean viernesfinal() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime viernesfinal = ahora.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY))
                .withHour(18)
                .withMinute(0)
                .withSecond(0);

        return ahora.equals(viernesfinal);
    }

}




