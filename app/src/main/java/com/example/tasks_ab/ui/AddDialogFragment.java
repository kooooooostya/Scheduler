package com.example.tasks_ab.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tasks_ab.Data.InputPanel;
import com.example.tasks_ab.Data.Task;
import com.example.tasks_ab.Data.TasksSQLiteOpenHelper;
import com.example.tasks_ab.R;

public class AddDialogFragment extends DialogFragment {

    private EditText mEditTextName;
    private EditText mEditTextDesk;
    private Button mButtonConfirm;
    private InputPanel mInputPanel;
    TasksSQLiteOpenHelper mTasksSQLiteOpenHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);
        Context context = root.getContext();

        getDialog().setTitle("Add Task");

        mButtonConfirm = root.findViewById(R.id.fragment_add_button_confirm);
        mEditTextName = root.findViewById(R.id.add_fragment_edit_text_name);
        mEditTextDesk = root.findViewById(R.id.add_fragment_edit_text_desk);
        mInputPanel = root.findViewById(R.id.fragment_add_input_panel);

        mTasksSQLiteOpenHelper = new TasksSQLiteOpenHelper(context);

        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditTextName.getText().toString().equals("")){
                    addTask();
                }
            }
        });

        return root;
    }

    private void addTask(){
        Task task = new Task(mEditTextName.getText().toString(),
                mEditTextDesk.getText().toString(),
                mInputPanel.getCalendar());
        mTasksSQLiteOpenHelper.insertTask(task, null);
        // TODO после этого нужно вернуться к предыдущему фрашменту
        this.dismiss();
    }



    @Override
    public void onDestroy() {
        mTasksSQLiteOpenHelper.close();
        super.onDestroy();
    }
}
