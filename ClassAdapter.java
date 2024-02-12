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

public class ClassAdapter extends ArrayAdapter<ClassDetails> {

    private List<ClassDetails> classList;
    private OnEditClickListener onEditClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public ClassAdapter(@NonNull Context context, List<ClassDetails> classList) {
        super(context, 0, classList);
        this.classList = classList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_class, parent, false);
        }

        TextView courseTextView = convertView.findViewById(R.id.textCourse);
        TextView dayTextView = convertView.findViewById(R.id.textDay);
        TextView timeTextView = convertView.findViewById(R.id.textTime);
        TextView instructorTextView = convertView.findViewById(R.id.textInstructor);
        ImageView editImageView = convertView.findViewById(R.id.imageViewEdit);
        ImageView deleteImageView = convertView.findViewById(R.id.imageViewDelete);

        ClassDetails currentClass = getItem(position);

        if (currentClass != null) {
            courseTextView.setText("Course: " + currentClass.getCourseName());
            dayTextView.setText("Day: " + currentClass.getDay());
            timeTextView.setText("Time: " + currentClass.getTime());
            instructorTextView.setText("Instructor: " + currentClass.getInstructor());

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

    public void setClassList(List<ClassDetails> classList) {
        this.clear();
        this.addAll(classList);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        classList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(ClassDetails newItem) {
        classList.add(newItem);
        notifyDataSetChanged();
    }

    public void removeItemByPosition(int position) {
        if (position >= 0 && position < classList.size()) {
            classList.remove(position);
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
