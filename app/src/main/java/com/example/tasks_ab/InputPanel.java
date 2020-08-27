package com.example.tasks_ab;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.tasks_ab.databinding.InputPanelBinding;

import java.util.Calendar;
import java.util.Date;

public class InputPanel extends LinearLayout {

    private Calendar mCalendar;
    private int mPriority;

    public InputPanel(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        layoutInflater.inflate(R.layout.input_panel, this, true);

        final InputPanelBinding binding = InputPanelBinding.inflate(LayoutInflater.from(context));
        binding.getRoot();

        mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());

        mPriority = Task.NO_PRIORITY;

        binding.inputPanelPickDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mCalendar.set(year, month, dayOfMonth);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE)
                );

                datePickerDialog.show();
            }
        });

        binding.inputPanelSetPriorityButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setItems(new CharSequence[]{"LOW_PRIORITY", "MEDIUM_PRIORITY", "HIGH_PRIORITY"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPriority = which + 1;
                    }
                });
                builder.create().show();
            }
        });

        binding.inputPanelArchiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO доделать ибо так не пойдет
                Toast toast = Toast.makeText(context, "В данный момент эта фунукия не раюотает", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    public int getPriority() {
        return mPriority;
    }
}

