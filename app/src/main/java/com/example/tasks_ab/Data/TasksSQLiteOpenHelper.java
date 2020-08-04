package com.example.tasks_ab.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TasksSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Tasks_db";
    private static int DATABASE_VERSION = 1;
    private final String COLUMN_ID = "_id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_DESCRIPTION = "description";
    private final String COLUMN_DATE = "date";
    private final String COLUMN_COMPLETE = "is_complete";
    public static final String FORMAT_DMY_HMS_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DMY_PATTERN = "yyyy-MM-dd";

    public TasksSQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_NAME +
                "(" + COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_DATE + "TEXT, "
                + COLUMN_COMPLETE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        DATABASE_VERSION = newVersion;
        onCreate(db);
    }


    public ArrayList<Task> getListTask(){
        ArrayList<Task> taskArrayList = new ArrayList<>();
        Cursor mCursor;
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DMY_HMS_PATTERN, Locale.ENGLISH);
        try{
            mCursor = this.getReadableDatabase().query(DATABASE_NAME, new String[]{COLUMN_ID,
                            COLUMN_NAME,
                            COLUMN_DESCRIPTION, COLUMN_DATE, COLUMN_COMPLETE},
                    COLUMN_COMPLETE + " = ?",
                    new String[]{Integer.toString(1)}, null, null,
                    COLUMN_ID + " DESC");
            if (mCursor.moveToFirst()) {

                do {
                    Calendar calendar;
                    String dateString = mCursor.getString(2);
                    try {
                         calendar = Calendar.getInstance();
                         if(dateString != null) calendar.setTime(dateFormat.parse(dateString));
                    }catch (ParseException e){
                        calendar = Calendar.getInstance();
                    }

                    taskArrayList.add(new Task(mCursor.getString(0),
                            mCursor.getString(1),
                            calendar));
                } while (mCursor.moveToNext());
                mCursor.close();
            }
        }catch (SQLException e){
            return new ArrayList<>();
        }
        return taskArrayList;
    }

    public void insertTask(Task task, @Nullable ArrayList<Task> taskArrayList){
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DMY_HMS_PATTERN, Locale.ENGLISH);

        contentValues.put(COLUMN_NAME, task.getName());
        contentValues.put(COLUMN_DESCRIPTION, task.getDescription());
        contentValues.put(COLUMN_DATE, dateFormat.format(task.getCalendar().getTime()));
        contentValues.put(COLUMN_COMPLETE, task.getComplete() ? 1 : 0);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DATABASE_NAME, null, contentValues);
        db.close();

        if(taskArrayList != null) taskArrayList.add(task);
    }
}
