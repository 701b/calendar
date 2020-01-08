package com.example.calendar;

import java.time.LocalDate;
import java.util.ArrayList;

public class Month {

    private LocalDate localDate;
    private ArrayList<Day> days;

    public Month(int year, int month) {
        this.localDate = LocalDate.of(year, month,1);
        days = new ArrayList<>();

        for (int i = 1; i <= localDate.lengthOfMonth(); i++) {
            days.add(new Day(year, month, i));
        }
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public ArrayList<Day> getDays() {
        return days;
    }
}
