package com.example.calendar;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CalendarView extends LinearLayout {

    private final int defaultValueOfPage = 50000;

    private final int marginPixelBetweenPage = 50;

    private ArrayList<Schedule> schedules;
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

        schedules = new ArrayList<>();
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
                Dialog dialog = new Dialog(getContext());

                dialog.setTitle("스케줄 추가");
                dialog.setContentView(R.layout.add_schedule_dialog);

                TextView startDateTimeText = dialog.findViewById(R.id.start_date_time);
                TextView endDateTimeText = dialog.findViewById(R.id.end_date_time);
                LinearLayout startDateTimeLayout = dialog.findViewById(R.id.start_date_time_layout);
                LinearLayout endDateTimeLayout = dialog.findViewById(R.id.end_date_time_layout);
                EditText inputOfTitle = dialog.findViewById(R.id.title_input);
                EditText inputOfMemo = dialog.findViewById(R.id.memo_input);
                Button saveButton = dialog.findViewById(R.id.save_button);
                Button cancleButton = dialog.findViewById(R.id.cancle_button);

                LocalDateTime currentDateTime = LocalDate.now().atTime(9,0);

                startDateTimeText.setText(currentDateTime.toString());
                endDateTimeText.setText(currentDateTime.plusHours(1).toString());

                startDateTimeLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //시작 시간 설정 코드
                    }
                });

                endDateTimeLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //종료 시간 설정 코드
                    }
                });

                saveButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 스케줄 추가
                    }
                });

                cancleButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 취소
                    }
                });

                dialog.show();
            }
        });

        calendarViewPagerAdapter = new CalendarViewPagerAdapter(getContext(), schedules, defaultDate, defaultValueOfPage);
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

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);

        calendarViewPagerAdapter.notifyDataSetChanged();
    }
}
