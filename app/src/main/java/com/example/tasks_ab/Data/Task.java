package com.example.tasks_ab.Data;

import java.util.Calendar;
import java.util.Date;

public class Task {
    private Calendar mCalendar;
    private String mName;
    private String mDescription;
    private Boolean mComplete;


    public Task(String name, String description, Calendar date, Boolean complete) {
        mCalendar = date;
        mName = name;
        mDescription = description;
        mComplete = complete;
    }

    public Task( String name, String description, Calendar date) {
        mCalendar = date;
        mName = name;
        mDescription = description;
        mComplete = false;
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public Boolean getComplete() {
        return mComplete;
    }

    public void setComplete(Boolean complete) {
        mComplete = complete;
    }

    @Override
    public String toString() {
        return "Task{" +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mComplete=" + mComplete +
                '}';
    }
}
