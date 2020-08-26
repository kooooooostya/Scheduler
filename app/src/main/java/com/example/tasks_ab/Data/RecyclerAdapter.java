package com.example.tasks_ab.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks_ab.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<Task> mTaskArrayList;
    private TasksSQLiteOpenHelper mTasksSQLiteOpenHelper;

    public RecyclerAdapter(Context context) {
        mTasksSQLiteOpenHelper = new TasksSQLiteOpenHelper(context);
        mTaskArrayList = mTasksSQLiteOpenHelper.getListTask(context, Calendar.getInstance());
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recucler_view, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TasksSQLiteOpenHelper.FORMAT_DMY_PATTERN, Locale.ENGLISH);
        String date = dateFormat.format(mTaskArrayList.get(position).getCalendar().getTime());
        holder.mTextViewName.setText(mTaskArrayList.get(position).getName());
        holder.mTextViewDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return mTaskArrayList.size();
    }

    // check name is free, if not free, make free and and to db
    public Task insertTask(Task task){
        if (!checkNameIsFree(task)){
            task.setName(task.getName() + "1");
            return insertTask(task);
        }else {
            mTaskArrayList.add(0, task);
            this.notifyItemInserted(0);
            mTasksSQLiteOpenHelper.insertTaskAsync(task);
            return task;
        }
    }
    // check name is free
    private boolean checkNameIsFree(Task task){
        for (Task task1 : mTaskArrayList){
            if(task.getName().equals(task1.getName())) return false;
        }
        return true;
    }
    public void changeTask(Task newTask, int index){
        mTasksSQLiteOpenHelper.changeTaskAsync(newTask, mTaskArrayList.get(index));
        mTaskArrayList.set(index, newTask);
    }

    //This is required when the user changes the date in the calendar view to display tasks for that day.
    public void changeDate(Context context, Calendar calendar){
        mTaskArrayList = mTasksSQLiteOpenHelper.getListTask(context, calendar);
        this.notifyDataSetChanged();
    }

}
