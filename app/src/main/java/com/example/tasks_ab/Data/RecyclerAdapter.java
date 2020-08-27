package com.example.tasks_ab.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks_ab.R;
import com.example.tasks_ab.Task;
import com.example.tasks_ab.databinding.ItemRecyclerViewBinding;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<Task> mTaskArrayList;
    private TasksSQLiteOpenHelper mTasksSQLiteOpenHelper;
    private Calendar mCalendar;

    public RecyclerAdapter(Context context) {
        mTasksSQLiteOpenHelper = new TasksSQLiteOpenHelper(context);
        mTaskArrayList = mTasksSQLiteOpenHelper.getListTask(context, Calendar.getInstance());
        mCalendar = Calendar.getInstance();
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecyclerViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_recycler_view, null, false);
        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
        holder.mBinding.setTask(mTaskArrayList.get(position));
        holder.mBinding.recyclerItemRatioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(holder.mBinding.getTask().getComplete() != isChecked){
                    int index = mTaskArrayList.indexOf(holder.mBinding.getTask());
                    holder.mBinding.getTask().setComplete(isChecked);
                    changeTask(holder.mBinding.getTask(), index);
                }
            }
        });

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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TasksSQLiteOpenHelper.FORMAT_DMY_PATTERN, Locale.ENGLISH);
            if(task.getTimeShortPattern().equals(simpleDateFormat.format(mCalendar.getTime()))){
                mTaskArrayList.add(0, task);
                this.notifyItemInserted(0);
            }
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
        mCalendar = calendar;
        mTaskArrayList = mTasksSQLiteOpenHelper.getListTask(context, calendar);
        this.notifyDataSetChanged();
    }


    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ItemRecyclerViewBinding mBinding;
        public RecyclerViewHolder(@NonNull ItemRecyclerViewBinding itemRecuclerViewBinding) {
            super(itemRecuclerViewBinding.getRoot());
            mBinding = itemRecuclerViewBinding;
        }
    }
}
