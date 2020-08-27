package com.example.tasks_ab.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tasks_ab.Data.RecyclerAdapter;
import com.example.tasks_ab.Task;
import com.example.tasks_ab.R;
import com.example.tasks_ab.databinding.FragmentCalendarBinding;
import com.example.tasks_ab.ui.AddActivity;

import java.util.Calendar;

public class CalendarFragment extends Fragment {

    private final int REQUEST_CODE = 1;
    private RecyclerAdapter mRecyclerAdapter;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentCalendarBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_calendar, container, false);

        binding.calendarRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerAdapter = new RecyclerAdapter(getActivity());
        binding.calendarRecyclerView.setAdapter(mRecyclerAdapter);

        //starts AddActivity to get new task
        binding.calendarFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivityForResult( AddActivity.newIntent(getActivity()), REQUEST_CODE);
            }
        });

        // TODO setOnDateChangeListener
        binding.calendarCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                mRecyclerAdapter.changeDate(getContext(), calendar);
            }
        });

        return binding.getRoot();
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
