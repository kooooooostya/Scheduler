package com.example.tasks_ab;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tasks_ab.Data.TasksSQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class Task implements Parcelable {
    private Calendar mCalendar;
    private String mName;
    private String mDescription;
    private Boolean mComplete;
    private int mPriority;

    public static final int NO_PRIORITY = 0;
    public static final int LOW_PRIORITY = 1;
    public static final int MEDIUM_PRIORITY = 2;
    public static final int HIGH_PRIORITY = 3;

    public Task(String name, String description, Calendar date, Boolean complete) {
        mCalendar = date;
        mName = name;
        mDescription = description;
        mComplete = complete;
        mPriority = NO_PRIORITY;
    }

    public Task( String name, String description, Calendar date) {
        mCalendar = date;
        mName = name;
        mDescription = description;
        mComplete = false;
        mPriority = NO_PRIORITY;
    }

    public Task(String name, String description, String dateFullPattern, boolean complete) {
        mName = name;
        mDescription = description;
        mCalendar = getCalendarFromString(dateFullPattern);
        mComplete = complete;
        mPriority = NO_PRIORITY;
    }

    public Task(String name, String description, Calendar calendar, Boolean complete, int priority) {
        mCalendar = calendar;
        mName = name;
        mDescription = description;
        mComplete = complete;
        mPriority = priority;
    }

    public String getTimeFullPattern(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TasksSQLiteOpenHelper.FORMAT_DMY_HMS_PATTERN,
                Locale.ENGLISH);
        return simpleDateFormat.format(this.mCalendar.getTime());
    }

    public String getTimeShortPattern(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TasksSQLiteOpenHelper.FORMAT_DMY_PATTERN,
                Locale.ENGLISH);
        return simpleDateFormat.format(this.mCalendar.getTime());
    }
    private Calendar getCalendarFromString(String stringFullPattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TasksSQLiteOpenHelper.FORMAT_DMY_HMS_PATTERN,
                Locale.ENGLISH);
        Calendar calendar = calendar = Calendar.getInstance();
        try {
            calendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(stringFullPattern)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    public int getPriority() {
        return mPriority;
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

    public void setName(String name) {
        this.mName = name;
    }




    public static final Creator<Task> CREATOR = new Creator<Task>() {

        @Override
        public Task createFromParcel(Parcel source) {

            return new Task(source.readString(), source.readString(),
                    source.readString(),source.readInt() == 1);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mDescription);
        dest.writeString(this.getTimeFullPattern());
        dest.writeInt(this.mComplete?1:0);
    }
}
