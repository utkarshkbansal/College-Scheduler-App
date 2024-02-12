package com.example.collegescheduler.ui.gallery;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

import com.example.collegescheduler.R;

import java.util.ArrayList;
import java.util.List;

import com.example.collegescheduler.AssignmentAdapter;
import com.example.collegescheduler.AssignmentDetails;
import com.example.collegescheduler.AssignmentManager;

public class AssignmentFragment extends Fragment {

    private AssignmentViewModel assignmentViewModel;
    private AssignmentAdapter assignmentAdapter;
    private static final int ADD_ASSIGNMENT_REQUEST_CODE = 3;
    private static final int EDIT_ASSIGNMENT_REQUEST_CODE = 4;
    private static final String ASSIGNMENT_DETAILS_KEY = "assignmentDetailsList";

    public AssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);

        ListView assignmentListView = view.findViewById(R.id.assignmentListView);
        Button addButton = view.findViewById(R.id.addAssignmentButton);

        assignmentAdapter = new AssignmentAdapter(getActivity(), new ArrayList<>());
        assignmentListView.setAdapter(assignmentAdapter);

        assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        assignmentViewModel.getAssignmentList().observe(getViewLifecycleOwner(), new Observer<List<AssignmentDetails>>() {
            @Override
            public void onChanged(List<AssignmentDetails> assignments) {
                assignmentAdapter.setAssignmentList(assignments);
                assignmentAdapter.notifyDataSetChanged();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAssignmentDialog();
            }
        });

        assignmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            if (requestCode == ADD_ASSIGNMENT_REQUEST_CODE) {
                if (data != null) {
                    AssignmentDetails newAssignment = data.getParcelableExtra("assignmentDetails");
                    if (newAssignment != null) {
                        assignmentViewModel.addAssignment(newAssignment);
                        Toast.makeText(getActivity(), "Assignment added successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (requestCode == EDIT_ASSIGNMENT_REQUEST_CODE) {
                if (data != null) {
                    AssignmentDetails updatedAssignment = data.getParcelableExtra("assignmentDetails");
                    int position = data.getIntExtra("position", -1);
                    if (updatedAssignment != null && position != -1) {
                        assignmentViewModel.updateAssignment(position, updatedAssignment);
                        Toast.makeText(getActivity(), "Assignment updated successfully", Toast.LENGTH_SHORT).show();
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
                if (position >= 0 && position < assignmentAdapter.getCount()) {
                    Intent intent = new Intent(getActivity(), EditAssignmentActivity.class);
                    intent.putExtra("assignmentDetails", assignmentAdapter.getItem(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, EDIT_ASSIGNMENT_REQUEST_CODE);
                } else {
                    Toast.makeText(getActivity(), "Invalid position for Edit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (position >= 0 && position < assignmentAdapter.getCount()) {
                    assignmentViewModel.removeAssignment(position);
                    Toast.makeText(getActivity(), "Assignment deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Invalid position for Delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.show();
    }

    private void showAddAssignmentDialog() {
        Intent intent = new Intent(getActivity(), EditAssignmentActivity.class);
        startActivityForResult(intent, ADD_ASSIGNMENT_REQUEST_CODE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(ASSIGNMENT_DETAILS_KEY, new ArrayList<>(AssignmentManager.getAssignmentList()));
        super.onSaveInstanceState(outState);
    }
}
