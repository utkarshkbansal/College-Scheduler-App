package com.example.collegescheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ExamAdapter extends ArrayAdapter<ExamDetails> {

    private List<ExamDetails> examList;
    private OnEditClickListener onEditClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public ExamAdapter(@NonNull Context context, List<ExamDetails> examList) {
        super(context, 0, examList);
        this.examList = examList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_exam, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.textTitle);
        TextView dateTextView = convertView.findViewById(R.id.textDate);
        TextView timeTextView = convertView.findViewById(R.id.textTime);
        ImageView editImageView = convertView.findViewById(R.id.imageViewEdit);
        ImageView deleteImageView = convertView.findViewById(R.id.imageViewDelete);

        TextView locationTextView = convertView.findViewById(R.id.textLocation);
        ExamDetails currentExam = getItem(position);

        if (currentExam != null) {
            titleTextView.setText("Title: " + currentExam.getTitle());
            dateTextView.setText("Date: " + currentExam.getDueDate());
            timeTextView.setText("Time: " + currentExam.getTime());
            locationTextView.setText("Location: " + currentExam.getLocation());
        }

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditClickListener != null) {
                    onEditClickListener.onEditClick(position);
                }
            }
        });

        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(position);
                }
            }
        });

        return convertView;
    }

    public void setExamList(List<ExamDetails> examList) {
        this.clear();
        this.addAll(examList);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        examList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(ExamDetails newItem) {
        examList.add(newItem);
        notifyDataSetChanged();
    }

    public void removeItemByPosition(int position) {
        if (position >= 0 && position < examList.size()) {
            examList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.onEditClickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    public List<ExamDetails> getExamList() {
        return new ArrayList<>(examList);
    }
}
