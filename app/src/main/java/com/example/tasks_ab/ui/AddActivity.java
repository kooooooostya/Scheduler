package com.example.tasks_ab.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tasks_ab.Data.InputPanel;
import com.example.tasks_ab.Data.Task;
import com.example.tasks_ab.Data.TasksSQLiteOpenHelper;
import com.example.tasks_ab.R;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    public static final String IS_INSERTED = "is_inserted";
    public static final String EXTRA_INSERTED_TASK = "inserted_task";
    private EditText mEditTextName;
    private EditText mEditTextDesk;
    private Button mButtonConfirm;
    private InputPanel mInputPanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mButtonConfirm = findViewById(R.id.add_act_button_confirm);
        mEditTextName = findViewById(R.id.add_act_edit_text_name);
        mEditTextDesk = findViewById(R.id.add_act_edit_text_desk);
        mInputPanel = findViewById(R.id.add_act_input_panel);

        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditTextName.getText().toString().equals("")){
                    addTask();
                }
            }
        });

    }

    private void addTask(){
        //TODO изменение даты
        Task task = new Task(mEditTextName.getText().toString(),
                mEditTextDesk.getText().toString(),
                mInputPanel.getCalendar());

        Intent intent = new Intent();
        //1 - success
        intent.putExtra(IS_INSERTED, 1);
        intent.putExtra(EXTRA_INSERTED_TASK, task);
        setResult(RESULT_OK, intent);
        finish();
        // TODO после этого нужно вернуться к предыдущему фрашменту

    }

    public static Intent newIntent(Context context){
        return new Intent(context, AddActivity.class);
    }
}