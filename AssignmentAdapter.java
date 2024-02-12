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

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AssignmentAdapter extends ArrayAdapter<AssignmentDetails> {

    private List<AssignmentDetails> assignmentList;
    private OnEditClickListener onEditClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public AssignmentAdapter(@NonNull Context context, List<AssignmentDetails> assignmentList) {
        super(context, 0, assignmentList);
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_assignment, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.textTitle);
        TextView courseTextView = convertView.findViewById(R.id.textCourse);
        TextView dueDateTextView = convertView.findViewById(R.id.textDueDate);
        ImageView editImageView = convertView.findViewById(R.id.imageViewEdit);
        ImageView deleteImageView = convertView.findViewById(R.id.imageViewDelete);

        AssignmentDetails currentAssignment = getItem(position);

        if (currentAssignment != null) {
            titleTextView.setText("Title: " + currentAssignment.getTitle());
            courseTextView.setText("Course: " + currentAssignment.getCourseName());
            dueDateTextView.setText("Due Date: " + currentAssignment.getDueDate());

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
        }

        return convertView;
    }

    public void setAssignmentList(List<AssignmentDetails> assignmentList) {
        this.clear();
        this.addAll(assignmentList);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        assignmentList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(AssignmentDetails newItem) {
        assignmentList.add(newItem);
        notifyDataSetChanged();
    }

    public void removeItemByPosition(int position) {
        if (position >= 0 && position < assignmentList.size()) {
            assignmentList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.onEditClickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }
}
