package com.example.tasks_ab.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TasksSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Tasks_db";
    private static int DATABASE_VERSION = 1;
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_DATE_FULL = "full_date";
    private static final String COLUMN_COMPLETE = "is_complete";
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
                + COLUMN_DATE + " TEXT, "
                + COLUMN_DATE_FULL + " TEXT, "
                + COLUMN_COMPLETE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        DATABASE_VERSION = newVersion;
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion, newVersion);

    }

    void insertTaskAsync(Task task){
        AddTaskAsync taskAsync = new AddTaskAsync(getWritableDatabase());
        taskAsync.doInBackground(task);
    }

    void changeTaskAsync(Task newTask, Task oldTask){
        ChangeTaskAsync taskAsync = new ChangeTaskAsync(getWritableDatabase());
        taskAsync.doInBackground(newTask, oldTask);
    }

    //returns a list of uncompleted tasks
    public ArrayList<Task> getListTask(Context context, Calendar calendar){
        ArrayList<Task> taskArrayList = new ArrayList<>();
        Cursor mCursor;
        SimpleDateFormat shortDateFormat = new SimpleDateFormat(FORMAT_DMY_PATTERN, Locale.ENGLISH);
        String shortDate = shortDateFormat.format(calendar.getTime());

        try{
            mCursor = this.getReadableDatabase().query(DATABASE_NAME,null,
                    COLUMN_COMPLETE + " = ?"  + " AND " + COLUMN_DATE + " = ? ",
                    new String[]{Integer.toString(0), shortDate}, null, null,
                    COLUMN_ID + " DESC");
            if (mCursor.moveToFirst()) {

                do {
                    String dateString = mCursor.getString(2);
                    taskArrayList.add(new Task(mCursor.getString(1),
                            mCursor.getString(2), dateString, false));
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }catch (SQLException e){
            Toast.makeText(context, "db Error", Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
        return taskArrayList;
    }

    //Adds new task in db async
    private static class AddTaskAsync extends AsyncTask<Task, Void, Boolean> {

        SQLiteDatabase mDatabase;

        AddTaskAsync(SQLiteDatabase db) {
            mDatabase = db;
        }
        @Override
        protected Boolean doInBackground(Task... tasks) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_NAME, tasks[0].getName());
                contentValues.put(COLUMN_DESCRIPTION, tasks[0].getDescription());
                contentValues.put(COLUMN_DATE, tasks[0].getTimeShortPattern());
                contentValues.put(COLUMN_DATE_FULL, tasks[0].getTimeFullPattern());
                contentValues.put(COLUMN_COMPLETE, tasks[0].getComplete()?1:0);
                mDatabase.insert(DATABASE_NAME, null, contentValues);
                mDatabase.close();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
    }

    // modifies the task in SQLite
    private static class ChangeTaskAsync extends AsyncTask<Task, Void, Integer> {

        SQLiteDatabase mDatabase;

        ChangeTaskAsync(SQLiteDatabase db) {
            mDatabase = db;
        }

        @Override
        protected Integer doInBackground(Task... tasks) {
            try {
                //businesses[0] - new, businesses[1] - old
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_NAME, tasks[0].getName());
                contentValues.put(COLUMN_DESCRIPTION, tasks[0].getDescription());
                contentValues.put(COLUMN_DATE, tasks[0].getTimeShortPattern());
                contentValues.put(COLUMN_DATE_FULL, tasks[0].getTimeFullPattern());
                contentValues.put(COLUMN_COMPLETE, tasks[0].getComplete()?1:0);

                return mDatabase.update(DATABASE_NAME, contentValues,
                        COLUMN_NAME + " = ?", new String[]{tasks[1].getName()});
            } catch (SQLException e) {
                return 0;
            }
        }
    }
}
