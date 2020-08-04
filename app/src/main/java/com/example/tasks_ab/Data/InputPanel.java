package com.example.tasks_ab.Data;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tasks_ab.R;

import java.util.Calendar;
import java.util.Date;

public class InputPanel extends LinearLayout implements DatePickerDialog.OnDateSetListener {
    private ImageButton mButtonPickDate;
    private ImageButton mButtonSetPriority;
    private ImageButton mButtonArchive;

    public final int NO_PRIORITY = 0;
    public final int LOW_PRIORITY = 1;
    public final int MEDIUM_PRIORITY = 2;
    public final int HIGH_PRIORITY = 3;

    private Calendar mCalendar;
    private int mPriority;



    public InputPanel(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.input_panel, this);

        mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());

        mPriority = NO_PRIORITY;

        mButtonPickDate = findViewById(R.id.input_panel_pick_date_button);
        mButtonSetPriority = findViewById(R.id.input_panel_set_priority_button);
        mButtonArchive = findViewById(R.id.input_panel_archive_button);

        mButtonPickDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, null,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE)
                );
                datePickerDialog.show();
            }
        });

        mButtonSetPriority.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setItems(new CharSequence[]{"LOW_PRIORITY", "MEDIUM_PRIORITY", "HIGH_PRIORITY"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPriority = which + 1;
                    }
                });
            }
        });

        mButtonArchive.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO доделать ибо так не пойдет
                Toast toast = Toast.makeText(context, "В данный момент эта фунукия не раюотает", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCalendar.set(year, month, dayOfMonth);
    }

    public Calendar getCalendar() {
        return mCalendar;
    }
}
