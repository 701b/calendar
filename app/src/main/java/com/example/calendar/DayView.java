package com.example.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.time.LocalDate;
import java.util.ArrayList;

public class DayView extends LinearLayout {

    private final int MAX_NUMBER_OF_SCHEDULE = 4;

    private LocalDate date;
    private TextView dayText;
    private Schedule[] schedules;
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
        schedules = new Schedule[MAX_NUMBER_OF_SCHEDULE];

        dayText = findViewById(R.id.day_text);
        scheduleTexts.add((TextView) findViewById(R.id.schedule_text1));
        scheduleTexts.add((TextView) findViewById(R.id.schedule_text2));
        scheduleTexts.add((TextView) findViewById(R.id.schedule_text3));
        scheduleTexts.add((TextView) findViewById(R.id.schedule_text4));
    }

    public void setDay(LocalDate date, boolean isInMonth) {
        this.date = date;

        dayText.setText(String.valueOf(date.getDayOfMonth()));

        // 날짜가 해당 월의 날짜인 경우 검은색으로 날짜 표기, 그 외에는 회색
        if (isInMonth) {
            dayText.setTextColor(Color.BLACK);
        } else {
            dayText.setTextColor(Color.GRAY);
        }

        // 스케줄이 들어가는 부분을 초기화
        for (int i = 0; i < MAX_NUMBER_OF_SCHEDULE; i++) {
            scheduleTexts.get(i).setText("");
            //scheduleTexts.get(i).setBackgroundColor(Color.WHITE);
        }
    }

    public int setSchedule(Schedule schedule, boolean isFirst, boolean isLast) throws NoSpaceAvailableException {
        for (int index = 0; index < MAX_NUMBER_OF_SCHEDULE; index++) {
            if (schedules[index] == null) {
                schedules[index] = schedule;
                renewSchedule(schedule, true, isFirst, isLast, index);
                return index;
            }
        }

        throw new NoSpaceAvailableException();
    }

    public void setSchedule(Schedule schedule, int index, boolean isFirst, boolean isLast) {
        if (schedules[index] != null) {
            throw new AlreadyScheduleExistedException();
        }

        schedules[index] = schedule;
        renewSchedule(schedule, false, isFirst, isLast, index);
    }

    private void renewSchedule(Schedule schedule, boolean isText, boolean isFirst, boolean isLast, int index) {
        TextView scheduleText = scheduleTexts.get(index);
        LayoutParams layoutParams = (LayoutParams) scheduleText.getLayoutParams();

        if (isText) {
            scheduleText.setText(schedule.getTitle());
        }

        if (isFirst && isLast) {
            scheduleText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.schedule_shape_both_sides));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
        } else if (isFirst) {
            scheduleText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.schedule_shape_left));
            layoutParams.leftMargin = 10;
        } else if (isLast) {
            scheduleText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.schedule_shape_right));
            layoutParams.rightMargin = 10;
        } else {
            scheduleText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.schedule_shape_middle));
        }
    }

    public class NoSpaceAvailableException extends Exception {
        public NoSpaceAvailableException() {
            super("There is no space avaliable to put schedule text.");
        }
    }

    public class AlreadyScheduleExistedException extends RuntimeException {
        public AlreadyScheduleExistedException() {
            super("There is already scehdule. Can not put schedule.");
        }
    }
}
