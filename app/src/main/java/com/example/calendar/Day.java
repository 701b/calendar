package com.example.calendar;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class Day {

    private LocalDate localDate;
    private ArrayList<Schedule> schedules;


    public Day(int year, int month, int day) {
        localDate = LocalDate.of(year, month, day);
        schedules = new ArrayList<>();
    }

    public void addSchedule(Schedule schedule) {
        if (schedule != null) {
            schedules.add(schedule);
        }
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

    public Schedule getScheduleAt(int index) {
        if (schedules.size() <= index) {
            return null;
        }

        return schedules.get(index);
    }

}
