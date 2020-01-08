package com.example.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DayView extends LinearLayout {

    private final int MAX_NUMBER_OF_SCHEDULE = 4;

    private Day day;

    private TextView dayText;
    private ArrayList<TextView> scheduleTexts;

    public DayView(Context context) {
        super(context);

        init(context, null);
    }

    public DayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public DayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        layoutParams.weight = 1;
        this.setLayoutParams(layoutParams);
        inflater.inflate(R.layout.day_view, this);
        setOrientation(LinearLayout.VERTICAL);
        scheduleTexts = new ArrayList<>();

        dayText = findViewById(R.id.day_text);
        scheduleTexts.add((TextView) findViewById(R.id.schedule_text1));
        scheduleTexts.add((TextView) findViewById(R.id.schedule_text2));
        scheduleTexts.add((TextView) findViewById(R.id.schedule_text3));
        scheduleTexts.add((TextView) findViewById(R.id.schedule_text4));
    }

    public void setDay(Day day, boolean isInMonth) {
        this.day = day;

        renew(isInMonth);
    }

    private void renew(boolean isInMonth) {
        dayText.setText(String.valueOf(day.getDay()));

        for (int i = 0; i < MAX_NUMBER_OF_SCHEDULE; i++) {
            Schedule schedule = day.getScheduleAt(i);

            if (schedule != null) {
                scheduleTexts.get(i).setText(schedule.getTitle());
            } else {
                scheduleTexts.get(i).setText("");
            }

            if (isInMonth) {
                scheduleTexts.get(i).setTextColor(Color.BLACK);
            } else {
                scheduleTexts.get(i).setTextColor(Color.GRAY);
            }
        }

        if (isInMonth) {
            dayText.setTextColor(Color.BLACK);
        } else {
            dayText.setTextColor(Color.GRAY);
        }
    }
}
