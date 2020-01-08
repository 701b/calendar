package com.example.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CalendarView extends LinearLayout {

    private final int MAX_NUMBER_OF_ROW = 6;
    private final int MAX_NUMBER_OF_DAY_IN_A_ROW = 7;

    private ArrayList<Day> days;
    private ArrayList<DayView> dayViews;

    public CalendarView(Context context) {
        super(context);

        init(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.calendar_view, this);
        setOrientation(LinearLayout.VERTICAL);
        dayViews = new ArrayList<>();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < MAX_NUMBER_OF_ROW; i++) {
            LinearLayout layout = new LinearLayout(getContext());
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            layoutParams.weight = 1;
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(layoutParams);
            addView(layout);

            for (int j = 0; j < MAX_NUMBER_OF_DAY_IN_A_ROW; j++) {
                DayView dayView = new DayView(getContext());
                LayoutParams dayViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

                dayViewLayoutParams.weight = 1;
                dayView.setLayoutParams(dayViewLayoutParams);
                layout.addView(dayView);
                dayViews.add(dayView);
            }
        }
    }

    public void setDays(ArrayList<Day> days, int month) {
        this.days = days;

        renew(month);
    }

    private void renew(int month) {
        for (int i = 0; i < MAX_NUMBER_OF_DAY_IN_A_ROW * MAX_NUMBER_OF_ROW; i++) {
            Day day = days.get(i);
            DayView dayView = dayViews.get(i);

            dayView.setDay(day, day.getMonth() == month);
        }
    }
}
