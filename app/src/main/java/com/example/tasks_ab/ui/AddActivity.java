package com.example.tasks_ab.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tasks_ab.Task;
import com.example.tasks_ab.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {

    public static final String IS_INSERTED = "is_inserted";
    public static final String EXTRA_INSERTED_TASK = "inserted_task";

    ActivityAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.addActButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.addActEditTextName.getText().toString().equals("")){
                    addTask();
                }
            }
        });

    }

    private void addTask(){
        Task task = new Task(binding.addActEditTextName.getText().toString(),
                binding.addActEditTextDesk.getText().toString(),
                binding.addActInputPanel.getCalendar(), false, binding.addActInputPanel.getPriority());

        Intent intent = new Intent();
        //1 - success
        intent.putExtra(IS_INSERTED, 1);
        intent.putExtra(EXTRA_INSERTED_TASK, task);
        setResult(RESULT_OK, intent);
        finish();

    }

    public static Intent newIntent(Context context){
        return new Intent(context, AddActivity.class);
    }
}