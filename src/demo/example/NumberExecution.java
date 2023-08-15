package demo.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NumberExecution {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Calendar calndr1 = Calendar.getInstance();
        for (int hour = 0; hour < 24; hour++) {
            calndr1.set(Calendar.HOUR_OF_DAY, hour);
            Date dt = calndr1.getTime();
            long t = dt.getTime();
            long normal1 = normalize(t);

            System.out.println("Hour " + hour + ": " + t + "---->" + normal1 + "---->" + normalize(normal1) + ")");
        }

        String input = "20230522163252Z";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssX");
        LocalDateTime localDate = LocalDateTime.parse(input, formatter);
        System.out.println(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private static long normalize(long value) {
        return value - (value + TimeUnit.HOURS.toMillis(7)) % TimeUnit.DAYS.toMillis(1);
    }

}
