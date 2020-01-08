package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendar_view);

        ArrayList<Day> days = new ArrayList<>();

        for (int i = 0; i < 31; i++) {
            days.add(new Day(2019, 1, i + 1));
        }

        for (int i = 0; i < 42 - 31; i++) {
            days.add(new Day(2019, 2, i + 1));
        }

        calendarView.setDays(days, 1);
    }
}
