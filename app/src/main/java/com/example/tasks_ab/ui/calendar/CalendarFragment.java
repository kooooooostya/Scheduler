package com.example.tasks_ab.ui.calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks_ab.Data.TasksSQLiteOpenHelper;
import com.example.tasks_ab.R;
import com.example.tasks_ab.Data.RecyclerAdapter;
import com.example.tasks_ab.Data.Task;
import com.example.tasks_ab.ui.AddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment extends Fragment {

    //TODO  перейти на MVVM
    private CalendarView mCalendarView;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private FloatingActionButton mActionButton;
    private final int REQUEST_CODE = 1;
    public static final String EXTRA_ARRAY_LIST = "extra_list";

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        final Context context = root.getContext();

        mActionButton = root.findViewById(R.id.calendar_floatingActionButton);
        mCalendarView = root.findViewById(R.id.calendar_calendarView);
        mRecyclerView = root.findViewById(R.id.calendar_recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mRecyclerAdapter = new RecyclerAdapter(context);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        //starts AddActivity to get new task
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivityForResult( AddActivity.newIntent(context), REQUEST_CODE);
            }
        });

        // TODO setOnDateChangeListener
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                mRecyclerAdapter.changeDate(root.getContext(), calendar);
            }
        });

        return root;
    }

    //takes a new task and inserts this task in db
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data != null && data.getIntExtra(AddActivity.IS_INSERTED, 0) == 1){
            Task insertedTask = data.getParcelableExtra(AddActivity.EXTRA_INSERTED_TASK);
            mRecyclerAdapter.insertTask(insertedTask);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
