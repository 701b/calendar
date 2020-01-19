package com.example.calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import org.w3c.dom.Text;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class CalendarView extends LinearLayout {

    private final int defaultValueOfPage = 50000;

    private final int marginPixelBetweenPage = 50;

    private ScheduleDBHelper scheduleDBHelper;
    private LocalDate defaultDate;

    private CalendarViewPagerAdapter calendarViewPagerAdapter;

    private ViewPager viewPager;
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

        scheduleDBHelper = new ScheduleDBHelper(getContext(), "ScheduleBook.db", null, 1);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ImageButton buttonToAddSchedule = findViewById(R.id.button_to_add_schedule);
        viewPager = findViewById(R.id.calendar_pager);
        monthText = findViewById(R.id.month_text);

        buttonToAddSchedule.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());

                dialog.setTitle("스케줄 추가");
                dialog.setContentView(R.layout.add_schedule_dialog);

                final TextView startDateTimeText = dialog.findViewById(R.id.start_date_time);
                final TextView endDateTimeText = dialog.findViewById(R.id.end_date_time);
                final LinearLayout startDateTimeLayout = dialog.findViewById(R.id.start_date_time_layout);
                final LinearLayout endDateTimeLayout = dialog.findViewById(R.id.end_date_time_layout);
                final EditText inputOfTitle = dialog.findViewById(R.id.title_input);
                final EditText inputOfMemo = dialog.findViewById(R.id.memo_input);
                final Button saveButton = dialog.findViewById(R.id.save_button);
                final Button cancleButton = dialog.findViewById(R.id.cancle_button);

                final DateTime startDateTime = new DateTime(LocalDate.now().atTime(9,0));
                final DateTime endDateTime = new DateTime(LocalDate.now().atTime(10,0));

                startDateTimeText.setText(startDateTime.toString());
                endDateTimeText.setText(endDateTime.toString());

                startDateTimeLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runDateTimePicker(startDateTime, startDateTimeText);
                    }
                });

                endDateTimeLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runDateTimePicker(endDateTime, endDateTimeText);
                    }
                });

                saveButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = inputOfTitle.getText().toString();
                        String memo = inputOfMemo.getText().toString();
                        Schedule schedule = new Schedule(title, startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime());

                        if (title.equals("")) {
                            return;
                        }

                        schedule.setMemo(memo);
                        addSchedule(schedule);
                        dialog.dismiss();
                    }
                });

                cancleButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        calendarViewPagerAdapter = new CalendarViewPagerAdapter(getContext(), scheduleDBHelper, defaultDate, defaultValueOfPage);
        viewPager.setAdapter(calendarViewPagerAdapter);
        viewPager.setCurrentItem(defaultValueOfPage);
        viewPager.setPageMargin(marginPixelBetweenPage);

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

    private void runDateTimePicker(final DateTime dateTime, final TextView textView) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateTime.year = year;
                dateTime.month = month + 1;
                dateTime.dayOfMonth = dayOfMonth;

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dateTime.hour = hourOfDay;
                        dateTime.minute = minute;

                        textView.setText(dateTime.toString());
                    }
                }, dateTime.hour, dateTime.minute, true);

                timePickerDialog.setMessage("시간 선택");
                timePickerDialog.show();
            }
        }, dateTime.year, dateTime.month - 1, dateTime.dayOfMonth);

        datePickerDialog.setMessage("날짜 선택");
        datePickerDialog.show();
    }

    public void addSchedule(Schedule schedule) {
        scheduleDBHelper.addSchedule(schedule);
        calendarViewPagerAdapter.notifyDataSetChanged();
    }

    class DateTime {

        int year;
        int month;
        int dayOfMonth;
        int hour;
        int minute;

        public DateTime(LocalDateTime localDateTime) {
            year = localDateTime.getYear();
            month = localDateTime.getMonthValue();
            dayOfMonth = localDateTime.getDayOfMonth();
            hour = localDateTime.getHour();
            minute = localDateTime.getMinute();
        }

        public LocalDateTime toLocalDateTime() {
            return LocalDateTime.of(year, month, dayOfMonth, hour, minute, 0);
        }

        public String toString() {
            LocalDateTime localDateTime = this.toLocalDateTime();
            String dayOfWeekString = "";
            String hourString = "";
            String minuteString = "";

            switch (localDateTime.getDayOfWeek()) {
                case SUNDAY:
                    dayOfWeekString = "일";
                    break;

                case MONDAY:
                    dayOfWeekString = "월";
                    break;

                case TUESDAY:
                    dayOfWeekString = "화";
                    break;

                case WEDNESDAY:
                    dayOfWeekString = "수";
                    break;

                case THURSDAY:
                    dayOfWeekString = "목";
                    break;

                case FRIDAY:
                    dayOfWeekString = "금";
                    break;

                case SATURDAY:
                    dayOfWeekString = "토";
                    break;
            }

            if (hour == 0) {
                hourString = "오전 12";
            } else if (hour > 0 && hour < 12) {
                hourString = "오전 " + hour;
            } else if (hour == 12) {
                hourString = "오후 12";
            } else {
                hourString = "오후 " + hour;
            }

            if (minute == 0) {
                minuteString = "00";
            } else if (minute < 10) {
                minuteString = "0" + minute;
            } else {
                minuteString = String.valueOf(minute);
            }

            return year + "년 " + month + "월 " + dayOfMonth + "일 (" + dayOfWeekString + ") " + hourString + ":" + minuteString;
        }

    }
}
