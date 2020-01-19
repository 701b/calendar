package com.example.calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class ScheduleDBHelper extends SQLiteOpenHelper {

    private ArrayList<Schedule> schedules;


    public ScheduleDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        schedules = new ArrayList<>();
        readAll();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SCHEDULEBOOK (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, startDateTime TEXT, endDateTime TEXT, memo TEXT);");
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        sortSchedules();
        insert(schedule);
    }

    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
        delete(schedule);
    }

    private void insert(Schedule schedule) {
        SQLiteDatabase db = getWritableDatabase();
        String title = schedule.getTitle();
        String startStr = schedule.getStartDateTime().toString();
        String endStr = schedule.getEndDateTime().toString();
        String memo = schedule.getMemo();

        db.execSQL("INSERT INTO SCHEDULEBOOK VALUES(null, '" + title + "', '" + startStr + "', '" + endStr + "', '" + memo + "');");
        db.close();
    }

    private void delete(Schedule schedule) {
        SQLiteDatabase db = getWritableDatabase();
        String title = schedule.getTitle();
        String startStr = schedule.getStartDateTime().toString();
        String endStr = schedule.getEndDateTime().toString();
        String memo = schedule.getMemo();

        db.execSQL("DELETE FROM SCHEDULEBOOK WHERE title='" + title + "', startDateTime='" + startStr + "', endDateTime='" + endStr + "', memo ='" + memo + "';");
        db.close();
    }

    private void sortSchedules() {
        Comparator<Schedule> comparator = new Comparator<Schedule>() {
            @Override
            public int compare(Schedule o1, Schedule o2) {
                return o1.getStartDateTime().compareTo(o2.getStartDateTime());
            }
        };

        schedules.sort(comparator);
    }

    private void readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SCHEDULEBOOK", null);

        while (cursor.moveToNext()) {
            LocalDateTime startDateTime = LocalDateTime.parse(cursor.getString(2));
            LocalDateTime endDateTime = LocalDateTime.parse(cursor.getString(3));
            Schedule schedule = new Schedule(cursor.getString(1), startDateTime, endDateTime);

            schedule.setMemo(cursor.getString(4));
            schedules.add(schedule);
        }

        sortSchedules();
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }
}
