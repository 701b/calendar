package com.example.calendar;

import android.graphics.Color;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Schedule {

    private String title = null;
    private LocalDateTime startDateTime = null;
    private LocalDateTime endDateTime = null;
    private String memo = null;
    private Color color = null;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public boolean isAtDate(LocalDate localDate) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        if (localDate.compareTo(startDate) > 0 && localDate.compareTo(endDate) < 0) {
            return true;
        }

        return false;
    }

    public boolean isInMonth(LocalDate monthDate) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        if (startDate.getMonthValue() == monthDate.getMonthValue()) {
            return true;
        }

        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
