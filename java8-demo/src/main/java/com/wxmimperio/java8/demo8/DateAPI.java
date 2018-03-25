package com.wxmimperio.java8.demo8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class DateAPI {

    // 线程安全
    @Test
    public void date1() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalDateTime localDateTime1 = LocalDateTime.of(2018, 3, 10, 23, 23, 56);
        System.out.println(localDateTime1);

        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);

        LocalTime localTime1 = localTime.plusHours(3);
        System.out.println(localTime1);

        LocalDate localDate1 = localDate.minusDays(3);
        System.out.println(localDate1);
        System.out.println(localDate.getDayOfYear());
    }

    @Test
    public void date2() {
        Instant instant = Instant.now();// 默认UTC时区
        System.out.println(instant);

        // +8H 带偏移量的时间
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);

        System.out.println(instant.toEpochMilli());
        System.out.println(offsetDateTime.toInstant().toEpochMilli());
    }

    @Test
    public void date3() throws Exception {
        Instant instant = Instant.now();
        Thread.sleep(3000);
        Instant instant1 = Instant.now();
        System.out.println(Duration.between(instant, instant1).toMillis());

        LocalTime localTime = LocalTime.now();
        Thread.sleep(2000);
        LocalTime localTime1 = LocalTime.now();
        System.out.println(Duration.between(localTime, localTime1).toMillis());

        LocalDate localDate = LocalDate.now();
        LocalDate localDate1 = LocalDate.of(2018, 4, 13);
        System.out.println(Period.between(localDate, localDate1).getDays());
    }

    @Test
    public void date4() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime newLocal = localDateTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        System.out.println(localDateTime);
        System.out.println(newLocal);
    }

    @Test
    public void date5() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.format(dateTimeFormatter));

        System.out.println("=============");

        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTime.format(dateTimeFormatter1));

        LocalDateTime localDateTime1 = LocalDateTime.parse("2017-02-13 12:25:56", dateTimeFormatter1);
        System.out.println(localDateTime1);
    }
}
