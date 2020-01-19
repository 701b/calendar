package com.example.calendar;

import android.graphics.Color;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Schedule {

    private final Color defaultColor = Color.valueOf(1f, 0.5f, 0.5f);

    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String memo = "";
    private Color color = null;


    public Schedule(String title, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public boolean isAtDate(LocalDate localDate) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        if (localDate.compareTo(startDate) > 0 && localDate.compareTo(endDate) < 0) {
            return true;
        }

        return false;
    }

    public boolean isInPeriod(LocalDate startDate, LocalDate endDate) {
        if ((startDateTime.toLocalDate().isBefore(endDate) && startDateTime.toLocalDate().isAfter(startDate))
                || (endDateTime.toLocalDate().isBefore(endDate) && endDateTime.toLocalDate().isAfter(startDate))) {
            return true;
        }

        return false;
    }

    public Color getColor() {
        if (color == null) {
            return defaultColor;
        } else {
            return color;
        }
    }

    public void setColor(Color color) {
        this.color = color;
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
