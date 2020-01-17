package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendar_view);

        Schedule schedule1 = new Schedule("캠프", LocalDateTime.of(2020, 1, 5, 12, 0, 0), LocalDateTime.of(2020, 1, 8, 12, 0, 0));
        Schedule schedule2 = new Schedule("여행", LocalDateTime.of(2020, 1, 20, 12, 0, 0), LocalDateTime.of(2020, 2, 2, 12, 0, 0));
        Schedule schedule3 = new Schedule("주간", LocalDateTime.of(2020, 1, 6, 13, 0, 0), LocalDateTime.of(2020, 1, 10, 12, 0, 0));
        Schedule schedule4 = new Schedule("불조심", LocalDateTime.of(2020, 2, 1, 13, 0, 0), LocalDateTime.of(2020, 2, 10, 12, 0, 0));

        schedule2.setColor(Color.valueOf(0.5f, 1f, 0.5f));
        schedule3.setColor(Color.valueOf(0.5f, 0.5f, 1f));
        schedule4.setColor(Color.valueOf(1f, 0.5f, 1f));

        calendarView.addSchedule(schedule1);
        calendarView.addSchedule(schedule2);
        calendarView.addSchedule(schedule3);
        calendarView.addSchedule(schedule4);
    }
}
