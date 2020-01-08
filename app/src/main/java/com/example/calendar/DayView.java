package com.example.calendar;

import android.content.Context;
import android.content.res.Resources;
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

    public void setDay(Day day, boolean isInMonth) {
        this.day = day;

        dayText.setText(String.valueOf(day.getDay()));

        // 날짜가 해당 월의 날짜인 경우 검은색으로 날짜 표기, 그 외에는 회색
        if (isInMonth) {
            dayText.setTextColor(Color.BLACK);
        } else {
            dayText.setTextColor(Color.GRAY);
        }

        // 스케줄이 들어가는 부분을 초기화
        for (int i = 0; i < MAX_NUMBER_OF_SCHEDULE; i++) {
            scheduleTexts.get(i).setText("");
            scheduleTexts.get(i).setBackgroundColor(Color.WHITE);
        }
    }

    public int setSchedule(Schedule schedule, boolean isFirst) throws NoSpaceAvailableException {
        for (int index = 0; index < MAX_NUMBER_OF_SCHEDULE; index++) {
            if (schedules[index] == null) {
                schedules[index] = schedule;
                renewSchedule(schedule, isFirst, index);
                return index;
            }
        }

        throw new NoSpaceAvailableException();
    }

    public void setSchedule(Schedule schedule, boolean isFirst, int index) {
        if (schedules[index] != null) {
            throw new AlreadyScheduleExistedException();
        }

        schedules[index] = schedule;
        renewSchedule(schedule, isFirst, index);
    }

    private void renewSchedule(Schedule schedule, boolean isFirst, int index) {
        TextView scheduleText = scheduleTexts.get(index);

        if (isFirst) {
            scheduleText.setText(schedule.getTitle());
        }

        scheduleText.setBackgroundColor(schedule.getColor().toArgb());
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
