package com.example.collegescheduler.ui.exams;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegescheduler.R;
import com.example.collegescheduler.ExamAdapter;
import com.example.collegescheduler.ExamDetails;

import java.util.ArrayList;
import java.util.List;

public class ExamFragment extends Fragment {

    private ExamViewModel examViewModel;
    private ExamAdapter examAdapter;
    private static final int ADD_EXAM_REQUEST_CODE = 5;
    private static final int EDIT_EXAM_REQUEST_CODE = 6;
    private static final String EXAM_DETAILS_KEY = "examDetailsList";

    public ExamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam, container, false);

        ListView examListView = view.findViewById(R.id.examListView);
        Button addButton = view.findViewById(R.id.addExamButton);

        examAdapter = new ExamAdapter(getActivity(), new ArrayList<>());
        examListView.setAdapter(examAdapter);

        examViewModel = new ViewModelProvider(this).get(ExamViewModel.class);
        examViewModel.getExamList().observe(getViewLifecycleOwner(), new Observer<List<ExamDetails>>() {
            @Override
            public void onChanged(List<ExamDetails> exams) {
                examAdapter.setExamList(exams);
                examAdapter.notifyDataSetChanged();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddExamDialog();
            }
        });

        examListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDeleteDialog(position);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_EXAM_REQUEST_CODE) {
                if (data != null) {
                    ExamDetails newExam = data.getParcelableExtra("examDetails");
                    if (newExam != null) {
                        examViewModel.addExam(newExam);
                        Toast.makeText(getActivity(), "Exam added successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (requestCode == EDIT_EXAM_REQUEST_CODE) {
                if (data != null) {
                    ExamDetails updatedExam = data.getParcelableExtra("examDetails");
                    int position = data.getIntExtra("position", -1);
                    if (updatedExam != null && position != -1) {
                        examViewModel.updateExam(position, updatedExam);
                        Toast.makeText(getActivity(), "Exam updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void showEditDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit or Delete");

        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (position >= 0 && position < examAdapter.getCount()) {
                    Intent intent = new Intent(getActivity(), EditExamActivity.class);
                    intent.putExtra("examDetails", examAdapter.getItem(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, EDIT_EXAM_REQUEST_CODE);
                } else {
                    Toast.makeText(getActivity(), "Invalid position for Edit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (position >= 0 && position < examAdapter.getCount()) {
                    examViewModel.removeExam(position);
                    Toast.makeText(getActivity(), "Exam deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Invalid position for Delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.show();
    }

    private void showAddExamDialog() {
        Intent intent = new Intent(getActivity(), EditExamActivity.class);
        startActivityForResult(intent, ADD_EXAM_REQUEST_CODE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(EXAM_DETAILS_KEY, new ArrayList<>(examAdapter.getExamList()));
        super.onSaveInstanceState(outState);
    }
}
