package com.example.tasks_ab.ui.calendar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks_ab.Data.TasksSQLiteOpenHelper;
import com.example.tasks_ab.R;
import com.example.tasks_ab.Data.RecyclerAdapter;
import com.example.tasks_ab.Data.Task;
import com.example.tasks_ab.ui.AddDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment {

    private CalendarView mCalendarView;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private FloatingActionButton mActionButton;
    TasksSQLiteOpenHelper mTasksSQLiteOpenHelper;
    ArrayList<Task> mTaskArrayList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        Context context = root.getContext();

        mActionButton = root.findViewById(R.id.calendar_floatingActionButton);
        mCalendarView = root.findViewById(R.id.calendar_calendarView);
        mRecyclerView = root.findViewById(R.id.calendar_recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mTasksSQLiteOpenHelper = new TasksSQLiteOpenHelper(context);

        mRecyclerAdapter = new RecyclerAdapter(mTaskArrayList);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialogFragment addDialogFragment = new AddDialogFragment();
                //TODO сделать красиво: диалоговое акно почти на весь экран
                addDialogFragment.show(getChildFragmentManager(), "add_task");
                mRecyclerAdapter.updateList(mTasksSQLiteOpenHelper.getListTask());
            }
        });

        // TODO setOnDateChangeListener

        return root;
    }

    @Override
    public void onResume() {
        mTaskArrayList = mTasksSQLiteOpenHelper.getListTask();
        mRecyclerAdapter.notifyDataSetChanged();
        super.onResume();
    }
}
