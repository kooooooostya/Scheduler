package com.example.tasks_ab.Data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks_ab.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<Task> mTaskArrayList;
// TODO fix bag
    public RecyclerAdapter(ArrayList<Task> taskArrayList) {
        mTaskArrayList = taskArrayList;
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

    public void updateList(ArrayList<Task> taskArrayList){
        mTaskArrayList = taskArrayList;
        super.notifyDataSetChanged();
    }

}
