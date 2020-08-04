package com.example.tasks_ab.Data;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks_ab.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public RadioButton mRadioButton;
    public TextView mTextViewName;
    public TextView mTextViewDate;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mRadioButton = itemView.findViewById(R.id.recycler_item_ratio_button);
        mTextViewDate = itemView.findViewById(R.id.recycler_item_text_view_date);
        mTextViewName = itemView.findViewById(R.id.recycler_item_text_view_name);
    }
}
