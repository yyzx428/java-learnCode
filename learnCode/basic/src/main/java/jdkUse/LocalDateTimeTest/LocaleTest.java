package jdkUse.LocalDateTimeTest;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class LocaleTest {
    public static void main(String[] args) {
        LocalDate ld = LocalDate.of(2018,10,1);
        System.out.println(ld.with(WeekFields.of(Locale.CHINA).dayOfWeek(),2L));
        System.out.println(ld.with(DayOfWeek.MONDAY));
    }
}
