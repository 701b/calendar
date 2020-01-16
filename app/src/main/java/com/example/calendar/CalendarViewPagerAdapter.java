package com.example.calendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewPagerAdapter extends PagerAdapter {

    public final int MAX_NUMBER_OF_ROW = 6;
    public final int MAX_NUMBER_OF_DAY_IN_A_ROW = 7;

    private final int defaultValueOfPage;
    private final LocalDate defaultDate;

    private Context context;
    private ArrayList<Schedule> schedules;

    public CalendarViewPagerAdapter(Context context, ArrayList<Schedule> schedules, LocalDate defaultDate, int defaultValueOfPage) {
        this.context = context;
        this.schedules = schedules;
        this.defaultDate = defaultDate;
        this.defaultValueOfPage = defaultValueOfPage;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parentLayout = (LinearLayout) inflater.inflate(R.layout.month_page, container, false);
        ArrayList<DayView> dayViews = new ArrayList<>();

        for (int i = 0; i < MAX_NUMBER_OF_ROW; i++) {
            LinearLayout layout = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            layoutParams.weight = 1;
            layoutParams.setMargins((int) (20 * context.getResources().getDisplayMetrics().density), 0, (int) (20 * context.getResources().getDisplayMetrics().density), 0);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(layoutParams);
            parentLayout.addView(layout);

            for (int j = 0; j < MAX_NUMBER_OF_DAY_IN_A_ROW; j++) {
                DayView dayView = new DayView(context);
                LinearLayout.LayoutParams dayViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                dayViewLayoutParams.weight = 1;
                dayView.setLayoutParams(dayViewLayoutParams);
                layout.addView(dayView);
                dayViews.add(dayView);
            }
        }

        container.addView(parentLayout);

        // 뷰들 텍스트 조정
        LocalDate monthToShow = defaultDate.plusMonths(position - defaultValueOfPage);
        LocalDate frontMonth = monthToShow.plusMonths(-1);
        LocalDate rearMonth = monthToShow.plusMonths(1);
        final int numberOfDayInMonthToShow = monthToShow.lengthOfMonth();
        final int numberOfDayInFrontMonth = monthToShow.plusDays(1).getDayOfWeek().getValue() - 1;
        final int numberOfDayInRearMonth = MAX_NUMBER_OF_DAY_IN_A_ROW * MAX_NUMBER_OF_ROW - monthToShow.lengthOfMonth() - numberOfDayInFrontMonth;
        ArrayList<Schedule> schedulesInMonth = new ArrayList<>();
        int count = 0;

        for (Schedule schedule : schedules) {
            if (schedule.isInMonth(monthToShow)) {
                schedulesInMonth.add(schedule);
            }
        }

        // 날짜 설정
        for (int i = 1; i <= numberOfDayInFrontMonth; i++) {
            DayView dayView = dayViews.get(count++);
            LocalDate date = LocalDate.of(frontMonth.getYear(), frontMonth.getMonthValue(), frontMonth.lengthOfMonth() - numberOfDayInFrontMonth + i);

            dayView.setDay(date, false);
        }

        for (int i = 1; i <= numberOfDayInMonthToShow; i++) {
            DayView dayView = dayViews.get(count++);
            LocalDate date = LocalDate.of(monthToShow.getYear(), monthToShow.getMonthValue(), i);

            dayView.setDay(date, true);
        }

        for (int i = 1; i <= numberOfDayInRearMonth; i++) {
            DayView dayView = dayViews.get(count++);
            LocalDate date = LocalDate.of(rearMonth.getYear(), rearMonth.getMonthValue(), i);

            dayView.setDay(date, false);
        }

        // 스케줄 설정
        for (Schedule schedule : schedulesInMonth) {
            LocalDate startDate = schedule.getStartDateTime().toLocalDate();
            LocalDate endDate = schedule.getEndDateTime().toLocalDate();
            DayView dayView = dayViews.get(numberOfDayInFrontMonth + startDate.getDayOfMonth() - 1);

            try {
                int index = dayView.setSchedule(schedule, true);

                if (startDate.getMonthValue() == endDate.getMonthValue()) {
                    for (int i = numberOfDayInFrontMonth + startDate.getDayOfMonth(); i <= numberOfDayInFrontMonth + endDate.getDayOfMonth(); i++) {
                        dayView = dayViews.get(i);
                        dayView.setSchedule(schedule, false, index);
                    }
                } else {
                    for (int i = numberOfDayInFrontMonth + startDate.getDayOfMonth(); i <= numberOfDayInFrontMonth + startDate.lengthOfMonth() - startDate.getDayOfMonth() + endDate.getDayOfMonth(); i++) {
                        dayView = dayViews.get(i);
                        dayView.setSchedule(schedule, false, index);
                    }
                }

            } catch (DayView.NoSpaceAvailableException e) {

            }
        }

        return parentLayout;
    }

    @Override
    public int getCount() {
        return 100000;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View) object);
    }
}
