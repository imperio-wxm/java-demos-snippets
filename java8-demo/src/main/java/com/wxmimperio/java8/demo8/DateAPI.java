package com.wxmimperio.java8.demo8;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateAPI {

    // 线程安全
    @Test
    public void date1() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalDateTime localDateTime1 = LocalDateTime.of(2018,3,10,23,23,56);
        System.out.println(localDateTime1);

        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);

        LocalTime localTime1 = localTime.plusHours(3);
        System.out.println(localTime1);

        LocalDate localDate1 = localDate.minusDays(3);
        System.out.println(localDate1);

        System.out.println(localDate.getDayOfYear());

    }
}
