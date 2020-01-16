package com.example.calendar;

import android.content.Context;
import android.graphics.Color;
import android.text.GetChars;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarView extends LinearLayout {

    private final int defaultValueOfPage = 50000;

    private ArrayList<Schedule> schedules;
    private LocalDate defaultDate;

    private CalendarViewPagerAdapter calendarViewPagerAdapter;

    private TextView monthText;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.calendar_view, this);
        setOrientation(LinearLayout.VERTICAL);

        defaultDate = LocalDate.now();
        defaultDate = defaultDate.minusDays(defaultDate.getDayOfMonth() - 1);

        schedules = new ArrayList<>();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ViewPager viewPager = findViewById(R.id.calendar_pager);
        monthText = findViewById(R.id.month_text);

        calendarViewPagerAdapter = new CalendarViewPagerAdapter(getContext(), schedules, defaultDate, defaultValueOfPage);
        viewPager.setAdapter(calendarViewPagerAdapter);
        viewPager.setCurrentItem(defaultValueOfPage);

        monthText.setText(defaultDate.getYear() + "년 " + defaultDate.getMonthValue() + "월");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LocalDate currentMonth = defaultDate.plusMonths(position - defaultValueOfPage);

                monthText.setText(currentMonth.getYear() + "년 " + currentMonth.getMonthValue() + "월");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
