package com.example.calendar;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class Day {

    private LocalDate localDate;

    public Day(int year, int month, int day) {
        localDate = LocalDate.of(year, month, day);
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public int getYear() {
        return localDate.getYear();
    }

    public int getMonth() {
        return localDate.getMonth().getValue();
    }

    public int getDay() {
        return localDate.getDayOfMonth();
    }

}
