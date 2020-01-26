package com.example.calendar;

import android.graphics.Color;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Schedule {

    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String memo = "";
    private Color color;


    /**
     * Schedule의 객체를 새로 생성한다. memo와 color는 필수 요소가 아니므로 setter을 이용해 설정한다.
     *
     * @param title schedule의 제목
     * @param startDateTime schedule의 시작 시간
     * @param endDateTime schedule의 종료 시간
     */
    public Schedule(String title, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        color = Color.valueOf(1f, 0.5f, 0.5f);
    }

    /**
     * 스케줄이 인자로 넘긴 기간 사이에 걸쳐지는지 확인하는 메소드
     *
     * @param startDate 확인하고자 하는 기간의 시작 날짜
     * @param endDate 확인하고자 하는 기간의 끝 날짜
     * @return 인자로 넘긴 날짜 사이에 스케줄이 걸치면 true, 아니라면 false
     */
    public boolean isInPeriod(LocalDate startDate, LocalDate endDate) {
        if ((startDateTime.toLocalDate().isBefore(endDate) && startDateTime.toLocalDate().isAfter(startDate))
                || (endDateTime.toLocalDate().isBefore(endDate) && endDateTime.toLocalDate().isAfter(startDate))) {
            return true;
        }

        return false;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
